package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.Node;
import Compiler.Parser.GeneratedFiles.ParseException;
import Compiler.Parser.GeneratedFiles.TestParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CombinedVisitorTest {

    private Node createAST(String fileName) throws Exception {
        String path = "Test/ProgramTestCases/LegalSyntax/" + fileName;
        return TestParser.createParseTree(path);
    }

    @Test
    void visitArithExpr() throws Exception {
        Node root = createAST("ArithExprTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitCommentTest() throws Exception {
        Node root = createAST("CommentTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitDecInitTest() throws Exception {
        Node root = createAST("DecInitTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitForLoopsTest() throws Exception {
        Node root = createAST("ForLoopsTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitFunctionCallTest() throws Exception {
        Node root = createAST("FunctionCallTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitGraphDclTest() throws Exception {
        Node root = createAST("GraphDclTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitIfElseTest() throws Exception {
        Node root = createAST("IfElseTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitMethodForEachTest() throws Exception {
        Node root = createAST("MethodForEachTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitMinQueueTest() throws Exception {
        Node root = createAST("MinQueueTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitQueueTest() throws Exception {
        Node root = createAST("QueueTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitSetTest() throws Exception {
        Node root = createAST("SetTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitVertexDclTest() throws Exception {
        Node root = createAST("VertexDclTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitWGraphDclTest() throws Exception {
        Node root = createAST("WGraphDclTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

    @Test
    void visitWhileLoopsTest() throws Exception {
        Node root = createAST("WhileLoopsTest");
        assertDoesNotThrow(() -> CombinedVisitor.visit(root));
    }

}