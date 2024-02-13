package org.example.Two;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Two_bTest {
    @Test
    public void testGetPeopleWhoKnowSecret() {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;

        List<Integer> result = Two_b.getPeopleWhoKnowSecret(n, intervals, firstPerson);

        // Assuming the expected result is [0, 1, 2, 3, 4] based on the provided example
        assertIterableEquals(List.of(0, 1, 2, 3, 4), result);
    }

    @Test
    public void testGetPeopleWhoKnowSecretWithEmptyIntervals() {
        // Testing when intervals are empty
        int n = 5;
        int[][] intervals = {};
        int firstPerson = 0;

        List<Integer> result = Two_b.getPeopleWhoKnowSecret(n, intervals, firstPerson);

        // Expected result is [0] since the first person knows the secret
        assertIterableEquals(List.of(0), result);
    }


}