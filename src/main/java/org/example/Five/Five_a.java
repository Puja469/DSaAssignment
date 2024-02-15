package org.example.Five;

import java.util.Arrays;
import java.util.Random;

class AntColony { // Representing an Ant Colony solving the Traveling Salesman Problem (TSP) with continuous updates
    private int[][] distanceMatrix;
    private int numAnts;
    private double[][] pheromoneMatrix;
    private double[][] probabilities;
    private int numCities;
    private int[] bestTour;
    private int bestTourLength;
    private double evaporationRate;
    private double alpha;
    private double beta;

    // Constructor to initialize the AntColony with required parameters
    public AntColony(int[][] distanceMatrix, int numAnts, double evaporationRate, double alpha, double beta) {
        this.distanceMatrix = distanceMatrix;
        this.numAnts = numAnts;
        this.evaporationRate = evaporationRate;
        this.alpha = alpha;
        this.beta = beta;
        this.numCities = distanceMatrix.length;
        this.pheromoneMatrix = new double[numCities][numCities];
        this.probabilities = new double[numCities][numCities];
        initializePheromones();
    }
    // Initializing pheromone levels on edges with a small initial value
    private void initializePheromones() {
        double initialPheromone = 1.0 / numCities;
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                if (i != j) {
                    pheromoneMatrix[i][j] = initialPheromone;
                }
            }
        }
    }

    // Solving the TSP problem using ant colony optimization with continuous updates
    public void solve(int maxIterations) {
        bestTourLength = Integer.MAX_VALUE;
        bestTour = new int[numCities];
        Random random = new Random();

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (int ant = 0; ant < numAnts; ant++) {
                boolean[] visited = new boolean[numCities];
                int[] tour = new int[numCities];
                int currentCity = random.nextInt(numCities);
                tour[0] = currentCity;
                visited[currentCity] = true;

                for (int i = 1; i < numCities; i++) {
                    calculateProbabilities(currentCity, visited);
                    int nextCity = selectNextCity(currentCity);
                    tour[i] = nextCity;
                    visited[nextCity] = true;
                    currentCity = nextCity;
                }

                int tourLength = calculateTourLength(tour);
                if (tourLength < bestTourLength) {
                    bestTourLength = tourLength;
                    bestTour = tour;
                }
            }

            updatePheromones();
        }
    }
    // Calculating probabilities for selecting the next city based on pheromone levels and distances
    private void calculateProbabilities(int city, boolean[] visited) {
        double total = 0.0;
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                probabilities[city][i] = Math.pow(pheromoneMatrix[city][i], alpha) *
                        Math.pow(1.0 / distanceMatrix[city][i], beta);
                total += probabilities[city][i];
            } else {
                probabilities[city][i] = 0.0;
            }
        }

        for (int i = 0; i < numCities; i++) {
            probabilities[city][i] /= total;
        }
    }
    // Selecting the next city based on calculated probabilities
    private int selectNextCity(int city) {
        double[] probabilities = this.probabilities[city];
        double r = Math.random();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < numCities; i++) {
            cumulativeProbability += probabilities[i];
            if (r <= cumulativeProbability) {
                return i;
            }
        }
        return -1;
    }
    // Updating pheromone levels based on ant paths and evaporation
    private void updatePheromones() {
        // Evaporation
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                pheromoneMatrix[i][j] *= (1.0 - evaporationRate);
            }
        }
        // Adding new pheromones
        for (int ant = 0; ant < numAnts; ant++) {
            for (int i = 0; i < numCities - 1; i++) {
                int city1 = bestTour[i];
                int city2 = bestTour[i + 1];
                pheromoneMatrix[city1][city2] += (1.0 / bestTourLength);
                pheromoneMatrix[city2][city1] += (1.0 / bestTourLength);
            }
        }
    }
    // Calculating the total length of a tour based on the distance matrix
    private int calculateTourLength(int[] tour) {
        int length = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            length += distanceMatrix[tour[i]][tour[i + 1]];
        }
        length += distanceMatrix[tour[tour.length - 1]][tour[0]]; // Return to the starting city
        return length;
    }
    // Getters for the best tour length and the best tour path
    public int getBestTourLength() {
        return bestTourLength;
    }

    public int[] getBestTour() {
        return bestTour;
    }
}

public class Five_a {
    public static void main(String[] args) {
        int[][] distanceMatrix = {
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };
        int numAnts = 5;
        double evaporationRate = 0.5;
        double alpha = 1.0;
        double beta = 2.0;

        AntColony colony = new AntColony(distanceMatrix, numAnts, evaporationRate, alpha, beta);
        colony.solve(1000); // Solve TSP with 1000 iterations

        int[] bestTour = colony.getBestTour();
        int bestTourLength = colony.getBestTourLength();

        System.out.println("Best tour: " + Arrays.toString(bestTour));
        System.out.println("Best tour length: " + bestTourLength);
    }
}


//Output: Best tour[2,0,1,3]
//Best tour length:80