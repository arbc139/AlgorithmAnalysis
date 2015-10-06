package dy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by DYKim on 15. 9. 27..
 */
public class DYMath {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static int roundToNearestPowerOfTwo(int n) {
        double log = Math.log(n)/Math.log(2); // log(n) by log2;
        return (int)Math.pow(2, Math.ceil(log));
    }

    public static Boolean compareDouble(double a, double b) {
        return Double.valueOf(a).equals(Double.valueOf(b));
    }
}
