package org.example.One;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class One_bTest {

    @Test
    public void testTimeToBuiltEngine() {
        int[] engines = {1, 2, 3};
        int splitCost = 1;

        int minTime = One_b.timeToBuiltEngine(engines, splitCost);

        // Assuming the expected minimum time is 4 based on the provided example
        assertEquals(4, minTime);
    }

}