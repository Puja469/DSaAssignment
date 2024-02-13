package org.example.Five;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Five_bTest {
    @Test
    public void testFindNodesWithOnlyTargetAsParent() {
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 6}, {2, 4}, {4, 6}, {4, 5}, {5, 7}};
        int target = 4;

        List<Integer> uniqueParents = Five_b.findNodesWithOnlyTargetAsParent(edges, target);

        assertNotNull(uniqueParents);
        assertFalse(uniqueParents.isEmpty());

        // Assuming the expected result is {2, 5} based on the provided edges
        assertTrue(uniqueParents.contains(2));
        assertTrue(uniqueParents.contains(5));
        assertEquals(2, uniqueParents.size());

        // Additional test cases can be added based on the specific graph structure and requirements
    }

}