/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;
import static org.junit.Assert.*;
import org.junit.Test;


public class ConcreteEdgesGraphTest extends GraphInstanceTest {
	
    @Test
    public void testGraphAndEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("C");
        graph.add("D");

        graph.set("C", "D", 5);
        
        String expectedGraphString = "ConcreteEdgesGraph(vertices=[C, D], edges=[Edge(C -> D, weight=5)])";
        assertEquals("Graph toString representation should match the expected format", expectedGraphString, graph.toString());

        Edge edge = new Edge("C", "D", 5);
        assertEquals("Source 'C'", "C", edge.getSource());
        assertEquals("Target 'D'", "D", edge.getTarget());
        assertEquals("Weight 5", 5, edge.getWeight());
    }
    
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
}
