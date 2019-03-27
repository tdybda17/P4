package Compiler.SymbolTable.Table.Symbol.Attributes;

public class ClassAttributes implements Attributes {
    public String name;

    @Override
    public String getAttributeKind() {
        return "Class";
    }
}
