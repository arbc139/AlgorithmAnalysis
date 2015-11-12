import dy.DYConsole;
import dy.DYFile;
import dy.DYTimeRecoder;
import powerset.PowerSet;
import tsp.CityDists;

import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
    static DYConsole console;
    static DYFile file;


    final static int MAX_VALUE = 1000000000;


    public static void main(String[] args) throws Exception {
        console = new DYConsole();
        file = new DYFile(5);
        console.setClassPrefix("Main");

        Main.console.consoleTurnOn = true;
        PowerSet.console.consoleTurnOn = true;
        CityDists.console.consoleTurnOn = true;

        // test, bug fix algorithm
        // testPlace();
        // execute algorithm
        executeAlgorithm();

        file.close();
    }

    public static void testPlace() throws Exception {
        console.setMethodPrefix("testPlace");

        // PDF example
        int[][] dist = {
                {-1, 7, -1, 6, -1, -1, 7, 10, -1, -1},
                {7, -1, 8, 5, -1, -1, 13, -1, -1, -1},
                {-1, 8, -1, 7, 6, 5, -1, -1, 10, -1},
                {6, 5, 7, -1, 4, -1, 8, 11, -1, -1},
                {-1, -1, 6, 4, -1, 5, -1, 10, 7, -1},
                {-1, -1, 5, -1, 5, -1, -1, -1, 6, 4},
                {7, 13, -1, 8, -1, -1, -1, 4, -1, -1},
                {10, -1, -1, 11, 10, -1, 4, -1, 12, -1},
                {-1, -1, 10, -1, 7, 6, -1, 12, -1, 7},
                {-1, -1, -1, -1, -1, 4, -1, -1, 7, -1}
        };

        int numberOfCity = 10;

        int result = TSP(numberOfCity, dist);
        console.println(result);

    }

    public static void executeAlgorithm() throws Exception {

        String testCaseStr = file.fin.readLine();
        int testCase = Integer.parseInt(testCaseStr);

        for(int i=0; i<testCase; i++) {
            DYTimeRecoder.startPoint();

            int numberOfCity = Integer.parseInt(file.fin.readLine());
            int[][] dist = getDist(numberOfCity);

            int result = TSP(numberOfCity, dist);

            console.setMethodPrefix("executeAlgorithm");
            console.println("result: " + result);
            file.fout.println(result);

            DYTimeRecoder.finishPoint();

            System.out.println("Running Time: " + DYTimeRecoder.runningTime());
        }

    }

    /*
        TSP Algorithm
     */
    /**
     * TSP Algorithm Method
     *
     * @param numberOfCity : no of City
     * @param dist         : distance information between 'city' and 'city'
     * @return optimal solution to travels each of other cites exactly once and returns to the 'city 1'
     * @throws Exception
     */
    public static int TSP(int numberOfCity, int[][] dist) throws Exception {
        /**
         *  <Pseudo code>
         *
         *      C({1}, 1) = 0
         *      for s=2 to n
         *          for all subsets S part of {1,2, ..., n} of size s and containing 1
         *              C(S,1) = infinite
         *              for all j in S, j!=1
         *                  C(S,j) = min{ C(S-{j},i) + Dij : i in S, i!=j }
         *
         *
         *      return min among j C({1,...,n},j) + Dj1
         *
         *  </Pseudo>
         */
        console.setMethodPrefix("TSP");

        // powerset initialized
        /*
            size 1: [1]
            size 2: [1,2], [1,3], [1,4], ..., [1,n]
            size 3: [1,2,3], [1,2,4], [1,2,5], ..., [1,n-1,n]
            ...
            size n: [1,2,3,...,n]
         */
        PowerSet powersets = new PowerSet(numberOfCity);
        console.println("make powerset completed");

        // City Distances information
        CityDists C = new CityDists();


        // C({1}, 1) = 0
        for (HashSet<Integer> S : powersets.getSetDictionary().get(1)) {
            C.addCityDist(S, 1, 0);
        }

        for (int size = 2; size < numberOfCity + 1; size++) { // size 2 to n
            HashSet<HashSet<Integer>> subsets = powersets.getSetDictionary().get(size);
            for (HashSet<Integer> S : subsets) { // all subsets in size including 'city 1'
                C.addCityDist(S, 1, MAX_VALUE); // C(S, 1) = infinite
                for (int j : S) {
                    if (j == 1) continue;
                    int minPath = MAX_VALUE;

                    for (int i : S) {
                        if (i == j) continue;
                        int localMinPath;
                        if (dist[i - 1][j - 1] == -1) localMinPath = MAX_VALUE;
                        else
                            localMinPath = C.getCityDist(powersets.findSubSetExcludeCity(size, S, j), i) + dist[i - 1][j - 1];
                        if (minPath > localMinPath)
                            minPath = localMinPath;
                    }

                    C.addCityDist(S, j, minPath);
                }
            }
        }

        // min{ among j,  C({1,...,n},j) + Dj1 }
        int finalMinPath = MAX_VALUE;
        for (int j = 2; j < numberOfCity + 1; j++) {
            for (HashSet<Integer> S : powersets.getSetDictionary().get(numberOfCity)) {
                int localMinPath;
                if (dist[j - 1][1 - 1] == -1) localMinPath = MAX_VALUE;
                else localMinPath = C.getCityDist(S, j) + dist[j - 1][1 - 1];
                if (finalMinPath > localMinPath)
                    finalMinPath = localMinPath;
            }
        }

        return finalMinPath;
    }

    /*
        Tool method
     */

    /**
     * getDist method
     * @param numberOfCity : no of City
     * @return dist information (2-d array) returned from input
     * @throws Exception
     */
    public static int[][] getDist(int numberOfCity) throws Exception {
        int[][] dist = new int[numberOfCity][numberOfCity];

        for(int i=0; i<numberOfCity; i++) {
            String rowStr = file.fin.readLine();
            dist[i] = splitRowString(rowStr, numberOfCity);
        }

        return dist;
    }

    /**
     * splitRowString method
     * @param rowStr
     * @param numberOfCity
     * @return
     */
    public static int[] splitRowString(String rowStr, int numberOfCity) {
        StringTokenizer st = new StringTokenizer(rowStr);

        int[] row = new int[numberOfCity];

        int index = 0;
        while(st.hasMoreTokens()) {
            row[index] = Integer.parseInt(st.nextToken());
            index++;
        }
        return row;
    }
}
