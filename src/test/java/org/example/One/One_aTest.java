package org.example.One;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class One_aTest {

    @Test
    public void testMinCostToDecorate() {
        // Testing case with the provided costs array
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        int expectedResult = 7; // Minimum cost is 1 + 6 + 1

        int result = One_a.minCostToDecorate(costs);
        assertEquals(expectedResult, result, "Test Case 1 Failed");


    }
}