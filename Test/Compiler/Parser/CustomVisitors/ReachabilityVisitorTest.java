package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.Visitor.ReachabilityExceptions.MissingReturnStatementException;
import Compiler.Exceptions.Visitor.ReachabilityExceptions.UnreachableCodeException;
import Compiler.Parser.GeneratedFiles.Node;
import Compiler.Parser.GeneratedFiles.TestParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReachabilityVisitorTest {
    private ReachabilityVisitor visitor;

    @BeforeEach
    private void beforeEach() {
        visitor = new ReachabilityVisitor();
    }


    private Node createAST(String fileName) throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/ReachabilityVisitorFiles/" + fileName;
        Node root = TestParser.createParseTree(path);
        root.jjtAccept(new TreeOptimizerVisitor(), null);
        return root;
    }

    @Test
    void testLegalVoidNoReturn() throws Exception {
        Node root = createAST("LegalVoidNoReturn");
        assertDoesNotThrow(() -> root.jjtAccept(visitor, null));
    }


    @Test
    void testLegalIfElseReturn1() throws Exception {
        Node root = createAST("LegalIfElseReturn1");
        assertDoesNotThrow(() -> root.jjtAccept(visitor, null));
    }

    @Test
    void testLegalIfElseReturn2() throws Exception {
        Node root = createAST("LegalIfElseReturn2");
        assertDoesNotThrow(() -> root.jjtAccept(visitor, null));
    }


    @Test
    void  testUnreachableCode1() throws Exception {
        Node root = createAST("UnreachableCode1");
        assertThrows(UnreachableCodeException.class, () -> root.jjtAccept(visitor, null));
    }

    @Test
    void  testUnreachableCode2() throws Exception {
        Node root = createAST("UnreachableCode2");
        assertThrows(UnreachableCodeException.class, () -> root.jjtAccept(visitor, null));
    }


    @Test
    void  testMissingReturn1() throws Exception {
        Node root = createAST("MissingReturn1");
        assertThrows(MissingReturnStatementException.class, () -> root.jjtAccept(visitor, null));
    }

    @Test
    void  testMissingReturn2() throws Exception {
        Node root = createAST("MissingReturn2");
        assertThrows(MissingReturnStatementException.class, () -> root.jjtAccept(visitor, null));
    }

    @Test
    void  testMissingReturn3() throws Exception {
        Node root = createAST("MissingReturn3");
        assertThrows(MissingReturnStatementException.class, () -> root.jjtAccept(visitor, null));
    }

    @Test
    void  testMissingReturn4() throws Exception {
        Node root = createAST("MissingReturn4");
        assertThrows(MissingReturnStatementException.class, () -> root.jjtAccept(visitor, null));
    }

    @Test
    void  testMissingReturn5() throws Exception {
        Node root = createAST("MissingReturn5");
        assertThrows(MissingReturnStatementException.class, () -> root.jjtAccept(visitor, null));
    }

    @Test
    void  testMissingReturn6() throws Exception {
        Node root = createAST("MissingReturn6");
        assertThrows(MissingReturnStatementException.class, () -> root.jjtAccept(visitor, null));
    }

    @Test
    void  testMissingReturn7() throws Exception {
        Node root = createAST("MissingReturn7");
        assertThrows(MissingReturnStatementException.class, () -> root.jjtAccept(visitor, null));
    }
}