package application.model;


public class Type {
	private int id;
	private String name;
	
	public Type(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Type(int id){
		this(id, "nvm");
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public static int resolveId(String ext){
		
		for(AudioFormats af : AudioFormats.values()){
			if(af.getExt().equals(ext)){
				return 1;
			}	
		}
		
		for(VideoFormats vf : VideoFormats.values()){
			if(vf.getExt().equals(ext)){
				return 2;
			}	
		}
		
		for(ImageFormats imf : ImageFormats.values()){
			if(imf.getExt().equals(ext)){
				return 3;
			}	
		}
		return -1;
	}

}
