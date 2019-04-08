package Compiler.SymbolTable.Table.Symbol.SymbolList;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import java.util.*;

public class SymbolList {

    private List<Symbol> symbols;

    public SymbolList() {
        this.symbols = new ArrayList<>();
    }

    public SymbolList(Symbol ... symbols) {
        this.symbols = new ArrayList<>();
        add(symbols);
    }

    public void add(Symbol ... symbols) {
        for (Symbol sym : symbols)
            add(sym);
    }

    public boolean add(Symbol symbol) {
        if(!BinarySymbolSearch.exists(this, symbol)) {
            symbols.add(symbol);
            Collections.sort(symbols);
            return true;
        } else {
            return false;
            //TODO: maybe throw exception instead because this is called in void methods (e.g. in add(Symbol... symbols)) so the user will never know that it returned false
        }
    }

    public Symbol get(String name) {
        return BinarySymbolSearch.find(this, name);
    }

    public Symbol get(int index) {
        return symbols.get(index);
    }

    public Symbol remove(int i) {
        return symbols.remove(i);
    }

    public int size() {
        return symbols.size();
    }

    /*
    public Symbol remove(Symbol symbol) {
        return this.symbols.remove(symbol);
    }
    */

    public List<Symbol> asList() {
        return symbols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolList that = (SymbolList) o;
        return Objects.equals(symbols, that.symbols);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbols);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (Symbol symbol : symbols) {
            stringBuilder.append(symbol).append(", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
