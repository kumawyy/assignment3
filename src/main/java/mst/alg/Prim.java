package mst.alg;

import mst.model.Edge;
import mst.model.Graph;

import java.util.*;

public class Prim {

    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();
        public double totalCost = 0.0;
        public long operationsCount = 0;
        public double timeMs = 0.0;
    }

    public static Result run(Graph g) {
        Result r = new Result();
        if (g.nodes.isEmpty()) return r;

        Map<String, List<Edge>> adj = g.adjacency();
        String start = g.nodes.get(0);
        Set<String> seen = new HashSet<>();
        seen.add(start);
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        long ops = 0;

        long t0 = System.nanoTime();
        for (Edge e : adj.get(start)) { pq.offer(e); ops++; }
        while (!pq.isEmpty() && seen.size() < g.nodes.size()) {
            Edge cur = pq.poll(); ops++;
            if (seen.contains(cur.to)) continue;
            r.mstEdges.add(cur);
            r.totalCost += cur.weight;
            seen.add(cur.to);
            for (Edge e : adj.get(cur.to)) {
                if (!seen.contains(e.to)) {
                    pq.offer(e); ops++;
                }
            }
        }
        long t1 = System.nanoTime();
        r.operationsCount = ops;
        r.timeMs = (t1 - t0) / 1_000_000.0;
        return r;
    }
}
