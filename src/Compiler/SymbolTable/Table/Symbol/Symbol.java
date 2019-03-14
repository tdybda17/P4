package Compiler.SymbolTable.Table.Symbol;

public class Symbol {

    private int num;
    private String name;
    // TODO: Figure out how to handle attributes


    public Symbol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
