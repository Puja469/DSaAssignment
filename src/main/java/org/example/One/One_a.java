package org.example.One;

public class One_a {

     //  finding the minimum cost to decorate all venues
    public static int minCostToDecorate(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int venues = costs.length;
        int themes = costs[0].length;

        // Dynamic programming array to store minimum costs
        int[][] dp = new int[venues][themes];

        // Copying the costs of decorating the first venue
        System.arraycopy(costs[0], 0, dp[0], 0, themes);

        // Iterating through venues starting from the second one
        for (int i = 1; i < venues; i++) {
            for (int j = 0; j < themes; j++) {
                //  minimum cost from the previous venue with a different theme
                int minPrevCost = findMinExcept(dp[i - 1], j);

                // Updating the minimum cost for the current venue and theme
                dp[i][j] = costs[i][j] + minPrevCost;
            }
        }

        // Finding the minimum cost from the last row
        int minCost = findMin(dp[venues - 1]);

        return minCost;
    }

    private static int findMinExcept(int[] array, int excludeIndex) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (i != excludeIndex) {
                min = Math.min(min, array[i]);
            }
        }
        return min;
    }

    private static int findMin(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int value : array) {
            min = Math.min(min, value);
        }
        return min;
    }

    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        int result = minCostToDecorate(costs);
        System.out.println("Minimum cost to decorate all venues: " + result);
    }
    

    
}
