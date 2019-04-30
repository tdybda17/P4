package Compiler.Exceptions.Visitor;

public class AssignmentException extends VisitorException {
    public AssignmentException() {
        super();
    }

    public AssignmentException(String message) {
        super(message);
    }

    public AssignmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
