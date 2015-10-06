package polynomial;

import dy.DYMath;
import java.util.ArrayList;

/**
 * Created by DYKim on 15. 9. 27..
 */
public class Coeff {
    int degree; // powered degree (original degree: 5 --> powered degree: 7)
    int size;   // binary size (2, 4, 8, 16, 32, 64)
    ArrayList<Integer> coeffList;

    /*
        Constructor
        size --> 2,4,8,16,32,64
        coefList --> polynomial's coef list
     */
    public Coeff(int size, ArrayList<Integer> coefList) {
        this.degree = size-1;
        this.size = size;
        this.coeffList = new ArrayList<Integer>();

        for(int i=0; i<this.size; i++) {
            if(i<coefList.size())   {   this.coeffList.add(coefList.get(i)); }
            else                    {   this.coeffList.add(0);   }
        }
    }

    /*
        Getter Method
     */
    public ArrayList<Integer> GetCoeffList() {
        return coeffList;
    }

    public Coeff GetOddCoeff() {
        ArrayList<Integer> oddCoefList = new ArrayList<Integer>();
        for(int i=0; i<this.size; i++) {
            if(i%2 == 1) {
                oddCoefList.add(this.coeffList.get(i));
            }
        }

        return new Coeff(this.size/2, oddCoefList);
    }
    /*
    public Coeff GetEvenCoeff() {
        ArrayList<Integer> evenCoefList = new ArrayList<Integer>();
        for(int i=0; i<this.size; i++) {
            if(i%2 == 0) {
                evenCoefList.add(this.coeffList.get(i));
            }
        }

        return new Coeff(this.size/2, evenCoefList);
    }
    */
}
