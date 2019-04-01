package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph.GraphTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;

public class UndirectedGraphTypeDescriptor extends GraphTypeDescriptor {
    public UndirectedGraphTypeDescriptor() {
        super();
        this.addUndirectedMethods();
    }

    @Override
    public String getTypeName() {
        return "UndirectedGraph";
    }


    private void addUndirectedMethods(){
        this.addMethod(this.getOutgoingEdges());
    }

    private Method getOutgoingEdges(){
        TypeDescriptor returnType = new SetTypeDescriptor(new UndirectedEdgeTypeDescriptor());

        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new VertexTypeDescriptor());
        return new Method("getOutgoingEdges", returnType, parameters);
    }


}
