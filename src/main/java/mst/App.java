package mst;

import mst.alg.Kruskal;
import mst.alg.Prim;
import mst.io.GraphIO;
import mst.model.Edge;
import mst.model.Graph;
import mst.util.DSU;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {

    static Map<String, Object> toEntry(Graph g, Prim.Result p, Kruskal.Result k) {
        Map<String,Object> entry = new LinkedHashMap<>();
        entry.put("graph_id", g.id);

        Map<String,Object> stats = new LinkedHashMap<>();
        stats.put("vertices", g.nodes.size());
        stats.put("edges", g.edges.size());
        entry.put("input_stats", stats);

        entry.put("prim", algoMap(p));
        entry.put("kruskal", algoMap(k));

        Map<String,Object> checks = new LinkedHashMap<>();
        boolean primValid = verifyMST(g, p.mstEdges);
        boolean kruskalValid = verifyMST(g, k.mstEdges);
        checks.put("prim_valid", primValid);
        checks.put("kruskal_valid", kruskalValid);
        checks.put("equal_total_cost", Math.abs(p.totalCost - k.totalCost) < 1e-9);
        entry.put("checks", checks);

        return entry;
    }

    static Map<String,Object> algoMap(Prim.Result r) {
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("mst_edges", edgesToList(r.mstEdges));
        m.put("total_cost", r.totalCost);
        m.put("operations_count", r.operationsCount);
        m.put("execution_time_ms", r.timeMs);
        return m;
    }
    static Map<String,Object> algoMap(Kruskal.Result r) {
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("mst_edges", edgesToList(r.mstEdges));
        m.put("total_cost", r.totalCost);
        m.put("operations_count", r.operationsCount);
        m.put("execution_time_ms", r.timeMs);
        return m;
    }

    static List<Map<String,Object>> edgesToList(List<Edge> es) {
        List<Map<String,Object>> out = new ArrayList<>();
        for (Edge e : es) {
            Map<String,Object> m = new LinkedHashMap<>();
            m.put("from", e.from);
            m.put("to", e.to);
            m.put("weight", e.weight);
            out.add(m);
        }
        return out;
    }

    public static boolean verifyMST(Graph g, List<Edge> mst) {
        if (g.nodes.isEmpty()) return true;
        if (mst.size() != g.nodes.size() - 1) return false;
        DSU dsu = new DSU(g.nodes);
        for (Edge e : mst) {
            if (!dsu.union(e.from, e.to)) return false;
        }
        String root = dsu.find(g.nodes.get(0));
        for (String v : g.nodes) {
            if (!dsu.find(v).equals(root)) return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        String input = args.length > 0 ? args[0] : "data/input.json";
        String outJson = args.length > 1 ? args[1] : "data/output.json";
        String outCsv  = args.length > 2 ? args[2] : "data/summary.csv";

        List<Graph> graphs = GraphIO.loadGraphs(input);
        List<Map<String,Object>> results = new ArrayList<>();

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"graph_id","vertices","edges","prim_total","kruskal_total",
                "prim_ms","kruskal_ms","prim_ops","kruskal_ops"});

        for (Graph g : graphs) {
            Prim.Result p = Prim.run(g);
            Kruskal.Result k = Kruskal.run(g);
            results.add(toEntry(g, p, k));

            rows.add(new String[]{
                    String.valueOf(g.id),
                    String.valueOf(g.nodes.size()),
                    String.valueOf(g.edges.size()),
                    String.valueOf(p.totalCost),
                    String.valueOf(k.totalCost),
                    String.format(Locale.US,"%.3f", p.timeMs),
                    String.format(Locale.US,"%.3f", k.timeMs),
                    String.valueOf(p.operationsCount),
                    String.valueOf(k.operationsCount)
            });
        }

        new File(outJson).getParentFile().mkdirs();
        new File(outCsv).getParentFile().mkdirs();

        GraphIO.saveResultsJson(outJson, results);
        GraphIO.saveSummaryCsv(outCsv, rows);
        System.out.println("Done. Wrote: " + outJson + " and " + outCsv);
    }
}
