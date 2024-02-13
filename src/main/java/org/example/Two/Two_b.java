package org.example.Two;

import java.util.ArrayList;
import java.util.List;

public class Two_b {

        
// Defining a method to get people who know the secret based on intervals
    public static List<Integer> getPeopleWhoKnowSecret(int n, int[][] intervals, int firstPerson) {
        boolean[] knowsSecret = new boolean[n];// Creating a boolean array to track who knows the secret
        knowsSecret[firstPerson] = true; // Setting the first person as someone who knows the secret

        for (int[] interval : intervals) {
            for (int i = interval[0]; i <= interval[1]; i++) {  // Looping through each person in the interval
                if (knowsSecret[i]) { // Checking if the current person knows the secret
                    for (int j = interval[0]; j <= interval[1]; j++) { // Updating all people in the interval as knowing the secret
                        knowsSecret[j] = true;
                    }
                    break; // Exiting the loop after updating the interval
                }
            }
        }

        List<Integer> result = new ArrayList<>();// Creating a list to store people who know the secret
        for (int i = 0; i < n; i++) { // Looping through all people
            if (knowsSecret[i]) { // Checking if the person knows the secret
                result.add(i); // Adding the person to the result list
            }
        }

        return result;
    }
public static void main(String[] args) {
        int n = 5; // Total number of people
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};// Intervals indicating who knows the secret
        int firstPerson = 0;// The first person in the chain


        List<Integer> result = getPeopleWhoKnowSecret(n, intervals, firstPerson);// Calling the method to get the result
        System.out.println(result);// Printing the result
    }
    }
    
//Output : [0,1,2,3,4]
