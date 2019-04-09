package Compiler.Exceptions.Visitor;

public class IncorrectTypeException extends VisitorException {
    public IncorrectTypeException() {
        super();
    }

    public IncorrectTypeException(String message) {
        super(message);
    }

    public IncorrectTypeException(String expectedType, String actualType) {
        super("Expected type " + expectedType + " but got type " + actualType);
    }

    public IncorrectTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
