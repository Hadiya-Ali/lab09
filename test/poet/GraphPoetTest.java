package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */

public class GraphPoetTest {
    
	 //here we are using the bridge words. here we are using i testing files. here the bridge will be
    // as i m testing. here m is the bridge word.
    //testing the files. here the is the bridge word.
    @Test
    public void testBridgeWords() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/testing1.txt"));
        String input = "i testing files.";
        String output = poet.p(input);
        
        assertEquals("Poem with bridge words", "i m testing the files.", output);
    }
	
	//we are testing with a file testing1.txt.
	//first we check the vertex if it exists
	//we check the edges as well
    
    @Test
    public void testConstructionGraphs() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/testing1.txt"));
        String graph = poet.toString();
        
        assertTrue("Graph contains vertices", graph.contains("lab9"));
        assertTrue("Graph contains edges", graph.contains("software -> construction"));
    }
    
    //we are putting the input and checking the output
    @Test
    public void testNoBridgeWords() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/testing1.txt"));
        String input = "Lets test the lab";
        String output = poet.p(input);
        
        assertEquals("Poem with no bridge words", "Lets test the lab", output);
    }
   
}