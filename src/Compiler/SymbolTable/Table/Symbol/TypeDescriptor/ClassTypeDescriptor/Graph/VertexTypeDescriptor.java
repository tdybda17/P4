package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.Exceptions.CompilerException;
import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;

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
        this.addMethod(getOutgoingEdges());
        this.addMethod(getNeighbours());
    }

    private void addFields() {
        this.addField(color());
    }

    private Method getOutgoingEdges(){
        TypeDescriptor returnType = new SetTypeDescriptor(new EdgeTypeDescriptor());
        return new Method("getOutgoingEdges", returnType, new ArrayList<>());
    }

    private Method getNeighbours(){
        TypeDescriptor returnType = new SetTypeDescriptor(new VertexTypeDescriptor());
        return new Method("getNeighbours", returnType, new ArrayList<>());
    }

    private Field color(){
        return new Field("color", new ColorTypeDescriptor());
    }

    public void addUserAttribute(Field userAttribute) throws TypeDescriptorException {
        for (Field field: this.getFields()) {
            if(field.equals(userAttribute)) {
                throw new TypeDescriptorException("The specified field: " + field.getFieldName() + ", added by the user was the same as an already added field");
            }
        }

        this.addField(userAttribute);
    }
}
