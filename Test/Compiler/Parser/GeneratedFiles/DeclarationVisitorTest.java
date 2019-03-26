package Compiler.Parser.GeneratedFiles;

import Compiler.Parser.whatever;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

class DeclarationVisitorTest {
    private Node root;
    private DeclarationVisitor visitor;

    @BeforeEach
    void beforeEach() throws Exception {
        String path = "Test/Compiler/Parser/GeneratedFiles/TestFile";
        File file = new File(path);
        TestParser parser = new TestParser(new BufferedReader(new FileReader(file)));

        root = parser.start();
        visitor = new DeclarationVisitor();
    }

    @Test
    void createSymbolTable() {
        visitor.createSymbolTable(root);
    }

    @Test
    void wrongRootNodeType() {

    }
}