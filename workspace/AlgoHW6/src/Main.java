import lp.LPManager;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        LPManager.console.consoleTurnOn = true;

        testPlace();
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
    /**
     * <The Simplex Algorithm Pseudo code>
     *
     * let v be any vertex of the feasible region
     *
     * while there is a neighbor v' of v with better objective value
     *  set v = v'
     *
     *
     * 1. Check if the current vertex is optimal. if so, halt
     * 2. Determine where to move next.
     */
}
