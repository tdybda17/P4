package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;

public class UndirectedEdgeTypeDescriptor extends EdgeTypeDescriptor {
    public UndirectedEdgeTypeDescriptor() {
        super();
        this.addFields();

    }

    @Override
    public String getTypeName() {
        return "UndirectedEdge";
    }

    private void addFields() {
        this.addField(vertexSet());
    }

    //TODO: overvej om vi skal lave en pair
    private Field vertexSet() {
        return new Field("startVertex", new SetTypeDescriptor(new VertexTypeDescriptor()));
    }
}
