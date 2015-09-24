package application.model;

public enum AudioFormats {
	MP3(".mp3"),
	WAVE(".wav"),
	OGG(".ogg"),
	WindowsMediaAudio(".wav");
	
	
	private final String ext;
	
	AudioFormats(String extension){
		ext = extension;
	}

	public String getExt() {
		return ext;
	}
}
