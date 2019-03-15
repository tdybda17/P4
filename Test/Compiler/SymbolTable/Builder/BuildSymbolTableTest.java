package Compiler.SymbolTable.Builder;

import Compiler.Parser.whatever;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildSymbolTableTest {

    private BuildSymbolTable builder;

    @Test
    void buildParseTree() {
        builder = new BuildSymbolTable(
                        "function main()\n" +
                        "int a = 5\n" +
                        "real b = 2.0\n" +
                        "real b = 2.0\n" +
                        "end\n" +
                        "function whatever()\n" +
                        "int c = 5\n" +
                        "end");
        whatever.printTree(builder.getNode(), 0);
    }
}