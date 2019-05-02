package Compiler.Exceptions.Visitor;

public class OptimizationException extends VisitorException {
    public OptimizationException() {
    }

    public OptimizationException(String message) {
        super(message);
    }

    public OptimizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
