package graph;

import java.util.Comparator;

/**
 * Created by DYKim on 15. 10. 6..
 */
public class Edge {
    Vertex u;
    Vertex v;
    int weight;

    public Edge(Vertex u, Vertex v) {
        this.u = u;
        this.v = v;
        this.weight = -1;
    }

    /*
        Getter
     */
    public Vertex GetU() {  return u;   }
    public Vertex GetV() {  return v;   }

    /*
        Comparator
     */
    public static class weightCompare implements Comparator<Edge> {
        // sorting edge to weight
        @Override
        public int compare(Edge e1, Edge e2) {
            // TODO Auto-generated method stub
            if(e1.weight < e2.weight) return -1;
            else if(e1.weight > e2.weight) return 1;
            else return 0;
        }
    }

    /*
        toString method
     */
    @Override
    public String toString() {
        String str = "(" + u.number + "," + v.number + "," + weight + ")";
        return str;
    }
}
