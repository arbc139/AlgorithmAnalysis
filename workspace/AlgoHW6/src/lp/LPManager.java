package lp;

import java.util.ArrayList;
import java.util.HashSet;

import dy.DYConsole;
import javafx.util.Pair;

/**
 * Created by DYKim on 2015. 11. 13..
 */
public class LPManager {
    public static DYConsole console = new DYConsole();

    int numberOfVariables;
    int numberOfPureConstraints;
    ObjFunc objFunc;
    ArrayList<Constraint> constraints;

    public LPManager(int numberOfVariables, int numberOfPureConstraints, String objFunc, ArrayList<String> constraints) {
        console.setClassPrefix("LPManager");
        console.setMethodPrefix("Constructor");

        // Parse String to objFunc
        this.objFunc = new ObjFunc(numberOfVariables, Expression.splitExpressionString(objFunc));
        this.constraints = new ArrayList<Constraint>();

        // Parse Constraints
        for(String constraint: constraints) {
            ArrayList<Double> expr = Expression.splitExpressionString(constraint);
            double maxConst = expr.get(expr.size() - 1);
            expr.remove(expr.size()-1);
            console.println("expr: " + expr);

            this.constraints.add(new Constraint(numberOfVariables, expr, maxConst));
        }

        this.numberOfVariables = numberOfVariables;
        this.numberOfPureConstraints = numberOfPureConstraints;
    }

    /**
     * linearProgramming method
     * @return object values in sequence. if unbounded, return null.
     *
     * execute linear programming in this case class.
     * case class' data is not changed. because of using local data.
     */
    public ArrayList<Double> linearProgramming() {
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
        console.setMethodPrefix("linearProgramming");
        ArrayList<Double> solution = new ArrayList<Double>();

        if (this.isOptimal()) {
            solution.add(0.0);
            return solution;
        }

        // make objFunc, constraints clone
        ObjFunc localObjFunc = new ObjFunc(numberOfVariables, new ArrayList<Double>(this.objFunc.coeffs));
        ArrayList<Constraint> localConstraints = new ArrayList<Constraint>();
        for(Constraint constraint: constraints) {
            localConstraints.add(
                    new Constraint(numberOfVariables, new ArrayList<Double>(constraint.coeffs), constraint.maxConst)
            );
        }

        solution.add(localObjFunc.objValue);

        while(true) {
            // change coordinate
            boolean isUnbound = changeToLocalOptimumCoor(localObjFunc, localConstraints);

            if (isUnbound == false)
                return null;


            console.setMethodPrefix("linearProgramming");
            console.println("");
            localObjFunc.printInsideData(console);
            for(Constraint constraint: localConstraints) {
                constraint.printInsideData(console);
            }

            solution.add(localObjFunc.objValue);

            if(localObjFunc.isOptimal()) {
                return solution;
            }
        }

    }

    /**
     * changeToLocalOptimumCoor method
     * @param localObjFunc objective function to change better case coordinate. which is local(base not changed)
     * @param localConstraints constraints to change better case coordinate. which is local(base not changed)
     * @return if unbounded, return false.
     *
     * change coordinate
     */
    public boolean changeToLocalOptimumCoor(ObjFunc localObjFunc, ArrayList<Constraint> localConstraints) {
        console.setMethodPrefix("changeToLocalOptimumCoor");

        // Pair's key : largest coefficient index (variable)
        // Pair's value : largest coefficient value;
        Pair<Integer, Double> largestCoeff = localObjFunc.getLargestCoeff();
        int largestCoeffVariableIndex = largestCoeff.getKey();

        console.println("largestCoeff(index, value): " + largestCoeff);
/*
        // remove largest coefficient variable from origin
        origin.remove(numberOfPureConstraints+largestCoeffVariableIndex);
*/
        // test if unbounded
        boolean testResult = false;
        for(Constraint constraint : localConstraints) {
            if(constraint.getTightnessValue(largestCoeffVariableIndex) != Double.MAX_VALUE) {
                testResult = true;
                break;
            }
        }
        // unbounded case.
        if (testResult == false) return testResult;


        // find tightest constraint
        int tightConstraintIndex=0;
        Constraint tightConstraint=localConstraints.get(0);
        for(int i=1; i<localConstraints.size(); i++) {
            Constraint compareConstraint = localConstraints.get(i);

            // if local's tightness value is smaller than past's tightness value
            if (compareConstraint.getTightnessValue(largestCoeffVariableIndex) < tightConstraint.getTightnessValue(largestCoeffVariableIndex)) {
                tightConstraintIndex = i;
                tightConstraint = compareConstraint;
            }
        }
        console.println("tight constraint index: " + tightConstraintIndex);
        tightConstraint.printInsideData(console);

        // Move Coordinate x to y (change obj func, constraints)
        // change obj func
        ObjFunc changedObjFunc = localObjFunc.changeCoordinate(largestCoeffVariableIndex, tightConstraint);

        // change constraints
        ArrayList<Constraint> changedConstraints = new ArrayList<Constraint>();
        for(int i=0; i<numberOfPureConstraints; i++) {
            // x(largest selected) >= 0 case
            if (i == tightConstraintIndex) {
                ArrayList<Double> coeffs = new ArrayList<Double>(localConstraints.get(tightConstraintIndex).coeffs);
                double maxConst = localConstraints.get(tightConstraintIndex).maxConst;

                coeffs.set(largestCoeffVariableIndex, 1.0);

                changedConstraints.add(new Constraint(numberOfVariables, coeffs, maxConst));
            }
            else
                changedConstraints.add(localConstraints.get(i).changeCoordinate(largestCoeffVariableIndex, tightConstraint));
        }

        localObjFunc.objValue = changedObjFunc.objValue;
        localObjFunc.coeffs = new ArrayList<Double>(changedObjFunc.coeffs);

        for(int i=0; i<numberOfPureConstraints; i++) {
            localConstraints.get(i).maxConst = changedConstraints.get(i).maxConst;
            localConstraints.get(i).coeffs = new ArrayList<Double>(changedConstraints.get(i).coeffs);
        }

        return testResult;
    }

    public boolean isOptimal() {
        return objFunc.isOptimal();
    }

    public void printInsideData() {
        console.setMethodPrefix("printInsideData");
        console.println("LPManager Datas");
        console.println("numberOfVariables : " + numberOfVariables);
        console.println("numberOfPureConstraints : " + numberOfPureConstraints);
        objFunc.printInsideData(console);
        for(Constraint constraint: constraints) {
            constraint.printInsideData(console);
        }
    }
}
