package Compiler.Scanner;

import Compiler.Scanner.Symbol.EduSymbols;
import Compiler.Scanner.Token.Token;
import Exceptions.SyntaxError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    private Scanner scanner;
    private List<Token> exceptedTokens;

    @BeforeEach
    void setUp() {
        exceptedTokens = new ArrayList<>();
    }

    @Test
    void scannerTest01() {
        scanner = new Scanner("1end1111hhfhfhf");
        assertThrows(SyntaxError.class, () -> scanner.scan());
    }

    @Test
    void scannerTest02() {
        scanner = new Scanner("22g");
        // This should not be generated into two tokens
        assertThrows(SyntaxError.class, () -> scanner.scan());
    }

    @Test
    void scannerTest03() {
        scanner = new Scanner("1234 end");
        scanner.scan();
        assertEquals(EduSymbols.INTEGER, scanner.getTokenList().get(0).getEduSymbol());
        assertEquals(EduSymbols.END, scanner.getTokenList().get(1).getEduSymbol());
    }

    @Test
    void scannerTest04() {
        scanner = new Scanner("1+1 ==2");
        scanner.scan();
        assertEquals(EduSymbols.INTEGER, scanner.getTokenList().get(0).getEduSymbol());
        assertEquals(EduSymbols.PLUS, scanner.getTokenList().get(1).getEduSymbol());
        assertEquals(EduSymbols.INTEGER, scanner.getTokenList().get(2).getEduSymbol());
        assertEquals(EduSymbols.EQEQ, scanner.getTokenList().get(3).getEduSymbol());
        assertEquals(EduSymbols.INTEGER, scanner.getTokenList().get(4).getEduSymbol());
    }

    @Test
    void scannerTest05() {
        scanner = new Scanner("create if");
        scanner.scan();
        assertEquals(EduSymbols.CREATE, scanner.getTokenList().get(0).getEduSymbol());
        assertEquals(EduSymbols.IF, scanner.getTokenList().get(1).getEduSymbol());
    }

    @Test
    void scannerTest06() {
        scanner = new Scanner("Create");
        scanner.scan();
        assertEquals(EduSymbols.IDENTIFIER, scanner.getTokenList().get(0).getEduSymbol());
    }

    @Test
    void scannerTest07() {
        scanner = new Scanner("while 1+1 == 2do");
        scanner.scan();
        assertEquals(EduSymbols.WHILE, scanner.getTokenList().get(0).getEduSymbol());
        assertEquals(EduSymbols.INTEGER, scanner.getTokenList().get(1).getEduSymbol());
        assertEquals(EduSymbols.PLUS, scanner.getTokenList().get(2).getEduSymbol());
        assertEquals(EduSymbols.INTEGER, scanner.getTokenList().get(3).getEduSymbol());
        assertEquals(EduSymbols.EQEQ, scanner.getTokenList().get(4).getEduSymbol());
        assertEquals(EduSymbols.INTEGER, scanner.getTokenList().get(5).getEduSymbol());
        assertEquals(EduSymbols.DO, scanner.getTokenList().get(6).getEduSymbol());
    }
}