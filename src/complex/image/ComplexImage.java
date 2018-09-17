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
		fillImage(ColoringAlgorithm.WEIGHT);
	}
	public ComplexImage(ComplexGraph g, ComplexFunction f, ColoringAlgorithm a) {
		super(g.width(), g.height(), BufferedImage.TYPE_INT_RGB);
		graph = g;
		func = f;
		fillImage(a);
	}
	
	public void fillImage(ColoringAlgorithm a) {
		// Declare local variables
		int X = 0;
		int Y = 0;
		int width = this.getWidth();
		int height = this.getHeight();
		float currX = graph.getXvalue(X);
		float currY = graph.getYvalue(Y);
		Complex c = new Complex(currX, currY);
		Complex f = new Complex();
		Complex[] vec = new Complex[width*height];
		
		// Declare color array
		int[] color = new int[width*height];
		
		// Variables for structured case
		int xGrid = 0;
		int yGrid = 0;
		int xTemp = 0;
		int yTemp = 0;
		// float xMin = 0;
		// float yMin = 0;
		// Iterate over entire image
		for (Y = 0; Y < height; Y++) {
			currY = graph.getYvalue(Y);
			c.setImaginary(currY);
			for (X = 0; X < width; X++) {
				currX = graph.getXvalue(X);
				c.setReal(currX);
				f = func.compute(c);
				vec[Y*width+X] = f;
				color[Y*width+X] = getComplexColor(f, a);
				if (a.equals(ColoringAlgorithm.STRUCTURED)) {
					xGrid = (int)Math.ceil(f.real());
					yGrid = (int)Math.ceil(f.imaginary());
					if (xGrid != xTemp) {
						color[Y*width+X] = Color.DARK_GRAY.getRGB();
					}
					if (yGrid != yTemp) {
						color[Y*width+X] = Color.DARK_GRAY.getRGB();
						yTemp = yGrid;
					}
					xTemp = xGrid;
					
					// System.out.println(xGrid);
					// System.out.println(yGrid);
				}
			}
			yTemp = yGrid;
		}
		
		xGrid = 0;
		yGrid = 0;
		xTemp = 0;
		yTemp = 0;
		// For the structured case, finish filling in the grid lines
		if (a.equals(ColoringAlgorithm.STRUCTURED)) {
			for (X = 0; X < width; X++) {
				for (Y = 0; Y < height; Y++) {
					f = vec[Y*width+X]; // Get the current complex number
					xGrid = (int)Math.ceil(f.real());
					yGrid = (int)Math.ceil(f.imaginary());
					if (xGrid != xTemp) {
						color[Y*width+X] = Color.DARK_GRAY.getRGB();
					}
					if (yGrid != yTemp) {
						color[Y*width+X] = Color.DARK_GRAY.getRGB();
					}
					xTemp = xGrid;
					yTemp = yGrid;
				}
				

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
		float weight = (float)graph.width()/(float)graph.spacing();
		
		// Get the radius and angle
		radius = z.radius();
		angle = z.phase();
		
		// Set the hue based on the angle
		h = (float)(0.5f*angle/Math.PI);
		
		// Set the saturation and brightness based on the radius
		s = weight/(radius+weight);
		b = 1 - 1/(weight*radius+1);
		
		// Get color from HSBtoRGB conversion
		rgb = Color.HSBtoRGB(h, s, b);
		
		return rgb;
	}
	
	public int getComplexColorDomainMethod(Complex z) {
		// Declare local variables
		int rgb = 0;
		float h, s, b, l; // Hue, saturation, brightness, and lightness values
		float radius, angle;
		float r = (float)graph.width()/(float)graph.spacing();
		
		// Get the radius and angle
		radius = z.radius();
		angle = z.phase();
		
		// Set the hue based on the angle
		h = (float)(0.5f*angle/Math.PI);
		
		// Set saturation to 100%
		s = 1.000f;
		
		// Set the lightness to 1 - e^(-3|z|)
		l = (float)Math.exp(-radius/r);
		l = 1-l*l*l; // Increase the color displayed
		// Convert to HSL to HSB
		b = (float)(l+s*(1-(Math.abs(2.000f*l-1)))/2.000f);
		s = 2.000f*(b-l)/b;
		
		// Get color from RGB conversion
		rgb = Color.HSBtoRGB(h, s, b);
		return rgb;
	}
	public int getComplexColorStructuredMethod(Complex z) {
		// Declare local variables
		int rgb = 0;
		if (isFloatInt(z.real()) || isFloatInt(z.imaginary())) {
			rgb = Color.DARK_GRAY.getRGB();
		}
		else {
			rgb = getComplexColorWeightMethod(z);
		}
		return rgb;
	}
	public int getComplexColor(Complex z, ColoringAlgorithm a) {
		// Declare local variables
		int rgb = 0;
		
		// Chose algorithm
		switch(a) {
		case WEIGHT:
		case STRUCTURED:
			rgb = getComplexColorWeightMethod(z);
			break;
		case DOMAIN:
			rgb = getComplexColorDomainMethod(z);
			break;
		case CONTOUR:
			rgb = getComplexColorContourMethod(z);
		}
		
		// Return the color
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
			CI.fillImage(ColoringAlgorithm.WEIGHT);
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
