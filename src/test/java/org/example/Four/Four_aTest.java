package org.example.Four;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Four_aTest {
    @Test
    public void testShortestPath() {
        char[][] grid = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };

        int result = Four_a.shortestPath(grid);

        // Assuming the expected result is 8 based on the provided example
        assertEquals(8, result);
    }


}