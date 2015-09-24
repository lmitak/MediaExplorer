package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MediaStavka {

	private IntegerProperty id;
	private  StringProperty ime;
	private  StringProperty path;
	private  IntegerProperty izvodac_id;
	private  IntegerProperty album_id;
	private  IntegerProperty tip_id;
	private  IntegerProperty pogledano_puta;
	private	 IntegerProperty zanr_id;
	
	private double duration;
	private double rate;
	private String extension;
	private Type tip;
	

	/*default constructor*/
	public MediaStavka(){
		this(null, null);
	}
	
	public MediaStavka(int id, String ime, String uri, int izvodjac_id, int album_id,int zanr_id, int tip_id){
		this(id, ime, uri, izvodjac_id, album_id, tip_id, zanr_id, 0);
	}
	
	public MediaStavka(int id, String ime, String uri, int izvodjac_id, int album_id, int tip_id, int zanr_id ,int pogledano){
		this.id = new SimpleIntegerProperty(id);
		this.ime = new SimpleStringProperty(ime);
		this.path = new SimpleStringProperty(uri);
		this.izvodac_id = new SimpleIntegerProperty(izvodjac_id);
		this.album_id = new SimpleIntegerProperty(album_id);
		this.tip_id = new SimpleIntegerProperty(tip_id);
		this.pogledano_puta = new SimpleIntegerProperty(pogledano);
		this.zanr_id = new SimpleIntegerProperty(zanr_id);
	}
	
	public MediaStavka(int id, String ime, String uri, int izvodjac_id, int album_id, int tip_id, int zanr_id ,int pogledano,
			double duration, double rate, String extension){
		this.id = new SimpleIntegerProperty(id);
		this.ime = new SimpleStringProperty(ime);
		this.path = new SimpleStringProperty(uri);
		this.izvodac_id = new SimpleIntegerProperty(izvodjac_id);
		this.album_id = new SimpleIntegerProperty(album_id);
		this.tip_id = new SimpleIntegerProperty(tip_id);
		this.pogledano_puta = new SimpleIntegerProperty(pogledano);
		this.zanr_id = new SimpleIntegerProperty(zanr_id);
		this.duration = duration;
		this.rate = rate;
		this.extension = extension;
		tip = new Type(tip_id);
	}
	
	public Type getTip() {
		return tip;
	}

	public MediaStavka(String ime, String uri, double duration, String extension, int tip_id){
		this(-1, ime, uri, -1, -1, tip_id, -1, -1, duration, -1, extension);
	}
	
	public MediaStavka(String ime, String uri){
		this(-1, ime, uri, -1, -1, -1, -1);
	}

	public IntegerProperty getZanr_id() {
		return zanr_id;
	}

	public IntegerProperty getId() {
		return id;
	}

	public StringProperty getIme() {
		return ime;
	}

	public StringProperty getPath() {
		return path;
	}

	public IntegerProperty getIzvodac_id() {
		return izvodac_id;
	}

	public IntegerProperty getAlbum_id() {
		return album_id;
	}

	public IntegerProperty getTip_id() {
		return tip_id;
	}

	public IntegerProperty getPogledano_puta() {
		return pogledano_puta;
	}
	
	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public void setId(int id){
		this.id = new SimpleIntegerProperty(id);
	}
	
	
}
