package Compiler.SymbolTable.Table;

import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.Symbol;

public interface ISymbolTable {

    void openScope();
    void closeScope();
    void enterSymbol(String name, Attributes attributes);
    Symbol retrieveSymbol(String name);
}
