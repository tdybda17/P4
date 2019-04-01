package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph.EdgeTypeDescriptor;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.VertexPairTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.RealTypeDescriptor;

public abstract class EdgeTypeDescriptor extends ClassTypeDescriptor {

    public EdgeTypeDescriptor() {
        super();
        this.addMethods();
        this.addFields();
    }

    private void addMethods(){
        //We do not have any methods for the Edge Type yet
    }

    private void addFields() {
        this.addField(color());
        this.addField(weight());
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
}
