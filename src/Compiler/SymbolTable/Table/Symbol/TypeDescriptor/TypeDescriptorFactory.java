package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import Compiler.Exceptions.SymbolTable.IllegalTypeException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.RealTypeDescriptor;

public class TypeDescriptorFactory {
    public TypeDescriptorFactory() {
    }

    public TypeDescriptor create(String typeName) throws IllegalTypeException {
        switch (typeName) {
            case "Integer": return new IntegerTypeDescriptor();
            case "Real": return new RealTypeDescriptor();
            case "Color": return new ColorTypeDescriptor();
            case "Boolean": return new BooleanTypeDescriptor();
            case "Set": return new SetTypeDescriptor(null);
            default: throw new IllegalTypeException("The typename:" + typeName + ", is not a legal type in our language");
        }
    }
}
