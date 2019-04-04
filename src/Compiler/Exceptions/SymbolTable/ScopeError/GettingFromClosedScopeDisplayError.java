package Compiler.Exceptions.SymbolTable.ScopeError;

public class GettingFromClosedScopeDisplayError extends Error {
    public GettingFromClosedScopeDisplayError() {
    }

    public GettingFromClosedScopeDisplayError(String message) {
        super(message);
    }
}
