package Compiler.SableCC.postfix.lexer;

import Compiler.SableCC.postfix.node.EOF;
import Compiler.SableCC.postfix.node.Token;
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
        lexer = new Lexer(new PushbackReader(new StringReader("!(false) 1 + 2 = 3")));
    }

    @Test
    void testLexer01() {
        Token token;

        try {
            while(!((token = lexer.next()) instanceof EOF)) {
                System.out.println(token);
            }
        } catch (IOException | LexerException e) {
            System.out.println(e.getMessage());
        }
    }


}