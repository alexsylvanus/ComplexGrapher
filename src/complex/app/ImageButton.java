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
public class ImageButton extends JPanel {
	/**
	 * Generate serial ID
	 */
	private static final long serialVersionUID = -8241438319344039251L;
	
	// Private members
	private JButton jpgButton;
	private JLabel fileLabel;
	
	// Constructor
	public ImageButton(int x, int y) {
		super();
		
		// Allow for custom layout
		this.setLayout(null);
		
		// Create the generate jpg button
		jpgButton = new JButton("Create JPG Image");
		jpgButton.setFont(font);
		jpgButton.setBounds(0, 0, jpgButton.getPreferredSize().width, textHeight);
		
		// Create the file label
		fileLabel = new JLabel("File in: ");
		fileLabel.setFont(font);
		
		// Create rectangle used for file label
		Rectangle r = jpgButton.getBounds();
		r.setLocation((int)r.getMaxX()+bufferSpace, 0);
		r.setSize(fileLabel.getPreferredSize().width, textHeight);		
		fileLabel.setBounds(r);
		
		// Set bounds of panel
		this.setLocation(x, y);
		this.setSize((int)fileLabel.getBounds().getMaxX()+bufferSpace, textHeight+2*bufferSpace);
		
		// Add the components to the panel
		this.add(jpgButton);
		this.add(fileLabel);
		this.setVisible(true);
	}
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		
		// Set the member locations
		jpgButton.setLocation(0, 0);
		fileLabel.setLocation((int)jpgButton.getBounds().getMaxX()+bufferSpace, 0);
	}
	
	// Access member data functions
	public void setText(String text) {
		// Set the text to the input
		fileLabel.setText(text);
		
		// Adjust the size to fit the text
		fileLabel.setSize(fileLabel.getPreferredSize().width, textHeight);
		this.setSize((int)fileLabel.getBounds().getMaxX()+bufferSpace, textHeight+2*bufferSpace);
	}
	public void addActionListener(ActionListener l) { // Adds an action listener to the button
		jpgButton.addActionListener(l);
	}
	public JButton jpgButton() { // To get the jpgButton object
		return jpgButton;
	}
}
