package Compiler.CodeGeneration.JavaFileBuilder.EnumBuilder;

import Compiler.CodeGeneration.JavaFileBuilder.JavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.StringOperator;

import java.util.ArrayList;
import java.util.List;

public class EnumBuilder extends JavaFileBuilder {

    private List<String> entries;

    public EnumBuilder(String identifier) {
        super("", identifier, new ArrayList<>());
        entries = new ArrayList<>();
    }

    public EnumBuilder(String accessModifier, String identifier) {
        super(accessModifier, identifier, new ArrayList<>());
        entries = new ArrayList<>();
    }

    public EnumBuilder appendEnum(String entry) {
        this.entries.add(entry);
        return this;
    }

    @Override
    public String getFileContent() {
        return super.createAccessModifier() + "enum " + super.createIdentifier() + " {" + createEntries() + "}";
    }

    private String createEntries() {
        if(entries == null || entries.isEmpty()) return "";
        else {
            return StringOperator.explode(entries, ", ");
        }
    }
}
