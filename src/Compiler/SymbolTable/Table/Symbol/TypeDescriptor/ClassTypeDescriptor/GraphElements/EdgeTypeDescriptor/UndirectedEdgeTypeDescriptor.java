package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;

import java.util.Set;

public class UndirectedEdgeTypeDescriptor extends EdgeTypeDescriptor {
    public UndirectedEdgeTypeDescriptor() {
        super();
    }

    @Override
    public Set<Method> getMethods() {
        return super.getMethods();
    }

    @Override
    public Set<Field> getFields() {
        Set<Field> fields = super.getFields();
        addUndirectedFields(fields);

        return super.getFields();
    }

    @Override
    public String getJavaName() {
        return getTypeName();
    }


    private void addUndirectedFields(Set<Field> fields) {
        fields.add(vertices());
    }

    private Field vertices() {
        return new Field("vertices", new SetTypeDescriptor(new VertexTypeDescriptor()));
    }

    @Override
    public String getTypeName() {
        return "UndirectedEdge";
    }
}
