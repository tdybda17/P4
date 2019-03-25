package Compiler.Exceptions.SymbolTable.ScopeError;

public class IllegalSymbolNameError extends Error {

    public IllegalSymbolNameError() {
    }

    public IllegalSymbolNameError(String message) {
        super(message);
    }
}

