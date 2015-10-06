
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

        console.consoleTurnOn = false;
        Graph.console.consoleTurnOn = false;
        Graph.printMode = false;

        //testPlace();
        executeAlgorithm();

        file.close();
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
        int result = kruskal(g);
        console.println("mincut count: " + result);
    }

    /*
        Executed(Used) Algorithms
     */
    public static void executeAlgorithm() throws Exception {
        String testCaseStr = file.fin.readLine();
        int testCase = Integer.parseInt(testCaseStr);

        for(int i=0; i<testCase; i++) {
            String numberStr = file.fin.readLine();

            int[] getNumberOfVertexEdge = splitString(numberStr);
            int numberOfVertex = getNumberOfVertexEdge[0];
            int numberOfEdge = getNumberOfVertexEdge[1];

            karger(numberOfVertex, numberOfEdge);
        }
    }

    /**
     * karger algorithm
     * @param numberOfVertex
     * @param numberOfEdge
     * @throws Exception
     */
    public static void karger(int numberOfVertex, int numberOfEdge) throws Exception {
        int finalMin = Integer.MAX_VALUE;

        Graph g = new Graph(numberOfVertex);

        for(int j=0; j<numberOfEdge; j++) {
            String edgeStr = file.fin.readLine();

            int[] fromTo = splitString(edgeStr);
            int u = fromTo[0];
            int v = fromTo[1];
            g.addEdge(u, v);
        }

        for(int i=0; i < numberOfVertex * numberOfVertex; i++) {

            g.assignRandomWeightInEdge();

            int tempMin = kruskal(g);
            if (tempMin < finalMin) {
                console.println("\nKARGER::kruskal algorithm started");
                finalMin = tempMin;
                console.println("KARGER::renewed tempMin: " + tempMin);
            }

            if(i % (numberOfVertex*numberOfVertex/10) == 0)
                System.out.println("KARGER::rest place! please take a rest, computer!");
        }

        console.println("\nKARGER::final result finalMin: " + finalMin);

        file.fout.println(finalMin);
    }

    public static int[] splitString(String numberStr) {
        StringTokenizer st = new StringTokenizer(numberStr);

        int firstItem = Integer.parseInt(st.nextToken());
        int secondItem = Integer.parseInt(st.nextToken());

        int[] result = new int[2];
        result[0] = firstItem;  result[1] = secondItem;
        return result;
    }

    public static int kruskal(Graph G) {
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

        int countOfMinCut = 0;

        G.makeSet();
        console.println("\nKRUSKAL::makeSet operated");
        G.print();

        ArrayList<Edge> result = new ArrayList<Edge>();

        for(Edge e: G.sortEdgeByWeight()) {
            // e = {u, v}
            if (G.find(e.GetU()) != G.find(e.GetV())) { // is same tree between u and v?
                result.add(e);
                if (result.size() == G.GetVertexes().size()-1) {
                    // remove last MST edge
                    result.remove(result.size()-1);
                    // MincutCount++
                    countOfMinCut++;
                }
                else
                    G.union(e.GetU(), e.GetV());
            }
        }

        console.println("\nKRUSKAL::find, union operated");
        G.print();


        console.println("\nKRUSKAL::result");
        // System.out.println(result);

        return countOfMinCut;
    }
}
