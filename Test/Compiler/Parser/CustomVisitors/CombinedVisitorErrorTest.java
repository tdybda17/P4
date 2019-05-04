package Compiler.Parser.CustomVisitors;

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
    void testWrongValueType() throws Exception {
        assertThrows(IncorrectTypeException.class, () -> visitAndCreateAST("WrongValueType"));
    }
}
