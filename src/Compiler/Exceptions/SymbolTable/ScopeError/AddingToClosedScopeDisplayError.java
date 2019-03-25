package Compiler.Exceptions.SymbolTable.ScopeError;

public class AddingToClosedScopeDisplayError extends Error {

    public AddingToClosedScopeDisplayError() {
    }

    public AddingToClosedScopeDisplayError(String message) {
        super(message);
    }

}
