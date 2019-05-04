package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.DuplicateEdgeException;
import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchFieldException;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchMethodException;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchSymbolError;
import Compiler.Exceptions.SymbolTable.UnmatchedParametersException;
import Compiler.Exceptions.Visitor.AssignmentException;
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

    @Test
    void testSameVariableName() {
        assertThrows(DuplicateSymbolError.class, () -> visitAndCreateAST("SameVariableName"));
    }

    @Test
    void testNoShadowingAllowed() {
        assertThrows(DuplicateSymbolError.class, () -> visitAndCreateAST("NoShadowingAllowed"));
    }

    @Test
    void testUseBeforeDeclaration() {
        assertThrows(NoSuchSymbolError.class, () -> visitAndCreateAST("UseBeforeDeclaration"));
    }

    @Test
    void testReferenceOutsideScope() {
        assertThrows(NoSuchSymbolError.class, () -> visitAndCreateAST("ReferenceOutsideScope"));
    }

    @Test
    void testDuplicateEdgeDiGraph() {
        assertThrows(DuplicateEdgeException.class, () -> visitAndCreateAST("DuplicateEdgeDiGraph"));
    }

    @Test
    void testDuplicateEdgeGraph() {
        assertThrows(DuplicateEdgeException.class, () -> visitAndCreateAST("DuplicateEdgeGraph"));
    }

    @Test
    void testFunctionAssignment() {
        assertThrows(AssignmentException.class, () -> visitAndCreateAST("FunctionAssignment"));
    }

    @Test
    void testMethodAssignment() {
        assertThrows(AssignmentException.class, () -> visitAndCreateAST("MethodAssignment"));
    }

    @Test
    void testIfConditionNotBool() {
        assertThrows(IncorrectTypeException.class, () -> visitAndCreateAST("IfConditionNotBool"));
    }

    @Test
    void testWhileConditionNotBool() {
        assertThrows(IncorrectTypeException.class, () -> visitAndCreateAST("WhileConditionNotBool"));
    }

    @Test
    void testWrongReturnType() {
        assertThrows(IncorrectTypeException.class, () -> visitAndCreateAST("WrongReturnType"));
    }

}

