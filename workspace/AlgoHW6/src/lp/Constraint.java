package lp;

import dy.DYConsole;

import java.util.ArrayList;

/**
 * Created by DYKim on 2015. 11. 13..
 */
public class Constraint extends Expression {
    double maxConst;

    Constraint(int numberOfVariables, ArrayList<Double> coeffs, double maxConst) {
        super(numberOfVariables, coeffs);
        this.maxConst = maxConst;
    }

    double getTightnessValue(int targetVariableIndex) {
        double targetCoeffValue = coeffs.get(targetVariableIndex);
        if (targetCoeffValue <= 0)
            return Double.MAX_VALUE;

        else
            return maxConst / targetCoeffValue;
    }

    // case "Aw * X <= Bw (w!=tight)"
    Constraint changeCoordinate(int targetVariableIndex, Constraint tightConstraint) {
        ArrayList<Double> newCoeffs = new ArrayList<Double>();
        ArrayList<Double> tightCoeffs = tightConstraint.coeffs;

        int target = targetVariableIndex;

        for(int i=0; i<coeffs.size(); i++) {
            double coeff;
            if (i==target)
                coeff =
                        -1 * coeffs.get(target);
            else
                coeff =
                    tightCoeffs.get(target) * coeffs.get(i) - tightCoeffs.get(i) * coeffs.get(target);

            newCoeffs.add(coeff);
        }

        double newMaxConst =
                tightCoeffs.get(target) * maxConst - coeffs.get(target) * tightConstraint.maxConst;

        Constraint newConstraint = new Constraint(numberOfVariables, newCoeffs, newMaxConst);

        return newConstraint;
    }

    public void printInsideData(DYConsole console) {
        console.println("constraint: (" + coeffs + ", maxConst: " + maxConst + ")");
    }
}