package mst.util;

import java.util.HashMap;
import java.util.Map;

public class DSU {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();
    public long ops = 0;

    public DSU(Iterable<String> items) {
        for (String x : items) {
            parent.put(x, x);
            rank.put(x, 0);
        }
    }

    public String find(String x) {
        ops++;
        String p = parent.get(x);
        if (!p.equals(x)) {
            parent.put(x, find(p));
        }
        return parent.get(x);
    }

    public boolean union(String a, String b) {
        ops++;
        String ra = find(a);
        String rb = find(b);
        if (ra.equals(rb)) return false;
        int rka = rank.get(ra);
        int rkb = rank.get(rb);
        if (rka < rkb) { String t = ra; ra = rb; rb = t; }
        parent.put(rb, ra);
        if (rka == rkb) rank.put(ra, rka + 1);
        return true;
    }
}
