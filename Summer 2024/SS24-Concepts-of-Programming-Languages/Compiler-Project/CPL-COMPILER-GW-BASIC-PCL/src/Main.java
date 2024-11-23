// Import default packages
import environment_variables.EnvVar;

import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }

        try {
            FileInputStream in = new FileInputStream(args[0]);
            GWBasicToPCLParser parser = new GWBasicToPCLParser(in);
            SimpleNode root = parser.Program();
            GWBasicToPCLParser.dumpAST(root, 0);

            EnvVar envVar = new EnvVar();
            String output_file_name = envVar.getOutputFileName();
            System.out.println("The compiler has compiled the <" + output_file_name + "> successfully!");

            GWBasicCompiler compiler = new GWBasicCompiler(output_file_name);
            compiler.generatePCL(root);
            compiler.writePCL();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}