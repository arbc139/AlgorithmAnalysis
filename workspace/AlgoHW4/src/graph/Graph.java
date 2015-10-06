package graph;

import dy.DYConsole;

import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;

/**
 * Created by DYKim on 15. 10. 6..
 */
public class Graph {
    public static DYConsole console = new DYConsole();

    ArrayList<Vertex> vertexes;
    ArrayList<Edge> edges;

    public Graph() {
        this.vertexes = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();
    }

    public Graph(int numberOfVertex) {
        this.vertexes = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();

        for(int i=0; i<numberOfVertex; i++) {
            vertexes.add(new Vertex(i));
        }
    }

    public void addEdge(int u, int v) {
        edges.add(new Edge(vertexes.get(u), vertexes.get(v)));
    }

    /*
        Tool method
     */
    public void print() {
        console.println(":Vertexes:");
        for(Vertex v: vertexes) {
            console.println(v.toString());
        }

        console.println(":Edges:");
        for(Edge e: edges) {
            console.println(e.toString());
        }
    }

    /*---------------------------------------------------------
        kruskal algorithm's method
     ---------------------------------------------------------*/
    /**
     *  randomized edge's weight method
     */
    public void assignRandomWeightInEdge() {
        Random randGenerator = new Random();

        for(Edge e: edges) {
            e.weight = randGenerator.nextInt(edges.size());
        }
    }

    /**
     *  makeSet method
     */
    public void makeSet() {
        for(Vertex u: vertexes) {
            u.makeSet();
        }
    }

    /**
     * sort edge by weight method
     * @return  sorted edge list
     */
    public ArrayList<Edge> sortEdgeByWeight() {
        ArrayList<Edge> result = new ArrayList<Edge>();
        result.addAll(edges);
        // sort
        Collections.sort(result, new Edge.weightCompare());

        return result;
    }

    /**
     * find method
     * @param u: target vertex
     * @return u's giant parent(root)
     */
    public Vertex find(Vertex u) {
        if (u != u.parent) {    u.parent = find(u.parent);  }
        return u.parent;
    }

    /**
     * union method
     * @param u: edge's first vertex
     * @param v: edge's last vertex
     */
    public void union(Vertex u, Vertex v) {
        Vertex rootOfU = find(u);
        Vertex rootOfV = find(v);

        if (rootOfU == rootOfV)             return;
        if (rootOfU.rank > rootOfV.rank)    rootOfV.parent = rootOfU;
        else {
            rootOfU.parent = rootOfV;
            if (rootOfU.rank == rootOfV.rank)   rootOfV.rank = rootOfV.rank+1;
        }
    }
}
