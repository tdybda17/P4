package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.DuplicateEdgeException;
import Compiler.Exceptions.SymbolTable.IllegalTypeException;
import Compiler.Exceptions.Visitor.IncorrectTypeException;
import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.IdentifierAttributes;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.DirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.UndirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.NumberTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.CollectionTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptorFactory;
import Compiler.SymbolTable.Table.SymbolTable;

import java.util.*;

public class StaticSemanticsVisitor implements TestParserVisitor {
    private TypeDescriptor convertToTypeDescriptor(Object data){
        if(data instanceof TypeDescriptor) {
            return (TypeDescriptor) data;
        } else {
            throw new IllegalTypeException("The given data object was not a TypeDescriptor");
        }
    }

    private SymbolTable convertToSymbolTable(Object data) {
        if(data instanceof SymbolTable) {
            return (SymbolTable) data;
        } else {
            throw new IllegalTypeException("The given data object was not a SymbolTable");
        }
    }

    private SimpleNode convertToSimpleNode(Object node) {
        if(node instanceof SimpleNode) {
            return (SimpleNode) node;
        } else {
            throw new IllegalTypeException("The given data object was not a SimpleNode");
        }
    }

    private String getIdentifierName(Node identifierNode) {
        if(identifierNode instanceof ASTIDENTIFIER) {
            SimpleNode simpleNode = (SimpleNode) identifierNode;
            return (String) simpleNode.jjtGetValue();
        } else {
            throw new IllegalTypeException("The given node was not an IdentifierNode");
        }
    }

    private Object defaultVisit(SimpleNode node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

    private TypeDescriptor getTypeDescriptor(Object node){
        if (node instanceof ASTSIMPLE_TYPES | node instanceof ASTGRAPH_ELEMENT_TYPES | node instanceof ASTGRAPH_TYPE) {
            SimpleNode simpleNode = convertToSimpleNode(node);
            TypeDescriptorFactory typeDescriptorFactory = new TypeDescriptorFactory();
            return typeDescriptorFactory.create((String) simpleNode.jjtGetValue());
        } else {
            throw new IllegalArgumentException("You could not get a type descriptor for the given node type");
        }
    }

    private TypeDescriptor getTypeForIdentifierSymbol(Symbol symbol) {
        if (symbol.getAttributes() instanceof IdentifierAttributes) {
            IdentifierAttributes attributes = (IdentifierAttributes) symbol.getAttributes();
            return attributes.getType();
        } else {
            throw new IllegalTypeException("The attributes you got from your symbol was not identifier attributes");
        }
    }

    private Symbol createSymbolFromDCLnode(Node dclNode, Object data) {
        if(dclNode instanceof ASTSIMPLE_DCL | dclNode instanceof ASTGRAPH_ELEMENT_DCL | dclNode instanceof ASTGRAPH_DCL | dclNode instanceof ASTCOLLECTION_ADT) {
            Node typeNode = dclNode.jjtGetChild(0);
            //We call the visit method for the simple data type node to get the type descriptor
            TypeDescriptor type = convertToTypeDescriptor(typeNode.jjtAccept(this, data));

            String id = getIdentifierName(dclNode.jjtGetChild(1));
            return new Symbol(id, new IdentifierAttributes(type));
        } else {
            throw new IllegalArgumentException("The given node was not an DCL, GRAPH_DCL or GRAPH_ELEMENT_DCL node");
        }
    }


    //We open a new scope each time we meet a block node and then we close it right after the block is done
    @Override
    public Object visit(ASTBLOCK node, Object data) {
        SymbolTable symbolTable = convertToSymbolTable(data);
        symbolTable.openScope();
        node.childrenAccept(this, data);
        System.out.println(symbolTable.toString()); //TODO: fjern denne print statement når vi er færdige
        symbolTable.closeScope();
        return null;
    }

    @Override
    public Object visit(ASTSIMPLE_DCL node, Object data) {
        SymbolTable symbolTable = convertToSymbolTable(data);
        if(node.jjtGetNumChildren() == 2 | node.jjtGetNumChildren() == 3) {
            Symbol symbol = createSymbolFromDCLnode(node, data);
            symbolTable.enterSymbol(symbol);
            if(node.jjtGetNumChildren() == 3) {
                TypeDescriptor expectedType = getTypeForIdentifierSymbol(symbol);
                checkSimpleDclInitialization(expectedType, node.jjtGetChild(2), symbolTable);
            }
            return null;
        } else {
            throw new WrongAmountOfChildrenException("The declaration node had: " + node.jjtGetNumChildren());
        }
    }

    private void checkSimpleDclInitialization(TypeDescriptor expectedType, Node initializationNode, Object data) {
        //TODO: making it so that evaluations can be type checked
        TypeDescriptor actualType = convertToTypeDescriptor(initializationNode.jjtAccept(this, data));
        if (!expectedType.equals(actualType)) {
            throw new IncorrectTypeException("The expected type: " + expectedType.getTypeName() + ", was not the same as the actual type: " + actualType.getTypeName());
        }
    }


    @Override
    public Object visit(ASTSIMPLE_TYPES node, Object data) {
        return getTypeDescriptor(node);
    }

    @Override
    public Object visit(ASTGRAPH_ELEMENT_DCL node, Object data) {
        SymbolTable symbolTable = convertToSymbolTable(data);
        if(node.jjtGetNumChildren() == 2) {
            symbolTable.enterSymbol(createSymbolFromDCLnode(node, data));
        } else {
            throw new WrongAmountOfChildrenException("The graph element declaration node had: " + node.jjtGetNumChildren());
        }
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_ELEMENT_TYPES node, Object data) {
        return getTypeDescriptor(node);
    }

    @Override
    public Object visit(ASTGRAPH_DCL node, Object data) {
        SymbolTable symbolTable = convertToSymbolTable(data);
        if(node.jjtGetNumChildren() == 2 | node.jjtGetNumChildren() == 3) {
            Symbol symbol = createSymbolFromDCLnode(node, data);
            symbolTable.enterSymbol(symbol);

            if(node.jjtGetNumChildren() == 3) {
                Node initializationNode = node.jjtGetChild(2);
                checkGraphDclInitialization(initializationNode, symbol, symbolTable);
            }
        } else {
            throw new WrongAmountOfChildrenException("The graph declaration node had: " + node.jjtGetNumChildren());
        }
        return defaultVisit(node, data);
    }

    private void checkGraphDclInitialization(Node initializationNode, Symbol symbol, SymbolTable symbolTable) {
        if(initializationNode instanceof ASTGRAPH_DCL_ELEMENTS) {
            TypeDescriptor graphType = getTypeForIdentifierSymbol(symbol);
            if(graphType instanceof DirectedGraphTypeDescriptor) {
                visitDirectedGraphInitialization(initializationNode);
            } else if (graphType instanceof UndirectedGraphTypeDescriptor) {
                visitUndirectedGraphInitialization(initializationNode);
            } else {
                throw new IllegalTypeException("Your graph was neither of the type directed or undirected");
            }
        } else if (initializationNode instanceof ASTGRAPH_ASSIGN) {
            initializationNode.jjtAccept(this, symbolTable);
        } else {
            throw new IllegalTypeException("The node use for initialization of your graph dcl was not an correct type");
        }
    }

    private void visitDirectedGraphInitialization(Node initializationNode){
        Map<String, List<String>> allVertexDcls = new HashMap<>();

        for (int i = 0; i < initializationNode.jjtGetNumChildren(); i++) {
            Node child = initializationNode.jjtGetChild(i);

            List<String> vertexPair = getVertexPair(child);

            String firstVertex = vertexPair.get(0);
            String secondVertex = vertexPair.get(1);
            addVertexToVertexDcls(allVertexDcls, firstVertex, secondVertex);
        }
    }

    private void visitUndirectedGraphInitialization(Node initializationNode){
        Map<String, List<String>> allVertexDcls = new HashMap<>();

        for (int i = 0; i < initializationNode.jjtGetNumChildren(); i++) {
            Node child = initializationNode.jjtGetChild(i);

            List<String> vertexPair = getVertexPair(child);
            String firstVertex = vertexPair.get(0);
            String secondVertex = vertexPair.get(1);
            addVertexToVertexDcls(allVertexDcls, firstVertex, secondVertex);
            addVertexToVertexDcls(allVertexDcls, secondVertex, firstVertex);
        }
    }

    private void addVertexToVertexDcls(Map<String, List<String>> allVertexDcls, String key, String value) {
        if(allVertexDcls.containsKey(key)) {
            List<String> neighbours = allVertexDcls.get(key);
            if(neighbours.contains(value)) {
                throw new DuplicateEdgeException("You tried to add more than one edge from " + key + " to " + value);
            }
            neighbours.add(value);
            allVertexDcls.put(key, neighbours);
        } else {
            List<String> neighbours = new ArrayList<>();
            neighbours.add(value);
            allVertexDcls.put(key, neighbours);
        }
    }

    private List<String> getVertexPair(Node node) {
        ASTGRAPH_VERTEX_DCL vertexDclNode;
        if(node instanceof ASTGRAPH_VERTEX_DCL) {
            vertexDclNode = (ASTGRAPH_VERTEX_DCL) node;
        } else {
            throw new IllegalArgumentException("Your graph declaration had a child node that was not an graph vertex dcl node");
        }
        List<String> vertexPair = new ArrayList<>();

        String firstVertex = getIdentifierName(vertexDclNode.jjtGetChild(0));
        String secondVertex = getIdentifierName(vertexDclNode.jjtGetChild(1));
        vertexPair.add(firstVertex);
        vertexPair.add(secondVertex);
        return vertexPair;
    }

    @Override
    public Object visit(ASTGRAPH_TYPE node, Object data) {
        return getTypeDescriptor(node);
    }

    @Override
    public Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data) {
        return defaultVisit(node, data);
    }


    @Override
    public Object visit(ASTGRAPH_VERTEX_DCL node, Object data) {
        return defaultVisit(node, data);
    }



    @Override
    public Object visit(ASTIDENTIFIER node, Object data) {
        return defaultVisit(node, data);
        //SymbolTable st = (SymbolTable) data;
        //return ((IdentifierAttributes)st.retrieveSymbol(node.jjtGetValue().toString()).getAttributes()).getType();
    }

    @Override
    public Object visit(ASTASSIGN node, Object data) { //TODO: Få lavet denne
        if(node.jjtGetNumChildren() != 2) {
            throw new WrongAmountOfChildrenException("The assignment node did not have two children");
        }
        TypeDescriptor expectedType = convertToTypeDescriptor(node.jjtGetChild(0));
        TypeDescriptor actualType = convertToTypeDescriptor(node.jjtGetChild(1));
        if(expectedType.equals(actualType)) {
            return null;
        } else {
            throw new IncorrectTypeException("The expected type: " + expectedType.getTypeName() + ", was not the same as the actual type: " + actualType.getTypeName());
        }
    }

    @Override
    public Object visit(SimpleNode node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTSTART node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTPROG node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVERTEX_ATTRIBUTES node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTEDGE_ATTRIBUTES node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTATTRIBUTE_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTOBJECT_TYPE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCOLLECTION_TYPE node, Object data) {
        SimpleNode collectionTypeNode = convertToSimpleNode(node);

        TypeDescriptor type = new TypeDescriptorFactory().create((String) collectionTypeNode.jjtGetValue());
        if(type instanceof CollectionTypeDescriptor) {
            CollectionTypeDescriptor collectionTypeDescriptor = (CollectionTypeDescriptor) type;

            Node childNode = node.jjtGetChild(0);
            //We make a recursive call to the visit method for the sub node
            TypeDescriptor elementType = convertToTypeDescriptor(childNode.jjtAccept(this, data));
            collectionTypeDescriptor.setElementType(elementType);
            return collectionTypeDescriptor;
        } else {
            throw new IncorrectTypeException("Somehow you got an none collection type descriptor from your collection type declaration");
        }
    }

    @Override
    public Object visit(ASTOR_EXPR node, Object data) {
        typeCheckChildren(BooleanTypeDescriptor.class, node, data);
        return new BooleanTypeDescriptor();
    }

    private <T extends TypeDescriptor> void typeCheckChildren(Class<T> expectedType, Node parent, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) parent.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) parent.jjtGetChild(1).jjtAccept(this, data);
        typeCheckChildren(expectedType, firstType, secondType);
    }

    private <T extends TypeDescriptor> void typeCheckChildren(Class<T> expectedType, TypeDescriptor child1, TypeDescriptor child2) {
        typeCheck(expectedType, child1);
        typeCheck(expectedType, child2);
    }

    private <T extends TypeDescriptor> void typeCheck(Class<T> expectedType, TypeDescriptor actualType) {
        if (!expectedType.isInstance(actualType))
            throw new IncorrectTypeException(expectedType.toString(), actualType.toString());
    }

    @Override
    public Object visit(ASTAND_EXPR node, Object data) {
        typeCheckChildren(BooleanTypeDescriptor.class, node, data);
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTEQUAL_EXPR node, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) node.jjtGetChild(1).jjtAccept(this, data);
        if (!firstType.getClass().isInstance(secondType))
            throw new IncorrectTypeException(firstType.getTypeName(), secondType.getTypeName());
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTREL_EXPR node, Object data) {
        typeCheckChildren(NumberTypeDescriptor.class, node, data);
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTADD_SUB node, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) node.jjtGetChild(1).jjtAccept(this, data);
        typeCheckChildren(NumberTypeDescriptor.class, firstType, secondType);
        return getCorrectNumberTypeDescriptor(firstType, secondType);
    }

    private NumberTypeDescriptor getCorrectNumberTypeDescriptor(TypeDescriptor type1, TypeDescriptor type2) {
        if (type1 instanceof RealTypeDescriptor || type2 instanceof RealTypeDescriptor)
            return new RealTypeDescriptor();
        else //We only have integers if both sides is integers
            return new IntegerTypeDescriptor();
    }

    @Override
    public Object visit(ASTMUL_DIV node, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) node.jjtGetChild(1).jjtAccept(this, data);
        typeCheckChildren(NumberTypeDescriptor.class, firstType, secondType);
        return getCorrectNumberTypeDescriptor(firstType, secondType);
    }

    @Override
    public Object visit(ASTNEG_EXPR node, Object data) {
        TypeDescriptor type = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        typeCheck(BooleanTypeDescriptor.class, type);
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTMEMBER_FUNCTION_CALL node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, data);
    }

    @Override
    public Object visit(ASTINUM_VAL node, Object data) {
        return new IntegerTypeDescriptor();
    }

    @Override
    public Object visit(ASTFNUM_VAL node, Object data) {
        return new RealTypeDescriptor();
    }

    @Override
    public Object visit(ASTBOOL_VAL node, Object data) {
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTCONSTANT_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMEMBER node, Object data) {
        if (node.jjtGetNumChildren() == 0)
            return node.jjtGetChild(0).jjtAccept(this, data);
        else {
            SymbolTable st = (SymbolTable) data;
            Symbol symbol = st.retrieveSymbol(((SimpleNode)node.jjtGetChild(0)).jjtGetValue().toString());
            return data;
        }
    }

    @Override
    public Object visit(ASTFUNC_CALL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTACTUAL_PARAMETERS node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAIN node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCREATE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_ASSIGN node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVERTEX_LIST node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVERTEX node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTWEIGHT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCTION_CALL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTWHILE_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFOR_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTINTEGER_VALUE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFOREACH_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTIF_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTELSE_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTRETURN_STMT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCOLLECTION_ADT node, Object data) {
        SymbolTable symbolTable = convertToSymbolTable(data);
        if(node.jjtGetNumChildren() == 2 | node.jjtGetNumChildren() == 3) {
            Symbol symbol = createSymbolFromDCLnode(node, data);
            symbolTable.enterSymbol(symbol);

            if(node.jjtGetNumChildren() == 3) {
                //TODO: make stuff for member function call and element list
            }

        } else {
            throw new WrongAmountOfChildrenException("The collection ADT node had: " + node.jjtGetNumChildren());
        }
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTELEMENT_LIST node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCS_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVERTEX_EDGE_ATTR node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTFUNC_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTRETURN_TYPE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFORMAL_PARAMETERS node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFORMAL_PARAMETER node, Object data) {
        return defaultVisit(node, data);
    }
}
