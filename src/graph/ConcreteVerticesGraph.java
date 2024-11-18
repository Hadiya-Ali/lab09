package graph;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;


public class ConcreteVerticesGraph implements Graph<String> {
	
    private final List<Vertex> v = new ArrayList<>(); 
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    private void checkRep() {  
        for (Vertex vertex : v) {
            assert vertex != null : "null elements are in vertices";
        }
    }
    @Override
    public boolean add(String vertexLabel) {
        for (Vertex vertex : v) {
            if (vertex.getLabel().equals(vertexLabel)) return false; 
        }
        v.add(new Vertex(vertexLabel));
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Weight is negative");

        Vertex sourceVertex = findVertex(source);         
        if (sourceVertex == null) {
            sourceVertex = new Vertex(source);
            v.add(sourceVertex);
        }
        Vertex targetVertex = findVertex(target);
        if (targetVertex == null) {
            targetVertex = new Vertex(target);
            v.add(targetVertex);
        }
        int previousWeight = sourceVertex.setEdge(target, weight); 
        checkRep();
        return previousWeight;
    }
    private Vertex findVertex(String label) {
        for (Vertex vertex : v) {
            if (vertex.getLabel().equals(label)) {
                return vertex;
            }
        }
        return null;
    }
    
   
    
    @Override
    public Set<String> vertices() {
        Set<String> labels = new HashSet<>();
        for (Vertex ve : v) {
            labels.add(ve.getLabel());
        }
        return labels;
    }
    @Override
    public boolean remove(String vertexLabel) {
        Vertex vertex = findVertex(vertexLabel);
        if (vertex == null) return false;

        v.remove(vertex);
        for (Vertex V : v) {
            V.removeEdge(vertexLabel);
        }
        checkRep();
        return true;
    }
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();   
        for (Vertex vertex : v) {
            Integer weight = vertex.getEdgeWeight(target);
            if (weight != null) {
                sources.put(vertex.getLabel(), weight);
            }
        }
        return sources;
    }
    @Override
    public Map<String, Integer> targets(String source) {
        Vertex vertex = findVertex(source);  
        if (vertex != null) {
            return vertex.getEdges();
        }
        return new HashMap<>();
    }
    @Override
    public String toString() {
        return "ConcreteVerticesGraph(vertices=" + v + ")";
    }
    
}

class Vertex {
	
    private final String label; 
    private final Map<String, Integer> e = new HashMap<>(); 

    public Vertex(String label) {
        this.label = label;
        checkRep(); 
    }
    private void checkRep() {
        assert label != null : "Vertex label is null";
        for (Integer weight : e.values()) {
            assert weight >= 0 : "Edge weights are negative";
        }
    }
    public String getLabel() {
        return label;
    }
    public int setEdge(String target, int weight) { 
        
        if (weight == 0) {
            Integer previousWeight = e.remove(target);
            return previousWeight == null ? 0 : previousWeight;
        } else {
            Integer previousWeight = e.put(target, weight);
            return previousWeight == null ? 0 : previousWeight;
        }
    }
    public Integer getEdgeWeight(String target) {
       
        return e.get(target);
    }
    public Map<String, Integer> getEdges() {
       
        return new HashMap<>(e);
    }
    public void removeEdge(String target) {
       
        e.remove(target);
    }
    @Override
    public String toString() {
        return "Vertex(" + label + "): " + e;
    }
   
}
