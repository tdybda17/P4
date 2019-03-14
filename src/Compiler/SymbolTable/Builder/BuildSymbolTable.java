package Compiler.SymbolTable.Builder;

import org.apache.commons.jexl3.parser.JexlNode;

public class BuildSymbolTable implements BuildTable {

    @Override
    public void processNode(JexlNode node) {
        String name = node.toString();
    }
}
