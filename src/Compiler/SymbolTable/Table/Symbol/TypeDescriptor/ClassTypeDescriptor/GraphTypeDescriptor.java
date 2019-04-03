package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.EdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphTypeDescriptor extends ClassTypeDescriptor {
    private EdgeTypeDescriptor edgeType;

    public GraphTypeDescriptor(EdgeTypeDescriptor edgeType) {
        super();
        this.edgeType = edgeType;
        this.addMethods();
        this.addFields();
    }

    @Override
    public String getTypeName() {
        return "Graph";
    }

    private void addMethods(){
        this.addMethod(addEdge());
        this.addMethod(removeEdge());
        this.addMethod(addVertex());
        this.addMethod(removeVertex());

        this.addMethod(getNeighbours());
        this.addMethod(getOutgoingEdges());
    }

    private Method addEdge(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(edgeType);
        return new Method("addEdge", new BooleanTypeDescriptor(), parameters);
    }

    private Method removeEdge(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(edgeType);
        return new Method("removeEdge", new BooleanTypeDescriptor(), parameters);
    }

    //TODO: få lavet
    private Method getEdge() {
        return null;
    }

    private Method addVertex() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new VertexTypeDescriptor());
        return new Method("addVertex", new BooleanTypeDescriptor(), parameters);
    }

    private Method removeVertex() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new VertexTypeDescriptor());
        return new Method("removeVertex", new BooleanTypeDescriptor(), parameters);
    }

    private Method getOutgoingEdges() {
        TypeDescriptor returnType = new SetTypeDescriptor(edgeType);
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new VertexTypeDescriptor());
        return new Method("getOutgoingEdges", returnType, parameters);
    }

    private Method getNeighbours(){
        TypeDescriptor returnType = new SetTypeDescriptor(new VertexTypeDescriptor());
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new VertexTypeDescriptor());
        return new Method("getNeighbours", returnType, parameters);
    }

    private void addFields() {
        this.addField(vertices());
        this.addField(edges());
    }

    private Field vertices() {
        return new Field("vertices", new SetTypeDescriptor(new VertexTypeDescriptor()));
    }

    private Field edges() {
        return new Field("edges", new SetTypeDescriptor(edgeType));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GraphTypeDescriptor that = (GraphTypeDescriptor) o;
        return Objects.equals(edgeType, that.edgeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), edgeType);
    }
}