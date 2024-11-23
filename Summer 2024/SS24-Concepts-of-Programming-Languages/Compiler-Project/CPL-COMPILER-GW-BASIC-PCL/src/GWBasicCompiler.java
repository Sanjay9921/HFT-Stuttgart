// Import Built in packages

// Files
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

// In Built Data Structures
import java.util.Map;
import java.util.ArrayList;

// Import Custom Packages
import data_structures.CustomDataStructures;
import environment_variables.EnvVar;
import pcl_subroutines.PCLSubRoutines;

enum VariableType {
    INT,
    STRING,
    DOUBLE,
}

public class GWBasicCompiler {
    CustomDataStructures cds = new CustomDataStructures();

    public GWBasicCompiler(String filename) {
        File myObj = new File(filename);

        try {
            myObj.createNewFile();
        } catch (IOException e) {
            System.err.println("SYSTEM ERROR!");
            e.printStackTrace();
        }
    }

    // PRINT STATEMENT
    String stmtPRINT(SimpleNode stmtPRINT) {
        String tokenStr = "printf(\"\\t%s\\n\", " + generatePCL((SimpleNode) stmtPRINT.jjtGetChild(0)) + ")";
        return tokenStr;
    }

    // ARRAY
    static String ArrayDeclaration(int size) {
        return "[" + size + "]";
    }

    public void writePCL() {
        try {
            EnvVar envVar = new EnvVar();
            String output_file_name = envVar.getOutputFileName();
            FileWriter myWriter = new FileWriter(output_file_name);

            // Subroutines
            PCLSubRoutines pclSubRoutines = new PCLSubRoutines(myWriter);

            for (String variable : cds.var_global.values()) {
                myWriter.write(variable + "\n");
            }
            myWriter.write("\n");

            for (Map.Entry<Integer, ArrayList<String>> entry : cds.functions.entrySet()) {
                myWriter.write("void subroutine_functions" + entry.getKey() + "() {\n");
                for (String line : entry.getValue()) {
                    myWriter.write("    " + line + "\n");
                }
                myWriter.write("}\n\n");
            }
            for (String line : cds.pcl) {
                myWriter.write(line + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.err.println("SYSTEM ERROR!");
            e.printStackTrace();
        }
    }

    String generatePCL(SimpleNode node) {

        int n;
        String tokenStr = "";

        switch (node.toString()) {
            // Program (break) -> Line -> Statement -> Set of statements -> Term -> Factor -> Base -> Number
            // DO NOT CHANGE THE SWITCH CASE -> affects the AST

            case "Program":
                cds.pcl.add("int main(argc, argv)" + "\nint argc;\n" + "char** argv[];\n" + "{");
                n = node.jjtGetNumChildren();
                for (int i = 0; i < n; i++) {
                    String line = generatePCL((SimpleNode) node.jjtGetChild(i));
                    if (cds.functionCall != null && !cds.functionCall.isEmpty()) {
                        cds.functions.get(cds.functionCall.get(0)).add(line);
                    } else {
                        cds.pcl.add("    " + line);
                    }
                }
                cds.pcl.add("}");
                break;
            case "Line":
                n = Integer.parseInt(node.jjtGetValue().toString());
                cds.pcl.add("label_" + n + "_start:");
                if (cds.losGOTO.contains(n)) {
                    cds.pcl.add("line_" + n + ":");
                }
                if (cds.functions.containsKey(n)) {
                    cds.functionCall.add(n);
                }
                return generatePCL((SimpleNode) node.jjtGetChild(0)) + ";";
            case "Statement":
                return generatePCL((SimpleNode) node.jjtGetChild(0));
            case "stmtINPUT":
                VariableType variableType = getJJTChildType(node);
                return generatePCL((SimpleNode) node.jjtGetChild(0)) + " = read_line()";
            case "stmtPRINT":
                return stmtPRINT(node);
            case "PrintItem":
                return "//" + generatePCL((SimpleNode) node.jjtGetChild(0));
            case "Expression":
                if (node.jjtGetValue() != null) {
                    return generatePCL((SimpleNode) node.jjtGetChild(0)) + node.jjtGetValue().toString() + generatePCL((SimpleNode) node.jjtGetChild(1));
                } else {
                    return generatePCL((SimpleNode) node.jjtGetChild(0));
                }
            case "Term":
                if (node.jjtGetValue() != null) {
                    return generatePCL((SimpleNode) node.jjtGetChild(0)) + node.jjtGetValue().toString() + generatePCL((SimpleNode) node.jjtGetChild(1));
                } else {
                    return generatePCL((SimpleNode) node.jjtGetChild(0));
                }
            case "Factor":
                if (node.jjtGetValue() != null) {
                    return node.jjtGetValue().toString() + generatePCL((SimpleNode) node.jjtGetChild(0));
                } else {
                    return generatePCL((SimpleNode) node.jjtGetChild(0));
                }
            case "Base":
                return generatePCL((SimpleNode) node.jjtGetChild(0));
            case "Number":
                return node.jjtGetValue().toString();
            case "String":
                return node.jjtGetValue().toString();
            case "ComparisonOperator":
                switch (node.jjtGetValue().toString()) {

                    // DO NOT CHANGE THE SWITCH CASE
                    case "=":
                        return "==";
                    case "<>":
                        return "!=";
                    default:
                        return node.jjtGetValue().toString();
                }
            case "Variable":
                if (!cds.var_global.containsKey(getJJTVariableExtension(node))) {
                    char charAtTheEnd = node.jjtGetValue().toString().charAt(node.jjtGetValue().toString().length() - 1);

                    // SWITCH STATEMENT AS PER THE ENUM

                    cds.var_global.put(getJJTVariableExtension(node),
                            switch(charAtTheEnd) {
                                case '%' -> "int " + node.jjtGetValue().toString().substring(0, node.jjtGetValue().toString().length() - 1)  + "_INTEGER";
                                case '$' -> "char *" + node.jjtGetValue().toString().substring(0, node.jjtGetValue().toString().length() - 1) + "_STRING";
                                case '#' -> "double " + node.jjtGetValue().toString().substring(0, node.jjtGetValue().toString().length() - 1) + "_DOUBLE";
                                default -> null;
                            } + ";"
                    );
                }
                return getJJTVariableExtension(node);
            case "Assignment":
                String variableName = generatePCL((SimpleNode) node.jjtGetChild(0)).split("\\[") != null ? generatePCL((SimpleNode) node.jjtGetChild(0)).split("\\[")[0] : generatePCL((SimpleNode) node.jjtGetChild(0));
                if (cds.var_global.containsKey(variableName))
                    return  generatePCL((SimpleNode) node.jjtGetChild(0)) + " = " + generatePCL((SimpleNode) node.jjtGetChild(1));
                variableType = getJJTChildType(node);
                System.err.println("Variable type: " + variableType + " Variable: " + variableName);

                // SWITCH STATEMENT AS PER THE ENUM

                switch (variableType) {
                    case INT:
                        return  "int " + generatePCL((SimpleNode) node.jjtGetChild(0)) + " = " + generatePCL((SimpleNode) node.jjtGetChild(1));
                    case STRING:
                        return  "char *" + generatePCL((SimpleNode) node.jjtGetChild(0)) + " = " + generatePCL((SimpleNode) node.jjtGetChild(1));
                    case DOUBLE:
                        return  "double " + generatePCL((SimpleNode) node.jjtGetChild(0)) + " = " + generatePCL((SimpleNode) node.jjtGetChild(1));
                }
                break;
            case "VariableAccess":
                return generatePCL((SimpleNode) node.jjtGetChild(0));
            case "stmtDIM":
                tokenStr = "";
                if (!cds.var_global.containsKey(generatePCL((SimpleNode) node.jjtGetChild(0)))) {
                    variableType = getJJTChildType((SimpleNode) node.jjtGetChild(0));

                    // SWITCH STATEMENT AS PER THE ENUM

                    switch (variableType) {
                        case INT:
                            tokenStr = "int " + generatePCL((SimpleNode) node.jjtGetChild(0));
                            break;
                        case STRING:
                            tokenStr =  "char *" + generatePCL((SimpleNode) node.jjtGetChild(0));
                            break;
                        case DOUBLE:
                            tokenStr =  "double " + generatePCL((SimpleNode) node.jjtGetChild(0));
                            break;
                    }
                    tokenStr += ";";
                    cds.var_global.put(getJJTVariableExtension(node), tokenStr);
                }
                return "";
            case "ArrayCall":
                return generatePCL((SimpleNode) node.jjtGetChild(0)) + "[" + generatePCL((SimpleNode) node.jjtGetChild(1)) + "]";
            case "stmtSTOP":
                return "exit(0)";
            case "stmtGOTO":
                n = Integer.parseInt(generatePCL((SimpleNode) node.jjtGetChild(0)));
                cds.losGOTO.add(n);
                return "goto " + "line_" + n;
            case "stmtFUNCTION":
                String whichFunction = "";
                n = node.jjtGetNumChildren();

                switch(node.jjtGetValue().toString()){
                    // Array Length defined
                    case "LENGTH" :
                        whichFunction = "ARRAY_LENGTH";

                        // Data Type conversion
                    case "IDBL" :
                        whichFunction = "int_to_double";
                        break;
                    case "ITOS" :
                        whichFunction = "int_to_str";
                        break;
                    case "ISTR" :
                        whichFunction = "int_to_str";
                        break;
                    case "STOI" :
                        whichFunction = "str_to_int";
                        break;
                    case "SINT" :
                        whichFunction = "str_to_int";
                        break;
                    case "SDBL" :
                        whichFunction = "str_to_double";
                        break;
                    case "DINT" :
                        whichFunction = "double_to_int";
                        break;
                    case "DSTR" :
                        whichFunction = "double_to_str";
                        break;

                    // String Operations
                    case "CONCAT" :
                        whichFunction = "str_concat";
                        break;

                    // Statistical Functions
                    case "EXP" :
                        whichFunction = "exp_function";
                        break;
                    case "SQRT" :
                        whichFunction = "sqrt_function";
                        break;

                    // Trigonometric Functions
                    case "SIN" :
                        whichFunction = "sin_function";
                        break;
                    case "COS" :
                        whichFunction = "cos_function";
                        break;
                    case "TAN" :
                        whichFunction = "tan_function";
                        break;
                    case "LOG" :
                        whichFunction = "log_function";
                        break;

                    // Default Case
                    default:
                        whichFunction = node.jjtGetValue().toString();
                        break;
                };

                tokenStr = whichFunction + "(" ;
                for (int i = 0; i < n; i++) {
                    tokenStr += generatePCL((SimpleNode) node.jjtGetChild(i));
                    if (i != n - 1) {
                        tokenStr += ", ";
                    }
                }
                tokenStr += ")";
                return tokenStr;
            case "stmtIF":
                return "if (" + generatePCL((SimpleNode) node.jjtGetChild(0)) + ") { " + generatePCL((SimpleNode) node.jjtGetChild(1)) + "; }" + (node.jjtGetNumChildren() > 2 ? " else { " + generatePCL((SimpleNode) node.jjtGetChild(2)) + "; }" : "");
            case "Condition":
                tokenStr = "";
                for (int i = 0; i < node.jjtGetNumChildren(); i++) {
                    tokenStr += generatePCL((SimpleNode) node.jjtGetChild(i));
                }
                return tokenStr;
            case "stmtFOR":
                String variable = getJJTChildVar((SimpleNode) node.jjtGetChild(0));
                return "for (" + generatePCL((SimpleNode) node.jjtGetChild(0)) + "; " + variable + "!=" + generatePCL((SimpleNode) node.jjtGetChild(1)) + "; " + variable + "+=" + generatePCL((SimpleNode) node.jjtGetChild(2)) + ") { ";
            case "stmtNEXT":
                return "}";
            case "stmtWHILE":
                return "while (" + generatePCL((SimpleNode) node.jjtGetChild(0)) + ") { ";
            case "stmtWEND":
                return "}";
            case "stmtGOSUB":
                cds.functions.put(Integer.parseInt(generatePCL((SimpleNode) node.jjtGetChild(0))), new ArrayList<String>());
                return "subroutine_functions" + generatePCL((SimpleNode) node.jjtGetChild(0)) + "()";
            case "stmtRETURN":
                cds.functions.get(cds.functionCall.get(0)).add("return;");
                cds.functionCall.remove(0);
                return "";
            default:
                return "/* " + node.toString() + " */";
        }
        return null;
    }


    static String getJJTChildVar(SimpleNode node) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            if (node.jjtGetChild(i).toString().equals("Variable")) {
                return getJJTVariableExtension(node);
            }
            else {
                return getJJTChildVar((SimpleNode) node.jjtGetChild(i));
            }
        }
        return null;
    }

    static String getJJTVariableExtension(SimpleNode node) {
        if (node.toString().equals("Variable")) {
            char charAtTheEnd = node.jjtGetValue().toString().charAt(node.jjtGetValue().toString().length() - 1);

            // SWITCH STATEMENT AS PER THE ENUM
            switch(charAtTheEnd) {
                case '%':
                    return node.jjtGetValue().toString().substring(0, node.jjtGetValue().toString().length() - 1) + "_INTEGER";
                case '#':
                    return node.jjtGetValue().toString().substring(0, node.jjtGetValue().toString().length() - 1) + "_DOUBLE";
                case '$':
                    return node.jjtGetValue().toString().substring(0, node.jjtGetValue().toString().length() - 1) + "_STRING";
            }
        }
        return getJJTVariableExtension((SimpleNode) node.jjtGetChild(0));
    }

    static VariableType getJJTChildType(SimpleNode node) {
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            if (node.jjtGetChild(i).toString().equals("Variable")) {
                SimpleNode child = (SimpleNode) node.jjtGetChild(i);
                char charAtTheEnd = child.jjtGetValue().toString().charAt(child.jjtGetValue().toString().length() - 1);

                // SWITCH STATEMENT AS PER THE ENUM
                switch(charAtTheEnd) {
                    case '%':
                        return VariableType.INT;
                    case '$':
                        return VariableType.STRING;
                    case '#':
                        return VariableType.DOUBLE;
                }
            }
            else {
                return getJJTChildType((SimpleNode) node.jjtGetChild(i));
            }
        }
        return null;
    }
}

