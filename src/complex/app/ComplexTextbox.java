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
	public JTextField funcField;
	public JLabel jl;
	private String expression;
	private ComplexFunction func;
	
	// Constants
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int screenWidth = screenSize.width;
	public static final int screenHeight = screenSize.height;
	public static final int bufferSpace = screenHeight/200;
	public static final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, screenHeight/80);
	

	
	// Constructor
	public ComplexTextbox(int x, int y, int width, int height) {
		super();
		
		// Create new label
		jl = new JLabel("f(z) = ");
		jl.setFont(font);
		jl.setBounds(x, y, (int)jl.getPreferredSize().getWidth(), height);
		
		// Create new text field object and assign it to the member text field
		funcField = new JTextField();
		funcField.setFont(font);
		
		// Initialize the boundaries of the text field
		funcField.setBounds(x+jl.getWidth()+bufferSpace, y, width, height);
		
		// Set the initial text in the box to the function 'z'
		expression = "z";
		funcField.setText(expression);
		
		// Initialize the complex function
		func = new ComplexFunction(expression);
		
		// Allow the text field to receive events from the class
		funcField.addActionListener(this);
	}
	public void setXY(int x, int y) {
		jl.setBounds(x, y, jl.getWidth(), jl.getHeight());
		funcField.setBounds(x+jl.getWidth()+bufferSpace, y, funcField.getWidth(), funcField.getHeight());
	}
	public int getEndX() {
		return (int)funcField.getBounds().getMaxX();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		expression = funcField.getText();
		func = new ComplexFunction(expression);
	}
	
	public ComplexFunction getFunction() {
		return func;
	}
}
