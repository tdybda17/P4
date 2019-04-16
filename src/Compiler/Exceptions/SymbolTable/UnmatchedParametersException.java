package Compiler.Exceptions.SymbolTable;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

public class UnmatchedParametersException extends SymbolTableException {
    public UnmatchedParametersException(TypeDescriptor formalParameter, TypeDescriptor actualParameter) {
        super("Error: Tried to parse actual parameter of type '" + actualParameter.getTypeName()
                + "' but expected type '" + formalParameter.getTypeName() + "'");
    }

    public UnmatchedParametersException(String message) {
        super(message);
    }
}
