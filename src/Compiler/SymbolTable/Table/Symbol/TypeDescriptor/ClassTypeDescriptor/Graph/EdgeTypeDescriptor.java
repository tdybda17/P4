package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.RealTypeDescriptor;

public class EdgeTypeDescriptor extends ClassTypeDescriptor {
    private boolean directed;
    public EdgeTypeDescriptor(Boolean directed) {
        super();
        this.directed = directed;
        this.addMethods();
        this.addFields();
    }

    @Override
    public String getTypeName() {
        return "Edge";
    }

    private void addMethods(){
        //We do not have any methods for the Edge Type yet
    }

    private void addFields() {
        this.addField(color());
        this.addField(weight());
        if(directed) {
            this.addDirectedFields();
        } else {
            this.addUndirectedFields();
        }
    }

    private Field color(){
        return new Field("color", new ColorTypeDescriptor());
    }

    private Field weight(){
        return new Field("weight", new RealTypeDescriptor());
    }

    public void addUserAttribute(Field userAttribute) throws TypeDescriptorException {
        for (Field field: this.getFields()) {
            if(field.equals(userAttribute)) {
                throw new TypeDescriptorException("The specified field: " + field.getFieldName() + ", added by the user was the same as an field already existing in the edge type.");
            }
        }

        this.addField(userAttribute);
    }

    private void addDirectedFields() {
        this.addField(this.startVertex());
        this.addField(this.endVertex());
    }

    private Field startVertex() {
        return new Field("startVertex", new VertexTypeDescriptor());
    }

    private Field endVertex() {
        return new Field("endVertex", new VertexTypeDescriptor());
    }

    private void addUndirectedFields() {
        this.addField(this.vertexSet());
    }

    private Field vertexSet() {
        return new Field("vertices", new SetTypeDescriptor(new VertexTypeDescriptor()));
    }

}
