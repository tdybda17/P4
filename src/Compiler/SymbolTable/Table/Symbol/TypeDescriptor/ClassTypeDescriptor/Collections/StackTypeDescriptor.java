package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.*;

public class StackTypeDescriptor extends CollectionTypeDescriptors {
    private TypeDescriptor elementType;

    public StackTypeDescriptor(TypeDescriptor elementType) {
        super();
        this.elementType = elementType;
        this.addMethods();
    }

    private void addMethods(){
        this.addMethod(push());
        this.addMethod(pop());
    }

    @Override
    public String getTypeName() {
        return "Stack";
    }

    private Method push(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);

        return new Method("push", new BooleanTypeDescriptor(), parameters);
    }

    private Method pop(){
        return new Method("pop", elementType, new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StackTypeDescriptor that = (StackTypeDescriptor) o;
        return Objects.equals(elementType, that.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), elementType);
    }
}
