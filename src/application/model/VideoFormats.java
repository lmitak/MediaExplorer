package application.model;

public enum VideoFormats {
	MP4(".mp4"),
	FLV(".flv"),
	AVI(".avi"),
	MOV(".mov"),
	WindowsMediaVideo(".wmv");
	
	
	private final String ext;
	
	VideoFormats(String extension){
		ext = extension;
	}

	public String getExt() {
		return ext;
	}


}
