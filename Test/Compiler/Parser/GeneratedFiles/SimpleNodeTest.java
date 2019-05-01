package Compiler.Parser.GeneratedFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleNodeTest {
    private SimpleNode node;

    @BeforeEach
    void beforeEach(){
        node = new SimpleNode(0);
        node.jjtAddChild(new ASTSTART(1), 0);
        node.jjtAddChild(new ASTPROG(2), 1);
        node.jjtAddChild(new ASTBLOCK(3), 2);
    }

    @Test
    void insertChildren1() {
        node.insertChildren(0, new ASTIDENTIFIER(4), new ASTSIMPLE_TYPES(5));
        assertTrue(node.jjtGetChild(0) instanceof ASTIDENTIFIER);
        assertTrue(node.jjtGetChild(1) instanceof ASTSIMPLE_TYPES);
        assertTrue(node.jjtGetChild(2) instanceof ASTSTART);
        assertTrue(node.jjtGetChild(3) instanceof ASTPROG);
        assertTrue(node.jjtGetChild(4) instanceof ASTBLOCK);

    }

    @Test
    void insertChildren2() {
        node.insertChildren(1, new ASTIDENTIFIER(4), new ASTSIMPLE_TYPES(5));
        assertTrue(node.jjtGetChild(0) instanceof ASTSTART);
        assertTrue(node.jjtGetChild(1) instanceof ASTIDENTIFIER);
        assertTrue(node.jjtGetChild(2) instanceof ASTSIMPLE_TYPES);
        assertTrue(node.jjtGetChild(3) instanceof ASTPROG);
        assertTrue(node.jjtGetChild(4) instanceof ASTBLOCK);

    }

    @Test
    void removeChild1(){
        node.removeChild(0);
        assertTrue(node.jjtGetChild(0) instanceof ASTPROG);
        assertTrue(node.jjtGetChild(1) instanceof ASTBLOCK);
    }

    @Test
    void removeChild2(){
        node.removeChild(1);
        assertTrue(node.jjtGetChild(0) instanceof ASTSTART);
        assertTrue(node.jjtGetChild(1) instanceof ASTBLOCK);
    }

    @Test
    void removeChild3(){
        node.removeChild(2);
        assertTrue(node.jjtGetChild(0) instanceof ASTSTART);
        assertTrue(node.jjtGetChild(1) instanceof ASTPROG);
    }
}