package Compiler.Exceptions;

public class NotImplementedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "This method is not implemented ";
    }
}
