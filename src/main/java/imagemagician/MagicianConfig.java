package imagemagician;

import java.io.File;
import java.util.ResourceBundle;

/**
 * @author adorechic
 */
public class MagicianConfig {
	public static final String IMAGE_MAGICK_BIN;
	public static final int BUFFER_SIZE;
	public static final File DATA_DIR;

	static {
		ResourceBundle rb = ResourceBundle.getBundle("magician");
		String imageMagickBin = rb.getString("imagemagick.bin");
		if(imageMagickBin.endsWith("/")) {
			imageMagickBin = imageMagickBin.substring(0,imageMagickBin.length() - 1);
		}
		IMAGE_MAGICK_BIN = imageMagickBin;
		BUFFER_SIZE = Integer.parseInt(rb.getString("buffer.size"));
		DATA_DIR = new File(rb.getString("tmp.data.dir"));
	}
}
