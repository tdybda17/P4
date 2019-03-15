package Compiler.SymbolTable.Table;

import Compiler.SymbolTable.Table.Scope.Scope;
import Compiler.SymbolTable.Table.Symbol.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable implements ISymbolTable {

    private Map<Integer, Scope> scopesMap;
    private int currentScope;

    public SymbolTable() {
        this.scopesMap = new HashMap<>();
    }

    @Override
    // Opens a new scope relatively to the current scope
    public void openScope() {

    }

    @Override
    // Closes the current scope and jumps to currents inner scope
    public void closeScope() {

    }

    @Override
    public void enterSymbol(Symbol symbol) {

    }

    @Override
    public Symbol retrieveSymbol(String name) {
        return null;
    }

    @Override
    public boolean declaredLocally(String name) {
        return false;
    }

}
