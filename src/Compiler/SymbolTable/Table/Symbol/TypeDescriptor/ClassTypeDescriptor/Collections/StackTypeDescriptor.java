package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.*;

public class StackTypeDescriptor extends CollectionTypeDescriptors {
    private TypeDescriptor elementType;

    StackTypeDescriptor(TypeDescriptor elementType) {
        super();
        this.elementType = elementType;

        methods.add(pushMethod());
        methods.add(popMethod());
    }


    @Override
    public Set<Method> getMethods() {
        return new HashSet<>(methods);
    }

    @Override
    public Set<Field> getFields() {
        return new HashSet<>(fields);
    }

    @Override
    public String getTypeName() {
        return "Stack";
    }

    private Method pushMethod(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);

        return new Method("push", new BooleanTypeDescriptor(), parameters);
    }

    private Method popMethod(){
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
        return Objects.hash(elementType);
    }
}
