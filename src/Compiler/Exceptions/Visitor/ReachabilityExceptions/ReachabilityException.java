package Compiler.Exceptions.Visitor.ReachabilityExceptions;

import Compiler.Exceptions.Visitor.VisitorException;

public class ReachabilityException extends VisitorException {
    public ReachabilityException() {
        super();
    }

    public ReachabilityException(String message) {
        super(message);
    }

    public ReachabilityException(String message, Throwable cause) {
        super(message, cause);
    }
}
