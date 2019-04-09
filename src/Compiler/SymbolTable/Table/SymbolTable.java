package Compiler.SymbolTable.Table;

import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import Compiler.Exceptions.SymbolTable.ScopeError.IllegalSymbolNameError;
import Compiler.Exceptions.SymbolTable.SymbolTableException;
import Compiler.SymbolTable.Table.Scope.ScopeDisplay;
import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.*;

public class SymbolTable implements ISymbolTable {
    private int depth;
    private ScopeDisplay scopeDisplay;
    private Map<String, Symbol> hashMap;

    public SymbolTable() {
        this.depth = 0;
        this.hashMap = new HashMap<>();
        this.scopeDisplay = new ScopeDisplay();
    }

    @Override
    public void openScope() {
        depth += 1; // First scope will be 1
        scopeDisplay.open(depth);
    }

    @Override
    public void closeScope() {
        // Delete symbols currently in scope
        deleteSymbolsInCurrentDepth();
        depth -= 1;
    }

    private void deleteSymbolsInCurrentDepth() {
        List<Symbol> symbolsToRemove = scopeDisplay.remove(depth);
        for (Symbol symbol : symbolsToRemove)
            hashMap.remove(symbol.getName());
    }

    @Override
    public void enterSymbol(String name, Attributes attributes) {
        validateInputName(name);

        Symbol oldSymbol = hashMap.get(name);
        if(oldSymbol == null) {
            Symbol newSymbol = new Symbol(name, attributes);
            scopeDisplay.add(newSymbol, depth);
            addToHashTable(newSymbol);
        } else {
            throw new DuplicateSymbolError(oldSymbol);
        }
    }

    public void enterSymbol(Symbol symbol) {
        enterSymbol(symbol.getName(), symbol.getAttributes());
    }

    private void validateInputName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalSymbolNameError("Name were null or blank");
        }
    }

    private void addToHashTable(Symbol symbol) {
        String name = symbol.getName();
        if(!hashMap.containsKey(name)) {
            hashMap.put(name, symbol);
        }
        else {
            throw new DuplicateSymbolError("An symbol with the name: " + name + ", already exists in the table");
        }
    }

    @Override
    public Symbol retrieveSymbol(String name) {
        Symbol symbol = hashMap.get(name);
        if(symbol!= null)
            return symbol;
        else throw new SymbolTableException("The given name: " + name + ", could not be retrieved because no mapping from it exists");
    }

    @Override
    public boolean declaredLocally(String name) {
        return false;
    }

    /* Method for testing */
    Map<String, Symbol> getHashMap() {
        return hashMap;
    }

    /* Method for testing */
    SymbolList getCurrentScopeDisplay() {
        return scopeDisplay.get(depth);
    }

    @Override
    public String toString() {
        return "currDepth = " + depth + "\n" +
                scopeDisplay.toString() + "\n" +
                "hashMap = " + hashMap + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolTable that = (SymbolTable) o;
        return depth == that.depth &&
                Objects.equals(scopeDisplay, that.scopeDisplay) &&
                Objects.equals(hashMap, that.hashMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth, scopeDisplay, hashMap);
    }
}
