package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.Visitor.WrongNodeTypeException;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptorFactory;

public class VisitorOperations {

    public static TypeDescriptor getTypeDescriptorFromTypeNode(Object node){
        if (node instanceof ASTSIMPLE_TYPES || node instanceof ASTGRAPH_TYPE || node instanceof ASTCOLLECTION_TYPE) {
            SimpleNode simpleNode = convertToSimpleNode(node);
            return new TypeDescriptorFactory().create(simpleNode);
        } else {
            throw new WrongNodeTypeException(node.getClass().getSimpleName(), ASTSIMPLE_TYPES.class.getSimpleName(), ASTGRAPH_TYPE.class.getSimpleName(), ASTCOLLECTION_TYPE.class.getSimpleName());
        }
    }

    public static SimpleNode convertToSimpleNode(Object node) {
        if(node instanceof SimpleNode) {
            return (SimpleNode) node;
        } else {
            throw new IllegalArgumentException("The given data object was not a SimpleNode");
        }
    }

    public static String getIdentifierName(Node identifierNode) {
        if(identifierNode instanceof ASTIDENTIFIER) {
            SimpleNode simpleNode = (SimpleNode) identifierNode;
            return (String) simpleNode.jjtGetValue();
        } else {
            throw new IllegalArgumentException("The given node was not an IdentifierNode");
        }
    }

    public static String convertToString(Object data){
        if(data instanceof String) {
            return (String) data;
        } else {
            throw new IllegalArgumentException("The given data object was not a String but instead was " + data.getClass().getSimpleName());
        }
    }
}
