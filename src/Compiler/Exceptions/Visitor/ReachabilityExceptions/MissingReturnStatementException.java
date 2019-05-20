package Compiler.Exceptions.Visitor.ReachabilityExceptions;

public class MissingReturnStatementException extends ReachabilityException {
    public MissingReturnStatementException() {
        super();
    }

    public MissingReturnStatementException(String message) {
        super(message);
    }

    public MissingReturnStatementException(String message, Throwable cause) {
        super(message, cause);
    }
}
