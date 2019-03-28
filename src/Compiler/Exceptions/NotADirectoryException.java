package Compiler.Exceptions;

public class NotADirectoryException extends RuntimeException {

    public NotADirectoryException() {
        super();
    }

    public NotADirectoryException(String message) {
        super(message);
    }


}
