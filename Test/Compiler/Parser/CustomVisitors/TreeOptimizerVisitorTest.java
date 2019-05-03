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
    void visit1() throws Exception{
        String path = "Test/Compiler/Parser/CustomVisitors/TestFiles/TreeOptimizerTest";
        System.out.println(TestParser.parseTextFile(path));
    }

    @Disabled
    @Test
    void visit2() throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/TestFiles/TreeOptimizerTest";
        Node root = TestParser.useVisitorMethod(treeOptimizerVisitor, path, childNumberIndex);
        System.out.println(TreePrinter.createDotOutput(root));
    }

    @Disabled
    @Test
    void visit3() throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/TestFiles/TreeOptimizerTest";
        System.out.println("Before:");
        Node root = TestParser.useVisitorMethod(new PrintVisitor(), path, null);

        root = TestParser.useVisitorMethod(treeOptimizerVisitor, path, childNumberIndex);

        System.out.println("\n\nAfter:");
        root.jjtAccept(new PrintVisitor(), null);
    }

}