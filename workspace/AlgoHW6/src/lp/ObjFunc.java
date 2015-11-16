package lp;

import dy.DYConsole;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by DYKim on 2015. 11. 13..
 */
public class ObjFunc extends Expression{
    double objValue;

    ObjFunc(int numberOfVariables, ArrayList<Double> coeffs) {
        super(numberOfVariables, coeffs);
        this.objValue = 0;
    }

    Pair<Integer, Double> getLargestCoeff() {
        int largestCoeffIndex = 0;
        double largestCoeffValue = coeffs.get(0);
        for(int i=0; i<coeffs.size(); i++) {
            if (largestCoeffValue < coeffs.get(i)) {
                largestCoeffIndex = i;
                largestCoeffValue = coeffs.get(i);
            }
        }

        return new Pair<Integer, Double>(largestCoeffIndex,largestCoeffValue);
    }

    /**
     * changeCoordinate method
     * @param targetVariableIndex : largest coefficient variable in objective function
     * @param tightConstraint : selected constraint by optimize conditions
     * @return changed coordinate objective function
     */
    ObjFunc changeCoordinate(int targetVariableIndex, Constraint tightConstraint) {
        ArrayList<Double> newCoeffs = new ArrayList<Double>();
        ArrayList<Double> tightCoeffs = tightConstraint.coeffs;

        int targetIndex = targetVariableIndex;

        for(int i=0; i<coeffs.size(); i++) {
            if (i == targetIndex)
                newCoeffs.add(
                        -1 * (  coeffs.get(i)
                                / tightCoeffs.get(i) )
                );
            else
                newCoeffs.add(
                        coeffs.get(i)
                        - ( tightCoeffs.get(i)
                            * coeffs.get(targetIndex)
                            / tightCoeffs.get(targetIndex)   )
                );
        }

        double newObjValue = coeffs.get(targetIndex)
                            * tightConstraint.maxConst
                            / tightCoeffs.get(targetIndex);

        ObjFunc newObjFunc = new ObjFunc(numberOfVariables, newCoeffs);
        newObjFunc.objValue = objValue + newObjValue;

        return newObjFunc;
    }

    /**
     * isOptimal method
     * @return true if all coefficients is negative in objective function
     */
    boolean isOptimal() {
        for(double coeff: coeffs) {
            if (coeff >= 0)
                return false;
        }

        return true;
    }

    public void printInsideData(DYConsole console) {
        console.println("objFunc : (" + coeffs + ", objValue: " + objValue + ")");
    }
}
