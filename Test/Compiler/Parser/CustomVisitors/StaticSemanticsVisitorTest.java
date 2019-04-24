package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import Compiler.Exceptions.SymbolTable.SymbolTableException;
import Compiler.Exceptions.Visitor.IncorrectTypeException;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.IdentifierAttributes;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.DirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
import Compiler.SymbolTable.Table.SymbolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticSemanticsVisitorTest {
    private SymbolTable symbolTable;
    private StaticSemanticsVisitor staticSemanticsVisitor;


    @BeforeEach
    void beforeEach() throws Exception {
        symbolTable = new SymbolTable();
        symbolTable.openScope();

        staticSemanticsVisitor = new StaticSemanticsVisitor();
    }

    //Testing that we can make a simple declaration with no initialization
    @Test
    void visitSimpleDeclarationNodeTest1() {
        ASTSIMPLE_DCL intDclNode = createSimpleDCLnode("int", "i");
        staticSemanticsVisitor.visit(intDclNode, symbolTable);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("i", new IdentifierAttributes(new IntegerTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    //Testing that we can initialize a real with a real value
    @Test
    void visitSimpleDeclarationNodeTest2() {
        ASTSIMPLE_DCL realDclNode = createSimpleDCLnode("real", "r");
        realDclNode.jjtAddChild(new ASTFNUM_VAL(2), 2);

        staticSemanticsVisitor.visit(realDclNode, symbolTable);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("r", new IdentifierAttributes(new RealTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    //Testing that we cannot initialize an integer with a real value
    @Test
    void visitSimpleDeclarationNodeTest3() {
        ASTSIMPLE_DCL realDclNode = createSimpleDCLnode("int", "i");
        realDclNode.jjtAddChild(new ASTFNUM_VAL(2), 2);

        assertThrows(IncorrectTypeException.class, () -> staticSemanticsVisitor.visit(realDclNode, symbolTable));
    }

    private ASTSIMPLE_DCL createSimpleDCLnode(String type, String id) {
        ASTSIMPLE_DCL dclNode = new ASTSIMPLE_DCL(1);
        ASTSIMPLE_TYPES simpleDataTypeNode = new ASTSIMPLE_TYPES(2);
        simpleDataTypeNode.jjtSetValue(type);

        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(3);
        identifierNode.jjtSetValue(id);

        dclNode.jjtAddChild(simpleDataTypeNode,0);
        dclNode.jjtAddChild(identifierNode, 1);
        return dclNode;
    }


    //We test that we are allowed to assign an integer value to an identifier entered in our symbol table.
    @Test
    void visitAssignNodeTest1(){
        ASTMEMBER leftNode = new ASTMEMBER(0);
        String identifierName = "x";
        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(1);
        identifierNode.jjtSetValue(identifierName);
        symbolTable.enterSymbol(identifierName, new IdentifierAttributes(new IntegerTypeDescriptor()));

        leftNode.jjtAddChild(identifierNode, 0);

        ASTINUM_VAL rightNode = new ASTINUM_VAL(2);
        rightNode.jjtSetValue("6");

        ASTASSIGN assignmentNode = createAssignNode(leftNode, rightNode);
        assertDoesNotThrow(() -> staticSemanticsVisitor.visit(assignmentNode, symbolTable));
    }

    //We test that we cannot assign the wrong value type to an identifier
    @Test
    void visitAssignNodeTest2(){
        ASTMEMBER leftNode = new ASTMEMBER(0);
        String identifierName = "x";
        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(1);
        identifierNode.jjtSetValue(identifierName);
        leftNode.jjtAddChild(identifierNode, 0);
        symbolTable.enterSymbol(identifierName, new IdentifierAttributes(new IntegerTypeDescriptor()));


        ASTFNUM_VAL rightNode = new ASTFNUM_VAL(2);
        rightNode.jjtSetValue("6.5");

        ASTASSIGN assignmentNode = createAssignNode(leftNode, rightNode);
        assertThrows(IncorrectTypeException.class,() -> staticSemanticsVisitor.visit(assignmentNode, symbolTable));
    }

    //We test that if our left node is not entered in the symbol table then the assignment throws an exception
    @Test
    void visitAssignNodeTest3(){
        ASTMEMBER leftNode = new ASTMEMBER(0);
        String identifierName = "x";
        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(1);
        identifierNode.jjtSetValue(identifierName);
        leftNode.jjtAddChild(identifierNode, 0);

        ASTINUM_VAL rightNode = new ASTINUM_VAL(2);
        rightNode.jjtSetValue("6");

        ASTASSIGN assignmentNode = createAssignNode(leftNode, rightNode);
        assertThrows(SymbolTableException.class,() -> staticSemanticsVisitor.visit(assignmentNode, symbolTable));
    }

    //We test that we cannot a



    private ASTASSIGN createAssignNode(Node leftNode, Node rightNode){
        ASTASSIGN assignmentNode = new ASTASSIGN(0);
        assignmentNode.jjtAddChild(leftNode, 0);
        assignmentNode.jjtAddChild(rightNode, 1);

        return assignmentNode;
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
    void duplicateSymbolExceptionTest() throws Exception{
        ASTGRAPH_ELEMENT_DCL edgeDclNode = createGraphElementDCLnode("DiEdge", "a");
        ASTSIMPLE_DCL intDclNode = createSimpleDCLnode("int", "a");

        staticSemanticsVisitor.visit(edgeDclNode, symbolTable);
        assertThrows(DuplicateSymbolError.class, () -> staticSemanticsVisitor.visit(intDclNode, symbolTable));
    }

    @Test
    void duplicateSymbolInDifferentScopesTest() throws Exception {
        ASTBLOCK block1 = new ASTBLOCK(0);
        block1.jjtAddChild(createSimpleDCLnode("int", "a"), 0);

        ASTBLOCK block2 = new ASTBLOCK(1);
        block2.jjtAddChild(createSimpleDCLnode("int", "a"), 0);

        staticSemanticsVisitor.visit(block1, symbolTable);
        assertDoesNotThrow(()-> staticSemanticsVisitor.visit(block2, symbolTable));
    }

    @Test
    void graphDeclarationElementsDirectedDuplicateEdgeTest(){
        //TODO: få lavet
    }

    @Test
    void graphDeclarationElementsUndirectedDuplicateEdgeTest(){
        //TODO: få lavet
    }

    @Test
    void visit1() throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/test";
        System.out.println(TestParser.parseTextFile(path));
    }

    @Test
    void visit2() throws Exception{
        String path = "Test/Compiler/Parser/CustomVisitors/test";
        //We use the function visitor to fill up our symbol table with functions
        TestParser.useVisitorMethod(new FunctionVisitor(), path, symbolTable);

        TestParser.useVisitorMethod(staticSemanticsVisitor, path, symbolTable);
    }
}