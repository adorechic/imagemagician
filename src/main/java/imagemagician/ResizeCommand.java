package imagemagician; 

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author adorechic
 */
public class ResizeCommand implements ConvertCommand, ConvertOption{
	private static final String COMMAND = MagicianConfig.IMAGE_MAGICK_BIN + "/convert";
	private byte[] src;
	private ImageType type;
	private String define;
	private int width;
	private int height;
	private char resizeOpt = ' ';
	private String colorSpace;
	private boolean autoOrient;
	private boolean strip;
	private boolean autoDefineJpgSize = true;
	private File tmpFile;
	private List<String> command;

	@Override
	public ConvertOption src(byte[] src, ImageType type) {
		this.src = src;
		this.type = type;
		return this;
	}

	@Override
	public ConvertOption define(String str) {
		this.define = define;
		return this;
	}

	@Override
	public ConvertOption resize(int size) {
		return resize(size,size);
	}

	@Override
	public ConvertOption resize(int width, int height) {
		return resize(width,height,'>');
	}

	@Override
	public ConvertOption resize(int width, int height, char c) {
		this.width = width;
		this.height = height;
		this.resizeOpt = c;
		return this;
	}

	@Override
	public ConvertOption colorSpace(String str) {
		this.colorSpace = str;
		return this;
	}

	@Override
	public ConvertOption autoOrient() {
		this.autoOrient = true;
		return this;
	}

	@Override
	public ConvertOption strip() {
		this.strip = true;
		return this;
	}

	@Override
	public ConvertOption autoDefineJpgSizeOff() {
		this.autoDefineJpgSize = false;
		return this;
	}

	@Override
	public ConvertCommand build() {
		try{
			destTmpFile();
			this.src = null;

			List<String> list = new ArrayList<String>();
			list.add(COMMAND);//convert
			list.add(tmpFile.getAbsolutePath());
			if(autoDefineJpgSize && ImageType.JPG.equals(type)) {
				define = "jpeg:size=" + width + "x" + height;
			}
			if(define != null) {
				list.add("-define");	//-define jpeg:size=100x100
				list.add(define);
			}
			list.add("-resize");		//-resize 800x800\>
			list.add(width+"x"+height+resizeOpt);
			if(colorSpace != null){
				list.add("-colorspace");
				list.add(colorSpace);
			}
			if(autoOrient) list.add("-auto-orient");
			if(strip) list.add("-strip");
			list.add(type.ext() + ":-");//png:-

			this.command = list;

			return this;
		} catch (IOException e) {
			throw new MagicianException(e);
		}
	}

		@Override
	public List<String> getCommand() {
		if(command == null) throw new MagicianException("not build yet.");
		return command;
	}

	@Override
	public void clean() {
		this.tmpFile.delete();
	}

	private void destTmpFile() throws IOException {
		FileOutputStream fos = null;
		try {
			this.tmpFile = File.createTempFile("imagemagician-", "." + type.ext(), MagicianConfig.DATA_DIR);
			fos = new FileOutputStream(tmpFile);
			fos.write(src);
		} finally {
			if (fos != null) fos.close();
		}
	}

	@Override
	public String toString() {
		return "command->" + command;
	}
}
