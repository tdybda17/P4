package Compiler.SymbolTable.Table.Symbol;

import java.util.Objects;

public class Symbol implements Comparable<Symbol> {

    private String name;
    private Object type;
    private Object var;
    private Object level;
    private int depth;


    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, int depth) {
        this.name = name;
        this.depth = depth;
    }

    public Symbol(String name, Object type, Object level, int depth) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.depth = depth;
    }

    public String getName() {
        return name;
    }

    public int getDepth() {
        return depth;
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
