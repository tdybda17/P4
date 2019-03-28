package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;

public class EdgeTypeDescriptor extends ClassTypeDescriptor {
    public EdgeTypeDescriptor() {
        super();
        this.addMethods();
        this.addFields();
    }

    @Override
    public String getTypeName() {
        return "Edge";
    }

    private void addMethods(){

    }

    private void addFields() {

    }


}
