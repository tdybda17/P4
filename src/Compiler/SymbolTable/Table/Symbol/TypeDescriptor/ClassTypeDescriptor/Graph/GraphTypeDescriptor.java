package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;

public class GraphTypeDescriptor extends ClassTypeDescriptor {
    private boolean directed;

    public GraphTypeDescriptor(Boolean directed) {
        super();
        this.directed = directed;
        this.addMethods();
        this.addFields();
    }

    @Override
    public String getTypeName() {
        return "Graph";
    }

    private void addMethods(){
        //We do not have any methods for the Edge Type yet
        this.addMethod(getOutgoingEdges());
        this.addMethod(getNeighbours());
    }

    private void addFields() {

    }

    private Method getOutgoingEdges(){
        TypeDescriptor returnType = new SetTypeDescriptor(new EdgeTypeDescriptor()); //vi har to mulige underklasser

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


}
