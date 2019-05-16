package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchSymbolError;
import Compiler.Exceptions.Visitor.IncorrectTypeException;
import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.IdentifierAttributes;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
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

    private ASTDCL createDclNode(String type, String id) {
        ASTDCL dclNode = new ASTDCL(TestParserTreeConstants.JJTDCL);

        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        identifierNode.jjtSetValue(id);

        Node typeNode = createTypeNode(type);

        dclNode.jjtAddChild(typeNode,0);
        dclNode.jjtAddChild(identifierNode, 1);
        return dclNode;
    }

    private Node createTypeNode(String type) {
        if(type.equals("Graph") | type.equals("DiGraph")) {
            ASTGRAPH_TYPE typeNode = new ASTGRAPH_TYPE(TestParserTreeConstants.JJTGRAPH_TYPE);
            typeNode.jjtSetValue(type);
            return typeNode;
        } else if (type.equals("int") | type.equals("real") | type.equals("boolean") | type.equals("Label") | type.equals("Color") | type.equals("Vertex") | type.equals("Edge") | type.equals("DiEdge")) {
            ASTSIMPLE_TYPES typeNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
            typeNode.jjtSetValue(type);
            return typeNode;
        }
        throw new IllegalArgumentException();
    }

    //Testing that we can make a simple declaration with no initialization
    @Test
    void visitDCLNodeTest1() {
        ASTDCL intDclNode = createDclNode("int", "i");
        staticSemanticsVisitor.visit(intDclNode, null);

        SymbolTable expected = new SymbolTable();
        expected.openScope();
        expected.enterSymbol("i", new IdentifierAttributes(new IntegerTypeDescriptor()));
        assertEquals(expected, symbolTable);
    }

    //Testing that trying to add an initialization should give an error, because we do not allow this after optimization
    @Test
    void visitDCLNodeTest2() {
        ASTDCL realDclNode = createDclNode("real", "r");
        ASTFNUM_VAL initialValue = new ASTFNUM_VAL(TestParserTreeConstants.JJTFNUM_VAL);
        initialValue.jjtSetValue("7.8");

        realDclNode.jjtAddChild(initialValue, 2);

        assertThrows(WrongAmountOfChildrenException.class, ()-> staticSemanticsVisitor.visit(realDclNode, null));
    }

    @Test
    void duplicateSymbolExceptionTest() throws Exception{
        ASTDCL edgeDclNode = createDclNode("DiEdge", "a");
        ASTDCL intDclNode = createDclNode("int", "a");

        staticSemanticsVisitor.visit(edgeDclNode, null);
        assertThrows(DuplicateSymbolError.class, () -> staticSemanticsVisitor.visit(intDclNode, null));
    }

    @Test
    void duplicateSymbolInDifferentScopesTest() throws Exception {
        ASTBLOCK block1 = new ASTBLOCK(0);
        block1.jjtAddChild(createDclNode("int", "a"), 0);

        ASTBLOCK block2 = new ASTBLOCK(1);
        block2.jjtAddChild(createDclNode("int", "a"), 0);

        staticSemanticsVisitor.visit(block1, null);
        assertDoesNotThrow(()-> staticSemanticsVisitor.visit(block2, null));
    }


    //We test that we are allowed to assign an integer value to an identifier entered in our symbol table.
    @Test
    void visitAssignNodeTest1(){
        String id = "i";
        ASTDCL dclNode = createDclNode("int", id);
        //We visit the dcl node to enter the symbol into our symbol table
        staticSemanticsVisitor.visit(dclNode, symbolTable);

        ASTVARIABLE leftNode = createVariableNode(id);
        ASTINUM_VAL rightNode = new ASTINUM_VAL(TestParserTreeConstants.JJTINUM_VAL);
        rightNode.jjtSetValue("6");

        ASTASSIGN assignmentNode = createAssignNode(leftNode, rightNode);
        assertDoesNotThrow(() -> staticSemanticsVisitor.visit(assignmentNode, null));
    }

    //We test that we cannot assign the wrong value type to an identifier
    @Test
    void incorrectTypeAssignmentTest(){
        String id = "b";
        ASTDCL dclNode = createDclNode("boolean", id);
        //We visit the dcl node to enter the symbol into our symbol table
        staticSemanticsVisitor.visit(dclNode, null);


        ASTVARIABLE leftNode = createVariableNode(id);
        ASTFNUM_VAL rightNode = new ASTFNUM_VAL(TestParserTreeConstants.JJTFNUM_VAL);
        rightNode.jjtSetValue("6.5");

        ASTASSIGN assignmentNode = createAssignNode(leftNode, rightNode);
        assertThrows(IncorrectTypeException.class,() -> staticSemanticsVisitor.visit(assignmentNode, null));
    }

    //We test that if our left node is not entered in the symbol table then the assignment throws an exception
    @Test
    void incorrectReferenceTest(){
        ASTVARIABLE leftNode = createVariableNode("x");

        ASTINUM_VAL rightNode = new ASTINUM_VAL(TestParserTreeConstants.JJTINUM_VAL);
        rightNode.jjtSetValue("6");

        ASTASSIGN assignmentNode = createAssignNode(leftNode, rightNode);
        assertThrows(NoSuchSymbolError.class,() -> staticSemanticsVisitor.visit(assignmentNode, null));
    }


    private ASTVARIABLE createVariableNode(String identifierName) {
        ASTVARIABLE variableNode = new ASTVARIABLE(TestParserTreeConstants.JJTVARIABLE);
        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        identifierNode.jjtSetValue(identifierName);
        variableNode.jjtAddChild(identifierNode, 0);
        return variableNode;
    }

    private ASTASSIGN createAssignNode(Node leftNode, Node rightNode){
        ASTASSIGN assignmentNode = new ASTASSIGN(0);
        assignmentNode.jjtAddChild(leftNode, 0);
        assignmentNode.jjtAddChild(rightNode, 1);

        return assignmentNode;
    }

    @Test
    void visit1() throws Exception{
        String path = "Test/Compiler/Parser/CustomVisitors/test";
        //We use the function visitor to fill up our symbol table with functions
        Node root = TestParser.createParseTree(path);
        root.jjtAccept(new TreeOptimizerVisitor(), symbolTable);
        root.jjtAccept(new FunctionVisitor(), symbolTable);
        root.jjtAccept(staticSemanticsVisitor, symbolTable);
    }

    @Test
    void visit2() throws Exception {
        String path = "Test/Compiler/Parser/CustomVisitors/test";
        System.out.println(TestParser.parseTextFile(path));
    }
}