package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.Visitor.ReachabilityExceptions.MissingReturnStatementException;
import Compiler.Exceptions.Visitor.ReachabilityExceptions.UnreachableCodeException;
import Compiler.Exceptions.Visitor.WrongAmountOfChildrenException;
import Compiler.Parser.GeneratedFiles.*;

public class ReachabilityVisitor implements Visitor {
    private Object defaultVisit(SimpleNode node, Object data){
        node.childrenAccept(this, data);
        return false;
    }

    private boolean convertToBool(Object data) {
        if(data instanceof Boolean) {
            return (Boolean) data;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Object visit(ASTBLOCK node, Object data) {
        int amtChildren = node.jjtGetNumChildren();
        for(int i = 0; i < amtChildren; i++) {
            boolean childReturnValue = convertToBool(node.jjtGetChild(i).jjtAccept(this, data));
            if(childReturnValue) {
                if(i == amtChildren - 1) {
                    return true;
                } else {
                    throw new UnreachableCodeException("You had code in a block that came after a return statement");
                }
            }
        }
        return false;
    }

    @Override
    public Object visit(ASTFUNCS_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNC_DCL node, Object data) {
        if(node.jjtGetNumChildren() == 4) {
            boolean functionBlockValue = convertToBool(node.jjtGetChild(3).jjtAccept(this, data));
            if(functionBlockValue) {
                return true;
            } else {
                if(node.jjtGetChild(0).jjtGetNumChildren() == 0) {
                    //this happens when we have a void function
                    return true;
                }
                String functionName = VisitorOperations.getIdentifierName(node.jjtGetChild(1));
                throw new MissingReturnStatementException("The function: " + functionName + ", is missing a return statement");
            }
        } else {
            throw new WrongAmountOfChildrenException("A function declaration node did not have 4 children but instead had: " + node.jjtGetNumChildren());
        }
    }

    @Override
    public Object visit(ASTIF_STATEMENT node, Object data) {
        if(node.jjtGetNumChildren() == 3) {
            boolean ifBlockValue = convertToBool(node.jjtGetChild(1).jjtAccept(this, data));
            if(ifBlockValue) {
                boolean elseBlockValue = convertToBool(node.jjtGetChild(2).jjtAccept(this, data));
                if(elseBlockValue) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            throw new WrongAmountOfChildrenException("An if node did not have 3 children but instead had: " + node.jjtGetNumChildren());
        }
    }

    @Override
    public Object visit(ASTELSE_STATEMENT node, Object data) {
        if(node.jjtGetNumChildren() == 0) {
            return false;
        } else if (node.jjtGetNumChildren() == 1) {
            return convertToBool(node.jjtGetChild(0).jjtAccept(this, data));
        } else {
            throw new WrongAmountOfChildrenException("An else node had: " + node.jjtGetNumChildren() + " instead of 0 or 1 children");
        }
    }

    @Override
    public Object visit(ASTRETURN_STMT node, Object data) {
        return true;
    }

    @Override
    public Object visit(SimpleNode node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTSTART node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTPROG node, Object data) {
        return defaultVisit(node, data);
    }


    @Override
    public Object visit(ASTVERTEX_EDGE_ATTR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVERTEX_ATTRIBUTES node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTEDGE_ATTRIBUTES node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTATTRIBUTES_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTSIMPLE_TYPES node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTIDENTIFIER node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTCOLLECTION_TYPE node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTOR_EXPR node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTAND_EXPR node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTEQUAL_EXPR node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTREL_EXPR node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTADD_SUB node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMUL_DIV_MOD node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTNEG_EXPR node, Object data) {
           return defaultVisit(node, data);
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
    public Object visit(ASTCOLOR_VAL node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTLABEL_VAL node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVARIABLE node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCTION_CALL node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFIELD_ACCESS node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTACTUAL_PARAMETERS node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAIN node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTDCL node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTGRAPH_TYPE node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTASSIGN node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCTION_CALL_STMT node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTWHILE_STATEMENT node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFOREACH_STATEMENT node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP_ADT node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP_ASSIGN node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTMAP_ELEMENT_LIST node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTKEY_VALUE_PAIR node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTRETURN_TYPE node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFORMAL_PARAMETERS node, Object data) {
           return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFORMAL_PARAMETER node, Object data) {
           return defaultVisit(node, data);
    }
}
