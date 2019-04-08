package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.SymbolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticSemanticsVisitorTest {
    private SymbolTable symbolTable;
    private String path;


    @BeforeEach
    void beforeEach() throws Exception {
        path = "Test/Compiler/Parser/CustomVisitors/test";
        symbolTable = new SymbolTable();
        symbolTable.openScope();
        //We use the function visitor to fill up our symbol table with initial values
        TestParser.useVisitorMethod(new FunctionVisitor(), path, symbolTable);
    }

    @Test
    void visitDeclarationNodeTest() throws Exception {
        //TestParser.useVisitorMethod(new StaticSemanticsVisitor(), path, symbolTable);
        //System.out.println(symbolTable);

        ASTDCL dclNode = new ASTDCL(1);
        ASTSIMPLE_TYPES simpleDataTypeNode = new ASTSIMPLE_TYPES(2);
        simpleDataTypeNode.jjtSetValue("int");

        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(3);
        identifierNode.jjtSetValue("i");

        dclNode.jjtAddChild(simpleDataTypeNode,0);
        dclNode.jjtAddChild(identifierNode, 1);

        SymbolTable symbolTable = new SymbolTable();
        symbolTable.openScope();
        dclNode.jjtAccept(new StaticSemanticsVisitor(), symbolTable);
        System.out.println(symbolTable);
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