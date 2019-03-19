package Compiler.SymbolTable.Table.Symbol;

import java.util.Objects;

public class Symbol implements Comparable<Symbol> {

    private String name;
    private Object type;
    private Object var;
    private Object level;
    private int scopeDepth;


    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, Object value) {
        this.name = name;
    }

    /* TODO: Fix these 'Object' to the real type when known */
    public Symbol(String name, Object type, Object level, int scopeDepth) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.scopeDepth = scopeDepth;
    }

    public String getName() {
        return name;
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
        return scopeDepth == symbol.scopeDepth &&
                Objects.equals(getName(), symbol.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), scopeDepth);
    }
}
