package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Exceptions.Visitor.WrongNodeTypeException;
import Compiler.Parser.CustomVisitors.EdgeInformation.EdgeInformation;
import Compiler.Parser.CustomVisitors.EdgeInformation.EdgeInformationHandler;
import Compiler.Parser.GeneratedFiles.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeOptimizerVisitor implements TestParserVisitor {
    private String currentGraphName = "";
    private String currentGraphType = "";

    private Object defaultVisit(SimpleNode node, Object data) {
        for(int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, i);
        }
        return data;
    }

    private ASTDCL convertToDclNode(Object node) {
        if(node instanceof ASTDCL) {
            return (ASTDCL) node;
        } else {
            throw new IllegalArgumentException("The given node was not an dcl node");
        }
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

    public String getIdentifierName(Node identifierNode) {
        if(identifierNode instanceof ASTIDENTIFIER) {
            SimpleNode simpleNode = (SimpleNode) identifierNode;
            return (String) simpleNode.jjtGetValue();
        } else {
            throw new IllegalArgumentException("The given node was not an IdentifierNode");
        }
    }

    private ASTVARIABLE createVariableFromId(String id) {
        ASTVARIABLE variableNode = new ASTVARIABLE(TestParserTreeConstants.JJTVARIABLE);
        ASTIDENTIFIER idNode = createIdentifierNodeForId(id);
        variableNode.jjtAddChild(idNode, 0);
        return variableNode;
    }

    private ASTVARIABLE copyVariableNode(ASTVARIABLE variableNode) {
        ASTIDENTIFIER idNode = (ASTIDENTIFIER) variableNode.jjtGetChild(0);
        ASTVARIABLE newVariableNode = new ASTVARIABLE(TestParserTreeConstants.JJTVARIABLE);
        newVariableNode.jjtAddChild(copyIdentifierNode(idNode), 0);
        return newVariableNode;
    }

    private ASTDCL createDclNode(String simpleTypeValue, Node idNode) {
        if (!(idNode instanceof ASTIDENTIFIER))
            throw new IllegalArgumentException("idNode was of illegal type: " + idNode.getClass().getSimpleName());

        ASTDCL dclNode = new ASTDCL(TestParserTreeConstants.JJTDCL);

        ASTSIMPLE_TYPES simpleTypesNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
        simpleTypesNode.jjtSetValue(simpleTypeValue);

        dclNode.insertChildren(0, simpleTypesNode, copyIdentifierNode((ASTIDENTIFIER) idNode));
        return dclNode;
    }

    private ASTDCL createDclNode(String simpleTypeValue, String identifier) {
        ASTIDENTIFIER idNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        idNode.jjtSetValue(identifier);
        return createDclNode(simpleTypeValue, idNode);
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
    public Object visit(ASTDCL node, Object data) {
        int index = convertDataToInt(data);

        if(node.jjtGetNumChildren() == 1) {
            int childIndex = 0;
            Object result = node.jjtGetChild(childIndex).jjtAccept(this, childIndex);
            List<Node> newChildren = convertResultToNodeList(result);

            SimpleNode parent = (SimpleNode) node.jjtGetParent();
            parent.insertChildren(index + 1, newChildren);
            return data;
        } else if (node.jjtGetNumChildren() == 2) {
            return data;
        } else {
            throw new WrongAmountOfChildrenException("An dcl node did not have 1 or 2 children but instead had: " + node.jjtGetNumChildren());
        }
    }

    @Override
    public Object visit(ASTGRAPH_DCL node, Object data) {
        List<Node> newChildren = new ArrayList<>();
        if(node.jjtGetNumChildren() == 2 | node.jjtGetNumChildren() == 3) {
            // We grap the parent dcl node and move our type and identifier up to it
            ASTDCL dclNode = convertToDclNode(node.jjtGetParent());
            moveTypeAndIdentifierToDclNode(dclNode, node.jjtGetChild(0), node.jjtGetChild(1));
            if(node.jjtGetNumChildren() == 3) {
                int childIndex = 2;
                currentGraphType = (String) ((ASTGRAPH_TYPE) node.jjtGetChild(0)).jjtGetValue();
                currentGraphName = getIdentifierName(node.jjtGetChild(1));

                // transform third child
                Node thirdChild = node.jjtGetChild(childIndex);
                if(thirdChild instanceof ASTGRAPH_ASSIGN) {
                    //Creating an assign node to replace the graph assign node
                    Node assignNode = createAssignNodeFromInitialization(getIdentifierName(node.jjtGetChild(1)), thirdChild);
                    newChildren.add(assignNode);
                } else if (thirdChild instanceof ASTGRAPH_DCL_ELEMENTS) {
                    newChildren.addAll(convertResultToNodeList(thirdChild.jjtAccept(this, childIndex)));
                } else {
                    throw new WrongNodeTypeException(node.getClass().getSimpleName(), ASTGRAPH_ASSIGN.class.getSimpleName(), ASTGRAPH_DCL_ELEMENTS.class.getSimpleName());
                }
            }
        } else {
            throw new WrongAmountOfChildrenException("A graph declaration node in the AST had neither 2 or 3 children");
        }
        return newChildren;
    }

    private void moveTypeAndIdentifierToDclNode(ASTDCL dclNode, Node typeNode, Node identifierNode) {
        dclNode.removeChild(0);
        dclNode.insertChildren(0, typeNode, identifierNode);
    }

    @Override
    public Object visit(ASTGRAPH_ASSIGN node, Object data) {
        return defaultVisit(node, data);
    }

    private ASTASSIGN createAssignNodeFromInitialization(String leftId, Node rightNode){
        if(rightNode instanceof ASTGRAPH_ASSIGN | rightNode instanceof ASTCOLLECTION_ASSIGN | rightNode instanceof ASTINITIALIZATION) {
            ASTASSIGN assignNode = new ASTASSIGN(TestParserTreeConstants.JJTASSIGN);

            ASTVARIABLE leftNode = createVariableFromId(leftId);
            assignNode.jjtAddChild(leftNode, 0);

            //We get the child because we want to skip the GraphAssign or CollectionAssign nodes themselves
            assignNode.jjtAddChild(rightNode.jjtGetChild(0), 1);
            return assignNode;
        } else {
            throw new WrongNodeTypeException(rightNode.getClass().getSimpleName(), ASTGRAPH_ASSIGN.class.getSimpleName(), ASTCOLLECTION_ASSIGN.class.getSimpleName(), ASTINITIALIZATION.class.getSimpleName());
        }
    }

    @Override
    public Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data) {
        ASTBLOCK blockNode = new ASTBLOCK(TestParserTreeConstants.JJTBLOCK);
        List<Node> blockNodeChildren = new ArrayList<>();
        Set<String> knownVertexNames = getAllVertexNames(node);

        //Creating all the create vertex and add vertex function statements
        blockNodeChildren.addAll(createAllVertexDclNodes(knownVertexNames));
        //blockNodeChildren.addAll()
        blockNodeChildren.addAll(createAddVertexFunctionCalls(knownVertexNames));


        //Creating all the add edge and set weight method calls
        EdgeInformationHandler edgeInformationHandler = new EdgeInformationHandler(currentGraphType);
        List<EdgeInformation> edgeInformationList = edgeInformationHandler.getAllEdgeInformations(node);

        blockNodeChildren.addAll(createAddEdgeFunctionCalls(edgeInformationList));
        blockNodeChildren.addAll(createWeightAssignment(edgeInformationList));

        for(int i = 0; i < blockNodeChildren.size(); i++) {
            blockNode.jjtAddChild(blockNodeChildren.get(i), i);
        }
        List<Node> nodesToAdd = new ArrayList<>();
        nodesToAdd.add(blockNode);
        return nodesToAdd;
    }

    private Set<String> getAllVertexNames(Node root) {
        Set<String> vertexNames = new HashSet<>();
        for(int i = 0; i < root.jjtGetNumChildren(); i++) {
            Node child = root.jjtGetChild(i);
            if(child instanceof ASTGRAPH_VERTEX_DCL) {
                vertexNames.add(getIdentifierName(child.jjtGetChild(0)));
                if(child.jjtGetChild(1) instanceof ASTIDENTIFIER) {
                    vertexNames.add(getIdentifierName(child.jjtGetChild(1)));
                } else if (child.jjtGetChild(1) instanceof ASTVERTEX_LIST) {
                    vertexNames.addAll(getAllVertexNamesFromVertexList((ASTVERTEX_LIST) child.jjtGetChild(1)));
                } else {
                    throw new WrongNodeTypeException(child.jjtGetChild(1).getClass().toString(), ASTIDENTIFIER.class.getSimpleName(), ASTVERTEX_LIST.class.getSimpleName());
                }
            } else {
                throw new WrongNodeTypeException("The graph dcl element node had a child that was not an node of the type: " + ASTGRAPH_VERTEX_DCL.class.getSimpleName() + ", but instead was: " + child.getClass().getSimpleName());
            }
        }
        return vertexNames;
    }

    private Set<String> getAllVertexNamesFromVertexList(ASTVERTEX_LIST vertexListNode) {
        Set<String> vertexNames = new HashSet<>();
        for(int i = 0; i < vertexListNode.jjtGetNumChildren(); i++) {
            Node child = vertexListNode.jjtGetChild(i);
            if(child instanceof ASTVERTEX) {
                vertexNames.add(getIdentifierName(child.jjtGetChild(0)));
            } else {
                throw new WrongNodeTypeException(child.getClass().getSimpleName(), ASTVERTEX.class.getSimpleName());
            }
        }
        return vertexNames;
    }

    private List<ASTDCL> createAllVertexDclNodes(Set<String> vertexNames) {
        List<ASTDCL> dclNodes = new ArrayList<>();
        for(String vertexName : vertexNames) {
            dclNodes.add(createDclNode("Vertex", vertexName));
        }
        return dclNodes;
    }

    private List<ASTFUNCTION_CALL_STMT> createAddVertexFunctionCalls(Set<String> vertexNames) {
        List<ASTFUNCTION_CALL_STMT> functionCallNodes = new ArrayList<>();
        for(String vertexName : vertexNames) {
            functionCallNodes.add(createFunctionCallStmtNode(currentGraphName, "addVertex", createVariableFromId(vertexName)));
        }
        return functionCallNodes;
    }

    private List<ASTFUNCTION_CALL_STMT> createAddEdgeFunctionCalls(List<EdgeInformation> edgeInformationList) {
        List<ASTFUNCTION_CALL_STMT> functionCallNodes = new ArrayList<>();
        for(EdgeInformation edgeInformation : edgeInformationList) {
            ASTVARIABLE firstActualParameter = createVariableFromId(edgeInformation.getFirstVertex());
            ASTVARIABLE secondActualParameter = createVariableFromId(edgeInformation.getSecondVertex());

            functionCallNodes.add(createFunctionCallStmtNode(currentGraphName, "addEdge", firstActualParameter, secondActualParameter));
        }
        return functionCallNodes;
    }

    private List<ASTASSIGN> createWeightAssignment(List<EdgeInformation> edgeInformationList) {
        List<ASTASSIGN> assignNodes = new ArrayList<>();

        for(EdgeInformation edgeInformation : edgeInformationList) {
            assignNodes.add(createAssignNodeFromEdgeInformation(edgeInformation));
        }
        return assignNodes;
    }

    private ASTASSIGN createAssignNodeFromEdgeInformation(EdgeInformation edgeInformation){
        ASTASSIGN assingNode = new ASTASSIGN(TestParserTreeConstants.JJTASSIGN);


        ASTVARIABLE firstActualParameter = createVariableFromId(edgeInformation.getFirstVertex());
        ASTVARIABLE secondActualParameter = createVariableFromId(edgeInformation.getSecondVertex());

        ASTFUNCTION_CALL_STMT functionCall = createFunctionCallStmtNode(currentGraphName, "getEdge", firstActualParameter, secondActualParameter);

        //Left value:
        Node leftValue = functionCall.jjtGetChild(0);

        Node leftChild = leftValue.jjtGetChild(1);
        leftChild.jjtAddChild(createFieldAccessId("weight"), 2);

        assingNode.jjtAddChild(leftValue, 0);
        //Right value:
        assingNode.jjtAddChild(edgeInformation.getWeight().jjtGetChild(0), 1);
        return assingNode;
    }

    private ASTFIELD_ACCESS createFieldAccessId(String id) {
        ASTFIELD_ACCESS fieldAcessNode = new ASTFIELD_ACCESS(TestParserTreeConstants.JJTFIELD_ACCESS);
        ASTIDENTIFIER idNode = createIdentifierNodeForId(id);
        fieldAcessNode.jjtAddChild(idNode, 0);
        return fieldAcessNode;
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
    public Object visit(ASTATTRIBUTES_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTSIMPLE_DCL node, Object data) {
        List<Node> newChildren = new ArrayList<>();
        if(node.jjtGetNumChildren() == 2 | node.jjtGetNumChildren() == 3) {
            // We grap the parent dcl node and move our type and identifier up to it
            ASTDCL dclNode = convertToDclNode(node.jjtGetParent());
            moveTypeAndIdentifierToDclNode(dclNode, node.jjtGetChild(0), node.jjtGetChild(1));
            if(node.jjtGetNumChildren() == 3) {
                int childIndex = 2;
                // transform third child
                Node thirdChild = node.jjtGetChild(childIndex);

                if(thirdChild instanceof ASTINITIALIZATION) {
                    String leftId = getIdentifierName(node.jjtGetChild(1));

                    ASTASSIGN assignNode = createAssignNodeFromInitialization(leftId, thirdChild);
                    newChildren.add(assignNode);
                } else {
                    throw new WrongNodeTypeException(node.getClass().getSimpleName(), ASTINITIALIZATION.class.getSimpleName());
                }
            }
        } else {
            throw new WrongAmountOfChildrenException("A simple declaration node in the AST had neither 2 or 3 children");
        }
        return newChildren;
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
    public Object visit(ASTINITIALIZATION node, Object data) {
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
    public Object visit(ASTMUL_DIV_MOD node, Object data) {
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

        ASTIDENTIFIER counterVariableIdNode = (ASTIDENTIFIER) node.jjtGetChild(0);
        // create dcl node
        ASTDCL dclNode = createDclNode("int", counterVariableIdNode);

        //Create assign node with initial value for our loop parameter:
        ASTASSIGN assignNode = new ASTASSIGN(TestParserTreeConstants.JJTASSIGN);
        assignNode.jjtAddChild(createVariableFromId((String) counterVariableIdNode.jjtGetValue()), 0);
        assignNode.jjtAddChild(node.jjtGetChild(1), 1);

        // create while node
        ASTWHILE_STATEMENT whileNode = createWhileNodeFromForLoop(node);

        // create encapsulating block for declaration and while loop
        ASTBLOCK newBlockNode = new ASTBLOCK(TestParserTreeConstants.JJTBLOCK);
        newBlockNode.insertChildren(0, dclNode, assignNode, whileNode);

        // replace the for-statement node with the new block node in the tree
        int index = convertDataToInt(data);
        parent.jjtAddChild(newBlockNode, index);

        // accept block child and then return
        newBlockNode.jjtAccept(this, data);
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
        List<Node> newChildren = new ArrayList<>(); // list of new children to replace the original COLLECTION_ADT node
        if (node.jjtGetNumChildren() == 2 | node.jjtGetNumChildren() == 3) {
            // Create a new dcl node and adopt collection_adt's first two children. Add dcl node to list of new children
            ASTDCL dclNode = convertToDclNode(node.jjtGetParent());
            moveTypeAndIdentifierToDclNode(dclNode, node.jjtGetChild(0), node.jjtGetChild(1));
            if(node.jjtGetNumChildren() == 3) {
                Node thirdChild = node.jjtGetChild(2);
                if (thirdChild instanceof ASTCOLLECTION_ASSIGN) {
                    String leftId = getIdentifierName(node.jjtGetChild(1));

                    ASTASSIGN assignNode = createAssignNodeFromInitialization(leftId, thirdChild);
                    newChildren.add(assignNode);
                } else if (thirdChild instanceof ASTELEMENT_LIST) {
                    List<Node> newNodesForBlock = new ArrayList<>();
                    // Create function_call_stmt node for each child of element_list and add to list of new children
                    ASTCOLLECTION_TYPE collectionTypeNode = (ASTCOLLECTION_TYPE) node.jjtGetChild(0);
                    ASTIDENTIFIER idNode = (ASTIDENTIFIER) node.jjtGetChild(1);
                    for (int i = 0; i < thirdChild.jjtGetNumChildren(); i++) {
                        newNodesForBlock.add(createFunctionCallStmtNode((String) idNode.jjtGetValue(), getFunctionNameFromCollectionType(collectionTypeNode), thirdChild.jjtGetChild(i)));
                    }

                    // Create encapsulating block for the new children
                    ASTBLOCK blockNode = new ASTBLOCK(TestParserTreeConstants.JJTBLOCK);
                    blockNode.insertChildren(0, newNodesForBlock);
                    newChildren.add(blockNode);
                } else {
                    throw new WrongNodeTypeException("COLLECTION_ADT node's third child is of illegal type: " + thirdChild.getClass().getSimpleName());
                }
            }
        } else {
            throw new IllegalArgumentException("COLLECTION_ADT node has " + node.jjtGetNumChildren() + " children. Should have 2 or 3");
        }
        return newChildren;
    }

    private ASTFUNCTION_CALL_STMT createFunctionCallStmtNode(String collectionOrGraphId, String functionName, Node... actualParameters) {
        ASTFUNCTION_CALL_STMT functionCallStmtNode = new ASTFUNCTION_CALL_STMT(TestParserTreeConstants.JJTFUNCTION_CALL_STMT);

        ASTVARIABLE variableNode = createVariableFromId(collectionOrGraphId);
        functionCallStmtNode.jjtAddChild(variableNode, 0);

        ASTFUNCTION_CALL funcCallNode = new ASTFUNCTION_CALL(TestParserTreeConstants.JJTFUNCTION_CALL);
        variableNode.jjtAddChild(funcCallNode, 1);

        ASTIDENTIFIER addVertexIdNode = createIdentifierNodeForId(functionName);
        funcCallNode.jjtAddChild(addVertexIdNode, 0);
        ASTACTUAL_PARAMETERS actualParametersNode = new ASTACTUAL_PARAMETERS(TestParserTreeConstants.JJTACTUAL_PARAMETERS);
        funcCallNode.jjtAddChild(actualParametersNode, 1);
        if (actualParameters != null) {
            for (int i = 0; i < actualParameters.length; i++) {
                actualParametersNode.jjtAddChild(actualParameters[i], i);
            }
        }

        return functionCallStmtNode;
    }

    private String getFunctionNameFromCollectionType(Node collectionType) {
        String type = (String) ((SimpleNode)collectionType).jjtGetValue();
        switch (type) {
            case "Queue": return "enqueue";
            case "MinQueue": case "MaxQueue": return "insert";
            case "Set": return "add";
            case "Stack": return "push";
            default: throw new IllegalArgumentException(collectionType.getClass().getSimpleName() + " was not a collection type");
        }
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
