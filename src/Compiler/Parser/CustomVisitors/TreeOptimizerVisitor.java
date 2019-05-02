package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.Visitor.OptimizationException;
import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Exceptions.Visitor.WrongNodeTypeException;
import Compiler.Parser.GeneratedFiles.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            throw new IllegalArgumentException("The given data object was not an integer but instead was " + data);
        }
    }

    private ASTIDENTIFIER copyIdentifierNode(ASTIDENTIFIER idNode) {
        ASTIDENTIFIER newNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        newNode.jjtSetValue(idNode.jjtGetValue());
        return newNode;
    }

    private String getIdentifierName(Node identifierNode) {
        if(identifierNode instanceof ASTIDENTIFIER) {
            SimpleNode simpleNode = (SimpleNode) identifierNode;
            return (String) simpleNode.jjtGetValue();
        } else {
            throw new IllegalArgumentException("The given node was not an IdentifierNode");
        }
    }

    private ASTVARIABLE copyVariableNode(ASTVARIABLE variableNode) {
        ASTIDENTIFIER idNode = (ASTIDENTIFIER) variableNode.jjtGetChild(0);
        ASTVARIABLE newVariableNode = new ASTVARIABLE(TestParserTreeConstants.JJTVARIABLE);
        newVariableNode.jjtAddChild(copyIdentifierNode(idNode), 0);
        return newVariableNode;
    }

    private ASTSIMPLE_DCL createSimpleDclNode(String simpleTypeValue, Node idNode, Node valueNode) {
        if (!(idNode instanceof ASTIDENTIFIER))
            throw new IllegalArgumentException("idNode was of illegal type: " + idNode.getClass().getSimpleName());

        ASTSIMPLE_DCL simpleDclNode = new ASTSIMPLE_DCL(TestParserTreeConstants.JJTSIMPLE_DCL);

        ASTSIMPLE_TYPES simpleTypesNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
        simpleTypesNode.jjtSetValue(simpleTypeValue);

        if (valueNode == null)
            simpleDclNode.insertChildren(0, simpleTypesNode, copyIdentifierNode((ASTIDENTIFIER) idNode));
        else
            simpleDclNode.insertChildren(0, simpleTypesNode, copyIdentifierNode((ASTIDENTIFIER) idNode), valueNode);

        return simpleDclNode;
    }

    private ASTSIMPLE_DCL createSimpleDclNode(String simpleTypeValue, Node identifierNode) {
        return createSimpleDclNode(simpleTypeValue, identifierNode, null);
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
        if(!(leftValue instanceof ASTIDENTIFIER)) {
            throw new IllegalArgumentException("Your left value node was not an identifier node");
        }
        ASTASSIGN assignNode = new ASTASSIGN(TestParserTreeConstants.JJTASSIGN);

        ASTVARIABLE leftNode = new ASTVARIABLE(TestParserTreeConstants.JJTVARIABLE);
        ASTIDENTIFIER leftIdentifier = copyIdentifierNode((ASTIDENTIFIER) leftValue);
        leftNode.jjtAddChild(leftIdentifier, 0);
        assignNode.jjtAddChild(leftNode, 0);

        assignNode.jjtAddChild(rightNode, 1);
        return assignNode;
    }

    @Override
    public Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data) {
        ASTBLOCK blockNode = new ASTBLOCK(TestParserTreeConstants.JJTBLOCK);
        Set<String> knownVertexNames = new HashSet<>();


        int amtBlockChildren = 0;
        for(int i = 0; i < node.jjtGetNumChildren(); i++) {
            List<Node> newNodes = convertResultToNodeList(node.jjtGetChild(i).jjtAccept(this, i));
            amtBlockChildren += checkNewNodes(blockNode, amtBlockChildren, newNodes, knownVertexNames);
        }

        List<Node> nodesToAdd = new ArrayList<>();
        nodesToAdd.add(blockNode);
        return nodesToAdd;
    }

    private List<Node> createAllVertex() {
        return null;
    }

    private String getVertexName(ASTSIMPLE_DCL vertexDcl) {
        return getIdentifierName(vertexDcl);
    }

    private int checkNewNodes(ASTBLOCK blockNode, int amtBlockChildren, List<Node> newNodes, Set<String> knownVertexNames) {
        for(int i = 0; i < newNodes.size(); i++) {
            Node n = newNodes.get(i);
            if(n instanceof ASTSIMPLE_DCL) {
                String vertexName = getVertexName((ASTSIMPLE_DCL) n);
                if(!knownVertexNames.contains(vertexName)) {
                    knownVertexNames.add(vertexName);
                    blockNode.jjtAddChild(n, amtBlockChildren);
                    amtBlockChildren++;
                    if(newNodes.get(i+1) instanceof ASTFUNCTION_CALL_STMT) {
                        blockNode.jjtAddChild(n, amtBlockChildren);
                        amtBlockChildren++;
                        i++;
                    } else {
                        throw new OptimizationException("The created vertex declaration in the optimization was not followed up with adding it to the graph");
                    }
                }
            }
        }

        return amtBlockChildren;
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
        SimpleNode parent = (SimpleNode) node.jjtGetParent();

        // create simple_dcl node
        ASTSIMPLE_DCL simpleDclNode = createSimpleDclNode("int", node.jjtGetChild(0), node.jjtGetChild(1));

        // create while node
        ASTWHILE_STATEMENT whileNode = createWhileNodeFromForLoop(node);

        // create encapsulating block for declaration and while loop
        ASTBLOCK newBlockNode = new ASTBLOCK(TestParserTreeConstants.JJTBLOCK);
        newBlockNode.insertChildren(0, simpleDclNode, whileNode);

        // replace the for-statement node with the new block node in the tree
        int index = convertDataToInt(data);
        parent.jjtAddChild(newBlockNode, index);

        return data;
    }

    private ASTWHILE_STATEMENT createWhileNodeFromForLoop(ASTFOR_STATEMENT forNode) {
        ASTIDENTIFIER idNode = (ASTIDENTIFIER) forNode.jjtGetChild(0);
        ASTINUM_VAL firstIntNode = (ASTINUM_VAL) forNode.jjtGetChild(1);
        ASTINUM_VAL secondIntNode = (ASTINUM_VAL) forNode.jjtGetChild(2);
        ASTBLOCK blockNode = (ASTBLOCK) forNode.jjtGetChild(3);

        ASTWHILE_STATEMENT whileNode = new ASTWHILE_STATEMENT(TestParserTreeConstants.JJTWHILE_STATEMENT);

        // create condition expression node
        ASTREL_EXPR relExprNode = new ASTREL_EXPR(TestParserTreeConstants.JJTREL_EXPR);

        // check for-loop direction, e.g. "for 1 to 10" or "for 10 to 1"
        boolean isIncrementing = forLoopIsIncrementing(firstIntNode, secondIntNode);
        relExprNode.jjtSetValue(isIncrementing ? "<=" : ">=");

        // create variable node - relExpr node's first child
        ASTVARIABLE variableNode = new ASTVARIABLE(TestParserTreeConstants.JJTVARIABLE);
        variableNode.jjtAddChild(copyIdentifierNode(idNode), 0);
        relExprNode.insertChildren(0, variableNode, secondIntNode);

        // add assign node to end of block (increment statement)
        blockNode.jjtAddChild(createAssignNodeForWhileLoop(isIncrementing, variableNode), blockNode.jjtGetNumChildren());

        // add condition and block to while node's children
        whileNode.insertChildren(0, relExprNode, blockNode);

        return whileNode;
    }

    private boolean forLoopIsIncrementing(Node firstValue, Node secondValue) {
        String firstValueString = (String) ((SimpleNode)firstValue).jjtGetValue();
        String secondValueString = (String) ((SimpleNode)secondValue).jjtGetValue();

        int firstInt = Integer.valueOf(firstValueString);
        int secondInt = Integer.valueOf(secondValueString);

        return firstInt <= secondInt;
    }

    private ASTASSIGN createAssignNodeForWhileLoop(boolean isIncrementing, ASTVARIABLE variableNode) {
        // create assign node
        ASTASSIGN assignNode = new ASTASSIGN(TestParserTreeConstants.JJTASSIGN);

        // create addSubNode and set value to + or - depending on value of isIncrementing
        ASTADD_SUB addSubNode = new ASTADD_SUB(TestParserTreeConstants.JJTADD_SUB);
        addSubNode.jjtSetValue(isIncrementing ? "+" : "-");

        // create integer node with value 1 (increment value)
        ASTINUM_VAL intNode = new ASTINUM_VAL(TestParserTreeConstants.JJTINUM_VAL);
        intNode.jjtSetValue("1");

        // add children to addSubNode
        addSubNode.insertChildren(0, copyVariableNode(variableNode), intNode);

        // finally, add children to assign
        assignNode.insertChildren(0, copyVariableNode(variableNode), addSubNode);

        return assignNode;
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
