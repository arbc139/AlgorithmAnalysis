package main;

import java.util.ArrayList;
import java.util.StringTokenizer;
import complex.*;
import dy.*;
import polynomial.*;

/**
 * Created by DYKim on 15. 9. 25..
 */
public class Main {
    static DYConsole console;
    static DYFile file;

    public static void main(String args[]) throws Exception {
        file = new DYFile(3);       // Homework number 3
        console = new DYConsole();

        console.consoleTurnOn = false;
        Omega.console.consoleTurnOn = false;
        Complex.console.consoleTurnOn = false;

        executeAlgorithm();

        file.close();
    }
    /*
        Algorithm Methods
     */
    /**
     *  executeAlgorithm method
     */
    public static void executeAlgorithm() throws Exception {
        String numberStr = file.fin.readLine();
        if(numberStr==null) throw new Exception();	// no input --> exception

        // parsing number of testcase.
        int numberOfTestCase = Integer.parseInt(numberStr);

        for(int i=0; i<numberOfTestCase; i++) {
            DYTimeRecoder.startPoint();
            String inputStr = file.fin.readLine();
            ArrayList<Integer> inputList = makeCoeffList(inputStr);
            console.println("" + inputList);

            // execute algorithm per test case
            file.fout.println("#" + (i+1));
            findFFTResult(inputList);
            DYTimeRecoder.finishPoint();
            System.out.println("Running Time: " + DYTimeRecoder.runningTime());
        }
    }

    /**
     * makeCoeffList method
        - make coefficient list from input string
     * @param inputStr : file input(String)
     * @return coefficient list
     */
    public static ArrayList<Integer> makeCoeffList(String inputStr) {
        StringTokenizer st = new StringTokenizer(inputStr);		// split lines
        ArrayList<Integer> result = new ArrayList<Integer>();
        while(st.hasMoreTokens()) {
            result.add(Integer.parseInt(st.nextToken()));
        }
        return result;
    }

    /**
     * findFFTResult method
        - execute FFT algorithm and print FFT algorithm's result
     * @param inputList : file input(String)
     */
    public static void findFFTResult(ArrayList<Integer> inputList) {
        int degree = inputList.get(0);
        inputList.remove(0);

        Omega w = new Omega(degree+1);

        for (Complex comp : FFT(inputList, w.GetComplexValue(), DYMath.roundToNearestPowerOfTwo(degree+1))) {
            file.fout.println(comp.complexToStr());
        }
    }

    /**
     * FFT Algorithm
     * @param a : coefficient array list
     * @param w : Omega value (set by degree)
     * @param n : full binary coefficeint array count ([1,2]-->2, [3,4]-->4, [5,6,7,8]-->8, [9,10,...,15]-->16, ...)
     * @return A(w) value
     */
    public static ArrayList<Complex> FFT(ArrayList<Integer> a, Complex w, int n) {
        Polynomial A = new Polynomial(a.size()-1, a);

        // if w == 1 + 0i
        if(n==1) {
            return A.GetResult(w);  // return A(w)
        }
        int oddN, evenN;            // divide n by 2
        if(n%2 == 0) oddN = n/2;    // if n==6 --> oddN = 3(1,3,5)
        else oddN = n/2+1;          // if n==7 --> oddN = 4(1,3,5,7)
        evenN = n/2;

        // recursive FFT by divide 2!
        ArrayList<Complex> evenS = FFT(GetEvenArray(A.GetCoefList()), w.inv(2), evenN);
        ArrayList<Complex> oddS = FFT(GetOddArray(A.GetCoefList()), w.inv(2), oddN);

        ArrayList<Complex> resultFirstHalf = new ArrayList<Complex>();      // Wj
        ArrayList<Complex> resultSecondHalf = new ArrayList<Complex>();     // Wj+n/2

        for(int j=0; j<n/2; j++) {
            Complex Sj = evenS.get(j);
            Complex SjDot = oddS.get(j);
            Complex Wj = w.inv(j);

            resultFirstHalf.add( Sj.add( Wj.mul(SjDot) ) );
            resultSecondHalf.add( Sj.sub( Wj.mul(SjDot) ) );
        }

        ArrayList<Complex> result = new ArrayList<Complex>();
        result.addAll(resultFirstHalf);
        result.addAll(resultSecondHalf);

        return result;
    }


    /*
        Tool Methods
     */
    public static ArrayList<Integer> GetEvenArray(ArrayList<Integer> a) {
        ArrayList<Integer> evenA = new ArrayList<Integer>();
        for(int i=0; i<a.size(); i++) {
            if(i%2 == 0) {
                evenA.add(a.get(i));
            }
        }
        return evenA;
    }

    public static ArrayList<Integer> GetOddArray(ArrayList<Integer> a) {
        ArrayList<Integer> oddA = new ArrayList<Integer>();
        for(int i=0; i<a.size(); i++) {
            if(i%2 == 1) {
                oddA.add(a.get(i));
            }
        }
        return oddA;
    }
}