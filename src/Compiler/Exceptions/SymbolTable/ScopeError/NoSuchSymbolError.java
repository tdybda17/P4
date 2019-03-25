package Compiler.Exceptions.SymbolTable.ScopeError;

import Compiler.SymbolTable.Table.Symbol.Symbol;

public class NoSuchSymbolError extends ScopeError {

    public NoSuchSymbolError() {
    }

    public NoSuchSymbolError(String message) {
        super(message);
    }

    public NoSuchSymbolError(Symbol symbol) {
        super("No symbol for name " + symbol.getName() + " found in table");
    }

}
