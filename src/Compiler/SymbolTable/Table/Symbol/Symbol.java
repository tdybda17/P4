package Compiler.SymbolTable.Table.Symbol;

import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.Objects;

public class Symbol implements Comparable<Symbol> {
    private String name;
    private Attributes attributes;
    private SymbolList symbolList;
    private int depth;

    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, int depth) {
        this.name = name;
        this.depth = depth;
    }

    public Symbol(String name, Attributes attributes, int depth, SymbolList symbolList) {
        this.name = name;
        this.attributes = attributes;
        this.depth = depth;
        this.symbolList = symbolList;




    }

    public String getName() {
        return name;
    }

    public int getDepth() {
        return depth;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public SymbolList getSymbolList() {
        return symbolList;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Symbol sym) {
        return this.name.compareTo(sym.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(getName(), symbol.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
