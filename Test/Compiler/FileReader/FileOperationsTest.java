package Compiler.FileReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


class FileOperationsTest {

    private Path directory;

    @Disabled
    void testGetFilesOfDirectory01() {
        directory = Paths.get("/Users/toby/Programs/P4/Test/ProgramTestCases/SyntaxError");
        try {
            List<Path> paths = FileOperations.getPathsInDirectory(directory);
            for (Path path : paths)
                System.out.println(path.toString());
        } catch (IOException e) {

        }
    }

}