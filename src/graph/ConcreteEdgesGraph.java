package graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class ConcreteEdgesGraph implements Graph<String> {
    private final Set<String> v = new HashSet<>();
    private final List<Edge> e = new ArrayList<>();
    
    public ConcreteEdgesGraph() {
        checkRep();
    }
    
    private void checkRep() {
        for (Edge E : e) {
            assert v.contains(E.getSource()) && v.contains(E.getTarget()) 
                   : "Edge vertices must exist in the vertices set";
        }
    }
   
    @Override
    public boolean add(String vertex) {
        if (vertex == null) throw new IllegalArgumentException("Vertex null");
        boolean added = v.add(vertex);
        checkRep();
        return added;
    }
    
   
    
    @Override
    public boolean remove(String vertex) {
        boolean removed = v.remove(vertex);
        e.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return removed;
    }
    
    @Override
    public Set<String> vertices() {
        return new HashSet<>(v); 
    }
    
    @Override
    public int set(String source, String target, int weight) {
        if (source == null || target == null) throw new IllegalArgumentException("Vertices null");

        int prevWeight = 0;
       
        for (Edge edge : e) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                prevWeight = edge.getWeight();
                e.remove(edge);
                break;
            }
        }
        
        
        if (weight > 0) {
            e.add(new Edge(source, target, weight));
            v.add(source);
            v.add(target);
        }

        checkRep();
        return prevWeight;
    }
  
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> s = new HashMap<>();
        for (Edge edge : e) {
            if (edge.getTarget().equals(target)) {
                s.put(edge.getSource(), edge.getWeight());
            }
        }
        return s;
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> t = new HashMap<>();
        for (Edge edge : e) {
            if (edge.getSource().equals(source)) {
                t.put(edge.getTarget(), edge.getWeight());
            }
        }
        return t;
    }
   
    @Override
    public String toString() {
        return "ConcreteEdgesGraph(vertices=" + v + ", edges=" + e + ")";
    }
   
}


class Edge {
	
    private final String source;
    private final String target;
    private final int weight;

    
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    private void checkRep() {
        assert source != null && target != null : "Source and target are null";
        assert weight >= 0 : "Weight is negative";
    }
    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }
    
    @Override
    public String toString() {
        return String.format("Edge(%s -> %s, weight=%d)", source, target, weight);
    }
}
