package Compiler.DotPrinter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DotPrinterTest {

    private DotPrinter printer;

    @BeforeEach
    void setUp() {
        printer = new DotPrinter("Compiler/DotPrinter/files/testfile.dot", "Compiler/DotPrinter/files/output.eps");
    }

    @Test
    void testGetCorrectCommand01() {
        String expectedCmd = "dot -Teps testfile.dot -o output.eps";
        String actualCmd = printer.getCommand("dot", "-Teps", "testfile.dot", "output.eps");
        assertEquals(expectedCmd, actualCmd);
    }

    @Test
    void testMakeFile() throws Exception {
        printer.exec();
    }
}