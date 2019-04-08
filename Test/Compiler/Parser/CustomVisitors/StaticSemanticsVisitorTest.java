package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.TestParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticSemanticsVisitorTest {
    @Test
    void visit() throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/test";

        TestParser.useVisitorMethod(new StaticSemanticsVisitor(), path);
    }

    @Test
    void visit1() {
    }

    @Test
    void visit2() {
    }

    @Test
    void visit3() {
    }

    @Test
    void visit4() {
    }

    @Test
    void visit5() {
    }

    @Test
    void visit6() {
    }

    @Test
    void visit7() {
    }
}