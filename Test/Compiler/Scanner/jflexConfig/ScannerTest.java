package Compiler.Scanner.jflexConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;

class ScannerTest {

    private Scanner scanner;

    @BeforeEach
    void setUp() {
        try {
            String filename = "/Users/toby/Programs/P4/Test/Compiler/Scanner/jflexConfig/testFiles/testprogram.txt";
            java.io.FileInputStream stream = new java.io.FileInputStream(filename);
            java.io.Reader reader = new java.io.InputStreamReader(stream, Charset.forName("UTF-8"));
            scanner = new Scanner(reader);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void scannerTest01() {
        try {
            while(scanner.next_token().sym != 0) {
                System.out.println(scanner.yytext());
            }
        } catch (IOException e) {

        }
    }
}