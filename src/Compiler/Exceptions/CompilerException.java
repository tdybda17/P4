package Compiler.Exceptions;

public class CompilerException extends RuntimeException {
    public CompilerException() {
    }

    public CompilerException(String message) {
        super(message);
    }

    public CompilerException(String message, Throwable cause) {
        super(message, cause);
    }
}
