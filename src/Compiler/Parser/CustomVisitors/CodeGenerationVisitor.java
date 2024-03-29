package Compiler.Parser.CustomVisitors;

import Compiler.CodeGeneration.CGClassLibrary.CGClassLib;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;
import Compiler.CodeGeneration.JavaFileBuilder.Writer.FileWriter;
import Compiler.Parser.GeneratedFiles.*;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static Compiler.Parser.CustomVisitors.VisitorOperations.*;

public class CodeGenerationVisitor implements Visitor {
    private List<Attribute> vertexAttributes;
    private List<Attribute> edgeAttributes;
    private Method mainMethod;
    private List<Method> funcs;

    public List<Attribute> getVertexAttributes() {
        return vertexAttributes;
    }

    public List<Attribute> getEdgeAttributes() {
        return edgeAttributes;
    }

    public Method getMainMethod() {
        return mainMethod;
    }

    public List<Method> getFuncs() {
        return funcs;
    }

    public CodeGenerationVisitor() {
        vertexAttributes = new ArrayList<>();
        edgeAttributes = new ArrayList<>();
        funcs = new ArrayList<>();
    }

    private Object defaultVisit(SimpleNode node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

    @Override
    public Object visit(SimpleNode node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTSTART node, Object data) {
        defaultVisit(node, data);
        createCodeFile();
        return data;
    }

    private void createCodeFile() {
        CGClassLib classLib = new CGClassLib();
        ClassBuilder vertexClass = (ClassBuilder) classLib.getBuilder("Vertex");
        for (Attribute vertexAttr : vertexAttributes)
            vertexClass.appendField(vertexAttr);

        ClassBuilder edgeClass = (ClassBuilder) classLib.getBuilder("Edge");
        for (Attribute edgeAttr : edgeAttributes)
            edgeClass.appendField(edgeAttr);

        ClassBuilder mainClass = (ClassBuilder) classLib.getBuilder("Main");
        mainClass.appendMethod(mainMethod);

        for (Method method : funcs)
            mainClass.appendMethod(method);

        Path dest = Paths.get("src/GeneratedCode.java");
        FileWriter fileWriter = new FileWriter(classLib.getLibrary());
        fileWriter.writeFile(dest);
    }

    @Override
    public Object visit(ASTPROG node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTFUNCS_DCL node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVERTEX_EDGE_ATTR node, Object data) {
        return defaultVisit(node, data);
    }

    @Override
    public Object visit(ASTVERTEX_ATTRIBUTES node, Object data) {
        List<Attribute> vertexAttr = new ArrayList<>();
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            vertexAttr.add((Attribute) node.jjtGetChild(i).jjtAccept(this, data));
        }
        this.vertexAttributes = vertexAttr;
        return data;
    }

    @Override
    public Object visit(ASTEDGE_ATTRIBUTES node, Object data) {
        List<Attribute> edgeAttr = new ArrayList<>();
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            edgeAttr.add((Attribute) node.jjtGetChild(i).jjtAccept(this, data));
        }
        this.edgeAttributes = edgeAttr;
        return data;
    }

    @Override
    public Object visit(ASTATTRIBUTES_DCL node, Object data) {
        String attributeType = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String identifier = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        return new Attribute(attributeType, identifier);
    }

    @Override
    public Object visit(ASTSIMPLE_TYPES node, Object data) {
        return getTypeDescriptorFromTypeNode(node).getJavaName();
    }

    @Override
    public Object visit(ASTIDENTIFIER node, Object data) {
        return getIdentifierName(node);
    }

    @Override
    public Object visit(ASTMAP node, Object data) {
        return null;
    }

    @Override
    public Object visit(ASTCOLLECTION_TYPE node, Object data) {
        return getTypeDescriptorFromTypeNode(node).getJavaName();
    }

    private String getExprString(Node exprNode, Object data, String operator) {
        String firstArgument = convertToString(exprNode.jjtGetChild(0).jjtAccept(this, data));
        String secondArgument = convertToString(exprNode.jjtGetChild(1).jjtAccept(this, data));

        return "(" + firstArgument + " " + operator + " " + secondArgument + ")";
    }

    @Override
    public Object visit(ASTOR_EXPR node, Object data) {
        return getExprString(node, data, "|");
    }

    @Override
    public Object visit(ASTAND_EXPR node, Object data) {
        return getExprString(node, data, "&");
    }

    @Override
    public Object visit(ASTEQUAL_EXPR node, Object data) {
        return getExprString(node, data, convertToString(node.jjtGetValue()));
    }

    @Override
    public Object visit(ASTREL_EXPR node, Object data) {
        return getExprString(node, data, convertToString(node.jjtGetValue()));
    }

    @Override
    public Object visit(ASTADD_SUB node, Object data) {
        return getExprString(node, data, convertToString(node.jjtGetValue()));
    }

    @Override
    public Object visit(ASTMUL_DIV_MOD node, Object data) {
        return getExprString(node, data, convertToString(node.jjtGetValue()));
    }

    @Override
    public Object visit(ASTNEG_EXPR node, Object data) {
        return getExprString(node, data, "!");
    }

    @Override
    public Object visit(ASTINUM_VAL node, Object data) {
        return convertToString(node.jjtGetValue());
    }

    @Override
    public Object visit(ASTFNUM_VAL node, Object data) {
        return convertToString(node.jjtGetValue());
    }

    @Override
    public Object visit(ASTBOOL_VAL node, Object data) {
        return convertToString(node.jjtGetValue());
    }

    @Override
    public Object visit(ASTCONSTANT_VAL node, Object data) {
        return null; //TODO: what to do here!?
    }

    @Override
    public Object visit(ASTCOLOR_VAL node, Object data) {
        return "Color." + convertToString(node.jjtGetValue());
    }

    @Override
    public Object visit(ASTLABEL_VAL node, Object data) {
        return convertToString(node.jjtGetValue()); // TODO: Bliver det et problem at value muligvis indeholder gåseøjne?
    }

    @Override
    public Object visit(ASTVARIABLE node, Object data) {
        String identifier = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String continuous = "";
        if (node.jjtGetNumChildren() == 2)
            continuous = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        return identifier + continuous;
    }

    @Override
    public Object visit(ASTFUNCTION_CALL node, Object data) {
        String identifier = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String actualParameters = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        String continuous = "";
        if (node.jjtGetNumChildren() == 3)
            continuous = convertToString(node.jjtGetChild(2).jjtAccept(this, data));

        Node parent = node.jjtGetParent();
        if (parent instanceof ASTVARIABLE || parent instanceof ASTFIELD_ACCESS || parent instanceof ASTFUNCTION_CALL)
            return "." + identifier + actualParameters + continuous;
        else
            return identifier + actualParameters + continuous;
    }

    @Override
    public Object visit(ASTFIELD_ACCESS node, Object data) {
        String identifier = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String continuous = "";
        if (node.jjtGetNumChildren() == 2)
            continuous = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        return "." + identifier + continuous;
    }

    @Override
    public Object visit(ASTACTUAL_PARAMETERS node, Object data) {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            sb.append(convertToString(node.jjtGetChild(i).jjtAccept(this, data)));
            if (i < node.jjtGetNumChildren() - 1)
                sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Object visit(ASTMAIN node, Object data) {
        String body = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        Method main = new Method("void", "main").asPublic().asStatic().appendAttribute(new Attribute("String[]", "args")).appendBody(body);
        mainMethod = main;
        return data;
    }

    @Override
    public Object visit(ASTBLOCK node, Object data) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            sb.append(convertToString(node.jjtGetChild(i).jjtAccept(this, data)));
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Object visit(ASTDCL node, Object data) {
        TypeDescriptor type = getTypeDescriptorFromTypeNode(node.jjtGetChild(0));
        String typeString = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String identifier = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        if (type instanceof ClassTypeDescriptor) {
            return typeString + " " + identifier + " = new " + typeString + "();";
        }
        else
            return typeString + " " + identifier + ";";
    }

    @Override
    public Object visit(ASTGRAPH_TYPE node, Object data) {
        return getTypeDescriptorFromTypeNode(node).getJavaName();
    }

    @Override
    public Object visit(ASTASSIGN node, Object data) {
        String leftSide = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String rightSide = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        return leftSide + " = " + rightSide + ";";
    }

    @Override
    public Object visit(ASTFUNCTION_CALL_STMT node, Object data) {
        String funcCall = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        return funcCall + ";";
    }

    @Override
    public Object visit(ASTWHILE_STATEMENT node, Object data) {
        String condition = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String block = convertToString(node.jjtGetChild(1).jjtAccept(this, data));

        return "while (" + condition + ") " + block;
    }

    @Override
    public Object visit(ASTFOREACH_STATEMENT node, Object data) {
        String identifier = getIdentifierName(node.jjtGetChild(0));
        String collectionType = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        String elementType = convertToString(node.jjtGetChild(1).jjtGetChild(0).jjtAccept(this, data));

        String collection = convertToString(node.jjtGetChild(2).jjtAccept(this, data));
        String block = convertToString(node.jjtGetChild(3).jjtAccept(this, data));

        return "for (" + elementType + " " + identifier + " : new " + collectionType + "(" + collection + ")) " + block;
    }

    @Override
    public Object visit(ASTIF_STATEMENT node, Object data) {
        String condition = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String block = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        String elseString = convertToString(node.jjtGetChild(2).jjtAccept(this, data));
        return "if (" + condition + ") " + block + elseString;
    }

    @Override
    public Object visit(ASTELSE_STATEMENT node, Object data) {
        if (node.jjtGetNumChildren() == 1) {
            String block = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
            return "else " + block;
        }
        else
            return "";
    }

    @Override
    public Object visit(ASTRETURN_STMT node, Object data) {
        if (node.jjtGetNumChildren() == 0)
            return "return;";
        else {
            String returnValue = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
            return "return " + returnValue + ";";
        }
    }

    @Override
    public Object visit(ASTMAP_ADT node, Object data) {
        return null; // TODO: if we include map, this node should be removed in optimizer
    }

    @Override
    public Object visit(ASTMAP_ELEMENT_LIST node, Object data) {
        return null; // TODO: if we include map, this node should be removed in optimizer
    }

    @Override
    public Object visit(ASTKEY_VALUE_PAIR node, Object data) {
        return null; // TODO: if we include map, this node should probably be removed in optimizer
    }

    @Override
    public Object visit(ASTFUNC_DCL node, Object data) {
        String returnType = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String identifier = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        List<Attribute> formalParameters = (List<Attribute>) node.jjtGetChild(2).jjtAccept(this, data);
        String block = convertToString(node.jjtGetChild(3).jjtAccept(this, data));

        Method method = new Method(returnType, identifier).asStatic();
        for (Attribute parameter : formalParameters)
            method.appendAttribute(parameter);
        method.appendBody(block);
        funcs.add(method);

        return data;
    }

    @Override
    public Object visit(ASTRETURN_TYPE node, Object data) {
        if (node.jjtGetNumChildren() == 0)
            return "void";
        else
            return convertToString(node.jjtGetChild(0).jjtAccept(this, data));
    }

    @Override
    public Object visit(ASTFORMAL_PARAMETERS node, Object data) {
        List<Attribute> parameters = new ArrayList<>();
        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            parameters.add((Attribute) node.jjtGetChild(i).jjtAccept(this, data));
        }
        return parameters;
    }

    @Override
    public Object visit(ASTFORMAL_PARAMETER node, Object data) {
        String type = convertToString(node.jjtGetChild(0).jjtAccept(this, data));
        String identifier = convertToString(node.jjtGetChild(1).jjtAccept(this, data));
        return new Attribute(type, identifier);
    }
}
