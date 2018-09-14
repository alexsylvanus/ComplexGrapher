/**
 * 
 */
package complex.app;

/**
 * @author alexs
 *
 */

// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * 
 * 
 * try This function tan(z/3)*tan(z/2-i+e)/tanh(z/2-3*i+2)
 * 
 * 
 * 
 */
// User imports
import complex.image.*;
import complex.math.*;

// Class declaration
public class ComplexFrame extends JFrame implements ActionListener, WindowParameters{
	// Generate serial ID
	private static final long serialVersionUID = 5470579037102672801L;
	
	// Private members
	private ComplexImage image;
	private FunctionPanel FP;
	private ImageButton IB;
	private JComboBox<String> Algorithm;
	private int Width;
	private int Height;
	private int x_offset;
	private int y_offset;
	
	// Constructors
	public ComplexFrame() {
		// Create the frame
		super("Complex Grapher");
		
		// Create a function input panel
		FP = new FunctionPanel();
		FP.addActionListener(this); // allows the frame to perform an action triggered by the text box in the function panel
		
		// Create new drop down menu
		String[] lst = {"Weight Method", "Contour Method", "Structured Method"};
		Algorithm = new JComboBox<String>(lst);
		Algorithm.setFont(font);
		Algorithm.setBounds(FP.getMaxX(), bufferSpace, Algorithm.getPreferredSize().width, textHeight);
		Algorithm.addActionListener(this);
		
		// Create a new image button
		IB = new ImageButton((int)Algorithm.getBounds().getMaxX()+bufferSpace, bufferSpace);
		IB.addActionListener(this);
		
		// Assign dimension member values
		Width = windowWidth;
		Height = windowHeight;
		x_offset = 0;
		y_offset = FP.getMaxY()+3*bufferSpace;
		
		// Set the bounds of the frame
		this.setBounds(100, 100, Width, Height);
		
		// Create the complex image
		ComplexGraph g = new ComplexGraph(Width-x_offset, Height-y_offset);
		ComplexFunction cf = new ComplexFunction(FP.getText());
		image = new ComplexImage(g, cf);
		
		// Generate the frame settings
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		// Add the components to the frame
		this.add(FP);
		this.add(Algorithm);
		this.add(IB);
		
		// Display the frame
		this.setVisible(true);
	}
	// Override functions
	@Override
	public void actionPerformed(ActionEvent e) {
		// Create a file for writing to jpg
		File f;
		
		// Create graph from Screen size and offset
		ComplexGraph g = new ComplexGraph(this.getWidth() - x_offset, this.getHeight() - y_offset);
		
		// Check if the button was pressed
		if (e.getSource().equals(IB.jpgButton())) {
			try {
				f = FileOutput.createJpg("ComplexGraphs", "Graph", image); // Get the file
				IB.setText("File in: "+f.getPath()); // Set the file label
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else {
			ColoringAlgorithm a = ColoringAlgorithm.WEIGHT;
			// If the Contour choice was selected, switch the enum value
			if (Algorithm.getSelectedItem().equals("Contour Method")) {
				a = ColoringAlgorithm.CONTOUR;
			}
			else if (Algorithm.getSelectedItem().equals("Structured Method")) {
				a = ColoringAlgorithm.STRUCTURED;
			}
			
			// Generate a new image based on the coloring algorithm
			image = new ComplexImage(g, new ComplexFunction(FP.getText()), a);
		}
		
		// Repaint the image
		this.repaint();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, x_offset, y_offset, null);
	}
	@Override
	public int getWidth() {
		Width = super.getWidth();
		return Width;
	}
	@Override
	public int getHeight() {
		Height = super.getHeight();
		return Height;
	}
	
	// Main function
	public static void main(String[] args) {
		// ComplexFrame complexframe = new ComplexFrame(screenWidth/2, screenWidth/2, 0, screenHeight/10);
		ComplexFrame complexframe = new ComplexFrame();
		complexframe.setVisible(true);
	}
}
