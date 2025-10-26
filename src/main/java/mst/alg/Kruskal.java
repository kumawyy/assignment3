package mst.alg;

import mst.model.Edge;
import mst.model.Graph;
import mst.util.DSU;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {

    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();
        public double totalCost = 0.0;
        public long operationsCount = 0;
        public double timeMs = 0.0;
    }

    public static Result run(Graph g) {
        Result r = new Result();
        if (g.nodes.isEmpty()) return r;

        List<Edge> es = new ArrayList<>(g.edges);
        Collections.sort(es);
        DSU dsu = new DSU(g.nodes);
        long ops = 0;

        long t0 = System.nanoTime();
        for (Edge e : es) {
            ops++;
            if (dsu.union(e.from, e.to)) {
                r.mstEdges.add(e);
                r.totalCost += e.weight;
                if (r.mstEdges.size() == g.nodes.size() - 1) break;
            }
        }
        long t1 = System.nanoTime();

        r.operationsCount = ops + dsu.ops;
        r.timeMs = (t1 - t0) / 1_000_000.0;
        return r;
    }
}
