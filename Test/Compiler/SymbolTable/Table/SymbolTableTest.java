package Compiler.SymbolTable.Table;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    private SymbolTable symbolTable;
    private String name;

    @BeforeEach
    void setUp() {
        symbolTable = new SymbolTable();
        name = null;
    }

}