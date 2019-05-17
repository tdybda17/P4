package Compiler.SymbolTable.Builder;

import Compiler.Parser.GeneratedFiles.ParseException;
import Compiler.Parser.GeneratedFiles.SimpleNode;
import Compiler.Parser.GeneratedFiles.TestParser;
import Compiler.SymbolTable.Table.SymbolTable;

import java.io.InputStream;
import java.io.StringReader;

public class BuildSymbolTable {

    private SimpleNode node;
    private SymbolTable symbolTable;

    public BuildSymbolTable(InputStream is) {
        symbolTable = new SymbolTable();
        symbolTable.openScope();
        try {
            TestParser p = new TestParser(is);
            node = p.start();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public SimpleNode getNode() {
        return node;
    }

}
