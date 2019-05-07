package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.Node;
import Compiler.Parser.GeneratedFiles.TestParser;
import Compiler.Parser.TreePrinter;
import Compiler.SymbolTable.Table.SymbolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

    @Disabled
    @Test
    void printASTBeforeOptimization() throws Exception{
        String path = "Test/Compiler/Parser/CustomVisitors/TestFiles/TreeOptimizerTest2";
        System.out.println(TestParser.parseTextFile(path));
    }

    @Disabled
    @Test
    void printASTAfterOptimization() throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/TestFiles/TreeOptimizerTest2";
        Node root = TestParser.createParseTree(path);
        root.jjtAccept(treeOptimizerVisitor, childNumberIndex);
        System.out.println(TreePrinter.createDotOutput(root));
    }

    @Disabled
    @Test
    void printCodeRepresentationBeforeAndAfter() throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/TestFiles/TreeOptimizerTest";
        System.out.println("Before:");
        Node root = TestParser.createParseTree(path);
        root.jjtAccept(new PrintVisitor(), null);

        root.jjtAccept(treeOptimizerVisitor, childNumberIndex);

        System.out.println("\n\nAfter:");
        root.jjtAccept(new PrintVisitor(), null);
    }

}