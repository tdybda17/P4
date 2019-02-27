package Compiler.Scanner;

import Compiler.Scanner.Symbol.EduSymbols;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static Compiler.Scanner.Symbol.EduSymbols.*;

class ScannerTest {

    private Scanner scanner;

    EduSymbols getSymbolFromResult(int index) {
        return scanner.getTokenList().get(index).getEduSymbol();
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
        assertEquals(INTEGER, getSymbolFromResult(1));
        assertEquals(IDENTIFIER, getSymbolFromResult(2));
        assertEquals(NEWLINE, getSymbolFromResult(3));
    }

    @Test
    void scannerTest03() {
        scanner = new Scanner("1234 end");
        scanner.scan();
        assertEquals(INTEGER, getSymbolFromResult(0));
        assertEquals(END, getSymbolFromResult(1));
    }

    @Test
    void scannerTest04() {
        scanner = new Scanner("1+1 ==2");
        scanner.scan();
        assertEquals(INTEGER, getSymbolFromResult(0));
        assertEquals(PLUS, getSymbolFromResult(1));
        assertEquals(INTEGER, getSymbolFromResult(2));
        assertEquals(EQEQ, getSymbolFromResult(3));
        assertEquals(INTEGER, getSymbolFromResult(4));
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

}