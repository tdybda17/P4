package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.IdentifierAttributes;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.RealTypeDescriptor;
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
    void visitDeclarationNodeTest() {
        ASTDCL intDclNode = createDCLnode("int", "i");
        ASTDCL realDclNode = createDCLnode("real", "r");

        SymbolTable symbolTable = new SymbolTable();
        symbolTable.openScope();
        intDclNode.jjtAccept(new StaticSemanticsVisitor(), symbolTable);
        realDclNode.jjtAccept(new StaticSemanticsVisitor(), symbolTable);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("i", new IdentifierAttributes(new IntegerTypeDescriptor()));
        expected.enterSymbol("r", new IdentifierAttributes(new RealTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    private ASTDCL createDCLnode(String type, String id) {
        ASTDCL dclNode = new ASTDCL(1);
        ASTSIMPLE_TYPES simpleDataTypeNode = new ASTSIMPLE_TYPES(2);
        simpleDataTypeNode.jjtSetValue(type);

        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(3);
        identifierNode.jjtSetValue(id);

        dclNode.jjtAddChild(simpleDataTypeNode,0);
        dclNode.jjtAddChild(identifierNode, 1);
        return dclNode;
    }

    @Test
    void visit1() throws Exception {
        System.out.println(TestParser.parseTextFile(path));
    }

    @Test
    void visit2() throws Exception{
        TestParser.useVisitorMethod(new StaticSemanticsVisitor(), path, symbolTable);
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