package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;

public class DirectedEdgeTypeDescriptor extends EdgeTypeDescriptor {
    public DirectedEdgeTypeDescriptor() {
        super();
        this.addDirectedFields();
    }

    private void addDirectedFields() {
        this.addField(this.startVertex());
        this.addField(this.endVertex());
    }

    private Field startVertex() {
        return new Field("startVertex", new VertexTypeDescriptor());
    }

    private Field endVertex() {
        return new Field("endVertex", new VertexTypeDescriptor());
    }

    @Override
    public String getTypeName() {
        return "DirectedEdge";
    }
}
