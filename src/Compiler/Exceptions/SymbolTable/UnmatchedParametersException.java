package Compiler.Exceptions.SymbolTable;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

public class UnmatchedParametersException extends SymbolTableException {
    public UnmatchedParametersException(TypeDescriptor formalParameter, TypeDescriptor actualParameter, String funcName) {
        super("Error: Tried to parse actual parameter of type '" + actualParameter.getTypeName()
                + "' but expected type '" + formalParameter.getTypeName() + "'" + ". This call was done in function '" + funcName + "'");
    }

    public UnmatchedParametersException(String message) {
        super(message);
    }
}
