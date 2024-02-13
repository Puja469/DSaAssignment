package org.example.Five;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Five_aTest {
    @Test
    public void testAntColonySolve() {
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
        colony.solve(1000);

        int[] bestTour = colony.getBestTour();
        int bestTourLength = colony.getBestTourLength();

        assertNotNull(bestTour);
        assertTrue(bestTour.length > 0);
        assertTrue(bestTourLength >= 0);
        assertTrue(bestTourLength <= calculateUpperBound(distanceMatrix));
    }

    private int calculateUpperBound(int[][] distanceMatrix) {
        // Calculate an upper bound by summing all distances
        int upperBound = 0;
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[0].length; j++) {
                upperBound += distanceMatrix[i][j];
            }
        }
        return upperBound;
    }

}