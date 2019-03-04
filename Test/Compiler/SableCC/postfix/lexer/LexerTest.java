package Compiler.SableCC.postfix.lexer;

import Compiler.SableCC.postfix.node.EOF;
import Compiler.SableCC.postfix.node.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

    private Lexer lexer;

    @BeforeEach
    void setUp() {
        lexer = new Lexer(new PushbackReader(new StringReader("12345.849")));
    }

    @Test
    void testLexer01() {

    }

    @Test
    void testLexer02() {
        try {
            Token token = lexer.next();
            assertFalse(token instanceof TIntegerLiteral);
            assertTrue(token instanceof TRealLiteral);
        } catch (IOException | LexerException e) {
            e.printStackTrace();
        }
    }
}