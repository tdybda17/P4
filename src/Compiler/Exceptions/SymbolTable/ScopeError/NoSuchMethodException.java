package Compiler.Exceptions.SymbolTable.ScopeError;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

public class NoSuchMethodException extends ScopeError {
    public NoSuchMethodException(Symbol symbol) {
        super("Error: Function '" + symbol.getName() + "' does not exist");
    }

    public NoSuchMethodException(TypeDescriptor td, String id) {
        super("Error: '" + td.getTypeName() + "' does not contain method '" + id + "'");
    }

    public NoSuchMethodException(String message) {
        super(message);
    }
}
