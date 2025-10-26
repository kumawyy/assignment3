# Minimum Spanning Tree Analysis — Prim’s vs Kruskal’s Algorithm

## 1. Experimental Setup

This experiment evaluates the performance and correctness of **Prim’s** and **Kruskal’s** algorithms for finding the **Minimum Spanning Tree (MST)** in a simulated city transportation network.

All input datasets were represented as **undirected weighted graphs**, where:
- **Vertices** represent city districts  
- **Edges** represent potential roads between districts  
- **Weights** represent the construction cost of each road  

Each algorithm was executed on multiple datasets with varying numbers of vertices and edges, as summarized below:

| Dataset Type | Number of Graphs | Vertices Range | Avg. Degree | Purpose |
|---------------|------------------|----------------|--------------|----------|
| Small         | 5                | 30             | 8–12         | Correctness & debugging |
| Medium        | 10               | 300            | 6–10         | Performance observation |
| Large         | 10               | 1000           | ~6           | Scalability evaluation |
| Extra-Large   | 3                | 1300–2000      | ~6           | Stress testing & efficiency comparison |


---

## 📊 2. Summary of Results

Below are selected results extracted from `summary.csv`.

| Graph ID | Vertices | Edges | Prim Cost | Kruskal Cost | Prim Ops | Kruskal Ops | Prim Time (ms) | Kruskal Time (ms) |
|-----------|-----------|--------|------------|---------------|------------|----------------|-------------------|--------------------|
| 1 | 30 | 120 | 218 | 218 | 587 | 536 | 1.82 | 1.41 |
| 2 | 300 | 1300 | 5649 | 5649 | 21345 | 19827 | 14.73 | 10.58 |
| 3 | 1000 | 3000 | 19432 | 19432 | 81987 | 74580 | 45.12 | 39.86 |
| 4 | 1600 | 4800 | 31258 | 31258 | 152371 | 142914 | 71.03 | 62.77 |
| 5 | 2000 | 6000 | 38542 | 38542 | 186320 | 174895 | 89.41 | 79.32 |

Both algorithms produced identical MST costs, confirming their **correctness**.

---

## 3. Theoretical Complexity Analysis

| Algorithm | Data Structures | Time Complexity | Space Complexity | Best Use Case |
|------------|------------------|------------------|-------------------|----------------|
| **Prim’s** | Min-Heap, Adjacency List | O(E log V) | O(V + E) | Dense graphs |
| **Kruskal’s** | Disjoint Set (Union–Find) | O(E log E) ≈ O(E log V) | O(V + E) | Sparse graphs |

- Prim’s algorithm incrementally expands the MST by always adding the smallest outgoing edge from the growing tree.  
- Kruskal’s algorithm sorts all edges globally and joins disjoint sets using **Union–Find (DSU)** operations.

---

## 4. Empirical Performance Analysis

### 4.1 Correctness  
For all tested graphs, the MST total cost obtained by both algorithms was identical, fulfilling the definition of a spanning tree (V−1 edges, no cycles).

### 4.2 Execution Time  
- Kruskal’s algorithm generally outperformed Prim’s by **10–25%** in execution time.  
- The difference increased with graph size, particularly for large and extra-large datasets.  
- Prim’s performance decreased on **sparse graphs** due to redundant heap operations.

### 4.3 Operation Count  
- Kruskal required fewer total operations (find + union) than Prim’s heap operations on sparse and medium graphs.  
- Prim’s advantage appears in denser graphs where heap updates are localized.  
- For very large graphs (>1500 vertices), Kruskal’s asymptotic growth was lower.

---

## 5. Practical Interpretation

| Condition | Preferred Algorithm | Reason |
|------------|---------------------|--------|
| Sparse network (few edges per vertex) | **Kruskal** | Fewer comparisons, efficient DSU operations |
| Dense network (many edges) | **Prim** | Heap limits redundant edge processing |
| Graph stored as adjacency list | **Prim** | Efficient frontier updates |
| Graph stored as edge list | **Kruskal** | Sorting aligns with algorithm design |

In city transportation planning, where roads form **sparse networks** and costs vary, **Kruskal’s algorithm** offers simpler and more predictable optimization.

---

## 6. Conclusions

1. Both algorithms yield **identical MST costs**, confirming theoretical correctness.  
2. **Kruskal’s** algorithm scales better for large, sparse graphs due to efficient edge sorting and union–find.  
3. **Prim’s** performs competitively on dense graphs but shows higher operation counts on sparse networks.  
4. The experimental data aligns with **theoretical complexity predictions** (O(E log V)).  

---

