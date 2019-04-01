package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.ColorTypeDescriptor;

public class VertexTypeDescriptor extends ClassTypeDescriptor {
    public VertexTypeDescriptor() {
        super();
        this.addMethods();
        this.addFields();
    }

    @Override
    public String getTypeName() {
        return "Vertex";
    }

    private void addMethods(){
        //We do not have any methods for vertexes yet
    }

    private void addFields() {
        this.addField(color());
    }


    private Field color(){
        return new Field("color", new ColorTypeDescriptor());
    }

    public void addUserAttribute(Field userAttribute) throws TypeDescriptorException {
        for (Field field: this.getFields()) {
            if(field.equals(userAttribute)) {
                throw new TypeDescriptorException("The specified field: " + field.getFieldName() + ", added by the user was the same as an field already existing in the vertex type.");
            }
        }

        this.addField(userAttribute);
    }
}
