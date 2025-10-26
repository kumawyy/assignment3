package mst;

import mst.alg.Kruskal;
import mst.alg.Prim;
import mst.model.Edge;
import mst.model.Graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class MSTTest {

    @Test
    void correctness_smallGraphs() {
        Graph g1 = new Graph();
        g1.id = 1;
        g1.nodes = Arrays.asList("A","B","C");
        g1.edges = Arrays.asList(
                new Edge("A","B",1),
                new Edge("B","C",2),
                new Edge("A","C",3)
        );
        Prim.Result p1 = Prim.run(g1);
        Kruskal.Result k1 = Kruskal.run(g1);
        assertEquals(p1.totalCost, k1.totalCost, 1e-9);
        assertTrue(App.verifyMST(g1, p1.mstEdges));
        assertTrue(App.verifyMST(g1, k1.mstEdges));
    }

    @Test
    void disconnected_graph_handling() {
        Graph g = new Graph();
        g.id = 2;
        g.nodes = Arrays.asList("A","B","C");
        g.edges = Arrays.asList(new Edge("A","B",1)); // C изолирована
        Prim.Result p = Prim.run(g);
        Kruskal.Result k = Kruskal.run(g);
        boolean okP = App.verifyMST(g, p.mstEdges);
        boolean okK = App.verifyMST(g, k.mstEdges);
        assertTrue(!okP || !okK);
    }

    @Test
    void nonnegative_metrics() {
        Graph g = new Graph();
        g.id = 3;
        g.nodes = Arrays.asList("A","B","C","D");
        g.edges = Arrays.asList(
                new Edge("A","B",1),
                new Edge("B","C",2),
                new Edge("C","D",3),
                new Edge("A","D",4),
                new Edge("B","D",5)
        );
        Prim.Result p = Prim.run(g);
        Kruskal.Result k = Kruskal.run(g);
        assertTrue(p.timeMs >= 0 && k.timeMs >= 0);
        assertTrue(p.operationsCount >= 0 && k.operationsCount >= 0);
    }
}
