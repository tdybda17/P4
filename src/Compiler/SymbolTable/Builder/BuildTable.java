package Compiler.SymbolTable.Builder;

import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.SimpleNode;

public interface BuildTable {

    void processNode(JexlNode node);

}
