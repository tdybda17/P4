package Compiler.Exceptions.Visitor;

import Compiler.Exceptions.CompilerException;

public class VisitorException extends CompilerException {
    public VisitorException() {
        super();
    }

    public VisitorException(String message) {
        super(message);
    }

    public VisitorException(String message, Throwable cause) {
        super(message, cause);
    }
}
