package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.HashSet;
import java.util.Set;

public abstract class ClassTypeDescriptor extends TypeDescriptor {
    private Set<Method> methods;
    private Set<Field> fields;
    public ClassTypeDescriptor() {
        methods = new HashSet<>();
        fields = new HashSet<>();
    }

    public Set<Method> getMethods() {
        return methods;
    }

    public Set<Field> getFields() {
        return fields;
    }

    protected void addMethod(Method method){
        methods.add(method);
    }

    protected void addField(Field field) {
        fields.add(field);
    }
}
