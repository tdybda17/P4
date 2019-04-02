package ProgramTestCases.SyntaxError;

import Compiler.Parser.GeneratedFiles.ParseException;
import Compiler.Parser.GeneratedFiles.TestParser;
import Compiler.Parser.GeneratedFiles.TokenMgrError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestParserErrorTest {
    private String absPath = "Test/ProgramTestCases/SyntaxError/";

    @Test
    void testError1() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error1"));
    }

    @Test
    void testError2() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error2"));
    }

    @Test
    void testError3() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error3"));
    }

    @Test
    void testError4() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error4"));
    }

    @Test
    void testError5() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error5"));
    }

    @Test
    void testError6() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error6"));
    }

    @Test
    void testError7() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error7"));
    }

    @Test
    void testError8() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error8"));
    }

    @Test
    void testError9() {
        assertThrows(TokenMgrError.class, () -> TestParser.parseTextFile(absPath + "Error9"));
    }

    @Test
    void testError10() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error10"));
    }

    @Test
    void testError11() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error11"));
    }

    @Test
    void testError12() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error12"));
    }

    @Test
    void testError13() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error13"));
    }

    @Test
    void testError14() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error14"));
    }

    @Test
    void testError15() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error15"));
    }

    @Test
    void testError16() {
        assertThrows(ParseException.class, () -> TestParser.parseTextFile(absPath + "Error16"));
    }
}
