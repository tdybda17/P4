package Compiler.Exceptions.SymbolTableExceptions;

import Compiler.SymbolTable.Table.Symbol.Symbol;

public class NoSuchSymbolException extends RuntimeException {

    public NoSuchSymbolException() {
    }

    public NoSuchSymbolException(String message) {
        super(message);
    }

    public NoSuchSymbolException(Symbol symbol) {
        super("No symbol for name " + symbol.getName() + " found in table");
    }

}
