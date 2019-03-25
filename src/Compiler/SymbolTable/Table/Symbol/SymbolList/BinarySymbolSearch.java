package Compiler.SymbolTable.Table.Symbol.SymbolList;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import java.util.Arrays;

public final class BinarySymbolSearch {

    public static Symbol find(SymbolList symbols, String name) {
        int hitIndex = Arrays.binarySearch(symbols.asList().toArray(), new Symbol(name));
        if(hitIndex > -1) {
            return symbols.get(hitIndex);
        } else {
            return null;
        }
    }

    public static boolean exists(SymbolList symbols, Symbol symbol) {
        return Arrays.binarySearch(symbols.asList().toArray(), symbol) > -1;
    }

}
