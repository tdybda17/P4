package ProgramTestCases.LegalSyntax;

import Compiler.Parser.GeneratedFiles.TestParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestParserTest {
    String absPath = "Test/ProgramTestCases/LegalSyntax/";

    @Test
    void testVertexDcl() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "VertexDclTest"));
    }

    @Test
    void testDecInit() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "DecInitTest"));
    }

    @Test
    void testGraphDcl() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "GraphDclTest"));
    }

    @Test
    void testWGraphDcl() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "WGraphDclTest"));
    }

    @Test
    void testFunctionCall() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "FunctionCallTest"));
    }

    @Test
    void testForLoops() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "ForLoopsTest"));
    }

    @Test
    void testWhileLoops() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "WhileLoopsTest"));
    }

    @Test
    void testMethodForEach() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "MethodForEachTest"));
    }

    @Test
    void testComment() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "CommentTest"));
    }

    @Test
    void testQueue() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "QueueTest"));
    }

    @Test
    void testSet() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "SetTest"));
    }

    @Test
    void testPriorityQueue() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "PriorityQueueTest"));
    }

    @Test
    void testArithExpr() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "ArithExprTest"));
    }

    @Test
    void testConstant() {
        assertDoesNotThrow(() -> TestParser.parseTextFile(absPath + "ConstantTest"));
    }




}