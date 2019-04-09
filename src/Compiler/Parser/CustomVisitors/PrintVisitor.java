package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.*;

public class PrintVisitor implements TestParserVisitor {

    private Object defaultVisit(SimpleNode node, Object data){
        node.childrenAccept(this, data);
        return data;
    }
    @Override
    public Object visit(SimpleNode node, Object data){
        return defaultVisit(node, data);
    }
    @Override
    public Object visit(ASTSTART node, Object data){
        return defaultVisit(node, data);
    }
    @Override
    public Object visit(ASTPROG node, Object data){
        return defaultVisit(node, data);
    }
    @Override
    public Object visit(ASTVERTEX_ATTRIBUTES node, Object data){
        System.out.print("Vertex dcl\n");
        node.childrenAccept(this, data);
        System.out.print("end\n\n");
        return data;
    }
    @Override
    public Object visit(ASTEDGE_ATTRIBUTES node, Object data){
        System.out.print("Edge dcl\n");
        node.childrenAccept(this, data);
        System.out.print("end\n\n");
        return data;
    }
    @Override
    public Object visit(ASTATTRIBUTE_DCL node, Object data){
        return defaultVisit(node, data);
    }
    @Override
    public Object visit(ASTASSIGN node, Object data){
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" = ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.print("\n");
        return data;
    }

    @Override
    public Object visit(ASTFUNCTION_CALL node, Object data) {
        node.childrenAccept(this, data);
        System.out.print("\n");
        return data;
    }
    @Override
    public Object visit(ASTDCL node, Object data){
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" ");
        node.jjtGetChild(1).jjtAccept(this, data);
        if (node.jjtGetNumChildren() == 3) {
            System.out.print(" = ");
            node.jjtGetChild(2).jjtAccept(this, data);
        }
        System.out.print("\n");
        return data;
    }
    @Override
    public Object visit(ASTOBJECT_TYPE node, Object data){
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.print("\n");
        return data;
    }
    @Override
    public Object visit(ASTSIMPLE_TYPES node, Object data){
        System.out.print(node.jjtGetValue());
        return data;
    }
    @Override
    public Object visit(ASTIDENTIFIER node, Object data){
        System.out.print(node.jjtGetValue());
        return data;
    }

    @Override
    public Object visit(ASTMAP node, Object data) {
        System.out.print("Map<");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(", ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.print(">");
        return data;
    }

    @Override
    public Object visit(ASTCOLLECTION_TYPE node, Object data){
        System.out.print(node.jjtGetValue());
        System.out.print("<");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(">");
        return data;
    }
    @Override
    public Object visit(ASTGRAPH_ELEMENT_TYPES node, Object data) {
        System.out.print(node.jjtGetValue());
        return data;
    }
    @Override
    public Object visit(ASTOR_EXPR node, Object data){
        return simpleExpr(node, data, "|");
    }
    @Override
    public Object visit(ASTAND_EXPR node, Object data){
        return simpleExpr(node, data, "&");
    }
    @Override
    public Object visit(ASTEQUAL_EXPR node, Object data){
        return simpleExpr(node, data, node.jjtGetValue().toString());
    }
    @Override
    public Object visit(ASTREL_EXPR node, Object data){
        return simpleExpr(node, data, node.jjtGetValue().toString());
    }
    @Override
    public Object visit(ASTADD_SUB node, Object data){
        return simpleExpr(node, data, node.jjtGetValue().toString());
    }
    @Override
    public Object visit(ASTMUL_DIV node, Object data){
        return simpleExpr(node, data, node.jjtGetValue().toString());
    }
    @Override
    public Object visit(ASTNEG_EXPR node, Object data){
        System.out.print("!");
        System.out.print("(");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(")");
        return data;
    }
    @Override
    public Object visit(ASTMEMBER_FUNCTION_CALL node, Object data){
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTINUM_VAL node, Object data) {
        System.out.print(node.jjtGetValue());
        return data;
    }

    @Override
    public Object visit(ASTFNUM_VAL node, Object data) {
        System.out.print(node.jjtGetValue());
        return data;
    }

    @Override
    public Object visit(ASTBOOL_VAL node, Object data) {
        System.out.print(node.jjtGetValue());
        return data;
    }

    @Override
    public Object visit(ASTCONSTANT_VAL node, Object data) {
        System.out.print(node.jjtGetValue());
        return data;
    }

    @Override
    public Object visit(ASTMEMBER node, Object data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        if (node.jjtGetNumChildren() > 1) {
            System.out.print(".");
            node.jjtGetChild(1).jjtAccept(this, data);
        }
        return data;
    }
    @Override
    public Object visit(ASTFUNC_CALL node, Object data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        node.jjtGetChild(1).jjtAccept(this, data);
        if (node.jjtGetNumChildren() > 2) {
            System.out.print(".");
            node.jjtGetChild(2).jjtAccept(this, data);
        }
        return data;
    }
    @Override
    public Object visit(ASTACTUAL_PARAMETERS node, Object data){
        System.out.print("(");
        int numChildren = node.jjtGetNumChildren();
        for (int i = 0; i < numChildren - 1; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
            System.out.print(", ");
        }
        if (numChildren > 0)
            node.jjtGetChild(numChildren - 1).jjtAccept(this, data);
        System.out.print(")");
        return data;
    }

    @Override
    public Object visit(ASTMAIN node, Object data) {
        System.out.print("function main()\n");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print("end\n");
        return data;
    }

    @Override
    public Object visit(ASTBLOCK node, Object data){
        return defaultVisit(node, data);
    }
    @Override
    public Object visit(ASTCREATE node, Object data){
        System.out.print("create ");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print("end\n");
        return data;
    }

    @Override
    public Object visit(ASTGRAPH_ELEMENT_DCL node, Object data) {
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.print("\n");
        return data;
    }

    @Override
    public Object visit(ASTGRAPH_DCL node, Object data){
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" ");
        node.jjtGetChild(1).jjtAccept(this, data);
        if (node.jjtGetNumChildren() > 2)
            node.jjtGetChild(2).jjtAccept(this, data);
        return data;
    }
    @Override
    public Object visit(ASTGRAPH_TYPE node, Object data){
        System.out.print(node.jjtGetValue());
        return data;
    }
    @Override
    public Object visit(ASTGRAPH_ASSIGN node, Object data){
        System.out.print(" = ");
        node.childrenAccept(this, data);
        System.out.print(" ");
        return data;
    }
    @Override
    public Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data){
        System.out.print("\n");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print("\n");
        int numChildren = node.jjtGetNumChildren();
        for (int i = 1; i < numChildren; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
            System.out.print("\n");
        }
        return data;
    }
    @Override
    public Object visit(ASTGRAPH_VERTEX_DCL node, Object data){
        node.jjtGetChild(0).jjtAccept(this, data);
        if (node.jjtGetNumChildren() == 2) {
            System.out.print(" -- ");
            System.out.print("{");
            node.jjtGetChild(1).jjtAccept(this, data);
            System.out.print("}");
        }
        else if (node.jjtGetNumChildren() == 3) {
            System.out.print(" -- ");
            node.jjtGetChild(1).jjtAccept(this, data);
            node.jjtGetChild(2).jjtAccept(this, data);
        }
        return data;
    }
    @Override
    public Object visit(ASTVERTEX_LIST node, Object data){
        int numChildren = node.jjtGetNumChildren();
        for (int i = 0; i < numChildren - 1; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
            System.out.print(", ");
        }
        node.jjtGetChild(numChildren - 1).jjtAccept(this, data);
        return data;
    }
    @Override
    public Object visit(ASTVERTEX node, Object data){
        return defaultVisit(node, data);
    }
    @Override
    public Object visit(ASTWEIGHT node, Object data){
        if (node.jjtGetNumChildren() == 1) {
            System.out.print("[");
            node.jjtGetChild(0).jjtAccept(this, data);
            System.out.print("]");
        }
        return data;
    }
    @Override
    public Object visit(ASTWHILE_STATEMENT node, Object data){
        System.out.print("while ");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" do\n");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.print("end\n");
        return data;
    }
    @Override
    public Object visit(ASTFOR_STATEMENT node, Object data){
        System.out.print("for ");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" = ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.print(" to ");
        node.jjtGetChild(2).jjtAccept(this, data);
        System.out.print(" do\n");
        node.jjtGetChild(3).jjtAccept(this, data);
        System.out.print("end\n");
        return data;
    }
    @Override
    public Object visit(ASTINTEGER_VALUE node, Object data){
        if (node.jjtGetNumChildren() == 0)
            System.out.print(node.jjtGetValue());
        else
            return defaultVisit(node, data);
        return data;
    }
    @Override
    public Object visit(ASTFOREACH_STATEMENT node, Object data){
        System.out.print("foreach ");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" in ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.print(" do\n");
        node.jjtGetChild(2).jjtAccept(this, data);
        System.out.print("end\n");
        return data;
    }
    @Override
    public Object visit(ASTIF_STATEMENT node, Object data){
        System.out.print("if ");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" then\n");
        node.jjtGetChild(1).jjtAccept(this, data);
        node.jjtGetChild(2).jjtAccept(this, data);
        return data;
    }
    @Override
    public Object visit(ASTELSE_STATEMENT node, Object data){
        if (node.jjtGetNumChildren() > 0) {
            System.out.print("else ");
            node.jjtGetChild(0).jjtAccept(this, data);
        }
        System.out.print("end\n");
        return data;
    }
    @Override
    public Object visit(ASTRETURN_STMT node, Object data){
        System.out.print("return ");
        if (node.jjtGetNumChildren() > 0)
            node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print("\n");
        return data;
    }
    @Override
    public Object visit(ASTCOLLECTION_ADT node, Object data){
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" ");
        node.jjtGetChild(1).jjtAccept(this, data);
        if (node.jjtGetNumChildren() > 2) {
            if (node.jjtGetChild(2).toString().equals("MEMBER_FUNCTION_CALL")) {
                System.out.print(" = ");
                node.jjtGetChild(2).jjtAccept(this, data);
                System.out.print(" ");
            }
            else
                node.jjtGetChild(2).jjtAccept(this, data);
        }
        return data;
    }
    @Override
    public Object visit(ASTELEMENT_LIST node, Object data){
        System.out.print("\n");
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
            System.out.print("\n");
        }
        return data;
    }
    @Override
    public Object visit(ASTFUNCS_DCL node, Object data){
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            System.out.print("\n");
            node.jjtGetChild(i).jjtAccept(this, data);
        }
        return data;
    }

    @Override
    public Object visit(ASTVERTEX_EDGE_ATTR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNC_DCL node, Object data){
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print("function ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.print("(");
        node.jjtGetChild(2).jjtAccept(this, data);
        System.out.print(")\n");
        node.jjtGetChild(3).jjtAccept(this, data);
        System.out.print("end\n");
        return data;
    }
    @Override
    public Object visit(ASTRETURN_TYPE node, Object data) {
        if (node.jjtGetNumChildren() > 0) {
            node.jjtGetChild(0).jjtAccept(this, data);
            System.out.print(" ");
        }
        return data;
    }
    @Override
    public Object visit(ASTFORMAL_PARAMETERS node, Object data){
        int numChildren = node.jjtGetNumChildren();
        for (int i = 0; i < numChildren - 1; i++) {
            node.jjtGetChild(i).jjtAccept(this, data);
            System.out.print(", ");
        }
        if (numChildren > 0)
            node.jjtGetChild(numChildren - 1).jjtAccept(this, data);
        return data;
    }
    @Override
    public Object visit(ASTFORMAL_PARAMETER node, Object data){
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" ");
        node.jjtGetChild(1).jjtAccept(this, data);
        return data;
    }

    private Object simpleExpr(SimpleNode node, Object data, String operator) {
        System.out.print("(");
        node.jjtGetChild(0).jjtAccept(this, data);
        System.out.print(" " + operator + " ");
        node.jjtGetChild(1).jjtAccept(this, data);
        System.out.print(")");
        return data;
    }
}
