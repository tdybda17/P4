package Compiler.Exceptions.SymbolTable.ScopeError;

import Compiler.SymbolTable.Table.Symbol.Symbol;

public class DuplicateSymbolError extends ScopeError {

    public DuplicateSymbolError() {
    }

    public DuplicateSymbolError(String message) {
        super(message);
    }

    public DuplicateSymbolError(Symbol symbol) {
        super("symbol name '" + symbol.getName() + "' already added");
    }

}
