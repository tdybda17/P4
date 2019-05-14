package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graphs;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;

public class UndirectedGraphTypeDescriptor extends GraphTypeDescriptor {

    public UndirectedGraphTypeDescriptor() {
        super(new UndirectedEdgeTypeDescriptor());
    }

    @Override
    public String getJavaName() {
        return getTypeName();
    }

    @Override
    public String getTypeName() {
        return "Graph";
    }
}
