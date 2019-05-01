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
        node.jjtAddChild(new ASTSTART(1), 1);
        node.jjtAddChild(new ASTSTART(1), 2);
    }

    @Test
    void insertChildren1() {
        node.insertChildren(0, new ASTIDENTIFIER(2), new ASTSIMPLE_TYPES(2));
        assertTrue(node.jjtGetChild(0) instanceof ASTIDENTIFIER);
        assertTrue(node.jjtGetChild(1) instanceof ASTSIMPLE_TYPES);
        assertTrue(node.jjtGetChild(2) instanceof ASTSTART);
        assertTrue(node.jjtGetChild(3) instanceof ASTSTART);
        assertTrue(node.jjtGetChild(4) instanceof ASTSTART);
    }

    @Test
    void insertChildren2() {
        node.insertChildren(1, new ASTIDENTIFIER(2), new ASTSIMPLE_TYPES(2));
        assertTrue(node.jjtGetChild(0) instanceof ASTSTART);
        assertTrue(node.jjtGetChild(1) instanceof ASTIDENTIFIER);
        assertTrue(node.jjtGetChild(2) instanceof ASTSIMPLE_TYPES);
        assertTrue(node.jjtGetChild(3) instanceof ASTSTART);
        assertTrue(node.jjtGetChild(4) instanceof ASTSTART);
    }
}