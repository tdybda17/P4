package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;

public class UndirectedEdgeTypeDescriptor extends EdgeTypeDescriptor {
    public UndirectedEdgeTypeDescriptor() {
        super();
        this.addUndirectedFields();
    }

    private void addUndirectedFields() {
        this.addField(this.vertices());
    }

    private Field vertices() {
        return new Field("vertices", new SetTypeDescriptor(new VertexTypeDescriptor()));
    }

    @Override
    public String getTypeName() {
        return "UndirectedEdge";
    }
}
