package org.example.Three;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Three_aTest {
    @Test
    public void testGetMedianScore() {
        Three_a tracker = new Three_a();

        tracker.addScore(85.5);
        tracker.addScore(92.3);
        tracker.addScore(77.8);
        tracker.addScore(90.1);

        double medianScore1 = tracker.getMedianScore();

        // Assuming the expected median score is 87.8 based on the provided example
        assertEquals(87.8, medianScore1, 0.01); // Using a delta for double comparison

        tracker.addScore(81.2);
        tracker.addScore(88.7);

        double medianScore2 = tracker.getMedianScore();

        // Assuming the expected median score is 87.1 based on the provided example
        assertEquals(87.1, medianScore2, 0.01); // Using a delta for double comparison
    }

    @Test
    public void testGetMedianScoreEmptyList() {
        Three_a tracker = new Three_a();

        // Trying to get the median from an empty list should throw an exception
        assertThrows(IllegalStateException.class, tracker::getMedianScore);
    }

}