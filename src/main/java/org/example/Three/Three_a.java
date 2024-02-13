package org.example.Three;
import java.util.ArrayList;


public class Three_a {
     private ArrayList<Double> scores;  //arraylist created to store the scores

    public Three_a() { // Constructor to initialize the ArrayList
        scores = new ArrayList<>(); // Initializing the ArrayList
    }
    

    public void addScore(double score) { // Method to add a score to the ArrayList
        scores.add(score); // Adding the score to the ArrayList
    }

    public double getMedianScore() { // Method to calculate and return the median score
        if (scores.isEmpty()) { // Checking if the ArrayList is empty
            throw new IllegalStateException("No scores added yet."); // Throwing an exception if no scores are added
        }

        int size = scores.size(); // Getting the size of the ArrayList


         // Sorting the scores in ascending order using the Bubble Sort algorithm
        for (int i = 0; i < size - 1; i++) { // Outer loop for the entire list
            for (int j = 0; j < size - i - 1; j++) { // Inner loop for the unsorted portion
                if (scores.get(j) > scores.get(j + 1)) { // Comparing adjacent elements
                    


                    double temp = scores.get(j); // Temporary variable to store the current value
                    scores.set(j, scores.get(j + 1)); // Swapping current and adjacent values
                    scores.set(j + 1, temp); // Updating adjacent value with the temporary variable
                }
            }
        }

        if (size % 2 == 0) { // Checking if the number of scores is even


           // Calculating and returning the median for even number of scores
            int middleIndex1 = (size / 2) - 1;
            int middleIndex2 = size / 2;
            return (scores.get(middleIndex1) + scores.get(middleIndex2)) / 2.0;
        } else {

            
             // Returning the middle score as the median for odd number of score
            return scores.get(size / 2);
        }
    }

    public static void main(String[] args) {
        Three_a tracker = new Three_a(); //creating object 

        // Adding scores to the tracker object
        tracker.addScore(85.5);
        tracker.addScore(92.3);
        tracker.addScore(77.8);
        tracker.addScore(90.1);
        System.out.println("Median Score 1: " + tracker.getMedianScore()); // Printing the median score

        tracker.addScore(81.2);
        tracker.addScore(88.7);

        System.out.println("Median Score 2: " + tracker.getMedianScore()); // Printing the updated median score
    }
    }
 //Output: Median Score 1: 87.8
//Median Score 2: 87.1
