package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Zanr {

	private final IntegerProperty id_zanra;
	private final StringProperty ime_zanra;
	
	public Zanr(int id, String ime){
		id_zanra = new SimpleIntegerProperty(id);
		ime_zanra = new SimpleStringProperty(ime);
	}
	
	public Zanr(){
		this(null);
	}
	
	public Zanr(String ime){
		this(-1, ime);
	}

	public IntegerProperty getId_zanra() {
		return id_zanra;
	}

	public StringProperty getIme_zanra() {
		return ime_zanra;
	}
	
	
	
}
