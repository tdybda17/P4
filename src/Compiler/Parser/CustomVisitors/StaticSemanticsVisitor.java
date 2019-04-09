package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.SymbolTable.IllegalTypeException;
import Compiler.Exceptions.Visitor.IncorrectTypeException;
import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.IdentifierAttributes;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptorFactory;
import Compiler.SymbolTable.Table.SymbolTable;

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

    private SimpleNode convertToSimpleNode(Object data) {
        if(data instanceof SimpleNode) {
            return (SimpleNode) data;
        } else {
            throw new IllegalTypeException("The given data object was not a SimpleNode");
        }
    }

    private Object defaultVisit(SimpleNode node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

    //We open a new scope each time we meet a block node and then we close it right after the block is done
    @Override
    public Object visit(ASTBLOCK node, Object data) {
        SymbolTable symbolTable = convertToSymbolTable(data);
        symbolTable.openScope();
        node.childrenAccept(this, data);
        symbolTable.closeScope();
        return null;
    }

    @Override
    public Object visit(ASTASSIGN node, Object data) {
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

    //The DCL node visitor
    @Override
    public Object visit(ASTDCL node, Object data) {
        SymbolTable symbolTable = convertToSymbolTable(data);
        if(node.jjtGetNumChildren() == 2) {
            Node typeNode = node.jjtGetChild(0);
            //We call the visit method for the simple data type node.
            TypeDescriptor type = convertToTypeDescriptor(typeNode.jjtAccept(this, data));

            SimpleNode identifierNode = convertToSimpleNode(node.jjtGetChild(1));
            String id = (String) identifierNode.jjtGetValue();

            symbolTable.enterSymbol(id, new IdentifierAttributes(type));
            return null;
        } else if(node.jjtGetNumChildren() == 3) {
            //WE HAVE AN INTIALIZATION
            return null;
        } else {
            throw new WrongAmountOfChildrenException("The declaration node had: " + node.jjtGetNumChildren());
        }
    }

    @Override
    public Object visit(ASTSIMPLE_TYPES node, Object data) {
        SimpleNode simpleNode = (SimpleNode) node;
        TypeDescriptorFactory typeDescriptorFactory = new TypeDescriptorFactory();
        return typeDescriptorFactory.create((String) simpleNode.jjtGetValue());
    }

    @Override
    public Object visit(ASTIDENTIFIER node, Object data) {
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
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_ELEMENT_TYPES node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTOR_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTAND_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTEQUAL_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTREL_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTADD_SUB node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMUL_DIV node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTNEG_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMEMBER_FUNCTION_CALL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTINUM_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFNUM_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTBOOL_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCONSTANT_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMEMBER node, Object data) {
        return defaultVisit(node, data);
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
    public Object visit(ASTGRAPH_ELEMENT_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_TYPE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_ASSIGN node, Object data) {
        return defaultVisit(node, data);
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
