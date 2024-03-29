package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.Visitor.IllegalVisitException;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.Attributes.FunctionAttributes;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.CollectionTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.EdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptorFactory;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.VoidTypeDescriptor;
import Compiler.SymbolTable.Table.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class FunctionVisitor implements Visitor {
    private Object defaultVisit(SimpleNode node, Object data){
        node.childrenAccept(this, data);
        return data;
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
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            fields.addAll((List<Field>) node.jjtGetChild(i).jjtAccept(this, data));
        }
        for (Field field : fields) {
            VertexTypeDescriptor.addUserAttribute(field);
        }
        return data;
    }

    @Override
    public Object visit(ASTEDGE_ATTRIBUTES node, Object data) {
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            fields.addAll((List<Field>) node.jjtGetChild(i).jjtAccept(this, data));
        }
        for (Field field : fields) {
            EdgeTypeDescriptor.addUserAttribute(field);
        }
        return data;
    }

    @Override
    public Object visit(ASTATTRIBUTES_DCL node, Object data) {
        return createFieldFromChildren(node, data);
    }

    private List<Field> createFieldFromChildren(Node node, Object data) {
        TypeDescriptor type = (TypeDescriptor) node.jjtGetChild(0).jjtAccept(this, data);
        String fieldName = ((SimpleNode)node.jjtGetChild(1)).jjtGetValue().toString();

        return List.of(new Field(fieldName, type));
    }


    @Override
    public Object visit(ASTSIMPLE_TYPES node, Object data) {
        return new TypeDescriptorFactory().create(node);
    }

    @Override
    public Object visit(ASTIDENTIFIER node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCOLLECTION_TYPE node, Object data) {
        return new TypeDescriptorFactory().create(node);
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
    public Object visit(ASTMUL_DIV_MOD node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTNEG_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCOLOR_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTLABEL_VAL node, Object data) {
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
    public Object visit(ASTVARIABLE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFIELD_ACCESS node, Object data) {
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
    public Object visit(ASTBLOCK node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_TYPE node, Object data) {
        return defaultVisit(node, data);
    }


    @Override
    public Object visit(ASTASSIGN node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCTION_CALL_STMT node, Object data) {
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
    public Object visit(ASTMAP_ADT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP_ELEMENT_LIST node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTKEY_VALUE_PAIR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCS_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVERTEX_EDGE_ATTR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNC_DCL node, Object data) {
        SymbolTable symbolTable = (SymbolTable) data;
        Attributes functionAttributes = new FunctionAttributes(getReturnType(node), getParameterTypes(node));
        // getting the right child
        SimpleNode idNode = (SimpleNode) node.jjtGetChild(1);
        String nodeName = (String) idNode.jjtGetValue();

        symbolTable.enterSymbol(nodeName, functionAttributes);
        return data;
    }

    private TypeDescriptor getReturnType(SimpleNode node) {
        SimpleNode returnNode = (SimpleNode) node.jjtGetChild(0);
        if (returnNode.jjtGetNumChildren() == 0)
            return new VoidTypeDescriptor();
        else {
            SimpleNode returnTypeNode = (SimpleNode) returnNode.jjtGetChild(0);
            TypeDescriptor returnTypeDescriptor = new TypeDescriptorFactory().create(returnTypeNode);
            setElementType(returnTypeDescriptor, returnTypeNode);

            return returnTypeDescriptor;
        }
    }

    private void setElementType(TypeDescriptor typeDescriptor, SimpleNode typeNode) {
        if (typeDescriptor instanceof CollectionTypeDescriptor) {
            SimpleNode elementTypeNode = (SimpleNode) typeNode.jjtGetChild(0);
            TypeDescriptor elementTypeDescriptor = new TypeDescriptorFactory().create(elementTypeNode);
            ((CollectionTypeDescriptor) typeDescriptor).setElementType(elementTypeDescriptor);
            setElementType(elementTypeDescriptor, elementTypeNode);
        }
    }

    private List<TypeDescriptor> getParameterTypes(SimpleNode node) {
        List<TypeDescriptor> parameterTypes = new ArrayList<>();
        SimpleNode parametersNode = (SimpleNode) node.jjtGetChild(2);
        int numberOfParams = parametersNode.jjtGetNumChildren();

        for (int i = 0; i < numberOfParams; i++)
            addParameter(parameterTypes, (SimpleNode) parametersNode.jjtGetChild(i));

        return parameterTypes;
    }

    private void addParameter(List<TypeDescriptor> parameterTypes, SimpleNode parameterNode) {
        SimpleNode parameterTypeNode = (SimpleNode) parameterNode.jjtGetChild(0);
        TypeDescriptor parameterType = new TypeDescriptorFactory().create(parameterTypeNode);
        setElementType(parameterType, parameterTypeNode);
        parameterTypes.add(parameterType);
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

    @Override
    public Object visit(ASTDCL node, Object data) {
        return defaultVisit(node, data);
    }

}
