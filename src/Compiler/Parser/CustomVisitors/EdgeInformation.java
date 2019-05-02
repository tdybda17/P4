package Compiler.Parser.CustomVisitors;

public class EdgeInformation {
    private String firstVertex;
    private String secondVertex;
    private double weight = 1;

    public EdgeInformation(String firstVertex, String secondVertex, double weight) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.weight = weight;
    }

    public EdgeInformation(String firstVertex, String secondVertex) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
    }

    public String getFirstVertex() {
        return firstVertex;
    }

    public String getSecondVertex() {
        return secondVertex;
    }

    public double getWeight() {
        return weight;
    }
}
