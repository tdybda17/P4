package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.DuplicateEdgeException;
import Compiler.Exceptions.NotImplementedException;
import Compiler.Exceptions.SymbolTable.IllegalTypeException;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchFieldException;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchMethodException;
import Compiler.Exceptions.SymbolTable.SymbolTableException;
import Compiler.Exceptions.SymbolTable.UnmatchedParametersException;
import Compiler.Exceptions.Visitor.*;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.Attributes.FunctionAttributes;
import Compiler.SymbolTable.Table.Symbol.Attributes.IdentifierAttributes;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.*;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.MapTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.DirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.UndirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.NumberTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.CollectionTypeDescriptor;
import Compiler.SymbolTable.Table.SymbolTable;

import java.util.List;
import java.util.Optional;

import java.util.*;

public class StaticSemanticsVisitor implements TestParserVisitor {
    private SymbolTable symbolTable;
    private String currentFunctionName;
    private TypeDescriptor currentFunctionReturnType;

    //TODO: lav det sådan at når man kalder denne skal man fange WrongAmountOfChildrenException + IllegalArgumentException. Derefter skal det siges der var en compiler fejl og ikke en fejl i den skrevne kode.
    public StaticSemanticsVisitor(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    private Object illegalVisit(Node node) {
        throw new IllegalVisitException("There is not visit method for nodes of the type \'" + node.getClass().getSimpleName() + "\' because they should have been removed by the tree optimizer");
    }

    private void typeCheck(TypeDescriptor expectedType, TypeDescriptor actualType) {
        //This part is added because we want to allow the users to assign both int and real to a real.
        if(expectedType.equals(new RealTypeDescriptor())) {
            if(!(actualType instanceof NumberTypeDescriptor)) {
                throw new IncorrectTypeException(NumberTypeDescriptor.class.getSimpleName(), actualType.getClass().getSimpleName());
            }
        }
        else if (!expectedType.equals(actualType))
            throw new IncorrectTypeException(expectedType.toString(), actualType.toString());
    }

    private boolean isCorrectType(TypeDescriptor expectedType, TypeDescriptor actualType) {
        return expectedType.getClass().isInstance(actualType);
    }

    private TypeDescriptor convertToTypeDescriptor(Object data){
        if(data instanceof TypeDescriptor) {
            return (TypeDescriptor) data;
        } else {
            throw new IllegalArgumentException("The given data object was not a TypeDescriptor but instead was " + data);
        }
    }

    private SymbolTable convertToSymbolTable(Object data) {
        if(data instanceof SymbolTable) {
            return (SymbolTable) data;
        } else {
            throw new IllegalArgumentException("The given data object was not a SymbolTable");
        }
    }

    private SimpleNode convertToSimpleNode(Object node) {
        if(node instanceof SimpleNode) {
            return (SimpleNode) node;
        } else {
            throw new IllegalArgumentException("The given data object was not a SimpleNode");
        }
    }

    private String getIdentifierName(Node identifierNode) {
        if(identifierNode instanceof ASTIDENTIFIER) {
            SimpleNode simpleNode = (SimpleNode) identifierNode;
            return (String) simpleNode.jjtGetValue();
        } else {
            throw new IllegalArgumentException("The given node was not an IdentifierNode");
        }
    }

    //We get the value from the type node and convert it to a type descriptor
    private TypeDescriptor getTypeDescriptorFromTypeNode(Object node){
        if (node instanceof ASTSIMPLE_TYPES | node instanceof ASTGRAPH_TYPE) {
            SimpleNode simpleNode = convertToSimpleNode(node);
            TypeDescriptorFactory typeDescriptorFactory = new TypeDescriptorFactory();
            return typeDescriptorFactory.create((String) simpleNode.jjtGetValue());
        } else {
            throw new WrongNodeTypeException(node.getClass().getSimpleName(), ASTSIMPLE_TYPES.class.getSimpleName(), ASTGRAPH_TYPE.class.getSimpleName());
        }
    }

    //This is a help method to make it easier and more secure to get the type of an identifier symbol.
    private TypeDescriptor getTypeForIdentifierSymbol(Symbol symbol) {
        if (symbol.getAttributes() instanceof IdentifierAttributes) {
            IdentifierAttributes attributes = (IdentifierAttributes) symbol.getAttributes();
            return attributes.getType();
        } else {
            throw new IllegalArgumentException("The given symbol was not an identifier symbol");
        }
    }

    //This is a helping method used to create symbols from our declaration nodes
    private Symbol createSymbolFromDclNode(Node dclNode, Object data) {
        if(dclNode instanceof ASTSIMPLE_DCL | dclNode instanceof ASTGRAPH_DCL | dclNode instanceof ASTCOLLECTION_ADT) {
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
        symbolTable.closeScope();
        return null;
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

        try {
            checkIfLeftValueIsAFunctionCall(leftNode);
        } catch (AssignmentException e) {
            String leftNodeName = getLeftNodeName(leftNode);
            throw new AssignmentException("It is not possible to assign values to function calls, which was tried the assignment of: " + leftNodeName + " = ...");
        }

        TypeDescriptor expectedType = convertToTypeDescriptor(leftNode.jjtAccept(this, symbolTable));
        TypeDescriptor actualType = convertToTypeDescriptor(rightNode.jjtAccept(this, symbolTable));
        try {
            typeCheck(expectedType, actualType);
        } catch (IncorrectTypeException e) {
            String leftNodeName = getLeftNodeName(leftNode);
            throw new IncorrectTypeException("You tried to assign a value of the type \'" + actualType + "\' to \'" + leftNodeName + "\' instead of the expected type \'" + expectedType + "\'");
        }
        return null;
    }

    private void checkIfLeftValueIsAFunctionCall(Node leftNode){
        int amtOfChildren = leftNode.jjtGetNumChildren();

        if(leftNode instanceof ASTVARIABLE | leftNode instanceof ASTFIELD_ACCESS) {
            if(amtOfChildren == 1) {
                return; //We get here if we end the left node with a variable or field access node
            } else if (amtOfChildren == 2) {
                checkIfLeftValueIsAFunctionCall(leftNode.jjtGetChild(1));
            } else {
                throw new WrongAmountOfChildrenException("Your variable node had an wrong amount of children which was: " + amtOfChildren);
            }
        } else if (leftNode instanceof ASTFUNCTION_CALL) {
            if(amtOfChildren == 2) {
                throw new AssignmentException("You cannot assign a value to a function call");
            } else if (amtOfChildren == 3) {
                checkIfLeftValueIsAFunctionCall(leftNode.jjtGetChild(2));
            } else {
                throw new WrongAmountOfChildrenException("Your function call node had an wrong amount of children which was: " + amtOfChildren);
            }
        } else {
            throw new WrongNodeTypeException(leftNode.getClass().getSimpleName(), ASTVARIABLE.class.getSimpleName(), ASTFIELD_ACCESS.class.getSimpleName(), ASTFUNCTION_CALL.class.getSimpleName());
        }
    }

    private String getLeftNodeName(Node leftNode) {
        int amtOfChildren = leftNode.jjtGetNumChildren();

        if(leftNode instanceof ASTVARIABLE | leftNode instanceof ASTFIELD_ACCESS) {
            if(amtOfChildren == 1) {
                return getIdentifierName(leftNode.jjtGetChild(0));
            } else if (amtOfChildren == 2) {
                return getIdentifierName(leftNode.jjtGetChild(0)) + "." + getLeftNodeName(leftNode.jjtGetChild(1));
            } else {
                throw new WrongAmountOfChildrenException("Your variable node had an wrong amount of children which was: " + amtOfChildren);
            }
        } else if (leftNode instanceof ASTFUNCTION_CALL) {
            if(amtOfChildren == 2) {
                return getIdentifierName(leftNode.jjtGetChild(0)) + "()";
            } else if (amtOfChildren == 3) {
                return getIdentifierName(leftNode.jjtGetChild(0)) + "()." + getLeftNodeName(leftNode.jjtGetChild(2));
            } else {
                throw new WrongAmountOfChildrenException("Your function call node had an wrong amount of children which was: " + amtOfChildren);
            }
        } else {
            throw new WrongNodeTypeException(leftNode.getClass().getSimpleName(), ASTVARIABLE.class.getSimpleName(), ASTFIELD_ACCESS.class.getSimpleName(), ASTFUNCTION_CALL.class.getSimpleName());
        }
    }

    //TODO: få lavet så denne tjekker du faktisk har et funktion call
    @Override
    public Object visit(ASTFUNCTION_CALL_STMT node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, symbolTable);
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
    public Object visit(ASTGRAPH_DCL node, Object data) {
        if(node.jjtGetNumChildren() == 2) {
            Symbol symbol = createSymbolFromDclNode(node, data);
            symbolTable.enterSymbol(symbol);
            return defaultVisit(node, data);
        } else if (node.jjtGetNumChildren() == 3) {
            throw new WrongAmountOfChildrenException("The graph declaration node had: " + node.jjtGetNumChildren() + ", which happens if you did not run the tree optimizer first");
        }
        else {
            throw new WrongAmountOfChildrenException("The graph declaration node had: " + node.jjtGetNumChildren());
        }
    }

    @Override
    public Object visit(ASTGRAPH_TYPE node, Object data) {
        return getTypeDescriptorFromTypeNode(node);
    }

    @Override
    public Object visit(ASTCOLLECTION_ADT node, Object data) {
        if(node.jjtGetNumChildren() == 2) {
            Symbol symbol = createSymbolFromDclNode(node, data);
            symbolTable.enterSymbol(symbol);
        }else if (node.jjtGetNumChildren() == 3) {
            throw new WrongAmountOfChildrenException("The collection ADT node had: " + node.jjtGetNumChildren() + ", which happens if you did not run the tree optimizer first");
        } else {
            throw new WrongAmountOfChildrenException("The collection ADT node had: " + node.jjtGetNumChildren());
        }
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
            throw new IllegalArgumentException("Somehow you got an none collection type descriptor from your collection type node");
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
        if (!firstType.equals(secondType)) {
            if (firstType instanceof NumberTypeDescriptor | secondType instanceof NumberTypeDescriptor) {
                return new BooleanTypeDescriptor();
            }
            throw new IncorrectTypeException("You tried to assert equality between an object of type \'" + firstType + "\' and an object of type \'" + secondType + "\'");
        }
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
    public Object visit(ASTMUL_DIV_MOD node, Object data) {
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
    public Object visit(ASTCOLOR_VAL node, Object data) {
        return new ColorTypeDescriptor();
    }

    @Override
    public Object visit(ASTLABEL_VAL node, Object data) {
        return new LabelTypeDescriptor();
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

    //TODO: kig på denne
    @Override
    public Object visit(ASTCONSTANT_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAIN node, Object data) {
        currentFunctionName = "main";
        currentFunctionReturnType = new VoidTypeDescriptor();
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTWHILE_STATEMENT node, Object data) {
        TypeDescriptor actualConditionType = convertToTypeDescriptor(node.jjtGetChild(0).jjtAccept(this, symbolTable));
        typeCheck(new BooleanTypeDescriptor(), actualConditionType);
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFOREACH_STATEMENT node, Object data) {
        // check that id does not already exist
        String id = getValueStringOfChild(node, 0);
        if (symbolTable.containsSymbol(id))
            throw new IllegalTypeException("Error: Tried to use the already declared variable '" + id + "' as iterator variable in foreach loop");

        // check that second child returns collection type
        TypeDescriptor type = convertToTypeDescriptor(node.jjtGetChild(1).jjtAccept(this, symbolTable));
        if (!(type instanceof CollectionTypeDescriptor))
            throw new IllegalTypeException("Error: Tried to iterate through non-collection type '" + type.getTypeName() + "' in foreach loop");

        // open node scope, add id to symbol table, accept all of third child's (block's) children, close node scope
        symbolTable.openScope();
        symbolTable.enterSymbol(id, new IdentifierAttributes(((CollectionTypeDescriptor) type).getElementType()));
        defaultVisit((SimpleNode) node.jjtGetChild(2), data);
        symbolTable.closeScope();
        return data;
    }

    @Override
    public Object visit(ASTIF_STATEMENT node, Object data) {
        TypeDescriptor actualConditionType = convertToTypeDescriptor(node.jjtGetChild(0).jjtAccept(this, symbolTable));
        typeCheck(new BooleanTypeDescriptor(), actualConditionType);
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTELSE_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTRETURN_STMT node, Object data) {
        if (node.jjtGetNumChildren() == 0) {
            if (currentFunctionReturnType instanceof VoidTypeDescriptor)
                return data;
            else
                throw new IncorrectTypeException("Missing return value in a return statement located in function '" + currentFunctionName +
                        "'. Expected return value of type " + currentFunctionReturnType.getTypeName());
        }
        else {
            TypeDescriptor actualReturnType = convertToTypeDescriptor(node.jjtGetChild(0).jjtAccept(this, data));
            TypeDescriptor expectedReturnType = currentFunctionReturnType;
            if (isCorrectType(expectedReturnType, actualReturnType))
                return data;
            else
                throw new IncorrectTypeException("Illegal return value in function " + currentFunctionName +
                        ". Expected return value of type " + expectedReturnType.getTypeName() + " but it was of type " +
                        actualReturnType.getTypeName());
        }
    }

    @Override
    public Object visit(ASTVARIABLE node, Object data) {
        Symbol symbol = symbolTable.retrieveSymbol(getValueStringOfChild(node, 0));
        TypeDescriptor variableType = getTypeForIdentifierSymbol(symbol);
        if (node.jjtGetNumChildren() > 1)
            return node.jjtGetChild(1).jjtAccept(this, variableType);
        else
            return variableType;
    }

    @Override
    public Object visit(ASTFIELD_ACCESS node, Object data) {
        TypeDescriptor fieldType;
        String identifier = getValueStringOfChild(node, 0);
        if (data instanceof TypeDescriptor) {
            Field field = getFieldFromTypeDescriptor((TypeDescriptor) data, identifier);
            fieldType = field.getType();
        }
        else if (data instanceof FunctionAttributes) {
            Field field = getFieldFromTypeDescriptor(((FunctionAttributes) data).getReturnType(), identifier);
            fieldType = field.getType();
        }
        else
            throw new VisitorException("data in ASTFIELD_ACCESS visitor method is of invalid type: " + data.getClass());

        if (node.jjtGetNumChildren() > 1)
            return node.jjtGetChild(1).jjtAccept(this, fieldType);
        else
            return fieldType;
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
    public Object visit(ASTFUNCTION_CALL node, Object data) {
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
            throw new VisitorException("data in FUNCTION_CALL visitor method is of invalid type: " + data);

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
                typeCheck(formalParameterType, actualParameterType);
            }
            return data;
        }
        else
            throw new UnmatchedParametersException("Error: Tried to parse " + numActualParameters + " parameters to a function that requires " + formalParameters.size() + " parameters");
    }

    @Override
    public Object visit(ASTFUNCS_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNC_DCL node, Object data) {
        if(node.jjtGetNumChildren() != 4) {
            throw new WrongAmountOfChildrenException("A function declaration node did not have 4 children but instead had: " + node.jjtGetNumChildren());
        }

        String functionName = getValueStringOfChild(node, 1);
        try {
            Symbol symbol = symbolTable.retrieveSymbol(functionName);
            if (symbol.getAttributes() instanceof FunctionAttributes) {
                currentFunctionReturnType = ((FunctionAttributes) symbol.getAttributes()).getReturnType();
            }
            currentFunctionName = functionName;
        } catch (SymbolTableException e) {
            throw new VisitorException("The function declarations have not yet been entered in the symbol table. (Remember to run function visitor on the tree)");
        }

        //We need to do this because we want our formal parameters to be in the scope of the function block
        symbolTable.openScope();
        Node formalParametersNode = node.jjtGetChild(2);
        formalParametersNode.jjtAccept(this, data);
        Node blockNode = node.jjtGetChild(3);
        blockNode.jjtAccept(this, data);
        symbolTable.closeScope();
        return null;
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
        TypeDescriptor type = getTypeDescriptorFromTypeNode(node.jjtGetChild(0));
        String symbolName = getIdentifierName(node.jjtGetChild(1));

        Symbol symbol = new Symbol(symbolName, new IdentifierAttributes(type));
        symbolTable.enterSymbol(symbol);
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTDCL node, Object data) {
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

    //TODO: få lavet map
    public Object visit(ASTMAP node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP_ADT node, Object data) {
        // get key-value type and create MapTypeDescriptor
        TypeDescriptor keyType = getTypeDescriptorFromTypeNode(node.jjtGetChild(0));
        TypeDescriptor valueType = getTypeDescriptorFromTypeNode(node.jjtGetChild(1));
        MapTypeDescriptor mapType = new MapTypeDescriptor(keyType, valueType);

        // add id to symbol table
        String id = getValueStringOfChild(node, 2);
        symbolTable.enterSymbol(id, new IdentifierAttributes(mapType));

        // accept 4th child with parameter MapTypeDescriptor
        node.jjtGetChild(3).jjtAccept(this, mapType);
        return data;
    }

    @Override
    public Object visit(ASTMAP_ASSIGN node, Object data) {
        MapTypeDescriptor expectedType = (MapTypeDescriptor) data;
        TypeDescriptor actualType = convertToTypeDescriptor(node.jjtGetChild(0).jjtAccept(this, symbolTable));
        typeCheck(expectedType, actualType);
        return data;
    }

    @Override
    public Object visit(ASTMAP_ELEMENT_LIST node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTKEY_VALUE_PAIR node, Object data) {
        MapTypeDescriptor mapType = (MapTypeDescriptor) data;

        TypeDescriptor keyType = convertToTypeDescriptor(node.jjtGetChild(0).jjtAccept(this, symbolTable));
        typeCheck(mapType.getKeyType(), keyType);

        TypeDescriptor valueType = convertToTypeDescriptor(node.jjtGetChild(1).jjtAccept(this, symbolTable));
        typeCheck(mapType.getElementType(), valueType);

        return data;
    }

    /*=====================================================================================
                All of the visit methods in this section are not needed
                because they are related to the vertex and edge attributes
                which is handled in the function visitor
    =======================================================================================*/

    @Override
    public Object visit(ASTVERTEX_EDGE_ATTR node, Object data) {
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
    public Object visit(ASTATTRIBUTES_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    /*=====================================================================================
                    ALL THE VISIT METHODS BELOW THIS POINT SHOULD NOT BE IMPLEMENTED
        =======================================================================================*/
    @Override
    public Object visit(ASTFOR_STATEMENT node, Object data) {
        return illegalVisit(node);
    }

    @Override
    public Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data) {
        return illegalVisit(node);
    }

    @Override
    public Object visit(ASTGRAPH_ASSIGN node, Object data) {
        return illegalVisit(node);
    }

    @Override
    public Object visit(ASTVERTEX_LIST node, Object data) {
        return illegalVisit(node);
    }

    @Override
    public Object visit(ASTVERTEX node, Object data) {
        return illegalVisit(node);
    }

    @Override
    public Object visit(ASTGRAPH_VERTEX_DCL node, Object data) {
        return illegalVisit(node);
    }

    @Override
    public Object visit(ASTWEIGHT node, Object data) {
        return illegalVisit(node);
    }


    @Override
    public Object visit(ASTCOLLECTION_ASSIGN node, Object data) {
        return illegalVisit(node);
    }

    @Override
    public Object visit(ASTELEMENT_LIST node, Object data) {
        return illegalVisit(node);
    }

}
