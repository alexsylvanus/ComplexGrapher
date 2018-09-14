/**
 * 
 */
package complex.math;

/**
 * @author alexs
 *
 */

public class Complex {
	// Declare and define constants
	public static float pi = (float)Math.PI;
	public static float e = (float)Math.E;
	// private static final int default_weight = 10;
	
	// Declare class member variables
	float real;
	float imaginary;
	
	// Constructors
	public Complex() { // Default Constructor
		real = 1;
		imaginary = 0;
	}
	public Complex(float R, float I) {
		real = R;
		imaginary = I;
	}
	
	// String expressions
	public void print() {
		System.out.print(real);
		System.out.print("+");
		System.out.print(imaginary);
		System.out.print("i");
		System.out.print("\n");
	}
	public String getString() {
		String ret = "";
		ret = String.valueOf(real) + '+' + String.valueOf(imaginary)+'i';
		return ret;
	}
	public static String getString(Complex C) {
		String ret = "";
		ret = String.valueOf(C.real) + '+' + String.valueOf(C.imaginary)+'i';
		return ret;
	}
	
	public void setReal(float R) {
		real = R;
	}
	public void setImaginary(float I) {
		imaginary = I;
	}
	public float real() {
		return real;
	}
	public float imaginary() {
		return imaginary;
	}
	
	// Operators
	public static Complex Add(Complex z1, Complex z2) {
		// Declare return variable
		Complex Z = new Complex();
		
		// Compute result
		Z.real = z1.real + z2.real;
		Z.imaginary = z1.imaginary + z2.imaginary;
		
		// Return as rectangular
		return Z;
	}

	public static Complex Subtract(Complex z1, Complex z2) {
		// Declare return variable
		Complex Z = new Complex();
   
		// Compute result
		Z.real = z1.real - z2.real;
		Z.imaginary = z1.imaginary - z2.imaginary;
		
		// Return as rectangular
		return Z;
	}

	// Define multiplication procedure
	public static Complex Multiply(Complex z1, Complex z2) {
		// Declare return variable
		Complex Z = new Complex();
   
		// Declare local variables
		float radius = z1.radius()*z2.radius();
		float angle = z1.phase()+z2.phase();
   
		// Compute result
		Z.real = radius*Cos(angle);
		Z.imaginary = radius*Sin(angle);
		
		// Return as rectangular
		return Z;
	}

	public static Complex Divide(Complex z1, Complex z2) {
		// Declare return variable
		Complex Z = new Complex();
		
		// Declare local variables
		float radius = z1.radius()/z2.radius();
		float angle = z1.phase()-z2.phase();
		
		// Compute result
		Z.real = radius*Cos(angle);
		Z.imaginary = radius*Sin(angle);
		
		// Return as rectangular
		return Z;
	}
	
	public static Complex Pow(Complex z1, Complex z2) {
		// Declare local variables
		float radius; 
		float angle; 
		radius = Pow(z1.radius(), z2.real)*Exp(-z1.phase()*z2.imaginary);
		angle = z1.phase()*z2.real+Ln(z1.radius())*z2.imaginary;
		
		// Compute parts
		float Real = radius*Cos(angle);
		float Imaginary = radius*Sin(angle);
		
		// Return result
		return new Complex(Real, Imaginary);
	}
	/** Define public functions for retrieving Complex Number information*/
	// 1. Get the absolute value/modulus/magnitude/radius of Complex Number
	public float radius() { // Perform function on self
		// Declare return variable
		float ABS = real*real+imaginary*imaginary;
		
		// Calculate absolute value
		ABS = Sqrt(ABS);
		
		// Return variable
		return ABS;
	}
	public static float abs(Complex c) { // Perform function on argument of complex class
		// Pass class members to local variables
		float R = c.real;
		float I = c.imaginary;
		
		// Declare return variable
		float ABS = R*R + I*I;
		
		// Calculate absolute value
		ABS = Sqrt(ABS);
		
		// Return variable
		return ABS;
	}
	
	// 2. Get the phase/argument/angle of Complex Number
	public float phase() { // Perform function on self
		return Arctan2(real, imaginary);
	}
	public static float phase(Complex c) { // Perform function on argument of complex class
		// Pass class members to local variables
		float R = c.real;
		float I = c.imaginary;
		
		// Return phase
		return Arctan2(R, I);
	}
	
	// Trancendental functions
	public Complex exp() {
		float radius = Exp(real);
		float angle = imaginary;
		float Real = radius*Cos(angle);
		float Imaginary = radius*Sin(angle);
		return new Complex(Real, Imaginary);
		
	}
	public Complex sin() {
		float Real = Sin(real)*Cosh(imaginary);
		float Imaginary = -Cos(real)*Sinh(imaginary);
		return new Complex(Real, Imaginary);
	}
	public Complex cos() {
		float Real = Cos(real)*Cosh(imaginary);
		float Imaginary = Sin(real)*Sinh(imaginary);
		return new Complex(Real, Imaginary);
	}
	public Complex tan() {
		float denom = 1+Tan(real)*Tan(real)*Tanh(imaginary)*Tanh(imaginary);
		float Real = Tan(real)*(1-Tanh(imaginary)*Tanh(imaginary))/denom;
		float Imaginary = (1+Tan(real)*Tan(real))*Tanh(imaginary)/denom;
		return new Complex(Real, Imaginary);
	}
	public Complex sinh() {
		float Real = -Sinh(real)*Cos(imaginary);
		float Imaginary = Cosh(real)*Sin(imaginary);
		return new Complex(Real, Imaginary);
	}
	public Complex cosh() {
		float Real = Cosh(real)*Cos(imaginary);
		float Imaginary = -Sinh(real)*Sin(imaginary);
		return new Complex(Real, Imaginary);
	}
	public Complex tanh() {
		return Divide(sinh(), cosh());
	}
	public Complex ln() {
		float Real = Ln(radius());
		float Imaginary = phase();
		return new Complex(Real, Imaginary);
	}
	public Complex log() {
		float Real = Log(radius());
		float Imaginary = Log(e)*phase();
		return new Complex(Real, Imaginary);
	}
	public Complex sqrt() {
		float Real = Sqrt(radius())*Cos(phase()/2.0f);
		float Imaginary = Sqrt(radius())*Sin(phase()/2.0f);
		return new Complex(Real, Imaginary);
	}
	public Complex abs() {
		return new Complex(radius(), 0);
	}
	public Complex ngtv() {
		return new Complex(-real, -imaginary);
	}
	public Complex conj() {
		return new Complex(real, -imaginary);
	}
	
	/** Define private functions (for code clarity) */
	// Local square root function
	private static float Sqrt(float val) {
		return (float)Math.sqrt(val);
	}
	
	// Local arctan function
	public static float Arctan(float val) {
		return (float)Math.atan(val);
	}
	
	// Local arctan2 function
	public static float Arctan2(float x, float y) {
		// Declare return variable 
		float ret = 0;
		
		// Calculate result
		if (x == 0 && y == 0) {
			ret = 0;
		}
		else {
			ret = (float)Math.atan2(y, x);
		}
		// Return variable
		return ret;
	}
	
	public static float Exp(float val) {
		return (float)Math.exp(val);
	}
	public static float Sin(float val) {
		return (float)Math.sin(val);
	}
	public static float Cos(float val) {
		return (float)Math.cos(val);
	}
	public static float Tan(float val) {
		return (float)Math.tan(val);
	}
	public static float Sinh(float val) {
		return (float)Math.sinh(val);
	}
	public static float Cosh(float val) {
		return (float)Math.cosh(val);
	}
	public static float Tanh(float val) {
		return (float)Math.tanh(val);
	}
	public static float Ln(float val) {
		return (float)Math.log(val);
	}
	public static float Log(float val) {
		return (float)Math.log10(val);
	}
	public static float Pow(float a, float b) {
		return (float)Math.pow(a, b);
	}
}