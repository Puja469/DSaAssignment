package org.example.Two;
 public class Two_a {
    
    // Defining a method to calculate the minimum number of moves to equalize the load on machines
    public static int minMove(int[] machines) {
        int totDresses = 0;// Initializing the variable for total dresses
        int noOfMachine = machines.length; // Calculating the length of machines

        // Calculating the total dresses
        for (int dress : machines) {
            totDresses += dress;
        }

       // Checking if the total dresses can be equally distributed among the machines
        if (totDresses % noOfMachine != 0) {
            return -1; // If not, return -1
        }

        // Calculating the dresses each machine should have

        int dressesPerMachine = totDresses / noOfMachine;


        int moves = 0; // Initializing the variable to count moves
        for (int i = 0; i < noOfMachine - 1; i++) {
            int diff = dressesPerMachine - machines[i];// Calculating the difference in dresses
            if (diff > 0) {
                int shift = Math.min(diff, machines[i + 1]);// Calculating the number of dresses to shift
                machines[i] += shift;// Updating the dresses on the current machine
                machines[i + 1] -= shift; // Updating the dresses on the next machine
                
                moves += shift;// Updating the total number of moves
            }
        }

        return moves; // Returning the minimum number of moves
    }
    // Main method to execute the program
    public static void main(String[] args) {
        int[] input = { 1, 0, 5 }; // Input array representing the initial load of dresses on each machine
        System.out.println(minMove(input) + " is the minimum number of moves to shift the dresses");
    }
}

//Output : 2 is the minimum number of moves to shift the dresses