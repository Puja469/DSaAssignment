package org.example.Four;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
public class Four_a {
    static class State {     // Declaring a static nested class named State
        int x, y;            // Declaring variables to store x and y coordinates
        String keys;         // Declaring a variable to store keys
 
         // Constructor to initialize the State
        State(int x, int y, String keys) {
            this.x = x;
            this.y = y;
            this.keys = keys;
        }
    }
 
    // Method to find the shortest path in the grid
    public static int shortestPath(char[][] grid) {
        int m = grid.length;    // Storing the number of rows in the grid
        int n = grid[0].length; // Storing the number of columns in the grid
        Set<Character> keys = new HashSet<>();  // Creating a HashSet to store keys
        Map<Character, int[]> doors = new HashMap<>();  // Creating a HashMap to store door positions
        int start_x = -1, start_y = -1;   // Initializing start position variables
 

        // Loop through the grid to identify keys, doors, and starting position
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char cell = grid[i][j];  // Storing the current cell value
                if (cell == 'S') {
                    start_x = i;
                    start_y = j;
                } else if ('a' <= cell && cell <= 'z') {
                    keys.add(cell); // Adding the key to the HashSet
                } else if ('A' <= cell && cell <= 'Z') {
                    doors.put(cell, new int[] { i, j });
                }
            }
        }
 
        List<Character> keysList = new ArrayList<>(keys); // Converting keys HashSet to a List
        int[] minDistance = { Integer.MAX_VALUE };  // Initializing an array to store minimum distance
        dfs(grid, start_x, start_y, keysList, doors, new boolean[m][n], "", 0, minDistance);
 
        return minDistance[0] == Integer.MAX_VALUE ? -1 : minDistance[0];  // Returning the minimum distance or -1 if not possible

    }
    
    // Recursive DFS method to explore paths
    private static void dfs(char[][] grid, int x, int y, List<Character> keys, Map<Character, int[]> doors,
            boolean[][] visited, String collectedKeys, int distance, int[] minDistance) {
        if (distance >= minDistance[0])
            return;
 
        visited[x][y] = true;


 
        // Exploring adjacent positions
        for (int[] dir : new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }) {
            int nx = x + dir[0];
            int ny = y + dir[1];
 
            // Checking if the new position is within the grid and not visited
            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && !visited[nx][ny]) {
                char cell = grid[nx][ny];  // Storing the value of the new cell

 
                if (cell == 'P' || cell == 'S') {
                    dfs(grid, nx, ny, keys, doors, visited, collectedKeys, distance + 1, minDistance);
                } else if ('a' <= cell && cell <= 'z') {
                    String newCollectedKeys = collectedKeys + cell;  // Collecting the new key
                    if (newCollectedKeys.length() == keys.size()) {
                        minDistance[0] = Math.min(minDistance[0], distance + 1); // Updating minimum distance
                    } else {
                        dfs(grid, nx, ny, keys, doors, visited, newCollectedKeys, distance + 1, minDistance);
                    }
                } else if ('A' <= cell && cell <= 'Z') {
                    char key = Character.toLowerCase(cell);
                    if (collectedKeys.indexOf(key) != -1) {
                        dfs(grid, nx, ny, keys, doors, visited, collectedKeys, distance + 1, minDistance); // Continue exploring
                    }
                }
            }
        }
 
        visited[x][y] = false;  // Backtrack: Marking the current position as not visited
    }
 

     // Main method for testing
    public static void main(String[] args) {
        char[][] grid = {
                { 'S', 'P', 'q', 'P', 'P' },
                { 'W', 'W', 'W', 'P', 'W' },
                { 'r', 'P', 'Q', 'P', 'R' }
        };
        System.out.println(shortestPath(grid));  // Printing the result of the shortestPath method
    }
}



//Output: 8