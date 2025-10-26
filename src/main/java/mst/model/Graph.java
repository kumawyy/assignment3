package mst.model;

import java.util.*;

public class Graph {
    public int id;
    public List<String> nodes = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();

    private final Map<String, List<Edge>> adj = new HashMap<>();
    private boolean adjDirty = true;

    public Graph(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = new ArrayList<>(nodes);
        this.edges = new ArrayList<>(edges);
        this.adjDirty = true; // чтобы adjacency() потом пересобрал связи
    }

    public Graph() {}

    public void setNodes(List<String> ns) {
        this.nodes = new ArrayList<>(ns);
        this.adjDirty = true;
    }

    public void setEdges(List<Edge> es) {
        this.edges = new ArrayList<>(es);
        this.adjDirty = true;
    }

    public Map<String, List<Edge>> adjacency() {
        if (adjDirty || adj.size() != nodes.size()) {
            buildAdj();
            adjDirty = false;
        }
        return adj;
    }

    private void buildAdj() {
        adj.clear();
        for (String u : nodes) {
            adj.put(u, new ArrayList<>());
        }
        for (Edge e : edges) {
            adj.get(e.from).add(e);
            adj.get(e.to).add(new Edge(e.to, e.from, e.weight));
        }
    }
}