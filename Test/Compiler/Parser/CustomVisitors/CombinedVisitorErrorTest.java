package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchFieldException;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchMethodException;
import Compiler.Exceptions.SymbolTable.UnmatchedParametersException;
import Compiler.Exceptions.Visitor.IncorrectTypeException;
import Compiler.Parser.GeneratedFiles.Node;
import Compiler.Parser.GeneratedFiles.TestParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CombinedVisitorErrorTest {
    private Node createAST(String fileName) throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/CombinedVisitorErrors/" + fileName;
        return TestParser.createParseTree(path);
    }

    private void visitAndCreateAST(String fileName) throws Exception {
        Node root = createAST(fileName);
        CombinedVisitor.visit(root);
    }

    @Test
    void testWrongTypeOfValue()  {
        assertThrows(IncorrectTypeException.class, () -> visitAndCreateAST("WrongTypeOfValue"));
    }

    @Test
    void testWrongFormalParameter() {
        assertThrows(IncorrectTypeException.class, () -> visitAndCreateAST("WrongFormalParameter"));
    }

    @Test
    void testWrongElementType() {
        assertThrows(IncorrectTypeException.class, () -> visitAndCreateAST("WrongElementType"));
    }

    @Test
    void testWrongElementInDcl() {
        assertThrows(IncorrectTypeException.class, () -> visitAndCreateAST("WrongElementInDcl"));
    }

    @Test
    void testWrongAmountOfParameters() {
        assertThrows(UnmatchedParametersException.class, () -> visitAndCreateAST("WrongAmtOfParameters"));
    }

    @Test
    void testNoSuchField() {
        assertThrows(NoSuchFieldException.class, () -> visitAndCreateAST("NoSuchField"));
    }

    @Test
    void testNoSuchMethod() {
        assertThrows(NoSuchMethodException.class, () -> visitAndCreateAST("NoSuchMethod"));
    }
}
