package Compiler.Parser.CustomVisitors;

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
        assertThrows(UnmatchedParametersException.class, () -> visitAndCreateAST("WrongFormalParameter"));
    }

    @Test
    void testWrongElementType() {
        assertThrows(IncorrectTypeException.class, () -> visitAndCreateAST("WrongElementType"));
    }
}
