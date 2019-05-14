package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.VoidTypeDescriptor;

import java.util.*;

public class StackTypeDescriptor extends CollectionTypeDescriptor {
    public StackTypeDescriptor(TypeDescriptor elementType) {
        super(elementType);
        this.addMethods();
    }

    @Override
    public void setElementType(TypeDescriptor elementType) {
        super.setElementType(elementType);
        this.addMethods();
    }

    private void addMethods() {
        this.addMethod(push());
        this.addMethod(pop());
    }

    @Override
    public String getTypeName() {
        return "Stack";
    }

    private Method push(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(getElementType());

        return new Method("push", new VoidTypeDescriptor(), parameters);
    }

    private Method pop(){
        return new Method("pop", getElementType(), new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StackTypeDescriptor that = (StackTypeDescriptor) o;
        return Objects.equals(getElementType(), that.getElementType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getElementType());
    }

    @Override
    public String getJavaName() {
        return "Stack<" + getElementType().getJavaName() + ">";
    }
}
