package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Izvodac {
	private final IntegerProperty id;
	private final StringProperty ime;
	
	public Izvodac(int id, String ime){
		this.id = new SimpleIntegerProperty(id);
		this.ime = new SimpleStringProperty(ime);
	}
	
	public Izvodac(String ime){
		this(-1, ime);
	}
	
	public Izvodac(){
		this(null);
	}
	
	public IntegerProperty getId() {
		return id;
	}

	public StringProperty getIme() {
		return ime;
	}

	
}
