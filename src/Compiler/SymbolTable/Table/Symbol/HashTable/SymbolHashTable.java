package Compiler.SymbolTable.Table.Symbol.HashTable;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.Map;

public class SymbolHashTable {

    private Map<String, SymbolList> hashTable;

    public void add(Symbol symbol) {

    }

    public SymbolList get(final String name) {
        return hashTable.get(name);
    }

}
