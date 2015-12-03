import dy.DYFile;
import dy.DYTimeRecoder;
import lp.LPManager;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static DYFile file;

    public static void main(String[] args) throws Exception {
        file = new DYFile(6);
        LPManager.console.consoleTurnOn = false;

        // testPlace();
        executeAlgorithm();

        file.close();
    }

    public static void testPlace() {
        int numberOfVariables = 2;
        int numberOfConstraints = 2;

        String objFunction = "2 5";
        ArrayList<String> constraints = new ArrayList<String>();
        constraints.add("2 -1 4");
        constraints.add("-1 2 9");
        constraints.add("-1 2 3");

        LPManager manager = new LPManager(numberOfVariables, numberOfConstraints, objFunction, constraints);

        manager.printInsideData();

        ArrayList<Double> result = manager.linearProgramming();
        System.out.println(result);
    }

    public static void executeAlgorithm() throws Exception {
        int numberOfTestCases = Integer.parseInt(file.fin.readLine());

        for(int i=0; i<numberOfTestCases; i++) {
            DYTimeRecoder.startPoint();

            //System.out.println(i);

            int[] varAndConst = splitString(file.fin.readLine());
            int numberOfVariables = varAndConst[0];
            int numberOfConstraints = varAndConst[1];

            //System.out.println("variables: " + numberOfVariables + "constraints: " + numberOfConstraints);

            String objFunction = file.fin.readLine();
            ArrayList<String> constraints = new ArrayList<String>();
            for(int j=0; j<numberOfConstraints; j++) {
                constraints.add(file.fin.readLine());
            }

            LPManager manager = new LPManager(numberOfVariables, numberOfConstraints, objFunction, constraints);

            ArrayList<Double> result = manager.linearProgramming();
            //System.out.println(result);
            if (result == null) {
                file.fout.println("Unbounded");
            }
            else {
                for(double objValue: result) {
                    double roundedValue = (double)Math.round(objValue * 100) / 100.0;
                    file.fout.print(roundedValue + " ");
                }
                file.fout.println("");
            }

            DYTimeRecoder.finishPoint();

            System.out.println("running time: "+DYTimeRecoder.runningTime());
        }
    }

    public static int[] splitString(String str) {
        StringTokenizer st = new StringTokenizer(str);

        int[] result = new int[2];

        result[0] = Integer.parseInt(st.nextToken());
        result[1] = Integer.parseInt(st.nextToken());

        return result;
    }
}
