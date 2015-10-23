
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
        // execute algorithm!
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
            DYTimeRecoder.startPoint();

            String numberStr = file.fin.readLine();

            int[] getNumberOfVertexEdge = splitString(numberStr);
            int numberOfVertex = getNumberOfVertexEdge[0];
            int numberOfEdge = getNumberOfVertexEdge[1];

            karger(numberOfVertex, numberOfEdge);

            DYTimeRecoder.finishPoint();

            System.out.println("Running Time: " + DYTimeRecoder.runningTime());
        }
    }

    /**
     * karger algorithm
     * @param numberOfVertex    : number of vertex
     * @param numberOfEdge      : number of edge
     * @throws Exception        : for I/O statement
     */
    public static void karger(int numberOfVertex, int numberOfEdge) throws Exception {
        int finalMin = Integer.MAX_VALUE;       // final min cut in n^2 repetition

        Graph g = new Graph(numberOfVertex);    // Graph initializing (make vertex objects)

        for(int j=0; j<numberOfEdge; j++) {     // Edge initializing
            String edgeStr = file.fin.readLine();

            int[] fromTo = splitString(edgeStr);
            int u = fromTo[0];
            int v = fromTo[1];
            g.addEdge(u, v);
        }

        // Do Karger Algorithm (repetition n^2)
        for(int i=0; i < numberOfVertex * numberOfVertex; i++) {

            // assign random weight at each edge
            g.assignRandomWeightInEdge();

            // Do Kruskal Algorithm
            console.println("\nKARGER::kruskal algorithm started");
            int tempMin = kruskal(g);

            // If temporary min cut by kruskal is smaller than prior???
            if (tempMin < finalMin) {
                finalMin = tempMin;
                console.println("KARGER::renewed tempMin: " + tempMin);
            }

            // intermediate point in loop sequence (for error detecting)
            // if(i % (numberOfVertex*numberOfVertex/10) == 0) System.out.println("KARGER::rest place! please take a rest, computer!");
        }

        console.println("\nKARGER::final result finalMin: " + finalMin);

        file.fout.println(finalMin);
    }

    /**
     * kruskal algorithm
     * @param G : Graph input
     * @return  : cut which divide graph to 2 component
     */
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

        int countOfCut = 0;  // count cut times

        G.makeSet();    // initial vertexes.parent point to self
        console.println("\nKRUSKAL::makeSet operated");
        G.print();

        ArrayList<Edge> result = new ArrayList<Edge>(); // MST result (X={})

        for(Edge e: G.sortEdgeByWeight()) {
            // e = {u, v}
            if (G.find(e.GetU()) != G.find(e.GetV())) { // is not same tree between u and v?
                result.add(e);  // link U, V
                if (result.size() == G.GetVertexes().size()-1) {    // if result is 1 component (fully completed MST)
                    result.remove(result.size()-1); // remove last MST edge
                    countOfCut++;   // MincutCount++
                }
                else
                    G.union(e.GetU(), e.GetV());    // union U and V
            }
        }

        console.println("\nKRUSKAL::find, union operated");
        G.print();


        console.println("\nKRUSKAL::result");
        // System.out.println(result);

        return countOfCut;
    }

    /*
        Tool methods
     */
    /**
     * splitString method
     * @param numberStr : string formed "a b" (a, b is integer)
     * @return int array which contain value a and b (int[0]=a, int[1]=b)
     */
    public static int[] splitString(String numberStr) {
        StringTokenizer st = new StringTokenizer(numberStr);

        int firstItem = Integer.parseInt(st.nextToken());
        int secondItem = Integer.parseInt(st.nextToken());

        int[] result = new int[2];
        result[0] = firstItem;  result[1] = secondItem;
        return result;
    }
}
