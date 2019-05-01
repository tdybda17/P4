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
    void insertChildren() {
        node.insertChildren(0, new ASTIDENTIFIER(2), new ASTIDENTIFIER(2));
        assertEquals(new ASTIDENTIFIER(2), node.jjtGetChild(0));
    }
}