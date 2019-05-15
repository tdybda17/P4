package Compiler.CodeGeneration.DotFileGenerator;

import Compiler.CodeGeneration.PngFileGenerator.PngFileGenerator;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DotFileGenerator {
    public static final String DOT_FILES_DIR = "src/Compiler/CodeGeneration/DotFiles/";

    public static void createDotFile(String dotString, int printIndex) {
        String filename = "dotOutput" + printIndex;
        Path path = Paths.get(DOT_FILES_DIR + filename + ".dot");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(dotString);
            PngFileGenerator.createPngFromDot(path.toString(), PngFileGenerator.PNG_FILES_DIR + filename + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
