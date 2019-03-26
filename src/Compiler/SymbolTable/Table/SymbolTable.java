package Compiler.SymbolTable.Table;

import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import Compiler.Exceptions.SymbolTable.ScopeError.IllegalSymbolNameError;
import Compiler.SymbolTable.Table.Scope.ScopeDisplay;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable implements ISymbolTable {

    private int depth;
    private ScopeDisplay scopeDisplay;
    private Map<String, SymbolList> hashTable;

    public SymbolTable() {
        this.depth = 0;
        this.hashTable = new HashMap<>();
        this.scopeDisplay = new ScopeDisplay();
    }

    @Override
    public void openScope() {
        depth += 1; // First scope will be 1
        scopeDisplay.open(depth);
    }

    @Override
    public void closeScope() {
        // Delete symbols in this scope
        depth -= 1;
    }

    @Override
    public void enterSymbol(String name, Object type) {
        validateInputName(name);
        Symbol oldSymbol = retrieveSymbol(name);
        if(oldSymbol == null) {
            Symbol newSymbol = new Symbol(name, type, this.depth, scopeDisplay.get(this.depth));
            scopeDisplay.add(newSymbol);
            addToHashTable(newSymbol);
        } else {
            throw new DuplicateSymbolError(oldSymbol);
        }
    }

    private void validateInputName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalSymbolNameError("Name were null or blank");
        }
    }

    private void addToHashTable(Symbol symbol) {
        String name = symbol.getName();
        if(!hashTable.containsKey(name))
            hashTable.put(name, new SymbolList());
        hashTable.get(name).add(symbol);
    }

    @Override
    public Symbol retrieveSymbol(String name) {
        SymbolList symbolList = hashTable.get(name);
        if(symbolList != null)
            return symbolList.get(name);
        else return null;
    }

    public SymbolList retrieveSymbol(String name, boolean v) {
        return hashTable.get(name);
    }

    @Override
    public boolean declaredLocally(String name) {
        return false;
    }

}
