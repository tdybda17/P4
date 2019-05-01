/* Generated By:JavaCC: Do not edit this line. TestParserDefaultVisitor.java Version 6.0_1 */
package Compiler.Parser.GeneratedFiles;

import Compiler.SymbolTable.Table.SymbolTable;

public class DeclarationVisitor implements TestParserVisitor {
    public SymbolTable createSymbolTable(Node root) {
        ASTSTART startNode;
        if (root instanceof ASTSTART) {
            startNode = (ASTSTART) root;
        } else {
            throw new IllegalArgumentException("Your root node was not an ASTSTART node, but instead a " + root.getClass().toString());
        }
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.openScope();

        symbolTable = typeCastToSymbolTable((this.visit(startNode, symbolTable)));

        return symbolTable;
    }

    //A help function created to typecast our Objects to SymbolTables.
    private SymbolTable typeCastToSymbolTable(Object data) {
        if (data instanceof SymbolTable) {
            return (SymbolTable) data;
        } else {
            throw new IllegalArgumentException("The given object was not an instance of SymbolTable");
        }
    }


    public Object defaultVisit(SimpleNode node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

    public Object visit(SimpleNode node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTSTART node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTPROG node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTVERTEX_ATTRIBUTES node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTEDGE_ATTRIBUTES node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTATTRIBUTE_DCL node, Object data) {
        return defaultVisit(node, data);
    }


    public Object visit(ASTOBJECT_TYPE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTSIMPLE_DCL node, Object data) {
        return null;
    }

    public Object visit(ASTSIMPLE_TYPES node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTIDENTIFIER node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTCOLLECTION_TYPE node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTOR_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTAND_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTEQUAL_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTREL_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTADD_SUB node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTMUL_DIV node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTNEG_EXPR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCOLOR_VAL node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTLABEL_VAL node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTINUM_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFNUM_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTBOOL_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCONSTANT_VAL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVARIABLE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFIELD_ACCESS node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTACTUAL_PARAMETERS node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAIN node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTBLOCK node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTCREATE node, Object data) {
        return defaultVisit(node, data);
    }


    public Object visit(ASTGRAPH_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTGRAPH_TYPE node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTGRAPH_ASSIGN node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTGRAPH_VERTEX_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTVERTEX_LIST node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTVERTEX node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTWEIGHT node, Object data) {
        return defaultVisit(node, data);
    }


    public Object visit(ASTASSIGN node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCTION_CALL_STMT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCTION_CALL node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTWHILE_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTFOR_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTFOREACH_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTIF_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTELSE_STATEMENT node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTRETURN_STMT node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTCOLLECTION_ADT node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCOLLECTION_ASSIGN node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTELEMENT_LIST node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP_ADT node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTMAP_ASSIGN node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTMAP_ELEMENT_LIST node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTKEY_VALUE_PAIR node, Object data) {
        return null;
    }

    public Object visit(ASTFUNCS_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVERTEX_EDGE_ATTR node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTFUNC_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTRETURN_TYPE node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTFORMAL_PARAMETERS node, Object data) {
        return defaultVisit(node, data);
    }

    public Object visit(ASTFORMAL_PARAMETER node, Object data) {
        return defaultVisit(node, data);
    }
}
/* JavaCC - OriginalChecksum=0f7535fe17904e237cba03d55ff10f5d (do not edit this line) */
