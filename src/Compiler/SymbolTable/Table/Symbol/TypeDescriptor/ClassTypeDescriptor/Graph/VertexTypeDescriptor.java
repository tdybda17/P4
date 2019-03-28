package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ColorTypeDescriptor;

public class VertexTypeDescriptor extends ClassTypeDescriptor {
    public VertexTypeDescriptor() {
        super();
        this.addMethods();
        this.addFields();
    }

    @Override
    public String getTypeName() {
        return "Stack";
    }

    private void addMethods(){
        this.addMethod(getOutgoingEdges());
        this.addMethod(getNeighbours());
    }

    private void addFields() {
        this.addField(color());
    }

    private Method getOutgoingEdges(){
        return null;
    }

    private Method getNeighbours(){
        return null;
    }

    private Field color(){
        return new Field("color", new ColorTypeDescriptor());
    }

}
