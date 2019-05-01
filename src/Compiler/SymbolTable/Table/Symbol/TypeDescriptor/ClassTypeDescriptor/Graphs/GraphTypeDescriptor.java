package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.EdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.LabelTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class GraphTypeDescriptor extends ClassTypeDescriptor {
    private EdgeTypeDescriptor edgeType;

    public GraphTypeDescriptor(EdgeTypeDescriptor edgeType) {
        super();
        this.edgeType = edgeType;
        this.addMethods();
        this.addFields();
    }

    private void addMethods(){
        this.addMethod(addEdge());
        this.addMethod(removeEdge());
        this.addMethod(addVertex());
        this.addMethod(removeVertex());
        this.addMethod(getEdge());
        this.addMethod(getVertex());


        this.addMethod(getNeighbours());
        this.addMethod(getOutgoingEdges());
    }

    private Method addEdge(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new VertexTypeDescriptor());
        parameters.add(new VertexTypeDescriptor());
        return new Method("addEdge", new BooleanTypeDescriptor(), parameters);
    }

    private Method removeEdge(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(edgeType);
        return new Method("removeEdge", new BooleanTypeDescriptor(), parameters);
    }

    private Method getEdge() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new VertexTypeDescriptor());
        parameters.add(new VertexTypeDescriptor());
        return new Method("getEdge", edgeType, parameters);
    }

    private Method getVertex() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new LabelTypeDescriptor());
        return new Method("getVertex", new VertexTypeDescriptor(), parameters);
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
