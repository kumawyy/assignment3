package mst.model;

public class Edge implements Comparable<Edge> {
    public final String from;
    public final String to;
    public final double weight;

    public Edge(String from, String to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.weight, o.weight);
    }
}
