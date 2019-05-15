package Compiler.Parser.CustomVisitors.EdgeInformation;

import Compiler.Exceptions.DuplicateEdgeException;
import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Exceptions.Visitor.WrongNodeTypeException;
import Compiler.Parser.CustomVisitors.TreeOptimizerVisitor;
import Compiler.Parser.GeneratedFiles.ASTGRAPH_VERTEX_DCL;
import Compiler.Parser.GeneratedFiles.ASTWEIGHT;
import Compiler.Parser.GeneratedFiles.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdgeInformationHandler {
    private TreeOptimizerVisitor treeOptimizerVisitor;
    private String graphType;
    private static String prefix = "_";

    public EdgeInformationHandler(String graphType) {
        if(!graphType.equals("Graph") && !graphType.equals("DiGraph")){
            throw new IllegalArgumentException();
        }
        this.graphType = graphType;
        this.treeOptimizerVisitor = new TreeOptimizerVisitor();
    }

    public List<EdgeInformation> getAllEdgeInformations(Node root) {
        List<EdgeInformation> edgeInformationList = new ArrayList<>();
        for(int i = 0; i < root.jjtGetNumChildren(); i++) {
            Node child = root.jjtGetChild(i);
            if(child instanceof ASTGRAPH_VERTEX_DCL) {
                edgeInformationList.addAll(getEdgeInformation((ASTGRAPH_VERTEX_DCL) root.jjtGetChild(i)));
            } else {
                throw new WrongNodeTypeException(child.getClass().getSimpleName(), ASTGRAPH_VERTEX_DCL.class.getSimpleName());
            }
        }

        checkEdgeInformation(edgeInformationList);
        return edgeInformationList;
    }

    private List<EdgeInformation> getEdgeInformation(ASTGRAPH_VERTEX_DCL node) {
        List<EdgeInformation> edgeInformations = new ArrayList<>();
        if(node.jjtGetNumChildren() == 2) {
            String firstVertex = prefix + treeOptimizerVisitor.getIdentifierName(node.jjtGetChild(0));

            Node vertexList = node.jjtGetChild(1);
            for(int i = 0; i < vertexList.jjtGetNumChildren(); i++){
                Node vertexNode = vertexList.jjtGetChild(i);

                String secondVertex = prefix + treeOptimizerVisitor.getIdentifierName(vertexNode.jjtGetChild(0));
                if(vertexNode.jjtGetChild(1) instanceof ASTWEIGHT) {
                    ASTWEIGHT weightNode = (ASTWEIGHT) vertexNode.jjtGetChild(1);
                    edgeInformations.add(new EdgeInformation(firstVertex, secondVertex, weightNode));
                } else {
                    throw new WrongNodeTypeException("The second child of an " + vertexNode.getClass().getSimpleName() + " node was not an weight node");
                }
            }
        } else if (node.jjtGetNumChildren() == 3) {
            String firstVertex = prefix + treeOptimizerVisitor.getIdentifierName(node.jjtGetChild(0));
            String secondVertex = prefix + treeOptimizerVisitor.getIdentifierName(node.jjtGetChild(1));
            if(node.jjtGetChild(2) instanceof ASTWEIGHT) {
                ASTWEIGHT weightNode = (ASTWEIGHT) node.jjtGetChild(2);
                edgeInformations.add(new EdgeInformation(firstVertex, secondVertex, weightNode));
            } else {
                throw new WrongNodeTypeException("The third child of an " + node.getClass().getSimpleName() + " node was not an weight node");
            }
        } else {
            throw new WrongAmountOfChildrenException("An " + node.getClass().getSimpleName() + " node in the AST had neither 2 or 3 children");
        }

        return edgeInformations;
    }


    private void checkEdgeInformation(List<EdgeInformation> edgeInformationList) {
        Map<String, List<String>> edgesInGraph = new HashMap<>();
        if(graphType.equals("DiGraph")) {
            for(EdgeInformation edgeInformation : edgeInformationList) {
                addEdgeToGraphInfo(edgesInGraph, edgeInformation.getFirstVertex(), edgeInformation.getSecondVertex());
            }
        } else if(graphType.equals("Graph")) {
            for(EdgeInformation edgeInformation : edgeInformationList) {
                if (edgeInformation.getFirstVertex().equals(edgeInformation.getSecondVertex()))
                    throw new IllegalArgumentException("Self loops are not allowed");
                addEdgeToGraphInfo(edgesInGraph, edgeInformation.getFirstVertex(), edgeInformation.getSecondVertex());
                addEdgeToGraphInfo(edgesInGraph, edgeInformation.getSecondVertex(), edgeInformation.getFirstVertex());
            }
        }
    }

    private void addEdgeToGraphInfo(Map<String, List<String>> edgesInGraph, String key, String value) {
        if(edgesInGraph.containsKey(key)) {
            List<String> neighbours = edgesInGraph.get(key);
            if(neighbours.contains(value)) {
                throw new DuplicateEdgeException("You tried to add more than one edge from " + key+ " to " + value);
            }
            neighbours.add(value);
            edgesInGraph.put(key, neighbours);
        } else {
            List<String> neighbours = new ArrayList<>();
            neighbours.add(value);
            edgesInGraph.put(key, neighbours);
        }

    }
}
