package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.Node;
import Compiler.Parser.GeneratedFiles.TestParser;
import Compiler.Parser.TreePrinter;
import Compiler.SymbolTable.Table.SymbolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeOptimizerVisitorTest {
    private int childNumberIndex;
    private TreeOptimizerVisitor treeOptimizerVisitor;


    @BeforeEach
    void beforeEach() throws Exception {
        childNumberIndex = 0;

        treeOptimizerVisitor = new TreeOptimizerVisitor();
    }

    @Test
    void visit1() throws Exception{
        String path = "Test/Compiler/Parser/CustomVisitors/TestFiles/Test2";
        System.out.println(TestParser.parseTextFile(path));
    }

    @Test
    void visit2() throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/TestFiles/Test2";
        System.out.println(TestParser.parseTextFile(path));
        Node root = TestParser.useVisitorMethod(treeOptimizerVisitor, path, childNumberIndex);
        System.out.println(TreePrinter.createDotOutput(root));
    }
}