package graph;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Collections;
import java.util.Set;
import java.util.Map;


public abstract class GraphInstanceTest {
	
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testEnabled() {
        assert false; 
    }
    
    @Test
    public void testNoVertices() {
        assertEquals("A new graph have no vertices initially", Collections.emptySet(), emptyInstance().vertices());
    }
    
    @Test
    public void testVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("Adding a new vertex should succeed", graph.add("C"));
        assertEquals("The vertex set has the added vertex", Set.of("C"), graph.vertices());
    }
    
    @Test
    public void testPreventDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("C");
        assertFalse("Adding duplicate results in fail", graph.add("C"));
        assertEquals("The vertex set has one vertex", Set.of("C"), graph.vertices());
    }
    
    @Test
    public void testEdgeWeight() {
        Graph<String> graph = emptyInstance();
        graph.add("C");
        graph.add("D");
        
        int previousWeight = graph.set("C", "D", 5);
        assertEquals("The previous weight of the edge should be zero", 0, previousWeight);
        
        previousWeight = graph.set("C", "D", 10);
        assertEquals("The previous weight of the edge should be 5 ", 5, previousWeight);
        previousWeight = graph.set("C", "D", 0);
        assertEquals("The previous weight of the edge should be 10 ", 10, previousWeight);
        assertTrue("The edge should be removed", graph.targets("C").isEmpty());
    }
    
    @Test
    public void testRemoveRelatedEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("C");
        graph.add("D");
        graph.set("C", "D", 5);

        assertTrue("Removing a vertex", graph.remove("C"));
        assertFalse("The vertex should be removed", graph.vertices().contains("C"));
        assertTrue("All edges related should be removed", graph.sources("D").isEmpty());
    }
    
    @Test
    public void testForEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("C");
        graph.add("D");
        graph.add("E");
        graph.set("C", "D", 5);
        graph.set("E", "D", 3);
        
        Map<String, Integer> sources = graph.sources("D");
        assertEquals("There should be two sources for vertex D", Map.of("C", 5, "E", 3), sources);

        Map<String, Integer> targets = graph.targets("C");
        assertEquals("There should be one target for vertex C", Map.of("D", 5), targets);
    }
}
