package Compiler.Parser.CustomVisitors;

import Compiler.Parser.GeneratedFiles.ASTFNUM_VAL;
import Compiler.Parser.GeneratedFiles.ASTWEIGHT;
import Compiler.Parser.GeneratedFiles.TestParserTreeConstants;

public class EdgeInformation {
    private String firstVertex;
    private String secondVertex;
    private ASTWEIGHT weight;
    private double standardWeight = 1.0;


    public EdgeInformation(String firstVertex, String secondVertex, ASTWEIGHT weight) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.weight = weight;
    }

    public EdgeInformation(String firstVertex, String secondVertex) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.weight = createStandardWeight();
    }

    private ASTWEIGHT createStandardWeight(){
        ASTFNUM_VAL weightValue = new ASTFNUM_VAL(TestParserTreeConstants.JJTFNUM_VAL);
        weightValue.jjtSetValue(standardWeight);

        ASTWEIGHT weightNode = new ASTWEIGHT(TestParserTreeConstants.JJTWEIGHT);
        weightNode.jjtAddChild(weightValue, 0);
        return weightNode;
    }

    public String getFirstVertex() {
        return firstVertex;
    }

    public String getSecondVertex() {
        return secondVertex;
    }

    public ASTWEIGHT getWeight() {
        return weight;
    }
}
