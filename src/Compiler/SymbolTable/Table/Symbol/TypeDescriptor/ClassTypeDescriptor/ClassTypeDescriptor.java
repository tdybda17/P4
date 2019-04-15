package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
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
        for (Method m : this.getMethods()) {
            if(m.equals(method)) {
                throw new TypeDescriptorException("The method name: " + method.getMethodName() + "was used two times in: " + this);
            }
        }
        methods.add(method);
    }

    protected void addField(Field field) {
        for (Field f : this.getFields()) {
            if(f.equals(field)) {
                throw new TypeDescriptorException("The field name: " + field.getFieldName() + "was used two times in: " + this);
            }
        }
        fields.add(field);
    }
}
