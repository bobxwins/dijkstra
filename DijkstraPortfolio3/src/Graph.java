import java.util.*;

import javafx.util.Pair;

public class Graph {
    private ArrayList<Vertex> Vertices = new ArrayList<>();
    private ArrayList<Boolean> CheckVertices = new ArrayList<>();


    public Vertex addvertex(String id) {
        Vertex newvertex = new Vertex(id);
        Vertices.add(newvertex);
        CheckVertices.add(false);

        return newvertex;

    }

    public void addvertex(Vertex v) {
        Vertices.add(v);
    }

    public Vertex getvertex(String s) {
        for (Vertex v : Vertices) {
            if (v.Name == s)
                return v;
        }
        return null;
    }

    public void newedge(Vertex from, Vertex to, int dist, int tim) {
        Edge newedge = new Edge(from, to);
        newedge.distance = dist;
        newedge.time = tim;
    }

    public Pair<Integer, Map<Vertex, Vertex>> ShortestDistance(Vertex source, Vertex zink) {
        Map<Vertex, Vertex> PredecessorMap = new HashMap<>();
        Map<Vertex, Integer> DistanceMap = new HashMap<>();
        // initialize arrays check alle v i vertices, for hver node i vertex bliver kaldt vertex V, og køre alle igennem
        for (Vertex v : Vertices) {
            DistanceMap.put(v, 1000);
            PredecessorMap.put(v, null); // starter med null, alle andre vertex afstand =1000
        }

        DistanceMap.replace(source, 0); // set source to 0
        PredecessorMap.replace(source, source);
        CheckVertices.set(Vertices.indexOf(source), true); // set source as visited

        Vertex current = source;// set current to source
        boolean done = false; // make while loop run inf

        while (!done) {
            int doneInt = 0;
            for (int x = 0; x < CheckVertices.size(); x++) {
                if (CheckVertices.get(x)) {
                    doneInt++;
                }
            }
            if (doneInt == CheckVertices.size()) {
                done = true;
            }

            ArrayList<Edge> bobX = current.getOutEdges(); // returnere nabo vertex
            Vertex smallBob = null; // smallest distance
            int i = 1000;
            for (Edge e : bobX) {
                Vertex v = e.getTovertex();
                if (!CheckVertices.get(Vertices.indexOf(v))) {
                    if (DistanceMap.get(current) + e.distance < DistanceMap.get(v)) {
                        DistanceMap.replace(v, DistanceMap.get(current) + e.distance);
                    }
                    if (i > e.distance) { //for at returne time, indsæt time & distance
                        i = e.distance;
                        smallBob = v;
                    }
                }
            }

            if (smallBob == null)  {
                int smallNum = 1000;
                for (int bob = 0; bob < CheckVertices.size(); bob++) {
                    if (!CheckVertices.get(bob)) {
                        if (smallNum > DistanceMap.get(Vertices.get(bob))) {
                            smallBob = Vertices.get(bob);
                        }
                    }
                }
            }
            PredecessorMap.replace(smallBob, current);
            current = smallBob;

            if (current != null) {
                if (!CheckVertices.get(Vertices.indexOf(current))) {
                    CheckVertices.set(Vertices.indexOf(current), true);
                }
            }
        }

        //implement Dijkstra
        return (new Pair<Integer, Map<Vertex, Vertex>>(DistanceMap.get(zink), PredecessorMap));
    }

    public Vertex getmin(Map<Vertex, Integer> qmap) {
        // Your code
        return null;
    }
}


class Vertex {
    public String Name;
    public ArrayList<Edge> OutEdges = new ArrayList<>();

    public Vertex(String id) {
        Name = id;
    }

    public void addOutEdge(Edge outedge) {
        OutEdges.add(outedge);
    }

    public ArrayList<Edge> getOutEdges() {
        return OutEdges;
    }
}

class Edge {
    private Vertex fromvertex;
    private Vertex tovertex;
    public int distance = 0;
    public int time = 0;


    public Vertex getTovertex() {
        return tovertex;
    }

    public Edge(Vertex from, Vertex to) {
        fromvertex = from;
        tovertex = to;
        fromvertex.addOutEdge(this);
        //If not directional
        tovertex.addOutEdge(this);
    }
}