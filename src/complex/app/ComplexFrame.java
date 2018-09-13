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
public class ComplexFrame extends JFrame implements ActionListener{
	// Generate serial ID
	private static final long serialVersionUID = 5470579037102672801L;
	
	// Private members
	private ComplexImage image;
	private ComplexTextbox tb;
	private FunctionPanel FP;
	private JButton jpgButton;
	private JLabel directory;
	private int Width;
	private int Height;
	private int x_offset;
	private int y_offset;
	
	// Constants
	private static final int bufferSpace = FunctionPanel.bufferSpace;
	private static final Font font = ComplexTextbox.font;
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int screenWidth = screenSize.width;
	public static final int screenHeight = screenSize.height;
	public static final int textHeight = screenHeight/33;
	public static final int textLength = screenWidth/10;
	
	// Constructors
	public ComplexFrame() {
		// Create the frame
		super("Complex Grapher");
		
		// Create a function input panel
		FP = new FunctionPanel();
		FP.addActionListener(this); // allows the frame to perform an action triggered by the text box in the function panel
		
		// Assign dimension member values
		Width = screenWidth/2;
		Height = screenWidth/2;
		x_offset = 0;
		y_offset = FP.getMaxY()+3*bufferSpace;
		
		// Set the bounds of the frame
		this.setBounds(100, 100, screenWidth/2, screenWidth/2);
		
		// Create the generate jpg button
		jpgButton = new JButton("Create JPG Image");
		jpgButton.setFont(font);
		jpgButton.setBounds(FP.getMaxX()+bufferSpace, bufferSpace, jpgButton.getPreferredSize().width, textHeight);
		jpgButton.addActionListener(this);
		
		// Create rectangle used for file label
		Rectangle r = jpgButton.getBounds();
		r.setLocation((int)r.getMaxX()+bufferSpace, bufferSpace);
		r.setSize(Width - r.x, textHeight);
		
		// Create the file label
		directory = new JLabel("File in: ");
		directory.setFont(font);
		directory.setBounds(r);
		
		// Create the complex image
		ComplexGraph g = new ComplexGraph(Width-x_offset, Height-y_offset);
		ComplexFunction cf = new ComplexFunction(FP.getText());
		image = new ComplexImage(g, cf);
		
		// Generate the frame settings
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		// Add the components to the frame
		this.add(FP);
		this.add(directory);
		this.add(jpgButton);
		
		// Display the frame
		this.setVisible(true);
	}
	public ComplexFrame(int Width, int Height, int x_offset, int y_offset) {
		// Create the frame
		super("Complex Grapher");
		
		// Set minimum sizes
		if (Width<screenWidth/2) {
			Width = screenWidth/2;
		}
		if (Height<screenHeight/2) {
			Height = screenHeight/2;
		}
		if (y_offset < textHeight) {
			y_offset = textHeight;
		}
		
		// Set the bounds of the frame
		this.setBounds(100, 100, Width, Height);
		
		// Assign integer member values
		this.Width = Width;
		this.Height = Height;
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		
		// Create graph from Screen size and offset
		ComplexGraph g = new ComplexGraph(Width - x_offset, Height - y_offset);
		
		// Create a text box underneath the graph with 10 pixels of space between
		tb = new ComplexTextbox(x_offset+2*bufferSpace, 2*bufferSpace, textLength, textHeight);
		tb.funcField.addActionListener(this);
		
		jpgButton = new JButton("Create image");
		jpgButton.addActionListener(this);
		jpgButton.setBounds(tb.getEndX()+2*bufferSpace, 2*bufferSpace, screenHeight/5, textHeight);
		jpgButton.setFont(font);
		directory = new JLabel("File in: ");
		Rectangle r = jpgButton.getBounds();
		
		// Determine where to put label
		r.setLocation((int)r.getMaxX() + 2*bufferSpace, 2*bufferSpace);
		r.setSize(screenWidth-r.x, textHeight);
		directory.setBounds(r);
		directory.setFont(font);
		
		// Create the complex Image
		image = new ComplexImage(g, tb.getFunction());
		
		// Generate settings for frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.getContentPane().add(tb.funcField);
		this.getContentPane().add(tb.jl);
		this.getContentPane().add(jpgButton);
		this.getContentPane().add(directory);
		this.setIconImage(image);
		this.setVisible(true);
	}
	
	// Override functions
	@Override
	public void actionPerformed(ActionEvent e) {
		File f;
		
		// Create graph from Screen size and offset
		ComplexGraph g = new ComplexGraph(this.getWidth() - x_offset, this.getHeight() - y_offset);
		directory.setSize(this.getWidth()-directory.getX(), directory.getHeight());
		
		// Regenerate the image
		image = new ComplexImage(g, new ComplexFunction(FP.getText()));
		
		// Repaint the image
		this.repaint();
		
		// Check if the button was pressed
		if (e.getSource().equals(jpgButton)) {
			try {
				f = FileOutput.createJpg("ComplexGraphs", "Graph", image);
				directory.setText("File in: "+f.getPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
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
	public static void main(String[] args) {
		// ComplexFrame complexframe = new ComplexFrame(screenWidth/2, screenWidth/2, 0, screenHeight/10);
		ComplexFrame complexframe = new ComplexFrame();
		complexframe.setVisible(true);
	}
}
