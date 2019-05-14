/*

import java.util.*;

enum Color {
    GRAY, BLACK, WHITE, RED, GREEN, BLUE, YELLOW
}

interface Collection<T> {
    Boolean isEmpty();
}

class Queue<T> implements Collection {
    LinkedList<T> p;

    public Queue() {
         p = new LinkedList<>();
    }

    public Boolean isEmpty() {
        return p.isEmpty();
    }

    Boolean enqueue(T p0) {
        return p.add(p0);
    }

    T dequeue() {
        return p.removeFirst();
    }
}

class MinQueue<T> implements Collection {
    PriorityQueue<T> p;

    MinQueue() {
        p = new PriorityQueue<>();
    }

    Boolean insert(T p0) {
        return p.add(p0);
    }

    T minimum() {
        return p.peek();
    }

    T extractMin() {
        return p.poll();
    }

    Boolean decreaseKey(T p0, Double p1) {
        if (p0 instanceof Edge) {
            ((Edge) p0).weight -= p1;
            return true;
        }
        return false;
    }

    public Boolean isEmpty() {
        return p.isEmpty();
    }

}

class MaxQueue<T> implements Collection {
    PriorityQueue<T> p;

    MaxQueue() {
        p = new PriorityQueue<>();
    }

    Boolean insert(T p0) {
        return p.add(p0);
    }

    T maximum() {
        return p.peek();
    }

    T extractMax() {
        return p.poll();
    }

    Boolean increaseKey(T p0, Double p1) {
        if (p0 instanceof DirectedEdge || p0 instanceof UndirectedEdge) {
            ((Edge) p0).weight += p1;
            return true;
        }
        return false;
    }

    public Boolean isEmpty() {
        return p.isEmpty();
    }
}

class Vertex {
    Color color = Color.WHITE;
    String label = new String("");
    // insert vertex attributes here
}

abstract class Edge {
    Double weight = 1.0;
    Color color = Color.WHITE;
    // insert edge attributes here
}

class DirectedEdge extends Edge {
    Vertex startVertex;
    Vertex endVertex;

    DirectedEdge(Vertex startVertex, Vertex endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }
}

class UndirectedEdge extends Edge {
    Set<Vertex> vertices;

    UndirectedEdge(Vertex v1, Vertex v2) {
        vertices.add(v1);
        vertices.add(v2);
    }
}

class DiGraph {
    Set<Vertex> vertices;
    Set<DirectedEdge> edges;

    DiGraph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    Boolean addEdge(Vertex p0, Vertex p1) {
        vertices.add(p0);
        vertices.add(p1);
        return edges.add(new DirectedEdge(p0, p1));
    }

    Boolean removeEdge(DirectedEdge edge) {
        return edges.remove(edge);
    }

    DirectedEdge getEdge(Vertex v1, Vertex v2) {
        for (DirectedEdge edge : edges) {
            if ((edge.startVertex.equals(v1) && edge.endVertex.equals(v2)) || (edge.endVertex.equals(v1) && edge.startVertex.equals(v2)))
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

    Boolean addVertex(Vertex v) {
        return vertices.add(v);
    }

    Boolean removeVertex(Vertex v) {
        return vertices.remove(v);
    }

    Set<DirectedEdge> getOutgoingEdges(Vertex v) {
        Set<DirectedEdge> outgoingEdges = new HashSet<>();
        for (DirectedEdge edge : edges) {
            if (edge.startVertex.equals(v))
                outgoingEdges.add(edge);
        }
        return outgoingEdges;
    }

    Set<Vertex> getNeighbours(Vertex v) {
        Set<Vertex> neighbours = new HashSet<>();
        for (DirectedEdge edge : edges) {
            if (edge.startVertex.equals(v))
                neighbours.add(edge.endVertex);
        }
        return neighbours;
    }

}

class Graph {
    Set<Vertex> vertices;
    Set<UndirectedEdge> edges;

    Graph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    Boolean addEdge(Vertex p0, Vertex p1) {
        vertices.add(p0);
        vertices.add(p1);
        return edges.add(new UndirectedEdge(p0, p1));
    }

    Boolean removeEdge(UndirectedEdge edge) {
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

    Boolean addVertex(Vertex v) {
        return vertices.add(v);
    }

    Boolean removeVertex(Vertex v) {
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

}

class Main {
    public static void main(String[] args) {
        // insert main here
    }

    // insert extra functions here
}




*/
