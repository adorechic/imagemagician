package imagemagician;

/**
 * @author adorechic
 */
public enum ImageType {
	JPG("jpg"),
	PNG("png"),
	GIF("gif");
	private String ext;
	private ImageType(String ext) {
		this.ext = ext;
	}
	public String ext() {
		return ext;
	}
}
