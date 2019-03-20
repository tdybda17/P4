package Compiler.FileReader;


import Compiler.Exceptions.NotADirectoryException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileOperations {
    public static final Path PROJECT_DIRECTORY = Paths.get(System.getProperty("user.dir"));
    public static final Path SRC_DIRECTORY = Paths.get(System.getProperty("user.dir") + "/src");
    public static final Path PARSER_DIR_PATH = Paths.get(System.getProperty("user.dir") + "/src/Compiler/Parser");
    public static final Path GENERATED_FILES_PATH = Paths.get(System.getProperty("user.dir") + "/src/Compiler/Parser/GeneratedFiles");

    public static List<Path> getPathsInDirectory(final Path dirPath) throws IOException {
        List<Path> filePaths = new ArrayList<>();
        if(isDirectory(dirPath)) {
            Stream<Path> pathStream = Files.walk(dirPath)
                    .filter(Files::isRegularFile);

            pathStream.forEach(e -> filePaths.add(e));
        } else {
            throw new NotADirectoryException();
        }
        return filePaths;
    }

    public static boolean isDirectory(final Path dirPath) {
        return Files.isDirectory(dirPath);
    }


}
