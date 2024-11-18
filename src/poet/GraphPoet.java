package poet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.List;
import java.util.Map;

import graph.ConcreteEdgesGraph;
/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */

public class GraphPoet {
    
    private final ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
    
    // Abstraction function:
    //   Represents a word affinity graph built from a given corpus of text.
    // Representation invariant:
    //   The graph contains vertices as lower-cased words from the corpus, 
    //   with edges representing adjacency counts.
    // Safety from rep exposure:
    //   The graph field is private and final.

    /**
     * Create a new poet with the graph from the given corpus.
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    
    
    //we are finding the bridge words in this function.
    private String findBWord(String w1, String w2) {
        Map<String, Integer> t1 = graph.targets(w1);
        Map<String, Integer> t2 = graph.sources(w2);

        String bB = null;
        int max = 0;

        for (String candidate : t1.keySet()) {
            if (t2.containsKey(candidate)) {
                int weight = t1.get(candidate) + t2.get(candidate);
                if (weight > max) {
                    max = weight;
                    bB = candidate;
                }
            }
        }
        return bB;
    }
    
    public GraphPoet(File corpus) throws IOException {
        List<String> l = Files.readAllLines(corpus.toPath());
        String con = String.join(" ", l).toLowerCase();
        String[] ws = con.split("\\s+");
        
        for (int i = 0; i < ws.length - 1; i++) {
            String let1 = ws[i];
            String let2 = ws[i + 1];
            int weight = graph.targets(let1).getOrDefault(let2, 0) + 1;
            graph.set(let1, let2, weight);
        }
    }
    
   
   
    
    //here we are implementing the poem 
    public String p(String inp) {
        String[] iWords = inp.split("\\s+");
        StringBuilder poem = new StringBuilder();

        for (int i = 0; i < iWords.length - 1; i++) {
            String let1 = iWords[i];
            String let2 = iWords[i + 1];
            poem.append(let1).append(" ");
            
            String bWord = findBWord(let1.toLowerCase(), let2.toLowerCase());
            if (bWord != null) {
                poem.append(bWord).append(" ");
            }
        }
        poem.append(iWords[iWords.length - 1]); 
        return poem.toString();
    }
    
    @Override
    public String toString() {
        return graph.toString();
    }
}