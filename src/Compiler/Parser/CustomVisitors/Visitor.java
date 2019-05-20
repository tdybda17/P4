package Compiler.Parser.CustomVisitors;

import Compiler.Exceptions.Visitor.IllegalVisitException;
import Compiler.Parser.GeneratedFiles.*;

public interface Visitor extends TestParserVisitor {
    private Object illegalVisit(Node node) {
        throw new IllegalVisitException("There is no visit method for nodes of the type \'" + node.getClass().getSimpleName() + "\' because they should have been removed by the tree optimizer");
    }

    @Override
    default Object visit(ASTFOR_STATEMENT node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTSIMPLE_DCL node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTINITIALIZATION node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTGRAPH_DCL node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTGRAPH_DCL_ELEMENTS node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTVERTEX_LIST node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTVERTEX node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTGRAPH_VERTEX_DCL node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTWEIGHT node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTCOLLECTION_ADT node, Object data) {
        return illegalVisit(node);
    }

    @Override
    default Object visit(ASTELEMENT_LIST node, Object data) {
        return illegalVisit(node);
    }
}

