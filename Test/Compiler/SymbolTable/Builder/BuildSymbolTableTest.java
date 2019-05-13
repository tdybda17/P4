package Compiler.SymbolTable.Builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class BuildSymbolTableTest {

    BuildSymbolTable symbolTableBuilder;

    @BeforeEach
    void setUp() {
        try {
            symbolTableBuilder = new BuildSymbolTable(
                    new BufferedInputStream(new FileInputStream("Test/ProgramTestCases/LegalSyntax/scopetestprogram"))
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void processNode() {

    }
}