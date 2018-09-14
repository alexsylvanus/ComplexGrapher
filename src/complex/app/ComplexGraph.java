/**
 * 
 */
package complex.app;

/**
 * @author alexs
 *
 */
public class ComplexGraph {
	// Graph information
	private int x_length, y_length; // Dimensions of the graph 
	private int x_space, y_space; // Number of pixels between each integer value on the axis'
	private int x0, y0; // Pixel values corresponding to origin
	/** Example of Graph information:
	 * We want the x range to be from -4 to 4 and the y range to be from -2 to 3
	 * The dimensions should be 1000 by 800, (x = 1000, y = 800)
	 * x_space = 1000/(4-(-4)) = 125
	 * y_space = 800/(3-(-2) = 160
	 * x0 = |-4*x_space| = 500
	 * y0 = |3*y_space| = 480
	 * */
	
	public ComplexGraph() {
		// Set graph dimensions
		x_length = 600;
		y_length = 400;
		
		// Declare default ranges
		int x_range = 9;
		int y_range = 6;
		
		// Calculate spacing
		x_space = x_length/x_range;
		y_space = y_length/y_range;
		
		// Calculate origin placement
		x0 = x_length/2;
		y0 = y_length/2;
	}
	
	public ComplexGraph(int width, int height, int x_range, int y_range) {
		// Set graph dimensions
		x_length = width;
		y_length = height;
		
		// Calculate spacing
		x_space = x_length/x_range;
		y_space = y_length/y_range;
		
		// Calculate origin placement
		x0 = x_length/2;
		y0 = y_length/2;
	}
	public ComplexGraph(int width, int height) { 
		// Set graph dimensions
		x_length = width;
		y_length = height;
		// Creates a graph with an automatic 1:1 ratio of xspace to yspace and default range of 10 for x
		int x_range = 10;
		int y_range = (y_length*x_range)/x_length;
		
		// Calculate spacing
		x_space = x_length/x_range;
		y_space = y_length/y_range;
		
		// Calculate origin placement
		x0 = x_length/2;
		y0 = y_length/2;
	}
	
	public float getXvalue(int X) {
		// Declare return variable
		float xVal = 0;
		
		// Compute value
		xVal = (float)(X-x0)/x_space;
		
		// Return variable
		return xVal;
	}
	
	public float getYvalue(int Y) {
		// Declare return variable
		float yVal = 0;
		
		// Compute value
		yVal = (float)((y_length-y0)-Y)/y_space;
		
		// Return value
		return yVal;
	}
	public int getXpixel(float x) {
		int xPixel = 0;
		
		// Compute value
		xPixel = (int)(x*x_space+x0);
		
		// Return value
		return xPixel;
	}
	public int getYpixel(float y) {
		int yPixel = 0;
		
		// Compute value
		yPixel = (int)(y_length-y0-y*y_space);
		
		// Return value
		return yPixel;
	}
	public int width() {
		return x_length;
	}
	public int height() {
		return y_length;
	}
	public int spacing() {
		return (int)(x_space+y_space)/2;
	}
}
