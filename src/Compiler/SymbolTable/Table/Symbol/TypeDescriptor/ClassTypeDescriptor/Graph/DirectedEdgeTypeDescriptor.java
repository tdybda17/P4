package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;

public class DirectedEdgeTypeDescriptor extends EdgeTypeDescriptor {
    public DirectedEdgeTypeDescriptor() {
        super();
        this.addFields();

    }

    @Override
    public String getTypeName() {
        return "DirectedEdge";
    }

    private void addFields() {
        this.addField(startVertex());
        this.addField(endVertex());
    }

    private Field startVertex() {
        return new Field("startVertex", new VertexTypeDescriptor());
    }

    private Field endVertex() {
        return new Field("endVertex", new VertexTypeDescriptor());
    }
}
