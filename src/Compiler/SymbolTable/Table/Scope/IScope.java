package Compiler.SymbolTable.Table.Scope;

import Compiler.SymbolTable.Table.Symbol.Symbol;

import java.util.List;

public interface IScope {

    IScope getInnerScope();
    IScope getOuterScope();
    List<Symbol> getSymbols();

}
