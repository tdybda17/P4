package Compiler.CodeGeneration.PngFileGenerator;

import Compiler.Parser.CCC;

import java.nio.file.Paths;

public class PngFileGenerator {
    public static final String PNG_FILES_DIR = "src/Compiler/CodeGeneration/PngFileGenerator/PngFiles/";

    public static void createPngFromDot(String inputPath, String outputPath) throws Exception {
        String[] cmd = {"dot", "-Tpng", "\"" + getAbsolutePath(inputPath) + "\"", "-o", "\"" + getAbsolutePath(outputPath) + "\""};
        Runtime.getRuntime().exec(cmd);
    }

    private static String getAbsolutePath(String projectPath) {
        return Paths.get(System.getProperty("user.dir") + "/" + projectPath).toString();
    }

}
