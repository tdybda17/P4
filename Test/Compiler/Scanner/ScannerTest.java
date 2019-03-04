package Compiler.Scanner;

import Compiler.Scanner.FlexScanner.Scanner;
import Compiler.Scanner.FlexScanner.Symbol.EduSymbols;
import Exceptions.SyntaxError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static Compiler.Scanner.FlexScanner.Symbol.EduSymbols.*;

class ScannerTest {

    private Scanner scanner;

    EduSymbols getSymbolFromResult(int index) {
        return scanner.getTokenList().get(index).getEduSymbol();
    }

    @Test
    void scannerKeywordTest01() {
        scanner = new Scanner("create if else while for end do function foreach in then int real Vertex Edge");
        scanner.scan();
        assertEquals(CREATE, getSymbolFromResult(0));
        assertEquals(IF, getSymbolFromResult(1));
        assertEquals(ELSE, getSymbolFromResult(2));
        assertEquals(WHILE, getSymbolFromResult(3));
        assertEquals(FOR, getSymbolFromResult(4));
        assertEquals(END, getSymbolFromResult(5));
        assertEquals(DO, getSymbolFromResult(6));
        assertEquals(FUNCTION, getSymbolFromResult(7));
        assertEquals(FOREACH, getSymbolFromResult(8));
        assertEquals(IN, getSymbolFromResult(9));
        assertEquals(THEN, getSymbolFromResult(10));
        assertEquals(INT, getSymbolFromResult(11));
        assertEquals(REAL, getSymbolFromResult(12));
        assertEquals(VERTEX, getSymbolFromResult(13));
        assertEquals(EDGE, getSymbolFromResult(14));
    }

    @Test
    void scannerCommentTest01() {
        scanner = new Scanner("create //1hej if then \n if");
        scanner.scan();
        assertEquals(CREATE, getSymbolFromResult(0));
        assertEquals(COMMENT, getSymbolFromResult(1));
        assertEquals(NEWLINE, getSymbolFromResult(2));
        assertEquals(IF, getSymbolFromResult(3));
    }

    @Test
    void scannerTest01() {
        scanner = new Scanner("create while if ()");
        scanner.scan();
        assertEquals(CREATE, getSymbolFromResult(0));
        assertEquals(WHILE, getSymbolFromResult(1));
        assertEquals(IF, getSymbolFromResult(2));
        assertEquals(LPAREN, getSymbolFromResult(3));
        assertEquals(RPAREN, getSymbolFromResult(4));
    }

    @Test
    void scannerTest02() {
        scanner = new Scanner("while 22g\n");
        scanner.scan();
        assertEquals(WHILE, getSymbolFromResult(0));
        assertEquals(INTEGER_LITERAL, getSymbolFromResult(1));
        assertEquals(IDENTIFIER, getSymbolFromResult(2));
        assertEquals(NEWLINE, getSymbolFromResult(3));
    }

    @Test
    void scannerTest03() {
        scanner = new Scanner("1234 end");
        scanner.scan();
        assertEquals(INTEGER_LITERAL, getSymbolFromResult(0));
        assertEquals(END, getSymbolFromResult(1));
    }

    @Test
    void scannerTest04() {
        scanner = new Scanner("1+1 ==2");
        scanner.scan();
        assertEquals(INTEGER_LITERAL, getSymbolFromResult(0));
        assertEquals(PLUS, getSymbolFromResult(1));
        assertEquals(INTEGER_LITERAL, getSymbolFromResult(2));
        assertEquals(EQEQ, getSymbolFromResult(3));
        assertEquals(INTEGER_LITERAL, getSymbolFromResult(4));
    }

    @Test
    void scannerTest05() {
        scanner = new Scanner("create if");
        scanner.scan();
        assertEquals(CREATE, getSymbolFromResult(0));
        assertEquals(IF, getSymbolFromResult(1));
    }

    @Test
    void scannerTest06() {
        scanner = new Scanner("Create");
        scanner.scan();
        assertEquals(IDENTIFIER, getSymbolFromResult(0));
    }

    @Test
    void scannerTest07() {
        scanner = new Scanner("22.5");
        scanner.scan();
        assertEquals(REAL_LITERAL, getSymbolFromResult(0));
        assertEquals(22.5, scanner.getTokenList().get(0).getValue());
    }

    @Test
    void scannerTest08() {
        scanner = new Scanner("23134567898");
        // Throws SyntaxError because of overflow
        assertThrows(SyntaxError.class, () -> scanner.scan());
    }

    @Test
    void testProgram01() {
        scanner = new Scanner("int a = 3\n" +
                              "int b = 3\n" +
                              "int c = a + b");
        System.out.println(scanner.scan());
    }
}