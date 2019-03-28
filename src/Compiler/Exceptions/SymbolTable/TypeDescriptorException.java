package Compiler.Exceptions.SymbolTable;

public class TypeDescriptorException extends SymbolTableException {
    public TypeDescriptorException() {
    }

    public TypeDescriptorException(String message) {
        super(message);
    }

    public TypeDescriptorException(String message, Throwable cause) {
        super(message, cause);
    }
}
