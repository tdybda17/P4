package Compiler.Exceptions.SymbolTableExceptions;

public class NoValueSetForSymbol extends RuntimeException {

    public NoValueSetForSymbol() {
    }

    public NoValueSetForSymbol(String message) {
        super(message);
    }
}
