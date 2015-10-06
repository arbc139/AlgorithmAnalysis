package complex;

import dy.DYConsole;
import dy.DYMath;
import dy.DYString;

/**
 * Created by DYKim on 15. 9. 27..
 */
public class Complex {
    public static DYConsole console = new DYConsole();

    private double real;
    private double imaginary;

    /*
        Constructor
     */
    Complex() {
        this.real = 0;
        this.imaginary = 0;
    }

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /*
        Getter Method
     */
    public double GetReal() {
        //if (DYMath.compareDouble(this.real, 0)) return DYMath.round(0.000000, 6);
        return DYMath.round(this.real, 6);
    }

    public double GetImaginary() {
        //if (DYMath.compareDouble(this.imaginary, 0)) return DYMath.round(0.000000, 6);
        return DYMath.round(this.imaginary, 6);
    }


    /*
        Operation Method
        - '+', '-', '*', '^' operation between complex and complex
     */
    public Complex add(Complex b) {
        return new Complex(this.real + b.real, this.imaginary + b.imaginary);
    }

    public Complex sub(Complex b) {
        return new Complex(this.real - b.real, this.imaginary - b.imaginary);
    }

    public Complex mul(Complex b) {
        return new Complex(this.real * b.real - this.imaginary * b.imaginary, this.real*b.imaginary + this.imaginary*b.real);
    }

    public Complex inv(int n) {
        if(n == 0)      { return new Complex(1, 0); }
        else if(n == 1) { return this; }
        Complex result = new Complex(this.real, this.imaginary);
        for(int i=1; i<n; i++) {
            result = result.mul(this);
        }
        return result;
    }
    // decimal multiplication
    public Complex mul(double value) {
        return new Complex(this.real*value, this.imaginary*value);
    }

    /*
        Tool Method
     */
    public String complexToStr() {
        return DYString.formatDoubleToSix(this.GetReal()) + " " + DYString.formatDoubleToSix(this.GetImaginary());
    }

    // need to right
    public Boolean isEqual(double real, double imaginary) {
        if (DYMath.round(this.real, 5) == DYMath.round(real, 5) && DYMath.round(this.imaginary, 5) == DYMath.round(imaginary, 5)) {
            return true;
        }
        else {  return false;   }
    }
}
