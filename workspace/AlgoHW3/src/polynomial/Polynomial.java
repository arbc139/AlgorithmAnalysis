package polynomial;

import java.util.ArrayList;
import dy.DYMath;
import complex.*;

/**
 * Created by DYKim on 15. 9. 27..
 */
public class Polynomial {
    int degree;     // original degree
    Coeff coeff;    // Full Stack coefficients

    /*
        Constructor
     */
    public Polynomial(int degree, ArrayList<Integer> coeffList) {
        this.degree = degree;
        int coeffSize = DYMath.roundToNearestPowerOfTwo(degree+1);
        this.coeff = new Coeff(coeffSize, coeffList);
    }

    Polynomial(int degree, Coeff coeff) {
        this.degree = degree;
        this.coeff = coeff;
    }

    /*
        Getter Method
     */
    public ArrayList<Integer> GetCoefList() {
        return coeff.coeffList;
    }

    public ArrayList<Complex> GetResult(Complex value) {
        ArrayList<Complex> result = new ArrayList<Complex>();

        for(int i=0; i<this.degree+1; i++) {
            Complex complexValue = value.inv(i);
            result.add( complexValue.mul(coeff.coeffList.get(i)) );
        }

        return result;
    }
    /*
    public Polynomial GetOddPolynomial() {
        Coeff oddCoeff = this.coeff.GetOddCoeff();
        int oddDegree;
        if (degree%2 == 0)  oddDegree = degree/2;
        else                oddDegree = degree/2 + 1;
        return new Polynomial(oddDegree, oddCoeff);
    }

    public Polynomial GetEvenPolynomial() {
        Coeff evenCoeff = this.coeff.GetEvenCoeff();
        int evenDegree = degree/2;

        return new Polynomial(evenDegree, evenCoeff);
    }
    */

}
