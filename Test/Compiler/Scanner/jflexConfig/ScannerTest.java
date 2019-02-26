package Compiler.Scanner.jflexConfig;

import Compiler.Scanner.Symbol.EduSymbols;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Compiler.Scanner.Symbol.EduSymbols.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScannerTest {

    private Scanner scanner;
    private List<Integer> exceptedSymbols;

    @BeforeEach
    void setUp() {
        // Create a new empty list for each test
        exceptedSymbols = new ArrayList<>();
    }

    // Call this at the start of every test to setup a new scanner
    void setUpScanner(String testString) {
        this.scanner = new Scanner(new StringReader(testString));
    }

    void setUpExpectedSymbolList(EduSymbols ... args) {
        for (EduSymbols current : args)
            exceptedSymbols.add(current.getValue());
    }

    void printError(String message, Symbol sym) {
        if(sym != null) System.out.println(message + ": After symbol " + sym.sym + " on line: " + (sym.left + 1));
        else System.out.println(message + " : Symbol was null");
    }

    @Test
    void scannerTest01() {
        setUpScanner("for end if create");
        setUpExpectedSymbolList(FOR, END, IF, CREATE);
        List<Integer> actualList = new ArrayList<>();
        Symbol sym = null;
        try {
            while ((sym = scanner.next_token()).sym != 0) {
                actualList.add(sym.sym);
            }
        } catch (IOException | Error e) {
            printError(e.getMessage(), sym);
        }
        assertEquals(exceptedSymbols, actualList);
    }

    @Test
    void scannerTest02() {

    }
}