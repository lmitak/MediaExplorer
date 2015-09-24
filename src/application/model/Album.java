package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Album {
	private IntegerProperty album_id;
	private StringProperty album_ime;
	
	public Album(int id, String ime){
		album_id = new SimpleIntegerProperty(id);
		album_ime = new SimpleStringProperty(ime);
	}
	
	public Album(){
		this(null);
	}
	
	public Album(String ime){
		this(-1, ime);
	}

	public IntegerProperty getAlbum_id() {
		return album_id;
	}

	public StringProperty getAlbum_ime() {
		return album_ime;
	}
	
	public void setAlbum_id(int id){
		album_id = new SimpleIntegerProperty(id);
	}
	
	public void setAlbum_ime(String ime){
		album_ime = new SimpleStringProperty(ime);
	}
	

}
