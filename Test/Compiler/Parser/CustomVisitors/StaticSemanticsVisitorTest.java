package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.DuplicateEdgeException;
import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import Compiler.Exceptions.Visitor.IncorrectTypeException;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.IdentifierAttributes;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.DirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
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

        staticSemanticsVisitor = new StaticSemanticsVisitor(symbolTable);
    }

    //Testing that we can make a simple declaration with no initialization
    @Test
    void visitSimpleDeclarationNodeTest1() {
        ASTSIMPLE_DCL intDclNode = createSimpleDCLnode("int", "i");
        staticSemanticsVisitor.visit(intDclNode, null);

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

        staticSemanticsVisitor.visit(realDclNode, null);

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

        assertThrows(IncorrectTypeException.class, () -> staticSemanticsVisitor.visit(realDclNode, null));
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
/*    @Test
    void visitAssignNodeTest1(){
        ASTMEMBER leftNode = new ASTMEMBER(0);
        String identifierName = "x";
        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(1);
        identifierNode.jjtSetValue(identifierName);
        leftNode.jjtAddChild(identifierNode, 0);
        symbolTable.enterSymbol(identifierName, new IdentifierAttributes(new IntegerTypeDescriptor()));


        ASTINUM_VAL rightNode = new ASTINUM_VAL(2);
        rightNode.jjtSetValue("6");

        ASTASSIGN assignmentNode = createAssignNode(leftNode, rightNode);
        assertDoesNotThrow(() -> staticSemanticsVisitor.visit(assignmentNode, null));
    }*/

    //We test that we cannot assign the wrong value type to an identifier
/*    @Test
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
        assertThrows(IncorrectTypeException.class,() -> staticSemanticsVisitor.visit(assignmentNode, null));
    }*/

    //We test that if our left node is not entered in the symbol table then the assignment throws an exception
/*    @Test
    void visitAssignNodeTest3(){
        ASTMEMBER leftNode = new ASTMEMBER(0);
        String identifierName = "x";
        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(1);
        identifierNode.jjtSetValue(identifierName);
        leftNode.jjtAddChild(identifierNode, 0);

        ASTINUM_VAL rightNode = new ASTINUM_VAL(2);
        rightNode.jjtSetValue("6");

        ASTASSIGN assignmentNode = createAssignNode(leftNode, rightNode);
        assertThrows(SymbolTableException.class,() -> staticSemanticsVisitor.visit(assignmentNode, null));
    }*/


    private ASTASSIGN createAssignNode(Node leftNode, Node rightNode){
        ASTASSIGN assignmentNode = new ASTASSIGN(0);
        assignmentNode.jjtAddChild(leftNode, 0);
        assignmentNode.jjtAddChild(rightNode, 1);

        return assignmentNode;
    }

    //We try creating an Edge object and tests that it gets entered into the symbol table
    @Test
    void visitGraphElementDeclarationNodeTest1(){
        ASTSIMPLE_DCL edgeDclNode = createSimpleDCLnode("Edge", "a");

        staticSemanticsVisitor.visit(edgeDclNode, null);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("a", new IdentifierAttributes(new UndirectedEdgeTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    //Testing that we can also create DiEdge objects
    @Test
    void visitGraphElementDeclarationNodeTest2(){
        ASTSIMPLE_DCL  edgeDclNode = createSimpleDCLnode("DiEdge", "a");

        staticSemanticsVisitor.visit(edgeDclNode, null);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("a", new IdentifierAttributes(new DirectedEdgeTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    //Testing that we can also create Vertex objects.
    @Test
    void visitGraphElementDeclarationNodeTest3(){
        ASTSIMPLE_DCL edgeDclNode = createSimpleDCLnode("Vertex", "a");

        staticSemanticsVisitor.visit(edgeDclNode, null);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("a", new IdentifierAttributes(new VertexTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    //We test that we cannot add an edge from a to b twice in the same graph.
    @Test
    void DirectedGraphDuplicateEdgeTest(){
        ASTGRAPH_DCL_ELEMENTS graphDclElementsNode = new ASTGRAPH_DCL_ELEMENTS(0);
        graphDclElementsNode.jjtAddChild(createVertexDclNode("a", "b", new ASTWEIGHT(0)), 0);
        graphDclElementsNode.jjtAddChild(createVertexDclNode("a", "b", new ASTWEIGHT(0)), 1);


        ASTGRAPH_DCL graphDclNode = createGraphDclNode("DiGraph", "testDiGraph", graphDclElementsNode);

        assertThrows(DuplicateEdgeException.class, ()-> staticSemanticsVisitor.visit(graphDclNode, null));
    }

    //We test that we cannot add both a to b and b to a in an undirected graph
    @Test
    void UndirectedGraphDuplicateEdgeTest(){
        ASTGRAPH_DCL_ELEMENTS graphDclElementsNode = new ASTGRAPH_DCL_ELEMENTS(0);
        graphDclElementsNode.jjtAddChild(createVertexDclNode("a", "b", new ASTWEIGHT(0)), 0);
        graphDclElementsNode.jjtAddChild(createVertexDclNode("b", "a", new ASTWEIGHT(0)), 1);


        ASTGRAPH_DCL graphDclNode = createGraphDclNode("Graph", "testGraph", graphDclElementsNode);

        assertThrows(DuplicateEdgeException.class, ()-> staticSemanticsVisitor.visit(graphDclNode, null));
    }

    private ASTGRAPH_DCL createGraphDclNode(String graphType, String identifier, ASTGRAPH_DCL_ELEMENTS graphDclElementsNode){
        ASTGRAPH_DCL graphDclNode = new ASTGRAPH_DCL(0);

        ASTGRAPH_TYPE graphTypeNode = new ASTGRAPH_TYPE(1);
        graphTypeNode.jjtSetValue(graphType);
        graphDclNode.jjtAddChild(graphTypeNode, 0);

        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(2);
        identifierNode.jjtSetValue(identifier);
        graphDclNode.jjtAddChild(identifierNode, 1);

        graphDclNode.jjtAddChild(graphDclElementsNode, 2);
        return graphDclNode;
    }

    private ASTGRAPH_VERTEX_DCL createVertexDclNode(String firstVertex, String secondVertex, ASTWEIGHT weightNode) {
        ASTGRAPH_VERTEX_DCL vertexDclNode = new ASTGRAPH_VERTEX_DCL(0);

        ASTIDENTIFIER firstVertexIdentifierNode = new ASTIDENTIFIER(1);
        firstVertexIdentifierNode.jjtSetValue(firstVertex);
        vertexDclNode.jjtAddChild(firstVertexIdentifierNode, 0);

        ASTIDENTIFIER secondVertexIdentifierNode = new ASTIDENTIFIER(2);
        secondVertexIdentifierNode.jjtSetValue(secondVertex);
        vertexDclNode.jjtAddChild(secondVertexIdentifierNode, 1);

        vertexDclNode.jjtAddChild(weightNode, 2);
        return vertexDclNode;
    }

    @Test
    void incorrectWeightType(){
        ASTWEIGHT weight = new ASTWEIGHT(0);
        ASTBOOL_VAL boolValNode = new ASTBOOL_VAL(1);
        boolValNode.jjtSetValue("true");
        weight.jjtAddChild(boolValNode, 0);


        assertThrows(IncorrectTypeException.class, ()-> staticSemanticsVisitor.visit(createVertexDclNode("a", "b", weight), null));
    }

    @Test
    void duplicateSymbolExceptionTest() throws Exception{
        ASTSIMPLE_DCL edgeDclNode = createSimpleDCLnode("DiEdge", "a");
        ASTSIMPLE_DCL intDclNode = createSimpleDCLnode("int", "a");

        staticSemanticsVisitor.visit(edgeDclNode, symbolTable);
        assertThrows(DuplicateSymbolError.class, () -> staticSemanticsVisitor.visit(intDclNode, null));
    }

    @Test
    void duplicateSymbolInDifferentScopesTest() throws Exception {
        ASTBLOCK block1 = new ASTBLOCK(0);
        block1.jjtAddChild(createSimpleDCLnode("int", "a"), 0);

        ASTBLOCK block2 = new ASTBLOCK(1);
        block2.jjtAddChild(createSimpleDCLnode("int", "a"), 0);

        staticSemanticsVisitor.visit(block1, symbolTable);
        assertDoesNotThrow(()-> staticSemanticsVisitor.visit(block2, null));
    }

    @Test
    void visit1() throws Exception{
        String path = "Test/Compiler/Parser/CustomVisitors/test";
        //We use the function visitor to fill up our symbol table with functions
        TestParser.useVisitorMethod(new FunctionVisitor(), path, symbolTable);

        TestParser.useVisitorMethod(staticSemanticsVisitor, path, symbolTable);
    }

    @Test
    void visit2() throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/test";
        System.out.println(TestParser.parseTextFile(path));
    }

}