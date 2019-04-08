package Compiler.Exceptions.Visitor;

public class WrongAmountOfChildrenException extends VisitorException {
    public WrongAmountOfChildrenException() {
        super();
    }

    public WrongAmountOfChildrenException(String message) {
        super(message);
    }

    public WrongAmountOfChildrenException(String message, Throwable cause) {
        super(message, cause);
    }
}
