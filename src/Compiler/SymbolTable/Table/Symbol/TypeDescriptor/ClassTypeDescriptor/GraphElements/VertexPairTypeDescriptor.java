package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;

public class VertexPairTypeDescriptor extends GraphElementTypeDescriptors {

    public VertexPairTypeDescriptor() {
        super();
        this.addMethods();
        this.addFields();
    }

    @Override
    public String getTypeName() {
        return "VertexPair";
    }

    private void addMethods(){
        //A vertex pair has no methods yet
    }

    private void addFields(){
        this.addField(this.vertexOne());
        this.addField(this.vertexTwo());
    }

    private Field vertexOne(){
        return new Field("vertexOne", new VertexTypeDescriptor());
    }

    private Field vertexTwo(){
        return new Field("vertexTwo", new VertexTypeDescriptor());
    }
    //Vertex a
    //Vertex b
}
