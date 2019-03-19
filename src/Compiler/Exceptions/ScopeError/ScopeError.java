package Compiler.Exceptions.ScopeError;

public class ScopeError extends Error {

    public ScopeError() {
    }

    public ScopeError(String message) {
        super(message);
    }

    public ScopeError(String message, Throwable cause) {
        super(message, cause);
    }

    public ScopeError(Throwable cause) {
        super(cause);
    }

    protected ScopeError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
