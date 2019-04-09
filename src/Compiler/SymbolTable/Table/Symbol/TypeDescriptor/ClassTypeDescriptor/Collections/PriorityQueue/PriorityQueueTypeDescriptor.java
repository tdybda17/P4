package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.CollectionTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class PriorityQueueTypeDescriptor extends CollectionTypeDescriptor {
    private TypeDescriptor elementType;

    PriorityQueueTypeDescriptor(TypeDescriptor elementType) {
        super();
        this.elementType = elementType;
        this.addMethods();
    }

    private void addMethods() {
        addMethod(insert());
    }

    private Method insert(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);
        return new Method("insert", new BooleanTypeDescriptor(), parameters);
    }

    @Override
    public void setElementType(TypeDescriptor elementType) {
        this.elementType = elementType;
    }

    @Override
    public TypeDescriptor getElementType() {
        return elementType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PriorityQueueTypeDescriptor that = (PriorityQueueTypeDescriptor) o;
        return elementType.equals(that.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), elementType);
    }
}
