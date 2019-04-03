package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;

import java.util.HashSet;
import java.util.Set;

public class DirectedEdgeTypeDescriptor extends EdgeTypeDescriptor {
    public DirectedEdgeTypeDescriptor() {
        super();
    }

    @Override
    public Set<Method> getMethods() {
        return new HashSet<>(super.getMethods());
    }

    @Override
    public Set<Field> getFields() {
        Set<Field> fields = super.getFields();
        addDirectedFields(fields);

        return new HashSet<>(fields);
    }

    private void addDirectedFields(Set<Field> fields) {
        fields.add(startVertex());
        fields.add(endVertex());
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
