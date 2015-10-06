
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.StringTokenizer;

import graph.*;
import dy.*;

public class Main {
    static DYFile file;
    static DYConsole console;

    public static void main(String[] args) throws Exception {
        file = new DYFile(4);
        console = new DYConsole();

        console.consoleTurnOn = true;
        Graph.console.consoleTurnOn = true;

        System.out.println("Hello World!");

        testPlace();
    }

    public static void testPlace() throws Exception {
        String numberStr = file.fin.readLine();

        int[] vertexEdge = splitString(numberStr);
        int numberOfVertex = vertexEdge[0];
        int numberOfEdge = vertexEdge[1];

        Graph g = new Graph(numberOfVertex);

        for(int i=0; i<numberOfEdge; i++) {
            String edgeStr = file.fin.readLine();

            int[] edgeVertexes = splitString(edgeStr);
            int u = edgeVertexes[0];
            int v = edgeVertexes[1];
            g.addEdge(u, v);
        }

        console.println("\nTESTPLACE::File input inserted");
        g.print();

        console.println("\nTESTPLACE::random weight in edge");
        g.assignRandomWeightInEdge();
        g.print();

        console.println("\nTESTPLACE::kruskal algorithm started");
        kruskal(g);
    }

    public static int[] splitString(String numberStr) {
        StringTokenizer st = new StringTokenizer(numberStr);

        int firstItem = Integer.parseInt(st.nextToken());
        int secondItem = Integer.parseInt(st.nextToken());

        int[] result = new int[2];
        result[0] = firstItem;  result[1] = secondItem;
        return result;
    }

    public static void kruskal(Graph G) {
        /**
         *  <Sudo Code>
         *  for all u in V
         *      makeSet(u)
         *  result = {}
         *  sort the edges E by weight (ascending-order)
         *
         *  for all edges {u,v} in E, in increasing order of weight
         *      if find(u) != find(v)
         *          add edge {u, v} to result
         *          union(u,v)
         *  </Sudo>
         */
        console.println("\nKRUSKAL::kruskal algorithm started");

        G.makeSet();
        console.println("\nKRUSKAL::makeSet operated");
        G.print();

        ArrayList<Edge> result = new ArrayList<Edge>();

        for(Edge e: G.sortEdgeByWeight()) {
            // e = {u, v}
            if (G.find(e.GetU()) != G.find(e.GetV())) { // is same tree between u and v?
                result.add(e);
                G.union(e.GetU(), e.GetV());
            }
        }


        console.println("\nKRUSKAL::find, union operated");
        G.print();


        console.println("\nKRUSKAL::result");
        System.out.println(result);
    }
}
