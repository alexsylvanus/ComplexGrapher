/**
 * 
 */
package complex.image;

import java.awt.image.*;
import java.io.*;
import java.nio.file.*;
import javax.imageio.ImageIO;

/**
 * @author alexs
 *
 */
public class FileOutput {
	public static File getFilePath(String foldername, String filename, String ext) throws IOException {
		// Get filepath
		String home = System.getProperty("user.home");
		Path outFolder = Paths.get(home, "Pictures", foldername);
		if (!Files.isDirectory(outFolder)) {
			System.out.println("Creating Directory");
			System.out.println(outFolder.toString());
			Files.createDirectories(outFolder);
		}
		
		// Generate filename
		int i = 0;
		String fname = filename + String.valueOf(i) + "." + ext;
		Path outFile = Paths.get(outFolder.toString(), fname);
		File F = outFile.toFile(); 
		while (F.exists()) { // Append an integer to the end of the filename so that files are not overwritten
			i++;
			fname = filename + String.valueOf(i) + "." + ext;
			outFile = Paths.get(outFolder.toString(), fname);
			F = outFile.toFile();
		}
		// Display the filepath
		System.out.print("File in: ");
		System.out.println(outFile.toString());
		
		// Return the file
		return F;
	}
	public static File createJpg(String filepath, String filename, BufferedImage img) throws IOException {
		File F = getFilePath(filepath, filename, "jpg");
		ImageIO.write(img, "jpg", F);
		return F;
	}
}
