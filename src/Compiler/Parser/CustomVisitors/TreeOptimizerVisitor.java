package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Exceptions.Visitor.WrongNodeTypeException;
import Compiler.Parser.GeneratedFiles.*;

import java.util.ArrayList;
import java.util.List;

public class TreeOptimizerVisitor implements TestParserVisitor {
    private Object defaultVisit(SimpleNode node, Object data) {
        for(int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, i);
        }
        return data;
    }

    private int convertDataToInt(Object data) {
        if(data instanceof Integer) {
            return (int) data;
        } else {
            throw new IllegalArgumentException("The given data object was not a integer but instead was " + data);
        }
    }

    private ASTIDENTIFIER copyIdentifierNode(ASTIDENTIFIER idNode) {
        ASTIDENTIFIER newNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        newNode.jjtSetValue(idNode.jjtGetValue());
        return newNode;
    }

    @Override
    public Object visit(ASTGRAPH_DCL node, Object data) {
        int index = convertDataToInt(data);

        if(node.jjtGetNumChildren() == 2) {
            return defaultVisit(node, data);
        } else if(node.jjtGetNumChildren() == 3) {
            int childIndex = 2;
            Node childNode = node.jjtGetChild(childIndex);
            List<Node> newNodesForASTList = new ArrayList<>();
            if(childNode instanceof ASTGRAPH_ASSIGN) {
                Node assignNodeForParent = createAssignNodeFromInitialization(node.jjtGetChild(1), childNode.jjtGetChild(0));
                newNodesForASTList.add(assignNodeForParent);
            } else if (childNode instanceof ASTGRAPH_DCL_ELEMENTS) {
                newNodesForASTList = convertResultToNodeList(node.jjtGetChild(childIndex).jjtAccept(this, childIndex));
            } else {
                throw new WrongNodeTypeException(node.getClass().getSimpleName(), ASTGRAPH_ASSIGN.class.getSimpleName(), ASTGRAPH_DCL_ELEMENTS.class.getSimpleName());
            }
            node.removeChild(childIndex);
            SimpleNode parent = (SimpleNode) node.jjtGetParent();
            parent.insertChildren(index + 1, newNodesForASTList);
            return index;
        } else {
            throw new WrongAmountOfChildrenException("A graph declaration node in the AST had neither 2 or 3 children");
        }
    }

    public List<Node> convertResultToNodeList(Object result){
        if(result instanceof List) {
            if(((List) result).isEmpty()) {
                return new ArrayList<>();
            } else {
                List<Node> symbolList = new ArrayList<>();
                for(Object element : (List) result) {
                    if (element instanceof Node) {
                        symbolList.add((Node) element);
                    } else {
                        throw new IllegalArgumentException("The result of an optimization visit was not a node list");
                    }
                }
                return symbolList;
            }
        }
        throw new IllegalArgumentException("The result of an optimization visit was not a node list");
    }

    @Override
    public Object visit(ASTGRAPH_ASSIGN node, Object data) {
        return defaultVisit(node, data);
    }

    private ASTASSIGN createAssignNodeFromInitialization(Node leftValue, Node rightNode){
        ASTASSIGN assignNode = new ASTASSIGN(TestParserTreeConstants.JJTASSIGN);
        ASTVARIABLE leftNode = new ASTVARIABLE(TestParserTreeConstants.JJTVARIABLE);

        leftNode.jjtAddChild(leftValue, 0);
        assignNode.jjtAddChild(leftNode, 0);
        leftNode.jjtSetParent(assignNode);

        assignNode.jjtAddChild(rightNode, 1);
        rightNode.jjtSetParent(assignNode);
        return assignNode;
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
    public Object visit(ASTFUNCS_DCL node, Object data) {
        return defaultVisit(node, data);
    }

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
    public Object visit(ASTATTRIBUTE_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTOBJECT_TYPE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTSIMPLE_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTSIMPLE_TYPES node, Object data) {
        return defaultVisit(node, data);
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
    public Object visit(ASTCOLOR_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTLABEL_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVARIABLE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCTION_CALL node, Object data) {
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
    public Object visit(ASTWHILE_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFOR_STATEMENT node, Object data) {
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
    public Object visit(ASTCOLLECTION_ASSIGN node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTELEMENT_LIST node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP_ADT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP_ASSIGN node, Object data) {
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
