package Compiler.Parser;

import Compiler.FileReader.FileOperations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CCC {

    public static void main(String[] args) {
        try {
            Path javaCC = Paths.get(FileOperations.PARSER_DIR_PATH + "/javacc.jar");
            Path testParserJJT = Paths.get(FileOperations.PARSER_DIR_PATH + "/testParser.jjt");
            Path testParserJJ = Paths.get(FileOperations.PROJECT_DIRECTORY + "/src/testParser.jj");
            execCmd("java -cp " + javaCC + " jjtree " + testParserJJT);
            execCmd("java -cp " + javaCC + " javacc " + testParserJJ);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void execCmd(String cmd) throws Exception {
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(cmd);
        proc.waitFor();
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        List<String> outputLines = stdInput.lines().collect(Collectors.toList());
        List<String> errorLines = stdError.lines().collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();

        if (outputLines.isEmpty()) {
            for (String line : errorLines) {
                sb.append(line);
                sb.append("\n");
            }
        }
        else {
            for (String line : outputLines) {
                sb.append(line);
                sb.append("\n");
            }
        }

        System.out.println(sb.toString());
    }
}
