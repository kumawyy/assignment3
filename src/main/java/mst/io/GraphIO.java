package mst.io;

import com.google.gson.*;
import mst.model.Edge;
import mst.model.Graph;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GraphIO {

    public static List<Graph> loadGraphs(String path) throws IOException {
        try (Reader r = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8)) {
            JsonObject root = JsonParser.parseReader(r).getAsJsonObject();
            JsonArray arr = root.getAsJsonArray("graphs");
            List<Graph> out = new ArrayList<>();
            for (JsonElement el: arr) {
                JsonObject o = el.getAsJsonObject();
                int id = o.get("id").getAsInt();

                JsonArray nodesArr = o.getAsJsonArray("nodes");
                List<String> nodes = new ArrayList<>();
                for (JsonElement ne: nodesArr) nodes.add(ne.getAsString());

                JsonArray edgesArr = o.getAsJsonArray("edges");
                List<Edge> edges = new ArrayList<>();
                for (JsonElement ee: edgesArr) {
                    JsonObject eo = ee.getAsJsonObject();
                    edges.add(new Edge(
                            eo.get("from").getAsString(),
                            eo.get("to").getAsString(),
                            eo.get("weight").getAsDouble()
                    ));
                }
                out.add(new Graph(id, nodes, edges));
            }
            return out;
        }
    }

    public static void saveResultsJson(String path, List<Map<String,Object>> results) throws IOException {
        Map<String,Object> root = new LinkedHashMap<>();
        root.put("results", results);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer w = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {
            w.write(gson.toJson(root));
        }
    }

    public static void saveSummaryCsv(String path, List<String[]> rows) throws IOException {
        try (Writer w = new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8)) {
            for (String[] row : rows) {
                w.write(String.join(",", row));
                w.write("\n");
            }
        }
    }
}
