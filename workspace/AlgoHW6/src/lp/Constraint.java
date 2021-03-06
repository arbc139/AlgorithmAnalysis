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

    /**
     * getTightnessValue method
     * @param targetVariableIndex : largest coefficient variable in objective function
     * @return tight value on this constraint
     */
    double getTightnessValue(int targetVariableIndex) {
        double targetCoeffValue = coeffs.get(targetVariableIndex);
        //if (!(targetCoeffValue <= 0 && maxConst <= 0) && (targetCoeffValue <= 0 || maxConst <= 0))
        if (targetCoeffValue <= 0)
            return Double.MAX_VALUE;

        else
            return maxConst / targetCoeffValue;
    }

    /**
     * changeCoordinate method
     * @param targetVariableIndex : largest coefficient variable in objective function
     * @param tightConstraint : selected constraint by optimize conditions
     * @return changed coordinate constraint
     *
     * case "Aw * X <= Bw (w!=tight constraint)"
     */
    Constraint changeCoordinate(int targetVariableIndex, Constraint tightConstraint) {
        ArrayList<Double> newCoeffs = new ArrayList<Double>();
        ArrayList<Double> tightCoeffs = tightConstraint.coeffs;

        int target = targetVariableIndex;

        for(int i=0; i<coeffs.size(); i++) {
            double coeff;
            if (i==target)
                coeff =
                        -1 * coeffs.get(target) / tightCoeffs.get(target);
            else
                coeff = // tightCoeffs.get(target) *
                    coeffs.get(i) - tightCoeffs.get(i) * coeffs.get(target) / tightCoeffs.get(target);



            newCoeffs.add(coeff);
        }

        double newMaxConst = // tightCoeffs.get(target) *
                        maxConst - coeffs.get(target) * tightConstraint.maxConst / tightCoeffs.get(target);

        Constraint newConstraint = new Constraint(numberOfVariables, newCoeffs, newMaxConst);

        return newConstraint;
    }

    public void printInsideData(DYConsole console) {
        console.println("constraint: (" + coeffs + ", maxConst: " + maxConst + ")");
    }
}