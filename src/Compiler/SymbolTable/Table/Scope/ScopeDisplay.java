package Compiler.SymbolTable.Table.Scope;

import Compiler.Exceptions.SymbolTable.ScopeError.AddingToClosedScopeDisplayError;
import Compiler.Exceptions.SymbolTable.ScopeError.GettingFromClosedScopeDisplayError;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeDisplay {
    private Map<Integer, SymbolList> symbolMap;

    public ScopeDisplay() {
        this.symbolMap = new HashMap<>();
    }

    public SymbolList get(final int depth) {
        SymbolList symbolList = symbolMap.get(depth);
        if(symbolList == null) {
            throw new GettingFromClosedScopeDisplayError("You tried to get the symbol list of a closed scope");
        } else {
            return symbolMap.get(depth);
        }
    }

    public void open(final int depth) {
        symbolMap.put(depth, new SymbolList());
    }

    public void add(Symbol symbol, int depth) {
        SymbolList symbolList = symbolMap.get(depth);
        if(symbolList == null) {
            throw new AddingToClosedScopeDisplayError("The depth: " + depth + ", was already closed.");
        } else {
            symbolMap.get(depth).add(symbol);
        }
    }

    public List<Symbol> remove(int depth) {
        List<Symbol> symbolsToRemove = symbolMap.get(depth).asList();
        symbolMap.remove(depth);
        return symbolsToRemove;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ScopeDisplay {\n");
        for(Integer key : symbolMap.keySet()) {
            stringBuilder.append('\t').append(key).append(": ").append(symbolMap.get(key)).append('\n');
        }
        stringBuilder.append('}');

        return stringBuilder.toString();
    }
}
