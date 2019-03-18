package Compiler.Exceptions.SymbolTableExceptions;

import Compiler.SymbolTable.Table.Symbol.Symbol;

public class DuplicationSymbolException extends RuntimeException {

    public DuplicationSymbolException() {
    }

    public DuplicationSymbolException(String message) {
        super(message);
    }

    public DuplicationSymbolException(Symbol symbol) {
        super("symbol name '" + symbol.getName() + "' already added");
    }

}
