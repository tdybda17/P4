package Compiler.SymbolTable.Builder;

import Compiler.Parser.GeneratedFiles.ParseException;
import Compiler.Parser.GeneratedFiles.SimpleNode;
import Compiler.Parser.GeneratedFiles.TestParser;
import org.apache.commons.jexl3.parser.JexlNode;
import java.io.StringReader;

public class BuildSymbolTable implements BuildTable {

    private SimpleNode node;

    public BuildSymbolTable(String str) {
        try {
            TestParser p = new TestParser(new StringReader(str));
            node = p.start();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void processNode(JexlNode node) {
        String name = node.toString();
    }

    public SimpleNode getNode() {
        return node;
    }
}
