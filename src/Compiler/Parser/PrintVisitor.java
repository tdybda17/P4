package Compiler.Parser;

import Compiler.Parser.GeneratedFiles.*;

public class PrintVisitor implements TestParserVisitor {

    public Object defaultVisit(SimpleNode node, Object data){
        node.childrenAccept(this, data);
        return data;
    }
    public Object visit(SimpleNode node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTSTART node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTPROG node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTVERTEX_ATTRIBUTES node, Object data){
        System.out.print("Vertex dcl\n");
        node.childrenAccept(this, data);
        System.out.print("end");
        return data;
    }
    public Object visit(ASTEDGE_ATTRIBUTES node, Object data){
        System.out.print("Edge dcl\n");
        node.childrenAccept(this, data);
        System.out.print("end");
        return data;
    }
    public Object visit(ASTATTRIBUTE_DCL node, Object data){
        int numChildren = node.jjtGetNumChildren();
        for (int i = 0; i < numChildren; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
            System.out.print("\n");
        }
        return data;
    }
    public Object visit(ASTASSIGN node, Object data){
        System.out.print(((SimpleNode)node.jjtGetChild(0)).jjtGetValue());
        System.out.print(" = ");
        node.jjtGetChild(1).jjtAccept(this, data);
        return data;
    }
    public Object visit(ASTDCL node, Object data){
        System.out.print(((SimpleNode)node.jjtGetChild(0)).jjtGetValue());
        System.out.print(" ");
        System.out.print(((SimpleNode)node.jjtGetChild(1)).jjtGetValue());
        System.out.print(" = ");
        return data;
    }
    public Object visit(ASTOBJECT_TYPE node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTSIMPLE_TYPES node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTIDENTIFIER node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTOBJECT_TYPES node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTCOLLECTION_TYPE node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTOR_EXPR node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTAND_EXPR node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTEQUAL_EXPR node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTREL_EXPR node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTADD_SUB node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTMUL_DIV node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTNEG_EXPR node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTVALUE node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTMEMBER_FUNCTION_CALL node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTACTUAL_PARAMETERS node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTBLOCK node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTCREATE node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTGRAPH_DCL node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTGRAPH_TYPE node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTGRAPH_ASSIGN node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTGRAPH_VERTEX_DCL node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTVERTEX_LIST node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTVERTEX node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTWEIGHT node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTSTMT node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTFUNC_CALL node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTLOOP node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTWHILE_STATEMENT node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTFOR_STATEMENT node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTINTEGER_VALUE node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTFOREACH_STATEMENT node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTIF_STATEMENT node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTELSE_STATEMENT node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTRETURN_STMT node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTCOLLECTION_ADT node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTELEMENT_LIST node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTFUNCS_DCL node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTFUNC_DCL node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTFORMAL_PARAMETERS node, Object data){
        return defaultVisit(node, data);
    }
    public Object visit(ASTFORMAL_PARAMETER node, Object data){
        return defaultVisit(node, data);
    }
}
