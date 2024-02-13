package org.example.Three;



import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// Class to represent an edge in the graph
class Edge implements Comparable<Edge> {  // Declaring a class named Edge implementing Comparable

    int src, dest, weight;  // Declaring variables to store source, destination, and weight of an edge

    // Constructor to initialize the edge

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}

// Class for disjoint-set data structure

class DisjointSet {           // Declaring a class named DisjointSet
    private int[] parent, rank;  // Declaring arrays to store parent and rank information


    public DisjointSet(int size) {    // Constructor to initialize DisjointSet
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;    // Initializing each element as its own parent
            rank[i] = 0;      // Initializing rank as 0
        }
    }

    public int find(int x) {  // Method to find the root of a set
        if (parent[x] != x)
            parent[x] = find(parent[x]);
        return parent[x];
    }

    public void union(int x, int y) {  // Method to perform union of two sets
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}

// Class for Kruskal's algorithm
public class Three_b {
    public List<Edge> kruskalMST(List<Edge> edges, int vertices) {
        // Initialize disjoint-set
        DisjointSet ds = new DisjointSet(vertices);

        // Sort edges based on weight using priority queue (min heap)
        PriorityQueue<Edge> pq = new PriorityQueue<>(edges); // Creating a priority queue for edges

        // Initialize result
        List<Edge> result = new ArrayList<>();  // Creating a list to store the Minimum Spanning Tree

        while (!pq.isEmpty() && result.size() < vertices - 1) {
            Edge edge = pq.poll(); // Currently getting the edge with the minimum weight
            int srcParent = ds.find(edge.src); // Currently finding the root of the source vertex set
            int destParent = ds.find(edge.dest); // Currently finding the root of the destination vertex set


            // Add edge to result if including it doesn't form a cycle
            if (srcParent != destParent) {
                result.add(edge);
                ds.union(srcParent, destParent);
            }
        }

        return result; // Currently returning the Minimum Spanning Tree
    }

    
    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>(); // Creating a list to store edges
        edges.add(new Edge(0, 1, 10)); // Currently adding edges to the list
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        Three_b kruskalMST = new Three_b(); // Creating an object of Three_b class
        List<Edge> mst = kruskalMST.kruskalMST(edges, 4);  // Currently finding the Minimum Spanning Tree

        System.out.println("Minimum Spanning Tree:"); // Currently printing the Minimum Spanning Tree
        for (Edge edge : mst) {
            System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight); // Currently printing each edge in the Minimum Spanning Tree
        }
        }
    }
//Output: 2 - 3 :4
//        0 - 3 :5
//        0 - 1 :10