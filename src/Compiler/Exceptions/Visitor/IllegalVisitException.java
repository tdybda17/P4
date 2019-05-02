package Compiler.Exceptions.Visitor;

public class IllegalVisitException extends VisitorException {
    public IllegalVisitException() {
        super();
    }

    public IllegalVisitException(String message) {
        super(message);
    }

    public IllegalVisitException(String message, Throwable cause) {
        super(message, cause);
    }
}
