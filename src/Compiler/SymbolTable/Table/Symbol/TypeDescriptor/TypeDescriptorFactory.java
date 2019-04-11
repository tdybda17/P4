package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import Compiler.Exceptions.SymbolTable.IllegalTypeException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue.MaxQueueTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue.MinQueueTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.QueueTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.StackTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.DirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.DirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.GraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs.UndirectedGraphTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;

public class TypeDescriptorFactory {
    public TypeDescriptorFactory() {
    }

    public TypeDescriptor create(String typeName) throws IllegalTypeException {
        switch (typeName) {
            case "Void": return new VoidTypeDescriptor();
            case "Integer": case "int": return new IntegerTypeDescriptor();
            case "Real": case "real": return new RealTypeDescriptor();
            case "Color": case "color": return new ColorTypeDescriptor();
            case "Boolean": case "boolean": return new BooleanTypeDescriptor();
            case "Vertex": return new VertexTypeDescriptor();
            case "DiEdge": return new DirectedEdgeTypeDescriptor();
            case "Edge": return new UndirectedEdgeTypeDescriptor();
            case "Graph": return new UndirectedGraphTypeDescriptor();
            case "DiGraph": return new DirectedGraphTypeDescriptor();
            case "Set": return new SetTypeDescriptor(new UndefinedTypeDescriptor());
            case "Stack": return new StackTypeDescriptor(new UndefinedTypeDescriptor());
            case "Queue": return new QueueTypeDescriptor(new UndefinedTypeDescriptor());
            case "MinQueue": return new MinQueueTypeDescriptor(new UndefinedTypeDescriptor());
            case "MaxQueue": return new MaxQueueTypeDescriptor(new UndefinedTypeDescriptor());
            //MANGLER MAP.
            default: throw new IllegalTypeException("The typename:" + typeName + ", is not a legal type in our language");
        }
    }
}
