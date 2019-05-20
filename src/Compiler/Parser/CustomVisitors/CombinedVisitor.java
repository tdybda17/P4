package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.Node;
import Compiler.SymbolTable.Table.SymbolTable;

public class CombinedVisitor {
    public static Node visit(Node root) {
        TreeOptimizerVisitor treeOptimizerVisitor = new TreeOptimizerVisitor();
        root.jjtAccept(treeOptimizerVisitor, 0);

        SymbolTable symbolTable = new SymbolTable();
        symbolTable.openScope();
        FunctionVisitor functionVisitor = new FunctionVisitor();
        root.jjtAccept(functionVisitor, symbolTable);

        ReachabilityVisitor reachabilityVisitor = new ReachabilityVisitor();
        root.jjtAccept(reachabilityVisitor, null);

        StaticSemanticsVisitor staticSemanticsVisitor = new StaticSemanticsVisitor(symbolTable);
        root.jjtAccept(staticSemanticsVisitor, symbolTable);
        return root;
    }
}
