package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.CollectionTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class PriorityQueueTypeDescriptor extends CollectionTypeDescriptor {
    PriorityQueueTypeDescriptor(TypeDescriptor elementType) {
        super(elementType);
        this.addMethods();
    }

    private void addMethods() {
        addMethod(insert());
    }

    private Method insert(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(getElementType());
        return new Method("insert", new BooleanTypeDescriptor(), parameters);
    }

    @Override
    public void resetMethods(){
        super.resetMethods();
        this.addMethods();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PriorityQueueTypeDescriptor that = (PriorityQueueTypeDescriptor) o;
        return getElementType().equals(that.getElementType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getElementType());
    }
}
