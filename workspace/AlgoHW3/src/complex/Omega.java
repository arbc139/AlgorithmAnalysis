package complex;

import dy.DYConsole;
import dy.DYMath;

/**
 * Created by DYKim on 15. 9. 27..
 */
public class Omega {
    final private static double PI = 3.141592;
    public static DYConsole console = new DYConsole();

    int n;

    /*
        Constructor
     */
    public Omega(int n) {
        this.n = DYMath.roundToNearestPowerOfTwo(n);
        console.println(""+this.n);
    }

    /*
        Getter Method
     */
    public Complex GetComplexValue() {
        double realPart = Math.cos(2*PI / n);
        double imaginaryPart = Math.sin(2*PI / n);

        return new Complex(realPart, imaginaryPart);
    }

    /*
        Tool Method
     */
    public Complex omegaInv(int n) {
        return this.GetComplexValue().inv(n);
    }
}
