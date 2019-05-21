/*

import Compiler.CodeGeneration.DotFileGenerator.DotFileGenerator;

import java.util.*;

enum Color {
    GRAY, BLACK, WHITE, RED, GREEN, BLUE, YELLOW
}

interface Collection<T> {
    boolean isEmpty();
}

class Queue<T> implements Collection {
    LinkedList<T> p;

    public Queue() {
         p = new LinkedList<>();
    }

    public boolean isEmpty() {
        return p.isEmpty();
    }

    boolean enqueue(T p0) {
        return p.add(p0);
    }

    T dequeue() {
        return p.removeFirst();
    }
}


class VertexMinComparator implements Comparator<Vertex> {
    @Override
    public int compare(Vertex o1, Vertex o2) {
        double diff = o1.distance - o2.distance;
        if (diff > 0)
            return 1;
        else if (diff < 0)
            return -1;
        else
            return 0;
    }
}

class VertexMaxComparator implements Comparator<Vertex> {
    @Override
    public int compare(Vertex o1, Vertex o2) {
        double diff = o1.distance - o2.distance;
        if (diff > 0)
            return -1;
        else if (diff < 0)
            return 1;
        else
            return 0;
    }
}

class EdgeMinComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge o1, Edge o2) {
        double diff = o1.weight - o2.weight;
        if (diff > 0)
            return 1;
        else if (diff < 0)
            return -1;
        else
            return 0;
    }
}

class EdgeMaxComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge o1, Edge o2) {
        double diff = o1.weight - o2.weight;
        if (diff > 0)
            return -1;
        else if (diff < 0)
            return 1;
        else
            return 0;
    }
}

class MinQueue<T> implements Collection<T> {
    List<T> p;

    MinQueue() {
        p = new ArrayList<>();
    }

    boolean insert(T p0) {
        p.add(p0);
        if (p0 instanceof Vertex)
            ((List<Vertex>) p).sort(new VertexMinComparator());
        else if (p0 instanceof Edge)
            ((List<Edge>) p).sort(new EdgeMinComparator());
        else
            return false;
        return true;
    }

    T minimum() {
        return p.get(0);
    }

    T extractMin() {
        T t =  p.remove(0);
        if (t instanceof Vertex)
            ((List<Vertex>) p).sort(new VertexMinComparator());
        else if (t instanceof Edge)
            ((List<Edge>) p).sort(new EdgeMinComparator());
        else
            throw new IllegalArgumentException("Cannot extract from MinQueue with element type " + t);
        return t;
    }

    boolean decreaseKey(T p0, double p1) {
        if (p0 instanceof Edge) {
            ((Edge) p0).weight -= p1;
            ((List<Edge>) p).sort(new EdgeMinComparator());
            return true;
        }
        else if (p0 instanceof Vertex) {
            ((Vertex) p0).distance -= p1;
            ((List<Vertex>) p).sort(new VertexMinComparator());
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return p.isEmpty();
    }

}

class MaxQueue<T> implements Collection {
    List<T> p;

    MaxQueue() {
        p = new ArrayList<>();
    }

    boolean insert(T p0) {
        p.add(p0);
        if (p0 instanceof Vertex)
            ((List<Vertex>) p).sort(new VertexMaxComparator());
        else if (p0 instanceof Edge)
            ((List<Edge>) p).sort(new EdgeMaxComparator());
        else
            return false;
        return true;
    }

    T maximum() {
        return p.get(0);
    }

    T extractMax() {
        T t =  p.remove(0);
        if (t instanceof Vertex)
            ((List<Vertex>) p).sort(new VertexMaxComparator());
        else if (t instanceof Edge)
            ((List<Edge>) p).sort(new EdgeMaxComparator());
        else
            throw new IllegalArgumentException("Cannot extract from MaxQueue with element type " + t);
        return t;
    }

    boolean increaseKey(T p0, double p1) {
        if (p0 instanceof Edge) {
            ((Edge) p0).weight += p1;
            ((List<Edge>) p).sort(new EdgeMaxComparator());
            return true;
        }
        else if (p0 instanceof Vertex) {
            ((Vertex) p0).distance += p1;
            ((List<Vertex>) p).sort(new VertexMaxComparator());
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return p.isEmpty();
    }
}

class Vertex {
    Color color = Color.WHITE;
    String label = "";
    double distance;
    // insert vertex attributes here
}

abstract class Edge {
    double weight = 1.0;
    Color color = Color.BLACK;
    // insert edge attributes here
}

class DirectedEdge extends Edge {
    Vertex source;
    Vertex target;

    DirectedEdge(Vertex source, Vertex target) {
        this.source = source;
        this.target = target;
    }
}

class UndirectedEdge extends Edge {
    Set<Vertex> vertices;

    UndirectedEdge(Vertex v1, Vertex v2) {
        vertices = new HashSet<>();
        vertices.add(v1);
        vertices.add(v2);
    }
}

class DiGraph {
    private int printIndex = 0;
    Set<Vertex> vertices;
    Set<DirectedEdge> edges;

    DiGraph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    boolean addEdge(Vertex p0, Vertex p1) {
        vertices.add(p0);
        vertices.add(p1);
        return edges.add(new DirectedEdge(p0, p1));
    }

    boolean removeEdge(DirectedEdge edge) {
        return edges.remove(edge);
    }

    DirectedEdge getEdge(Vertex v1, Vertex v2) {
        for (DirectedEdge edge : edges) {
            if ((edge.source.equals(v1) && edge.target.equals(v2)) || (edge.target.equals(v1) && edge.source.equals(v2)))
                return edge;
        }
        throw new RuntimeException("Tried to get nonexistent edge between two vertices, '" + v1.label + "' and '" + v2.label + "'");
    }

    Vertex getVertex(String label) {
        for (Vertex vertex : vertices) {
            if (vertex.label.equals(label))
                return vertex;
        }
        throw new RuntimeException("Tried to get nonexistent vertex from label '" + label + "'");
    }

    boolean addVertex(Vertex v) {
        return vertices.add(v);
    }

    boolean removeVertex(Vertex v) {
        return vertices.remove(v);
    }

    Set<DirectedEdge> getOutgoingEdges(Vertex v) {
        Set<DirectedEdge> outgoingEdges = new HashSet<>();
        for (DirectedEdge edge : edges) {
            if (edge.source.equals(v))
                outgoingEdges.add(edge);
        }
        return outgoingEdges;
    }

    Set<Vertex> getNeighbours(Vertex v) {
        Set<Vertex> neighbours = new HashSet<>();
        for (DirectedEdge edge : edges) {
            if (edge.source.equals(v))
                neighbours.add(edge.target);
        }
        return neighbours;
    }

    void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        for (Vertex vertex : vertices)
            sb.append(vertex.label).append(" [style=\"filled\", fillcolor=").append(vertex.color.name()).append("]\n");

        for (DirectedEdge edge : edges) {
            sb.append(edge.source.label).append(" -> ").append(edge.target.label);
            sb.append(" [color=\"").append(edge.color.name()).append("\", label=\"").append(edge.weight).append("\"]\n");
        }
        sb.append("}\n");
        DotFileGenerator.createDotFile(sb.toString(), printIndex);
        printIndex++;
    }

}

class Graph {
    private int printIndex = 0;
    Set<Vertex> vertices;
    Set<UndirectedEdge> edges;

    Graph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    boolean addEdge(Vertex p0, Vertex p1) {
        vertices.add(p0);
        vertices.add(p1);
        return edges.add(new UndirectedEdge(p0, p1));
    }

    boolean removeEdge(UndirectedEdge edge) {
        return edges.remove(edge);
    }

    UndirectedEdge getEdge(Vertex v1, Vertex v2) {
        for (UndirectedEdge edge : edges) {
            if (edge.vertices.contains(v1) && edge.vertices.contains(v2) && !v1.equals(v2))
                return edge;
        }
        throw new RuntimeException("Tried to get nonexistent edge between two vertices, '" + v1.label + "' and '" + v2.label + "'");
    }

    Vertex getVertex(String label) {
        for (Vertex vertex : vertices) {
            if (vertex.label.equals(label))
                return vertex;
        }
        throw new RuntimeException("Tried to get nonexistent vertex from label '" + label + "'");
    }

    boolean addVertex(Vertex v) {
        return vertices.add(v);
    }

    boolean removeVertex(Vertex v) {
        return vertices.remove(v);
    }

    Set<UndirectedEdge> getOutgoingEdges(Vertex v) {
        Set<UndirectedEdge> outgoingEdges = new HashSet<>();
        for (UndirectedEdge edge : edges) {
            if (edge.vertices.contains(v))
                outgoingEdges.add(edge);
        }
        return outgoingEdges;
    }

    Set<Vertex> getNeighbours(Vertex v) {
        Set<Vertex> neighbours = new HashSet<>();
        for (UndirectedEdge edge : edges) {
            if (edge.vertices.contains(v)) {
                for (Vertex vertex : edge.vertices) {
                    if (!vertex.equals(v))
                        neighbours.add(vertex);
                }
            }
        }
        return neighbours;
    }

    void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("graph {\n");
        for (Vertex vertex : vertices)
            sb.append(vertex.label).append(" [style=\"filled\", fillcolor=").append(vertex.color.name()).append("]\n");

        for (UndirectedEdge edge : edges) {
            Iterator<Vertex> vertexIterator = edge.vertices.iterator();
            sb.append(vertexIterator.next().label).append(" -- ").append(vertexIterator.next().label);
            sb.append(" [color=\"").append(edge.color.name()).append("\", label=\"").append(edge.weight).append("\"]\n");
        }
        sb.append("}\n");
        DotFileGenerator.createDotFile(sb.toString(), printIndex);
        printIndex++;
    }

}

class Main {
    public static void main(String[] args) {
        // insert main here
    }

    // insert extra functions here
}




*/
