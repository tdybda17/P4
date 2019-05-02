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
    private String currentGraphName = "";

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

    private ASTSIMPLE_DCL createSimpleDclNode(String simpleTypeValue, String identifier) {
        ASTIDENTIFIER idNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        idNode.jjtSetValue(identifier);
        return createSimpleDclNode(simpleTypeValue, idNode, null);
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

    public ASTIDENTIFIER createIdentifierNodeForId(String id) {
        ASTIDENTIFIER identifierNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        identifierNode.jjtSetValue(id);
        return identifierNode;
    }

    @Override
    public Object visit(ASTGRAPH_DCL node, Object data) {
        int index = convertDataToInt(data);

        if(node.jjtGetNumChildren() == 2) {
            return defaultVisit(node, data);
        } else if(node.jjtGetNumChildren() == 3) {
            int childIndex = 2;
            currentGraphName = getIdentifierName(node.jjtGetChild(1));
            Node childNode = node.jjtGetChild(childIndex);
            List<Node> newNodesForASTList = new ArrayList<>();
            if(childNode instanceof ASTGRAPH_ASSIGN) {
                Node assignNodeForParent = createAssignNodeFromInitialization(getIdentifierName(node.jjtGetChild(1)), childNode);
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

    @Override
    public Object visit(ASTGRAPH_ASSIGN node, Object data) {
        return defaultVisit(node, data);
    }

    private ASTASSIGN createAssignNodeFromInitialization(String leftId, Node rightNode){
        if(rightNode instanceof ASTGRAPH_ASSIGN | rightNode instanceof ASTCOLLECTION_ASSIGN) {
            ASTASSIGN assignNode = new ASTASSIGN(TestParserTreeConstants.JJTASSIGN);

            ASTVARIABLE leftNode = createVariableFromId(leftId);
            assignNode.jjtAddChild(leftNode, 0);

            //We get the child because we want to skip the GraphAssign or CollectionAssign nodes themselves
            assignNode.jjtAddChild(rightNode.jjtGetChild(0), 1);
            return assignNode;
        } else {
            throw new WrongNodeTypeException(rightNode.getClass().getSimpleName(), ASTGRAPH_ASSIGN.class.getSimpleName(), ASTCOLLECTION_ASSIGN.class.getSimpleName());
        }
    }

    @Override
    public Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data) {
        ASTBLOCK blockNode = new ASTBLOCK(TestParserTreeConstants.JJTBLOCK);
        List<Node> blockNodeChildren = new ArrayList<>();
        Set<String> knownVertexNames = getAllVertexNames(node);

        blockNodeChildren.addAll(createAllVertexDclNodes(knownVertexNames));
        blockNodeChildren.addAll(createAddingVertexToGraphFunctionCalls(knownVertexNames));

        for(int i = 0; i < blockNodeChildren.size(); i++) {
            blockNode.jjtAddChild(blockNodeChildren.get(i), i);
        }
        //TODO: få lavet så alle vertex bliver tilføjet til graphen, samt vi får fat i alle edges samt deres vægt
        int amtBlockChildren = 0;
        //for(int i = 0; i < node.jjtGetNumChildren(); i++) {
        //    List<Node> newNodes = convertResultToNodeList(node.jjtGetChild(i).jjtAccept(this, i));
        //    amtBlockChildren += checkNewNodes(blockNode, amtBlockChildren, newNodes, knownVertexNames);
        //}

        List<Node> nodesToAdd = new ArrayList<>();
        nodesToAdd.add(blockNode);
        return nodesToAdd;
    }

    private Set<String> getAllVertexNames(Node root) {
        Set<String> vertexNames = new HashSet<>();
        for(int i = 0; i < root.jjtGetNumChildren(); i++) {
            Node child = root.jjtGetChild(i);
            if(child instanceof ASTIDENTIFIER) {
                vertexNames.add(getIdentifierName(child));
            } else {
                vertexNames.addAll(getAllVertexNames(child));
            }
        }
        return vertexNames;
    }

    private List<EdgeInformation> getAllEdgeInformations(Node root) {

        return null;
    }

    private EdgeInformation getEdgeInformation(ASTGRAPH_VERTEX_DCL node) {
        if(node.jjtGetNumChildren() == 2) {
            return null; //TODO: få lavet for VERTEX LIST
        } else if (node.jjtGetNumChildren() == 3) {
            String firstVertex = getIdentifierName(node.jjtGetChild(0));
            String secondVertex = getIdentifierName(node.jjtGetChild(1));
            if(node.jjtGetChild(2) instanceof ASTWEIGHT) {
                ASTWEIGHT weightNode = (ASTWEIGHT) node.jjtGetChild(2);
                if(weightNode.jjtGetNumChildren() == 0){

                }
            }


            return null; //TODO: få lavet
        } else {
            throw new WrongAmountOfChildrenException("An " + node.getClass().getSimpleName() + " node in the AST had neither 2 or 3 children");
        }
    }



    private List<ASTSIMPLE_DCL> createAllVertexDclNodes(Set<String> vertexNames) {
        List<ASTSIMPLE_DCL> dclNodes = new ArrayList<>();
        for(String vertexName : vertexNames) {
            dclNodes.add(createSimpleDclNode("Vertex", vertexName));
        }
        return dclNodes;
    }

    private List<ASTFUNCTION_CALL_STMT> createAddingVertexToGraphFunctionCalls(Set<String> vertexNames) {
        List<ASTFUNCTION_CALL_STMT> functionCallNodes = new ArrayList<>();
        for(String vertexName : vertexNames) {
            functionCallNodes.add(createAddVertexFunctionCall(vertexName));
        }
        return functionCallNodes;
    }

    private ASTFUNCTION_CALL_STMT createAddVertexFunctionCall(String vertexName) {
        ASTFUNCTION_CALL_STMT functionCallNode = new ASTFUNCTION_CALL_STMT(TestParserTreeConstants.JJTFUNCTION_CALL_STMT);

        ASTVARIABLE graphNode = createVariableFromId(currentGraphName);
        functionCallNode.jjtAddChild(graphNode, 0);

        ASTFUNCTION_CALL callNodeForAddVertex = new ASTFUNCTION_CALL(TestParserTreeConstants.JJTFUNCTION_CALL);
        graphNode.jjtAddChild(callNodeForAddVertex, 1);

        ASTIDENTIFIER addVertexIdNode = createIdentifierNodeForId("addVertex");
        callNodeForAddVertex.jjtAddChild(addVertexIdNode, 0);
        ASTACTUAL_PARAMETERS actualParametersNode = new ASTACTUAL_PARAMETERS(TestParserTreeConstants.JJTACTUAL_PARAMETERS);
        callNodeForAddVertex.jjtAddChild(actualParametersNode, 1);

        ASTVARIABLE vertexNode = createVariableFromId(vertexName);
        actualParametersNode.jjtAddChild(vertexNode, 0);

        return functionCallNode;
    }

    private ASTVARIABLE createVariableFromId(String id) {
        ASTVARIABLE variableNode = new ASTVARIABLE(TestParserTreeConstants.JJTVARIABLE);
        ASTIDENTIFIER idNode = createIdentifierNodeForId(id);
        variableNode.jjtAddChild(idNode, 0);
        return variableNode;
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
