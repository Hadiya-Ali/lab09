package graph;
import static org.junit.Assert.*;
import org.junit.Test;


public class ConcreteVerticesGraphTest extends GraphInstanceTest {
	
    @Test
    public void testConcreteVerticesString() {
        Graph<String> graph = emptyInstance();
        graph.add("C");
        graph.add("D");
        graph.set("C", "D", 5);
        
        String expected = "ConcreteVerticesGraph(vertices=[Vertex(C): {D=5}, Vertex(D): {}])";
        assertEquals("expected specific toString output for ConcreteVerticesGraph", expected, graph.toString());
    }
    
    @Test
    public void testVertexOperations() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        Vertex vertex = new Vertex("C");
        vertex.setEdge("D", 5);
        assertEquals("expected weight", Integer.valueOf(5), vertex.getEdgeWeight("D"));
        vertex.setEdge("D", 0);
        assertNull("expected edge removed", vertex.getEdgeWeight("D"));
    }
    
	@Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
}
