package org.example.Four;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Four_bTest {
    @Test
    public void testClosestKValues() {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        double target = 3.8;
        int x = 2;

        List<Integer> result = Four_b.closestKValues(root, target, x);

        // Asserting that the result matches the expected values
        assertIterableEquals(List.of(4, 3), result);
    }

}