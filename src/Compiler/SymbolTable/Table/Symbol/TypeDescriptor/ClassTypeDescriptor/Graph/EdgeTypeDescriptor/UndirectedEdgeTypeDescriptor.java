package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph.EdgeTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.VertexPairTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;

public class UndirectedEdgeTypeDescriptor extends EdgeTypeDescriptor {
    public UndirectedEdgeTypeDescriptor() {
        super();
        this.addUndirectedFields();
    }

    private void addUndirectedFields() {
        this.addField(this.vertices());
    }

    private Field vertices() {
        return new Field("vertices", new VertexPairTypeDescriptor());
    }

    @Override
    public String getTypeName() {
        return "UndirectedEdge";
    }
}
