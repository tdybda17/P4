package Compiler.SymbolTable.Table.Scope;

import Compiler.Exceptions.SymbolTable.ScopeError.AddingToClosedScopeDisplayError;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeDisplay {

    private int currentDepth = 0;
    private Map<Integer, SymbolList> symbolMap;

    public ScopeDisplay() {
        this.symbolMap = new HashMap<>();
    }

    public SymbolList get(final int depth) {
        return symbolMap.get(depth);
    }

    public void open(final int depth) {
        currentDepth++;
        symbolMap.put(depth, new SymbolList());
    }

    public void add(Symbol symbol) {
        if(currentDepth > 0)
            symbolMap.get(this.currentDepth).add(symbol);
        else throw new AddingToClosedScopeDisplayError();
    }

    public List<Symbol> remove(int depth) {
        List<Symbol> symbolsToRemove = symbolMap.get(depth).asList();
        symbolMap.remove(depth);
        if(currentDepth > 0)
            currentDepth--;

        return symbolsToRemove;
    }

}
