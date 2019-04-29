package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.DuplicateEdgeException;
import Compiler.Exceptions.SymbolTable.IllegalTypeException;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchFieldException;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchMethodException;
import Compiler.Exceptions.SymbolTable.UnmatchedParametersException;
import Compiler.Exceptions.Visitor.IncorrectTypeException;
import Compiler.Exceptions.Visitor.VisitorException;
import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Exceptions.Visitor.WrongNodeTypeException;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.Attributes.FunctionAttributes;
import Compiler.SymbolTable.Table.Symbol.Attributes.IdentifierAttributes;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.DirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.UndirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.NumberTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.CollectionTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptorFactory;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.VoidTypeDescriptor;
import Compiler.SymbolTable.Table.SymbolTable;

import java.util.List;
import java.util.Optional;

import java.util.*;

public class StaticSemanticsVisitor implements TestParserVisitor {
    private SymbolTable symbolTable;
    private Method currentMethod;

    //TODO: lav det sådan at når man kalder denne skal man fange WrongAmountOfChildrenException og sige det er en compiler error, da det ikke er dem som skriver programmets fejl
    //TODO: få lavet det sådan vi har IllegalType er errors til os, og IncorrectType er errors til brugeren
    public StaticSemanticsVisitor(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    private void typeCheck(TypeDescriptor expectedType, TypeDescriptor actualType) {
        //This part is added because we want to allow the users to assign both int and real to an real.
        if(expectedType.equals(new RealTypeDescriptor())) {
            if(!(actualType instanceof NumberTypeDescriptor)) {
                throw new IncorrectTypeException(NumberTypeDescriptor.class.getSimpleName(), actualType.getClass().getSimpleName());
            }
        }
        else if (!expectedType.equals(actualType))
            throw new IncorrectTypeException(expectedType.getClass().getSimpleName(), actualType.getClass().getSimpleName());
    }

    private boolean isCorrectType(TypeDescriptor expectedType, TypeDescriptor actualType) {
        return expectedType.getClass().isInstance(actualType);
    }

    private TypeDescriptor convertToTypeDescriptor(Object data){
        if(data instanceof TypeDescriptor) {
            return (TypeDescriptor) data;
        } else {
            throw new IllegalTypeException("The given data object was not a TypeDescriptor but instead was " + data);
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

    //We get the value from the type node and convert it to a type descriptor
    private TypeDescriptor getTypeDescriptorFromTypeNode(Object node){
        if (node instanceof ASTSIMPLE_TYPES | node instanceof ASTGRAPH_ELEMENT_TYPES | node instanceof ASTGRAPH_TYPE) {
            SimpleNode simpleNode = convertToSimpleNode(node);
            TypeDescriptorFactory typeDescriptorFactory = new TypeDescriptorFactory();
            return typeDescriptorFactory.create((String) simpleNode.jjtGetValue());
        } else {
            throw new WrongNodeTypeException(node.getClass().getSimpleName(), ASTSIMPLE_TYPES.class.getSimpleName(), ASTGRAPH_ELEMENT_TYPES.class.getSimpleName(), ASTGRAPH_TYPE.class.getSimpleName());
        }
    }

    //This is a help method to make it easier and more secure to get the type of an identifier symbol.
    private TypeDescriptor getTypeForIdentifierSymbol(Symbol symbol) {
        if (symbol.getAttributes() instanceof IdentifierAttributes) {
            IdentifierAttributes attributes = (IdentifierAttributes) symbol.getAttributes();
            return attributes.getType();
        } else {
            throw new IllegalTypeException("The attributes you got from your symbol was not identifier attributes");
        }
    }

    //This is a helping method used to create symbols from our declaration nodes
    private Symbol createSymbolFromDclNode(Node dclNode, Object data) {
        if(dclNode instanceof ASTSIMPLE_DCL | dclNode instanceof ASTGRAPH_ELEMENT_DCL | dclNode instanceof ASTGRAPH_DCL | dclNode instanceof ASTCOLLECTION_ADT) {
            //The first child of our dcl nodes is always their type, which we can then call the visit method on to get the child.
            Node typeNode = dclNode.jjtGetChild(0);
            TypeDescriptor type = convertToTypeDescriptor(typeNode.jjtAccept(this, data));

            //The second child is the the identifier of our declaration
            String id = getIdentifierName(dclNode.jjtGetChild(1));
            return new Symbol(id, new IdentifierAttributes(type));
        } else {
            throw new IllegalArgumentException("The given node was not an SIMPLE_DCL, GRAPH_DCL, COLLECTION_ADT or GRAPH_ELEMENT_DCL node");
        }
    }

    private Object defaultVisit(SimpleNode node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

    //We open a new scope each time we meet a block node and then we close it right after the block is done
    @Override
    public Object visit(ASTBLOCK node, Object data) {
        symbolTable.openScope();
        node.childrenAccept(this, data);
        //System.out.println(symbolTable.toString()); //TODO: fjern denne print statement når vi er færdige
        symbolTable.closeScope();
        return null;
    }

    @Override
    public Object visit(ASTSIMPLE_DCL node, Object data) {
        if(node.jjtGetNumChildren() == 2 | node.jjtGetNumChildren() == 3) {
            Symbol symbol = createSymbolFromDclNode(node, data);
            symbolTable.enterSymbol(symbol);
            if(node.jjtGetNumChildren() == 3) {
                TypeDescriptor expectedType = getTypeForIdentifierSymbol(symbol);
                Node initializationNode = node.jjtGetChild(2);

                TypeDescriptor actualType = convertToTypeDescriptor(initializationNode.jjtAccept(this, data));
                try {
                    typeCheck(expectedType, actualType);
                } catch (IncorrectTypeException e) {
                    throw new IncorrectTypeException("You declared the identifier: \'" + symbol.getName() + "\' as a type \'" + expectedType + "\' but tried to assign it a value of type \'" + actualType + '\'');
                }
            }
            return defaultVisit(node, data);
        } else {
            throw new WrongAmountOfChildrenException("The declaration node had: " + node.jjtGetNumChildren());
        }
    }

    @Override
    public Object visit(ASTSIMPLE_TYPES node, Object data) {
        return getTypeDescriptorFromTypeNode(node);
    }

    @Override
    public Object visit(ASTGRAPH_ELEMENT_DCL node, Object data) {
        if(node.jjtGetNumChildren() == 2) {
            symbolTable.enterSymbol(createSymbolFromDclNode(node, data));
        } else {
            throw new WrongAmountOfChildrenException("The graph element declaration node had: " + node.jjtGetNumChildren());
        }
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_ELEMENT_TYPES node, Object data) {
        return getTypeDescriptorFromTypeNode(node);
    }

    @Override
    public Object visit(ASTGRAPH_DCL node, Object data) {
        if(node.jjtGetNumChildren() == 2 | node.jjtGetNumChildren() == 3) {
            Symbol symbol = createSymbolFromDclNode(node, data);
            symbolTable.enterSymbol(symbol);
            if(node.jjtGetNumChildren() == 3) {
                Node initializationNode = node.jjtGetChild(2);
                return checkGraphDclInitialization(initializationNode, symbol);
            } else  {
                return defaultVisit(node, data);
            }
        } else {
            throw new WrongAmountOfChildrenException("The graph declaration node had: " + node.jjtGetNumChildren());
        }

    }

    private Object checkGraphDclInitialization(Node initializationNode, Symbol symbol) {
        TypeDescriptor graphType = getTypeForIdentifierSymbol(symbol);
        if(initializationNode instanceof ASTGRAPH_DCL_ELEMENTS) {
            if(graphType instanceof DirectedGraphTypeDescriptor) {
                return visitDirectedGraphInitialization(initializationNode);
            } else if (graphType instanceof UndirectedGraphTypeDescriptor) {
                return visitUndirectedGraphInitialization(initializationNode);
            } else {
                throw new IllegalTypeException("Your graph was neither of the type directed graph or undirected graph");
            }
        } else if (initializationNode instanceof ASTGRAPH_ASSIGN) {
            TypeDescriptor actualType = convertToTypeDescriptor(initializationNode.jjtAccept(this, symbolTable));
            try {
                typeCheck(graphType, actualType);
            } catch (IncorrectTypeException e) {
                throw new IncorrectTypeException("You had a graph of type \'" + graphType + "\' but tried to declare it with a value of the type \'" + actualType + "\'");
            }
            return initializationNode.jjtAccept(this, symbolTable);
        } else {
            throw new IllegalTypeException("The node use for initialization of your graph dcl was not an correct type");
        }
    }

    private Object visitDirectedGraphInitialization(Node initializationNode){
        Map<String, List<String>> verticesInGraph = new HashMap<>();

        for (int i = 0; i < initializationNode.jjtGetNumChildren(); i++) {
            Node child = initializationNode.jjtGetChild(i);

            List<String> vertexPair = getVertexPair(child);

            String firstVertex = vertexPair.get(0);
            String secondVertex = vertexPair.get(1);
            addVertexPairToGraph(verticesInGraph, firstVertex, secondVertex);
        }

        //We run through all the children to check the weights
        return initializationNode.jjtAccept(this, symbolTable);
    }

    private Object visitUndirectedGraphInitialization(Node initializationNode){
        Map<String, List<String>> verticesInGraph = new HashMap<>();

        for (int i = 0; i < initializationNode.jjtGetNumChildren(); i++) {
            Node child = initializationNode.jjtGetChild(i);

            List<String> vertexPair = getVertexPair(child);
            String firstVertex = vertexPair.get(0);
            String secondVertex = vertexPair.get(1);
            addVertexPairToGraph(verticesInGraph, firstVertex, secondVertex);
            addVertexPairToGraph(verticesInGraph, secondVertex, firstVertex);
        }

        //We run through all the children to check the weights
        return initializationNode.jjtAccept(this, symbolTable);
    }

    private void addVertexPairToGraph(Map<String, List<String>> verticesInGraph, String key, String value) {
        if(verticesInGraph.containsKey(key)) {
            List<String> neighbours = verticesInGraph.get(key);
            if(neighbours.contains(value)) {
                throw new DuplicateEdgeException("You tried to add more than one edge from " + key + " to " + value);
            }
            neighbours.add(value);
            verticesInGraph.put(key, neighbours);
        } else {
            List<String> neighbours = new ArrayList<>();
            neighbours.add(value);
            verticesInGraph.put(key, neighbours);
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
        return getTypeDescriptorFromTypeNode(node);
    }

    @Override
    public Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data) {
        //We just visit all the children of the graph dcl
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_VERTEX_DCL node, Object data) {
        if(node.jjtGetNumChildren() != 3) {
            throw new WrongAmountOfChildrenException("One of your vertex specifications in your graph constructor did not have 3 children but instead had: " + node.jjtGetNumChildren());
        } else {
            if(node.jjtGetChild(0) instanceof ASTIDENTIFIER && node.jjtGetChild(1) instanceof ASTIDENTIFIER) {
                Node weightNode = node.jjtGetChild(2);
                return weightNode.jjtAccept(this, data);
            } else {
                throw new VisitorException("One of the first two childs in a vertex specification was not an identifier node");
            }
        }
    }

    @Override
    public Object visit(ASTWEIGHT node, Object data) {
        if(node.jjtGetNumChildren() == 0) {
            return defaultVisit(node, data);
        } else if (node.jjtGetNumChildren() == 1) {
            TypeDescriptor actualType = convertToTypeDescriptor(node.jjtGetChild(0).jjtAccept(this, data));
            try {
                typeCheck(new RealTypeDescriptor(), actualType);
            } catch (IncorrectTypeException e) {
                throw new IncorrectTypeException("The type of a weight in your graph was not real but: " + actualType);
            }
            return defaultVisit(node, data);
        } else {
            throw new WrongAmountOfChildrenException("The weight node had a wrong amount of children, which was: " + node.jjtGetNumChildren());
        }
    }

    @Override
    public Object visit(ASTGRAPH_ASSIGN node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, data);
    }

    @Override
    public Object visit(ASTIDENTIFIER node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTASSIGN node, Object data) {
        if(node.jjtGetNumChildren() != 2) {
            throw new WrongAmountOfChildrenException("The assignment node did not have two children");
        }
        Node leftNode = node.jjtGetChild(0);
        Node rightNode = node.jjtGetChild(1);

        TypeDescriptor expectedType = convertToTypeDescriptor(leftNode.jjtAccept(this, symbolTable));
        TypeDescriptor actualType = convertToTypeDescriptor(rightNode.jjtAccept(this, symbolTable));
        try {
            typeCheck(expectedType, actualType);
        } catch (IncorrectTypeException e) {
            //TODO: få navnet ud af venstre node
            throw new IncorrectTypeException("You tried to assign a value of the type \'" + actualType + "\' to ...., instead of the expected type \'" + expectedType + "\'");
        }
        return null;
    }

    //TODO: få lavet map
    @Override
    public Object visit(ASTMAP node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCOLLECTION_ADT node, Object data) {
        SymbolTable symbolTable = convertToSymbolTable(data);
        if(node.jjtGetNumChildren() == 2 | node.jjtGetNumChildren() == 3) {
            Symbol symbol = createSymbolFromDclNode(node, data);
            symbolTable.enterSymbol(symbol);

            if(node.jjtGetNumChildren() == 3) {
                checkCollectionInitialization(node.jjtGetChild(2), symbol, symbolTable);
            }

        } else {
            throw new WrongAmountOfChildrenException("The collection ADT node had: " + node.jjtGetNumChildren());
        }
        return defaultVisit(node, data);
    }

    private void checkCollectionInitialization(Node initializationNode, Symbol symbol, Object data){
        TypeDescriptor expectedType, actualType;
        if(initializationNode instanceof ASTELEMENT_LIST) {
            expectedType = getElementType(getTypeForIdentifierSymbol(symbol));
            actualType = convertToTypeDescriptor(initializationNode.jjtAccept(this, data));
        } else if(initializationNode instanceof ASTMEMBER_FUNCTION_CALL) {
            expectedType =  getTypeForIdentifierSymbol(symbol);
            actualType = convertToTypeDescriptor(initializationNode.jjtAccept(this, data));
        } else {
            throw new WrongNodeTypeException(initializationNode.getClass().getSimpleName(), ASTELEMENT_LIST.class.getSimpleName(), ASTMEMBER_FUNCTION_CALL.class.getSimpleName());
        }
        try {
            typeCheck(expectedType, actualType);
        } catch (IncorrectTypeException e) {
            throw new IncorrectTypeException("When trying to declare the collection \'" + symbol.getName() +"\' you expected type \'" + expectedType + "\' but got \'" + actualType + "\'");
        }
    }

    private TypeDescriptor getElementType(TypeDescriptor collectionType){
        if(collectionType instanceof CollectionTypeDescriptor) {
            CollectionTypeDescriptor collectionTypeDescriptor = (CollectionTypeDescriptor) collectionType;
            return collectionTypeDescriptor.getElementType();
        } else {
            throw new IllegalTypeException("The type of your collection was not a collection type but instead: " + collectionType);
        }
    }

    @Override
    public Object visit(ASTELEMENT_LIST node, Object data) {
        int amtChildren = node.jjtGetNumChildren();
        if(amtChildren < 1) {
            throw new WrongAmountOfChildrenException("Your element list when trying to declare an collection had less than one child");
        }
        TypeDescriptor expectedType = convertToTypeDescriptor(node.jjtGetChild(0).jjtAccept(this, data));

        for(int i = 1; i < amtChildren; i++) {
            TypeDescriptor actualType = convertToTypeDescriptor(node.jjtGetChild(i).jjtAccept(this, data));
            try {
                typeCheck(expectedType, actualType);
            } catch (IncorrectTypeException e) {
                throw new IncorrectTypeException("When trying to initialize a collection you had more than one element type, you had both: \'" + actualType +"\' and \'" + expectedType + "\'");
            }
        }
        return expectedType;
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
            throw new IllegalTypeException("Somehow you got an none collection type descriptor from your collection type declaration");
        }
    }

    @Override
    public Object visit(ASTOR_EXPR node, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) node.jjtGetChild(1).jjtAccept(this, data);
        if(!(firstType instanceof BooleanTypeDescriptor)) {
            throw new IncorrectTypeException("The left value of an OR expression was not a boolean value");
        } else if(!(secondType instanceof  BooleanTypeDescriptor)) {
            throw new IncorrectTypeException("The right value of an OR expression was not a boolean value");
        }
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTAND_EXPR node, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) node.jjtGetChild(1).jjtAccept(this, data);
        if(!(firstType instanceof BooleanTypeDescriptor)) {
            throw new IncorrectTypeException("The left value of an AND expression was not a boolean value");
        } else if(!(secondType instanceof  BooleanTypeDescriptor)) {
            throw new IncorrectTypeException("The right value of an AND expression was not a boolean value");
        }
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTEQUAL_EXPR node, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) node.jjtGetChild(1).jjtAccept(this, data);
        if (!firstType.equals(secondType))
            throw new IncorrectTypeException("You tried to assert equality between an object of type \'" + firstType + "\' and an object of type \'" + secondType + "\'");
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTREL_EXPR node, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) node.jjtGetChild(1).jjtAccept(this, data);
        if(!(firstType instanceof NumberTypeDescriptor)) {
            throw new IncorrectTypeException("The left value of an relation operator was not a number");
        } else if(!(secondType instanceof  NumberTypeDescriptor)) {
            throw new IncorrectTypeException("The right value of an relation operator was not a number");
        }
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTADD_SUB node, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) node.jjtGetChild(1).jjtAccept(this, data);
        if(!(firstType instanceof NumberTypeDescriptor)) {
            throw new IncorrectTypeException("The left value of an ADD or SUB expression was not a number");
        } else if(!(secondType instanceof  NumberTypeDescriptor)) {
            throw new IncorrectTypeException("The right value of an ADD or SUB expression was not a number");
        }
        return getCorrectNumberTypeDescriptor(firstType, secondType);
    }

    @Override
    public Object visit(ASTMUL_DIV node, Object data) {
        TypeDescriptor firstType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        TypeDescriptor secondType = (TypeDescriptor) node.jjtGetChild(1).jjtAccept(this, data);
        if(!(firstType instanceof NumberTypeDescriptor)) {
            throw new IncorrectTypeException("The left value of an MUL or DIV expression was not a number");
        } else if(!(secondType instanceof  NumberTypeDescriptor)) {
            throw new IncorrectTypeException("The right value of an MUL or DIV expression was not a number");
        }
        return getCorrectNumberTypeDescriptor(firstType, secondType);
    }

    private NumberTypeDescriptor getCorrectNumberTypeDescriptor(TypeDescriptor type1, TypeDescriptor type2) {
        if (type1 instanceof RealTypeDescriptor || type2 instanceof RealTypeDescriptor)
            return new RealTypeDescriptor();
        else //We only have integers if both sides is integers
            return new IntegerTypeDescriptor();
    }

    @Override
    public Object visit(ASTNEG_EXPR node, Object data) {
        TypeDescriptor type = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        typeCheck(new BooleanTypeDescriptor(), type);
        return new BooleanTypeDescriptor();
    }

    @Override
    public Object visit(ASTMEMBER_FUNCTION_CALL node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, symbolTable);
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

    //TODO: gik på denne
    @Override
    public Object visit(ASTCONSTANT_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMEMBER node, Object data) {
        TypeDescriptor memberType;
        String identifier = getValueStringOfChild(node, 0);
        if (data instanceof SymbolTable) {
            SymbolTable st = (SymbolTable) data;
            Symbol symbol = st.retrieveSymbol(identifier);
            memberType = getTypeForIdentifierSymbol(symbol);
        }
        else if (data instanceof TypeDescriptor) {
            Field field = getFieldFromTypeDescriptor((TypeDescriptor) data, identifier);
            memberType = field.getType();
        }
        else if (data instanceof FunctionAttributes) {
            Field field = getFieldFromTypeDescriptor(((FunctionAttributes) data).getReturnType(), identifier);
            memberType = field.getType();
        }
        else
            throw new VisitorException("data in ASTMEMBER visitor method is of invalid type: " + data);

        if (node.jjtGetNumChildren() > 1)
            return node.jjtGetChild(1).jjtAccept(this, memberType);
        else {
            return memberType;
        }
    }

    private Field getFieldFromTypeDescriptor(TypeDescriptor td, String identifier) {
        if (td instanceof ClassTypeDescriptor) {
            Optional<Field> field = ((ClassTypeDescriptor) td).getFields().stream().filter(e -> e.getFieldName().equals(identifier)).findAny();
            if (field.isPresent())
                return field.get();
            else
                throw new NoSuchFieldException(td, identifier);
        }
        else
            throw new NoSuchFieldException(td, identifier);
    }

    private String getValueStringOfChild(Node node, int index) {
        if (node.jjtGetNumChildren() <= index)
            throw new VisitorException("Tried to get child with index " + index + " from an " + node.toString()
                    + " node but it has " + node.jjtGetNumChildren() + " children");
        else {
            Object value = ((SimpleNode)node.jjtGetChild(index)).jjtGetValue();
            if (value == null)
                throw new VisitorException("Tried to get value of an " + node.jjtGetChild(index).toString() + " node but it has no value");
            return value.toString();
        }
    }

    @Override
    public Object visit(ASTFUNC_CALL node, Object data) {
        FunctionAttributes attributes;
        String identifier = getValueStringOfChild(node, 0);
        if (data instanceof SymbolTable) {
            SymbolTable st = (SymbolTable) data;
            Symbol symbol = st.retrieveSymbol(identifier);
            attributes = getFunctionAttributes(symbol);
        } else if (data instanceof TypeDescriptor) {
            Method method = getMethodFromTypeDescriptor((TypeDescriptor) data, identifier);
            attributes = new FunctionAttributes(method.getReturnType(), method.getParameterTypes());
        } else if (data instanceof FunctionAttributes) {
            Method method = getMethodFromTypeDescriptor(((FunctionAttributes) data).getReturnType(), identifier);
            attributes = new FunctionAttributes(method.getReturnType(), method.getParameterTypes());
        } else
            throw new VisitorException("data in FUNC_CALL visitor method is of invalid type: " + data);

        node.jjtGetChild(1).jjtAccept(this, attributes); //type check parameters

        if (node.jjtGetNumChildren() > 2)
            return node.jjtGetChild(2).jjtAccept(this, attributes);
        else {
            return attributes.getReturnType();
        }
    }

    private FunctionAttributes getFunctionAttributes(Symbol symbol) {
        Attributes attributes = symbol.getAttributes();
        if (attributes instanceof FunctionAttributes)
            return (FunctionAttributes) attributes;
        else
            throw new NoSuchMethodException(symbol);
    }

    private Method getMethodFromTypeDescriptor(TypeDescriptor td, String id) {
        if (td instanceof ClassTypeDescriptor) {
            Optional<Method> method = ((ClassTypeDescriptor) td).getMethods().stream().filter(e -> e.getMethodName().equals(id)).findAny();
            if (method.isPresent())
                return method.get();
            else
                throw new NoSuchMethodException(td, id);
        }
        else
            throw new NoSuchMethodException(td, id);
    }

    @Override
    public Object visit(ASTACTUAL_PARAMETERS node, Object data) {
        FunctionAttributes attributes = (FunctionAttributes) data;
        List<TypeDescriptor> formalParameters = attributes.getParameterTypes();
        int numActualParameters = node.jjtGetNumChildren();
        if (formalParameters == null) {
            if (numActualParameters == 0)
                return data;
            else
                throw new UnmatchedParametersException("Error: Tried to pass parameters to the parameterless function");
        }
        else if (numActualParameters == formalParameters.size()) {
            for (int i = 0; i < numActualParameters; i++) {
                TypeDescriptor formalParameterType = formalParameters.get(i);
                TypeDescriptor actualParameterType = (TypeDescriptor) node.jjtGetChild(i).jjtAccept(this, data);

                if (formalParameterType instanceof RealTypeDescriptor && actualParameterType instanceof NumberTypeDescriptor)
                    continue;
                if (!formalParameterType.getTypeName().equals(actualParameterType.getTypeName()))
                    throw new UnmatchedParametersException(formalParameterType, actualParameterType);
            }
            return data;
        }
        else
            throw new UnmatchedParametersException("Error: Tried to parse " + numActualParameters + " parameters to a function that requires " + formalParameters.size() + " parameters");
    }

    @Override
    public Object visit(ASTMAIN node, Object data) {
        currentMethod = new Method("main", new VoidTypeDescriptor(), new ArrayList<>());

        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCREATE node, Object data) {
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

    //TODO: få lavet type check af function call
    @Override
    public Object visit(ASTFUNCTION_CALL node, Object data) {
        return defaultVisit(node, data);
    }


    //TODO: få lavet type check af while
    @Override
    public Object visit(ASTWHILE_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    //TODO: få lavet type check af for
    @Override
    public Object visit(ASTFOR_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTINTEGER_VALUE node, Object data) {
        return defaultVisit(node, data);
    }

    //TODO: få lavet type check af for-each
    @Override
    public Object visit(ASTFOREACH_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }


    //TODO: få lavet type check af if statement
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
        if (node.jjtGetNumChildren() == 0) {
            if (currentMethod.getReturnType() instanceof VoidTypeDescriptor)
                return data;
            else
                throw new IncorrectTypeException("Missing return value in a return statement located in function '" + currentMethod.getMethodName() +
                        ". Expected return value of type " + currentMethod.getReturnType().getTypeName());
        }
        else {
            TypeDescriptor actualReturnType = convertToTypeDescriptor(node.jjtGetChild(0).jjtAccept(this, data));
            TypeDescriptor expectedReturnType = currentMethod.getReturnType();
            if (isCorrectType(expectedReturnType, actualReturnType))
                return data;
            else
                throw new IncorrectTypeException("Illegal return value in function " + currentMethod.getMethodName() +
                        ". Expected return value of type " + expectedReturnType.getTypeName() + " but it was of type " +
                        actualReturnType.getTypeName());
        }
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
        TypeDescriptor returnType = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        String methodName = getValueStringOfChild(node, 1);
        currentMethod = new Method(methodName, returnType, new ArrayList<>());

        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTRETURN_TYPE node, Object data) {
        if (node.jjtGetNumChildren() == 0)
            return new VoidTypeDescriptor();
        else
            return node.jjtGetChild(0).jjtAccept(this, data);
    }

    @Override
    public Object visit(ASTFORMAL_PARAMETERS node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFORMAL_PARAMETER node, Object data) {
        return defaultVisit(node, data);
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

    //This is the object type node used in the vertex/edge attribute declaration so we ignore it in this visitor
    @Override
    public Object visit(ASTOBJECT_TYPE node, Object data) {
        return defaultVisit(node, data);
    }
}
