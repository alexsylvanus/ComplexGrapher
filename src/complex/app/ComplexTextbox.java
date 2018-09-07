/**
 * 
 */
package complex.app;

/**
 * @author alexs
 *
 */

// Imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// User imports
import complex.math.*;

// Class declaration
public class ComplexTextbox implements ActionListener {
	// Private members
	public JTextField jtf1;
	public JLabel jl;
	private String expression;
	private ComplexFunction func;
	
	// Constants
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int screenWidth = screenSize.width;
	public static final int screenHeight = screenSize.height;
	public static final int bufferSpace = screenHeight/200;
	
	public static final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, screenHeight/80);
	public static final int default_x = 100;
	public static final int default_y = 100;
	public static final int default_width = 300;
	public static final int default_height = 60;
	

	
	// Constructor
	public ComplexTextbox(int x, int y, int width, int height) {
		super();
		
		// Create new label
		jl = new JLabel("f(z) = ");
		jl.setBounds(x, y, screenHeight/25, height);
		jl.setFont(font);
		
		// Create new text field object and assign it to the member text field
		jtf1 = new JTextField();
		jtf1.setFont(font);
		
		// Initialize the boundaries of the text field
		jtf1.setBounds(x+jl.getWidth()+bufferSpace, y, width, height);
		
		// Set the initial text in the box to the function 'z'
		expression = "z";
		jtf1.setText(expression);
		
		// Initialize the complex function
		func = new ComplexFunction(expression);
		
		// Allow the text field to receive events from the class
		jtf1.addActionListener(this);
	}
	public void setXY(int x, int y) {
		jl.setBounds(x, y, jl.getWidth(), jl.getHeight());
		jtf1.setBounds(x+jl.getWidth()+bufferSpace, y, jtf1.getWidth(), jtf1.getHeight());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		expression = jtf1.getText();
		func = new ComplexFunction(expression);
	}
	
	public ComplexFunction getFunction() {
		return func;
	}
}
