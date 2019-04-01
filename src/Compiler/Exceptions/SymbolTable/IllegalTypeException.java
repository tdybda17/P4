package Compiler.Exceptions.SymbolTable;

import Compiler.Exceptions.CompilerException;

public class IllegalTypeException extends CompilerException {
    public IllegalTypeException() {
        super();
    }

    public IllegalTypeException(String message) {
        super(message);
    }

    public IllegalTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
