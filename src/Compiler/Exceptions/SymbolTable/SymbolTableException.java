package Compiler.Exceptions.SymbolTable;

import Compiler.Exceptions.CompilerException;

public class SymbolTableException extends CompilerException {
    public SymbolTableException() {
    }

    public SymbolTableException(String message) {
        super(message);
    }

    public SymbolTableException(String message, Throwable cause) {
        super(message, cause);
    }
}
