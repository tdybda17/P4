package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.SymbolTable.IllegalTypeException;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.IdentifierAttributes;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.DirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.RealTypeDescriptor;
import Compiler.SymbolTable.Table.SymbolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticSemanticsVisitorTest {
    private SymbolTable symbolTable;
    private String path;
    private StaticSemanticsVisitor staticSemanticsVisitor;


    @BeforeEach
    void beforeEach() throws Exception {
        path = "Test/Compiler/Parser/CustomVisitors/test";
        symbolTable = new SymbolTable();
        symbolTable.openScope();

        staticSemanticsVisitor = new StaticSemanticsVisitor();
    }

    @Test
    void visitDeclarationNodeTest1() {
        ASTDCL intDclNode = createDCLnode("int", "i");
        staticSemanticsVisitor.visit(intDclNode, symbolTable);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("i", new IdentifierAttributes(new IntegerTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    @Test
    void visitDeclarationNodeTest2() {
        //TODO: opdater denne test til at inkludere en initilazation
        ASTDCL realDclNode = createDCLnode("real", "r");
        staticSemanticsVisitor.visit(realDclNode, symbolTable);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
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
    void visitGraphElementDeclarationNodeTest1(){
        ASTGRAPH_ELEMENT_DCL edgeDclNode = createGraphElementDCLnode("Edge", "a");

        staticSemanticsVisitor.visit(edgeDclNode, symbolTable);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("a", new IdentifierAttributes(new UndirectedEdgeTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    @Test
    void visitGraphElementDeclarationNodeTest2(){
        ASTGRAPH_ELEMENT_DCL edgeDclNode = createGraphElementDCLnode("DiEdge", "a");

        staticSemanticsVisitor.visit(edgeDclNode, symbolTable);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("a", new IdentifierAttributes(new DirectedEdgeTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    @Test
    void visitGraphElementDeclarationNodeTest3(){
        ASTGRAPH_ELEMENT_DCL edgeDclNode = createGraphElementDCLnode("Vertex", "a");

        staticSemanticsVisitor.visit(edgeDclNode, symbolTable);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("a", new IdentifierAttributes(new VertexTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    private ASTGRAPH_ELEMENT_DCL createGraphElementDCLnode(String type, String id) {
        ASTGRAPH_ELEMENT_DCL dclNode = new ASTGRAPH_ELEMENT_DCL(1);
        ASTGRAPH_ELEMENT_TYPES graph_element_types = new ASTGRAPH_ELEMENT_TYPES(2);

        graph_element_types.jjtSetValue(type);

        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(3);
        identifierNode.jjtSetValue(id);

        dclNode.jjtAddChild(graph_element_types, 0);
        dclNode.jjtAddChild(identifierNode, 1);
        return dclNode;
    }


    @Test
    void visit1() throws Exception {
        System.out.println(TestParser.parseTextFile(path));
    }

    @Test
    void visit2() throws Exception{
        symbolTable = new SymbolTable();
        symbolTable.openScope();
        //We use the function visitor to fill up our symbol table with functions
        TestParser.useVisitorMethod(new FunctionVisitor(), path, symbolTable);

        TestParser.useVisitorMethod(new StaticSemanticsVisitor(), path, symbolTable);
    }
}