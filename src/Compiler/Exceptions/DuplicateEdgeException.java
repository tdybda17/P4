package Compiler.Exceptions;

public class DuplicateEdgeException extends CompilerException {
    public DuplicateEdgeException() {
    }

    public DuplicateEdgeException(String message) {
        super(message);
    }

    public DuplicateEdgeException(String message, Throwable cause) {
        super(message, cause);
    }
}
