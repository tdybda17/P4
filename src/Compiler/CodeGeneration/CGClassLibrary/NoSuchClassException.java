package Compiler.CodeGeneration.CGClassLibrary;

public class NoSuchClassException extends RuntimeException {
    public NoSuchClassException() {
    }

    public NoSuchClassException(String message) {
        super(message);
    }
}
