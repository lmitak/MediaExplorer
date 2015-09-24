package application.model;

public enum ImageFormats {
	PNG(".png"),
	GIF(".gif"),
	JPG(".jpg"),
	BMP(".bmp");
	
	
	private final String ext;
	
	ImageFormats(String extension){
		ext = extension;
	}

	public String getExt() {
		return ext;
	}

}
