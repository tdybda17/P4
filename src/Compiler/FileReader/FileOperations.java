package Compiler.FileReader;

import Compiler.Exceptions.NotADirectoryException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileOperations {

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
