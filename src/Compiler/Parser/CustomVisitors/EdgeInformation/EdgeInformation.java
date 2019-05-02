package Compiler.Parser.CustomVisitors.EdgeInformation;

import Compiler.Parser.GeneratedFiles.ASTFNUM_VAL;
import Compiler.Parser.GeneratedFiles.ASTWEIGHT;
import Compiler.Parser.GeneratedFiles.TestParserTreeConstants;

public class EdgeInformation {
    private String firstVertex;
    private String secondVertex;
    private ASTWEIGHT weight;


    public EdgeInformation(String firstVertex, String secondVertex, ASTWEIGHT weightNode) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        if(weightNode.jjtGetNumChildren() == 0){
            this.weight = createStandardWeightNode();
        } else {
            this.weight = weightNode;
        }
    }

    private ASTWEIGHT createStandardWeightNode(){
        String standardWeight = "1.0";
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
