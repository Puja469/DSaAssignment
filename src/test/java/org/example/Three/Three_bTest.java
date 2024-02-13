package org.example.Three;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Three_bTest {

    @Test
    public void testKruskalMST() {
        List<Edge> edges = List.of(
                new Edge(0, 1, 10),
                new Edge(0, 2, 6),
                new Edge(0, 3, 5),
                new Edge(1, 3, 15),
                new Edge(2, 3, 4)
        );

        Three_b kruskalMST = new Three_b();
        List<Edge> mst = kruskalMST.kruskalMST(edges, 4);

        // Assuming the expected Minimum Spanning Tree is {{2, 3, 4}, {0, 3, 5}, {0, 1, 10}}
        assertEquals(3, mst.size());

        // Checking each edge in the Minimum Spanning Tree
        assertEdgeInList(new Edge(2, 3, 4), mst);
        assertEdgeInList(new Edge(0, 3, 5), mst);
        assertEdgeInList(new Edge(0, 1, 10), mst);
    }

    private void assertEdgeInList(Edge expectedEdge, List<Edge> edgeList) {
        assertTrue(edgeList.stream().anyMatch(edge ->
                (edge.src == expectedEdge.src && edge.dest == expectedEdge.dest && edge.weight == expectedEdge.weight) ||
                        (edge.src == expectedEdge.dest && edge.dest == expectedEdge.src && edge.weight == expectedEdge.weight)
        ));
    }

}