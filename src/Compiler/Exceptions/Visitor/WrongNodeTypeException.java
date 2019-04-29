package Compiler.Exceptions.Visitor;

import java.util.Arrays;

public class WrongNodeTypeException extends VisitorException {
    public WrongNodeTypeException () {
        super();
    }

    public WrongNodeTypeException (String message) {
        super(message);
    }

    public WrongNodeTypeException (String actualNodeType, String ... expectedNodeTypes) {
        super("The given node was of the type \'" + actualNodeType + "\', instead of one of the expected types:" + Arrays.toString(expectedNodeTypes));
    }

    public WrongNodeTypeException (String message, Throwable cause) {
        super(message, cause);
    }
}
