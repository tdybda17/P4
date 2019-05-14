package Compiler.FileOperations;

import Compiler.Parser.CustomVisitors.CodeGenerationVisitor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileWriter {
    private final static String VERTEX_PLACEHOLDER = "insert vertex attributes here";
    private final static String EDGE_PLACEHOLDER = "insert edge attributes here";
    private final static String MAIN_PLACEHOLDER = "insert main here";
    private final static String FUNCTIONS_PLACEHOLDER = "insert extra functions here";

    public static void createCodeFile(CodeGenerationVisitor visitor) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/Classes.java"))) {
            reader.lines().forEach(e -> {{
                if (e.contains(VERTEX_PLACEHOLDER))
                    lines.addAll(visitor.getVertexAttributes());
                else if (e.contains(EDGE_PLACEHOLDER))
                    lines.addAll(visitor.getEdgeAttributes());
                else if (e.contains(MAIN_PLACEHOLDER))
                    lines.add(visitor.getMain());
                else if (e.contains(FUNCTIONS_PLACEHOLDER))
                    lines.addAll(visitor.getFuncs());
                else if (e.contains("/*") || e.contains("*/"))
                    lines.add("\n");
                else
                    lines.add(e);
            }});
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("src/GeneratedCode.java"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
