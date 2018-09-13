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

// Interface declaration
public interface WindowParameters {
	// Constants
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public int screenWidth = screenSize.width;
	public int screenHeight = screenSize.height;
	public int windowWidth = 2*screenWidth/3;
	public int windowHeight = 2*screenHeight/3;
	public int textHeight = screenHeight/33;
	public int textWidth = screenWidth/10;
	public int bufferSpace = screenHeight/100;
	public Font font = new Font(Font.SANS_SERIF, Font.PLAIN, screenHeight/80);
	
	
}
