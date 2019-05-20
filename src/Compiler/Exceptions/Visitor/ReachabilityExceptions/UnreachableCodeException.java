package Compiler.Exceptions.Visitor.ReachabilityExceptions;

public class UnreachableCodeException extends ReachabilityException {
    public UnreachableCodeException() {
        super();
    }

    public UnreachableCodeException(String message) {
        super(message);
    }

    public UnreachableCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
