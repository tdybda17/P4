package Compiler.Exceptions.SymbolTable.ScopeError;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

public class NoSuchFieldException extends ScopeError {
    public NoSuchFieldException(TypeDescriptor td, String id) {
        super("Error: '" + td.getTypeName() + "' does not contain field '" + id + "'");
    }

    public NoSuchFieldException(String message) {
        super(message);
    }
}
