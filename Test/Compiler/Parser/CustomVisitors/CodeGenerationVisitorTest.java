package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeGenerationVisitorTest {
    private ASTMAIN mainNode;
    private ASTBLOCK blockNode;
    private ASTDCL dclNode;
    private ASTSIMPLE_TYPES simpleTypesNode;
    private ASTIDENTIFIER idNode;
    private CodeGenerationVisitor codeGenVisitor;

    @BeforeEach
    void setUp() {
        mainNode = new ASTMAIN(TestParserTreeConstants.JJTMAIN);
        blockNode = new ASTBLOCK(TestParserTreeConstants.JJTBLOCK);
        dclNode = new ASTDCL(TestParserTreeConstants.JJTDCL);

        simpleTypesNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
        simpleTypesNode.jjtSetValue("int");

        idNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        idNode.jjtSetValue("a");

        codeGenVisitor = new CodeGenerationVisitor();
    }

    @Test
    void mainTest() {
        // Tests the following EduGraph program:
        // function main()
        //     int a
        // end

        mainNode.insertChildren(0, blockNode);
        blockNode.insertChildren(0, dclNode);
        dclNode.insertChildren(0, simpleTypesNode, idNode);

        mainNode.jjtAccept(codeGenVisitor, null);
        String actualJavaCode = codeGenVisitor.getMainMethod().createMethod();
        String expectedJavaCode = "public static void main(String[] args){{Integer a;}}";

        assertEquals(expectedJavaCode, actualJavaCode);
    }

    @Test
    void whileTest() {
        // Tests the following EduGraph while loop:
        // while true do
        //     int a
        // end

        ASTWHILE_STATEMENT whileNode = new ASTWHILE_STATEMENT(TestParserTreeConstants.JJTWHILE_STATEMENT);
        ASTBOOL_VAL boolValNode = new ASTBOOL_VAL(TestParserTreeConstants.JJTBOOL_VAL);
        boolValNode.jjtSetValue("true");

        whileNode.insertChildren(0, boolValNode, blockNode);
        blockNode.insertChildren(0, dclNode);
        dclNode.insertChildren(0, simpleTypesNode, idNode);

        String expectedCode = "while (true) {Integer a;}";
        String actualCode = (String) whileNode.jjtAccept(codeGenVisitor, null);

        assertEquals(expectedCode, actualCode);
    }

    @Test
    void forEachTest() {
        // Tests the following EduGraph foreach loop:
        // foreach v in g.vertices do
        //     int a
        // end

        // all nodes are created
        ASTFOREACH_STATEMENT forEachNode = new ASTFOREACH_STATEMENT(TestParserTreeConstants.JJTFOREACH_STATEMENT);
        ASTIDENTIFIER elementIdNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        elementIdNode.jjtSetValue("v");

        ASTCOLLECTION_TYPE collectionTypeNode = new ASTCOLLECTION_TYPE(TestParserTreeConstants.JJTCOLLECTION_TYPE);
        collectionTypeNode.jjtSetValue("Set");
        ASTSIMPLE_TYPES elementTypeNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
        elementTypeNode.jjtSetValue("Vertex");

        ASTVARIABLE variableNode = new ASTVARIABLE(TestParserTreeConstants.JJTVARIABLE);
        ASTIDENTIFIER variableIdNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        variableIdNode.jjtSetValue("g");
        ASTFIELD_ACCESS fieldAccessNode = new ASTFIELD_ACCESS(TestParserTreeConstants.JJTFIELD_ACCESS);
        ASTIDENTIFIER fieldAccessIdNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        fieldAccessIdNode.jjtSetValue("vertices");


        // all nodes are added to their respective parents
        forEachNode.insertChildren(0, elementIdNode, collectionTypeNode, variableNode, blockNode);
        collectionTypeNode.insertChildren(0, elementTypeNode);
        variableNode.insertChildren(0, variableIdNode, fieldAccessNode);
        fieldAccessNode.insertChildren(0, fieldAccessIdNode);
        blockNode.insertChildren(0, dclNode);
        dclNode.insertChildren(0, simpleTypesNode, idNode);


        String expectedCode = "for (Vertex v : new HashSet<Vertex>(g.vertices)) {Integer a;}";
        String actualCode = (String) forEachNode.jjtAccept(codeGenVisitor, null);

        assertEquals(expectedCode, actualCode);
    }

    @Test
    void funcDclTest() {
        // Tests the following EduGraph function declaration
        //  int function foo(int a)
        //      return 1
        //  end

        // all nodes are created
        ASTFUNC_DCL funcDclNode = new ASTFUNC_DCL(TestParserTreeConstants.JJTFUNC_DCL);

        ASTRETURN_TYPE returnTypeNode = new ASTRETURN_TYPE(TestParserTreeConstants.JJTRETURN_TYPE);
        ASTSIMPLE_TYPES simpleTypesReturnTypeNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
        simpleTypesReturnTypeNode.jjtSetValue("int");

        ASTIDENTIFIER funcNameNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        funcNameNode.jjtSetValue("foo");

        ASTFORMAL_PARAMETERS formalParamsNode = new ASTFORMAL_PARAMETERS(TestParserTreeConstants.JJTFORMAL_PARAMETERS);
        ASTFORMAL_PARAMETER formalParamNode = new ASTFORMAL_PARAMETER(TestParserTreeConstants.JJTFORMAL_PARAMETER);
        ASTSIMPLE_TYPES paramTypeNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
        paramTypeNode.jjtSetValue("int");
        ASTIDENTIFIER paramIdNode = new ASTIDENTIFIER(TestParserTreeConstants.JJTIDENTIFIER);
        paramIdNode.jjtSetValue("a");

        ASTBLOCK blockNode = new ASTBLOCK(TestParserTreeConstants.JJTBLOCK);
        ASTRETURN_STMT returnStmtNode = new ASTRETURN_STMT(TestParserTreeConstants.JJTRETURN_STMT);
        ASTINUM_VAL inumValNode = new ASTINUM_VAL(TestParserTreeConstants.JJTINUM_VAL);
        inumValNode.jjtSetValue("1");


        // all nodes are added to their respective parents
        funcDclNode.insertChildren(0, returnTypeNode, funcNameNode, formalParamsNode, blockNode);
        returnTypeNode.insertChildren(0, simpleTypesReturnTypeNode);
        formalParamsNode.insertChildren(0, formalParamNode);
        formalParamNode.insertChildren(0, paramTypeNode, paramIdNode);
        blockNode.insertChildren(0, returnStmtNode);
        returnStmtNode.insertChildren(0, inumValNode);


        funcDclNode.jjtAccept(codeGenVisitor, null);
        String expectedCode = "static Integer foo(Integer a){{return 1;}}";
        assertEquals(1, codeGenVisitor.getFuncs().size());
        String actualCode = codeGenVisitor.getFuncs().get(0).createMethod();

        assertEquals(expectedCode, actualCode);
    }


}