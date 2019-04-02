package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import Compiler.Exceptions.SymbolTable.IllegalTypeException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.DirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.EdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.RealTypeDescriptor;

public class TypeDescriptorFactory {
    public TypeDescriptorFactory() {
    }

    public TypeDescriptor create(String typeName) throws IllegalTypeException {
        switch (typeName) {
            case "Integer": case "int": return new IntegerTypeDescriptor();
            case "Real": case "real": return new RealTypeDescriptor();
            case "Color": case "color": return new ColorTypeDescriptor();
            case "Boolean": case "boolean": return new BooleanTypeDescriptor();
            case "Vertex": return new VertexTypeDescriptor();
            case "DiEdge": return new DirectedEdgeTypeDescriptor();
            case "Edge": return new UndirectedEdgeTypeDescriptor();
            case "Set": return new SetTypeDescriptor(new UndefinedTypeDescriptor());
            default: throw new IllegalTypeException("The typename:" + typeName + ", is not a legal type in our language");
        }
    }
}
