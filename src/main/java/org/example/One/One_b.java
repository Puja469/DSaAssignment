package org.example.One;


import java.util.Arrays;

public class One_b {
    // Defining a method to calculate the time to build engines with a given split cost
    public static int timeToBuiltEngine(int[] engines, int splitCost) {
        int Engine = engines.length; // Getting the number of engines
        int[] dp = new int[Engine + 1]; // Creating an array to store minimum time for building engines

        Arrays.fill(dp, Integer.MAX_VALUE); //fiiling integer with Max(infinity) 
        dp[0] = 0; // Setting the minimum time to build the first engine to 0


        for (int i = 1; i <= Engine; i++) { // Looping through each engine
            dp[i] = engines[i - 1] + splitCost; // Calculating the time to build one engine and adding the split cost
            for (int j = 1; j < i; j++) {  // looping through each possible split point
                dp[i] = Math.min(dp[i], dp[j] + dp[i - j]); // updating minimum time by choosing minimum spilt 
            }
        }
        return dp[Engine]; //returning the minimum time to build all engine
    }
// Main method to execute the program
    public static void main(String[] args) {
        int[] engines = {1,2,3};
        int splitCost = 1; // Setting the cost to split

        int minTime = timeToBuiltEngine(engines, splitCost);// Calling the function to calculate the minimum time
        System.out.println("Minimum time to build all engines: " + minTime); // Printing the result
    }
}

//Output: 4