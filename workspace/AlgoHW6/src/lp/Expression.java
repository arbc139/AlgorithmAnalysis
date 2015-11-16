package lp;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by DYKim on 2015. 11. 13..
 */
public class Expression {
    int numberOfVariables;
    ArrayList<Double> coeffs;

    Expression(int numberOfVariables, ArrayList<Double> coeffs) {
        this.numberOfVariables = numberOfVariables;
        this.coeffs = coeffs;
    }

    /**
     * splitRowString method
     * @param str
     * @return
     */
    public static ArrayList<Double> splitExpressionString(String str) {
        StringTokenizer st = new StringTokenizer(str);

        ArrayList<Double> expr = new ArrayList<Double>();

        while(st.hasMoreTokens()) {
            expr.add((double)Integer.parseInt(st.nextToken()));
        }
        return expr;
    }

    public static ArrayList<Double> makeVariableConstraint(int target, int numberOfVariables) {
        ArrayList<Double> expr = new ArrayList<Double>();

        for(int i=0; i<numberOfVariables; i++) {
            if(i==target) {
                expr.add(1.0);
            }
            expr.add(0.0);
        }

        return expr;
    }
}
