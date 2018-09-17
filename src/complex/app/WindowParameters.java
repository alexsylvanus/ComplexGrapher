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
public final class WindowParameters {
	// Constructor
	private WindowParameters() {
		
	}
	
	// Constants
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int screenWidth = screenSize.width;
	public static final int screenHeight = screenSize.height;
	public static final int windowWidth = 2*screenWidth/3;
	public static final int windowHeight = 2*screenHeight/3;
	public static final int textHeight = screenHeight/33;
	public static final int textWidth = screenWidth/10;
	public static final int bufferSpace = screenHeight/100;
	public static final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, screenHeight/80);
	
	
}
