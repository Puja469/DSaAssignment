package org.example.Two;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Two_aTest {
    @Test
    public void testMinMove() {
        int[] input = {1, 0, 5};

        int moves = Two_a.minMove(input);

        // Assuming the expected minimum moves is 2 based on the provided example
        assertEquals(2, moves);
    }

    @Test
    public void testMinMoveWithUnequalDistribution() {
        // Testing when the total dresses cannot be equally distributed
        int[] input = {1, 2, 3};

        int moves = Two_a.minMove(input);

        // Expected result is -1 since the total dresses cannot be equally distributed
        assertEquals(-1, moves);
    }


}