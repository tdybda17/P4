package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.DirectedEdgeTypeDescriptor;

public class DirectedGraphTypeDescriptor extends GraphTypeDescriptor {

    public DirectedGraphTypeDescriptor() {
        super(new DirectedEdgeTypeDescriptor());
    }

    @Override
    public String getTypeName() {
        return "DiGraph";
    }
}
