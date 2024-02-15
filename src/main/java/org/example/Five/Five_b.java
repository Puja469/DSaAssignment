package org.example.Five;

import java.util.*;

public class Five_b{


    // Method to find nodes with only the target node as the parent in a directed graph

    public static List<Integer> findNodesWithOnlyTargetAsParent(int[][] edges, int target) {
        // Create a graph represented by an adjacency list and a map to store in-degrees of each node
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        // Build the graph and calculate in-degree of each node
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            graph.putIfAbsent(from, new ArrayList<>());
            graph.get(from).add(to);
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
        }

        // Perform Depth-First Search (DFS) starting from the target node
        List<Integer> result = new ArrayList<>();
        dfs(graph, inDegree, target, target, result);

        return result;
    }
    // Recursive DFS method to explore nodes and find those with only the target node as their parent
    private static void dfs(Map<Integer, List<Integer>> graph, Map<Integer, Integer> inDegree, int node, int target,
                            List<Integer> result) {
        // If the current node has no incoming edges other than from the target node,
        // add it to the result
        if (inDegree.getOrDefault(node, 0) == 1 && graph.get(target).contains(node)) {
            result.add(node);
            // Add child nodes recursively
            addChildren(graph, node, result);
        }

        // Recursively explore the children of the current node
        if (graph.containsKey(node)) {
            for (int child : graph.get(node)) {
                dfs(graph, inDegree, child, target, result);
            }
        }
    }
    // Recursive method to add children of a node to the result
    private static void addChildren(Map<Integer, List<Integer>> graph, int node, List<Integer> result) {
        if (graph.containsKey(node)) {
            for (int child : graph.get(node)) {
                result.add(child);
                addChildren(graph, child, result); // Recursively add children of children
            }
        }
    }
    // Main method for testing the functionality
    public static void main(String[] args) {
        int[][] edges = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 6 }, { 2, 4 }, { 4, 6 }, { 4, 5 }, { 5, 7 } };
        int target = 4;
        // Finding nodes whose only parent is the target node

        List<Integer> uniqueParents = findNodesWithOnlyTargetAsParent(edges, target);

        // Displaying the result

        System.out.println("Nodes whose only parent is " + target + ":");
        for (int node : uniqueParents) {
            System.out.println(node);
        }
    }
}