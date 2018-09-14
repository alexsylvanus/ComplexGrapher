/**
 * 
 */
package complex.image;

import java.awt.Color;

/**
 * @author alexs
 *
 */

// Imports
import java.awt.image.*;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

// User Imports
import complex.app.*;
import complex.math.*;


// Class declaration
public class ComplexImage extends BufferedImage {
	// Private members
	private ComplexGraph graph;
	private ComplexFunction func;

	// Constructors
	public ComplexImage(int width, int height, int imageType) {
		super(width, height, imageType);
		// TODO Auto-generated constructor stub
		graph = new ComplexGraph(width, height, 9, 6);
		func = new ComplexFunction("z");
	}
	public ComplexImage(int width, int height, int x_range, int y_range, int imageType, String function) {
		super(width, height, imageType);
		graph = new ComplexGraph(width, height, x_range, y_range);
		func = new ComplexFunction(function);
	}
	public ComplexImage(ComplexGraph g, ComplexFunction f) {
		super(g.width(), g.height(), BufferedImage.TYPE_INT_RGB);
		graph = g;
		func = f;
		fillImageWeight();
	}
	public ComplexImage(ComplexGraph g, ComplexFunction f, ColoringAlgorithm a) {
		super(g.width(), g.height(), BufferedImage.TYPE_INT_RGB);
		graph = g;
		func = f;
		switch (a) {
		case WEIGHT:
			fillImageWeight();
			break;
		case CONTOUR:
			fillImageContour();
			break;
		case STRUCTURED:
			fillImageStructured();
		}
		
	}
	public void fillImageContour() {
		// Declare local variables
		int X = 0;
		int Y = 0;
		int width = this.getWidth();
		int height = this.getHeight();
		float currX = graph.getXvalue(X);
		float currY = graph.getYvalue(Y);
		Complex c = new Complex(currX, currY);
		
		// Declare color array
		int[] color = new int[width*height];
		
		// Iterate over entire image
		for (Y = 0; Y < height; Y++) {
			currY = graph.getYvalue(Y);
			c.setImaginary(currY);
			for (X = 0; X < width; X++) {
				currX = graph.getXvalue(X);
				c.setReal(currX);
				// c.setImaginary(currY);
				// c = func.compute(c);
				color[Y*width+X] = getComplexColorContourMethod(func.compute(c));
			}
		}
		this.setRGB(0, 0, width, height, color, 0, width);
	}
	public void fillImageWeight() {
		// Declare local variables
		int X = 0;
		int Y = 0;
		int width = this.getWidth();
		int height = this.getHeight();
		float currX = graph.getXvalue(X);
		float currY = graph.getYvalue(Y);
		Complex c = new Complex(currX, currY);
		
		// Declare color array
		int[] color = new int[width*height];
		
		// Iterate over entire image
		for (Y = 0; Y < height; Y++) {
			currY = graph.getYvalue(Y);
			c.setImaginary(currY);
			for (X = 0; X < width; X++) {
				currX = graph.getXvalue(X);
				c.setReal(currX);
				color[Y*width+X] = getComplexColorWeightMethod(func.compute(c));
			}
		}
		this.setRGB(0, 0, width, height, color, 0, width);
	}
	public void fillImageStructured() {
		// Declare local variables
		int X = 0;
		int Y = 0;
		int width = this.getWidth();
		int height = this.getHeight();
		float currX = graph.getXvalue(X);
		float currY = graph.getYvalue(Y);
		Complex c = new Complex(currX, currY);
		
		// Declare color array
		int[] color = new int[width*height];
		
		// Iterate over entire image
		for (Y = 0; Y < height; Y++) {
			currY = graph.getYvalue(Y);
			c.setImaginary(currY);
			for (X = 0; X < width; X++) {
				currX = graph.getXvalue(X);
				c.setReal(currX);
				color[Y*width+X] = getComplexColorStructuredMethod(func.compute(c));
			}
		}
		this.setRGB(0, 0, width, height, color, 0, width);
	}
	public int getComplexColorContourMethod(Complex z) {
		// Declare return variable
		int rgb = 0;
        float radius = z.radius();
        float theta = z.phase();
        
        // map the magnitude logarithmically into the repeating interval 0 < r < 1
        // this is essentially where we are between contour lines
        float r0 = 0.0f;
        float r1 = 1.0f;
        while (radius > r1) 
        {
            r0 = r1;
            r1 *= Math.E;
        }
        // Put contour lines at 0, 1, e, e^2, e^3, ...
        float r = (radius-r0)/(r1-r0);
        // Determine saturation and value based on r
        float q = Math.abs(2.0f*r-1); // p and q are complementary distances from a contour line
        float p = 1.0f - q;
        
        // The values should always be near 1 until they get very near zero
        float p1 = 1-q*q;
        float q1 = 1-p*p;
        // fix s and v from p1 and q1
        float s = 0.3f+0.7f*p1;
        float v = 0.3f+0.7f*q1;
        rgb = Color.HSBtoRGB(0.5f*theta/((float)Math.PI), v, s);
        
		// Return Color
		return rgb;
	}
	
	public int getComplexColorWeightMethod(Complex z) {
		// Declare local variables
		int rgb = 0;
		float h, s, b; // hue, saturation, and brightness
		float radius, angle;
		float w = (float)graph.width()/(float)graph.spacing();
		
		// Get the radius and angle
		radius = z.radius();
		angle = z.phase();
		
		// Set the hue based on the angle
		h = (float)(0.5f*angle/Math.PI);
		
		// Set the saturation and brightness based on the radius
		s = w/(radius+w);
		b = 1 - 1/(w*radius+1);
		
		// Get color from HSBtoRGB conversion
		rgb = Color.HSBtoRGB(h, s, b);
		
		return rgb;
	}
	public int getComplexColorStructuredMethod(Complex Z) {
		// Declare local variables
		int rgb = 0;
		if (isFloatInt(Z.real()) || isFloatInt(Z.imaginary())) {
			rgb = Color.DARK_GRAY.getRGB();
		}
		else {
			rgb = getComplexColorWeightMethod(Z);
		}
		return rgb;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter a function in calculator format. Use z as the variable");
		System.out.print("Enter the function: ");
		String function = in.nextLine();
		String folder = "";
		Instant start;
		Instant end;
		ComplexImage CI;
		// Get foldername from user
		System.out.println("Enter the name of the folder you would like your image to be placed in.");
		System.out.println("You can find this folder in your home pictures directory.");
		System.out.print("Folder Name: ");
		folder = in.next();
		
		while (!function.equals("exit")) {
			System.out.println("Creating image . . .");
			start = Instant.now();
			CI = new ComplexImage(3000, 2000, 9, 6, BufferedImage.TYPE_INT_RGB, function);
			CI.fillImageContour();
			end = Instant.now();
			System.out.println(Duration.between(start, end));
			FileOutput.createJpg(folder, "Graph", CI);
			System.out.println("Enter 'exit' to quit the program");
			System.out.println("Enter a function in calculator format. Use z as the variable");
			System.out.print("Enter function: ");
			function = in.next();
		}		
		System.out.println("Goodbye!");
		in.close();
	}
	
	private boolean isFloatInt(float f) {
		if ((f < Math.ceil(f)+0.05f) && (f > Math.ceil(f)-0.05f)) {
			return true;
		}
		else {
			return false;
		}
	}
}
