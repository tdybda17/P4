package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StackTypeDescriptor extends CollectionTypeDescriptors {
    private TypeDescriptor elementType;

    StackTypeDescriptor(TypeDescriptor elementType) {
        super();
        this.elementType = elementType;

        methods.add(pushMethod());
        methods.add(popMethod());
    }


    @Override
    public List<Method> getMethods() {
        return new ArrayList<>(methods);
    }

    @Override
    public List<Field> getFields() {
        return new ArrayList<>(fields);
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
}
