package Compiler.SymbolTable.Builder;

import Compiler.Parser.ParseException;
import Compiler.Parser.SimpleNode;
import Compiler.Parser.testParser;
import org.apache.commons.jexl3.parser.JexlNode;
import org.w3c.dom.Node;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Set;

public class BuildSymbolTable implements BuildTable {

    private SimpleNode node;

    public BuildSymbolTable(String str) {
        try {
            testParser p = new testParser(new StringReader(str));
            node = p.one_line();
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
