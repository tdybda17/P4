package Compiler.SymbolTable.Table.Symbol.Attributes;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

public interface Attributes {
    String getKind();
    TypeDescriptor getType();
}
