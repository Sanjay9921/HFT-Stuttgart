package pcl_subroutines;

import java.io.File;
import java.io.FileWriter;

public class PCLSubRoutines {
    String outputFileName;
    FileWriter fileWriter;

    public PCLSubRoutines(FileWriter fileWriter){
        writeImports(fileWriter);
        writeDataTypes(fileWriter);
        writeScientific(fileWriter);
    }

    public void writeImports(FileWriter fileWriter){
        try{
            fileWriter.write("#include <stdio.h>\n");
            fileWriter.write("#include <stdlib.h>\n");
            fileWriter.write("#include <string.h>\n");
            fileWriter.write("#include <math.h>\n");
            fileWriter.write("#define ARRAY_LENGTH(array) (sizeof(array) / sizeof((array)[0]))\n\n");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeScientific(FileWriter fileWriter){
        try {
            fileWriter.write("double sqrt_function(double x) { return sqrt(x); }\n");
            fileWriter.write("double exp_function(double x) { return exp(x); }\n\n");

            fileWriter.write("double sin_function(double x) { return sin(x); }\n");
            fileWriter.write("double cos_function(double x) { return cos(x); }\n");
            fileWriter.write("double tan_function(double x) { return tan(x); }\n");
            fileWriter.write("double log_function(double x) { return log(x); }\n\n");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeDataTypes(FileWriter fileWriter){
        try {
            fileWriter.write("double int_to_double(int value) { return (double)value; }\n");
            fileWriter.write("char* int_to_str(int value) { char* buffer = malloc(12); snprintf(buffer, 12, \"%d\", value); return buffer; }\n");
            fileWriter.write("int str_to_int(const char* str) { return atoi(str); }\n");
            fileWriter.write("double str_to_double(const char* str) { return atof(str); }\n");
            fileWriter.write("int double_to_int(double value) { return (int)value; }\n");
            fileWriter.write("char* double_to_str(double value) { char* buffer = malloc(32); snprintf(buffer, 32, \"%f\", value); return buffer; }\n\n");

            fileWriter.write("char* read_line() { size_t size = 1024; char* buffer = malloc(size); if (fgets(buffer, size, stdin) != NULL) { buffer[strcspn(buffer, \"\\n\")] = '\\0'; return buffer; } free(buffer); return NULL; }\n");
            fileWriter.write("char* str_concat(const char* str1, const char* str2) { char* result = malloc(strlen(str1) + strlen(str2) + 1); strcpy(result, str1); strcat(result, str2); return result; }\n\n");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
