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
import static complex.app.WindowParameters.*;

// Class declaration
public class FunctionPanel extends JPanel {

	/**
	 * Generate serial ID
	 */
	private static final long serialVersionUID = -6778187853697000566L;
	
	// Private members
	private JLabel funcLabel;
	private JTextField funcField;
	private Rectangle BOUNDS;
	
	// Constructor
	public FunctionPanel() {
		// Construct superclass
		super();
		
		// Allow for custom layout
		this.setLayout(null);
		
		// Create the function label
		funcLabel = new JLabel("f(z) = ");
		funcLabel.setFont(font);
		funcLabel.setBounds(0, 0, funcLabel.getPreferredSize().width, textHeight);
		
		// Create new function text box
		funcField = new JTextField();
		funcField.setFont(font);
		funcField.setBounds((int)funcLabel.getBounds().getMaxX(), 0, textWidth, textHeight);
		funcField.setText("z");
		
		// Set the bounds
		BOUNDS = new Rectangle();
		BOUNDS.setLocation(bufferSpace, bufferSpace);
		BOUNDS.width = (int)funcField.getBounds().getMaxX()+bufferSpace;
		BOUNDS.height = funcField.getBounds().height+2*bufferSpace;
		this.setBounds(BOUNDS);
		
		// Add the members to the pane
		this.add(funcLabel);
		this.add(funcField);
		this.setVisible(true);
	}
	
	@Override
	public Rectangle getBounds() {
		BOUNDS = super.getBounds();
		return BOUNDS;
	}
	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		
		// Set the member locations
		funcLabel.setLocation(x, y);
		funcField.setLocation((int)funcLabel.getBounds().getMaxX()+bufferSpace, y);
	}
	@Override
	public void setLocation(Point p) {
		super.setLocation(p);
		
		// Set the member locations
		funcLabel.setLocation(p);
		funcField.setLocation((int)funcLabel.getBounds().getMaxX()+bufferSpace, p.y);
	}
	
	public int getMaxX() {
		return (int)this.getBounds().getMaxX();
	}
	public int getMaxY() {
		return (int)this.getBounds().getMaxY();
	}
	// JTextField extension functions
	public void addActionListener(ActionListener l) {
		funcField.addActionListener(l);
	}
	public String getText() {
		return funcField.getText();
	}
}
