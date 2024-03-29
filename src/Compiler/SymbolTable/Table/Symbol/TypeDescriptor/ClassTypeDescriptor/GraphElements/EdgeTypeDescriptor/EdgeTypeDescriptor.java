package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.GraphElementTypeDescriptors;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;

import java.util.HashSet;
import java.util.Set;

public abstract class EdgeTypeDescriptor extends GraphElementTypeDescriptors {
    private static Set<Field> fields = getStandardFields();
    private static Set<Method> methods = getStandardMethods();


    public EdgeTypeDescriptor() {
        super();
    }

    @Override
    public Set<Method> getMethods() {
        return methods;
    }

    @Override
    public Set<Field> getFields() {
        return fields;
    }

    private static Set<Field> getStandardFields() {
        Set<Field> standardFields = new HashSet<>();
        standardFields.add(color());
        standardFields.add(weight());
        return standardFields;
    }

    private static Set<Method> getStandardMethods() {
        //Edges do not have any methods yet
        return new HashSet<>();
    }

    private static Field weight(){
        return new Field("weight", new RealTypeDescriptor());
    }

    private static Field color(){
        return new Field("color", new ColorTypeDescriptor());
    }

    public static void addUserAttribute(Field userAttribute) throws TypeDescriptorException {
        for (Field field: fields) {
            if(field.equals(userAttribute)) {
                throw new TypeDescriptorException("The specified field: " + field.getFieldName() + ", added by the user was the same as a field already existing in the edge type.");
            }
        }

        fields.add(userAttribute);
    }
}
