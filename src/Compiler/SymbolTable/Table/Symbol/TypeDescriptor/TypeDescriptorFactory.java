package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import Compiler.Exceptions.SymbolTable.IllegalTypeException;
import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Parser.GeneratedFiles.SimpleNode;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue.MaxQueueTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue.MinQueueTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.QueueTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.StackTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.DirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.DirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.UndirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;

public class TypeDescriptorFactory {
    public TypeDescriptorFactory() {
    }

    public TypeDescriptor create(SimpleNode node) {
        String typeName = (String) node.jjtGetValue();
        switch (typeName) {
            case "Void": return new VoidTypeDescriptor();
            case "Integer": case "int": return new IntegerTypeDescriptor();
            case "Real": case "real": return new RealTypeDescriptor();
            case "Color": case "color": return new ColorTypeDescriptor();
            case "Boolean": case "boolean": return new BooleanTypeDescriptor();
            case "Label": case "label": return new LabelTypeDescriptor();
            case "Vertex": return new VertexTypeDescriptor();
            case "DiEdge": return new DirectedEdgeTypeDescriptor();
            case "Edge": return new UndirectedEdgeTypeDescriptor();
            case "Graph": return new UndirectedGraphTypeDescriptor();
            case "DiGraph": return new DirectedGraphTypeDescriptor();
            case "Set": return new SetTypeDescriptor(create(getChild(node)));
            case "Stack": return new StackTypeDescriptor(create(getChild(node)));
            case "Queue": return new QueueTypeDescriptor(create(getChild(node)));
            case "MinQueue": return new MinQueueTypeDescriptor(create(getChild(node)));
            case "MaxQueue": return new MaxQueueTypeDescriptor(create(getChild(node)));
            //MANGLER MAP.
            default: throw new IllegalTypeException("The typename:" + typeName + ", is not a legal type in our language");
        }
    }

    private SimpleNode getChild(SimpleNode node) {
        if (node.jjtGetNumChildren() != 1)
            throw new WrongAmountOfChildrenException("Collection type node should have exactly 1 child, not " + node.jjtGetNumChildren());
        return (SimpleNode) node.jjtGetChild(0);
    }
}
