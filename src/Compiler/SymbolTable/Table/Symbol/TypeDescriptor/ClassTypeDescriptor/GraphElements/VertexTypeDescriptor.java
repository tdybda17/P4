package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.ColorTypeDescriptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VertexTypeDescriptor extends GraphElementTypeDescriptors {
    private static Set<Field> fields = getStandardFields();
    private static Set<Method> methods = getStandardMethods();

    public VertexTypeDescriptor() {
        super();
    }

    @Override
    public String getTypeName() {
        return "Vertex";
    }

    @Override
    public Set<Method> getMethods() {
        return new HashSet<>(methods);
    }

    @Override
    public Set<Field> getFields() {
        return new HashSet<>(fields);
    }

    private static Set<Method> getStandardMethods() {
        //We do not have any methods for vertices yet
        return new HashSet<>();
    }

    private static Set<Field> getStandardFields() {
        Set<Field> standardFields = new HashSet<>();
        standardFields.add(color());
        return standardFields;
    }

    private static Field color(){
        return new Field("color", new ColorTypeDescriptor());
    }

    public static void addUserAttribute(Field userAttribute) throws TypeDescriptorException {
        for(Field field : fields) {
            if(field.equals(userAttribute)) {
                throw new TypeDescriptorException("The specified field: " + field.getFieldName() + ", added by the user was the same as a field already existing in the vertex type.");
            }
        }

        fields.add(userAttribute);
    }
}
