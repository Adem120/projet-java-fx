package model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ordonnance {
	 private IntegerProperty codeOrd;
	    private ObjectProperty<LocalDate> dateVisite;
	    private StringProperty listeMedicaments;
	    private IntegerProperty codeDocteur;
	    private IntegerProperty numMalade;
	   
	    // Constructors

	    public Ordonnance(int codeOrd, LocalDate dateVisite, String listeMedicaments, int codeDocteur, int numMalade) {
	        this.codeOrd = new SimpleIntegerProperty(codeOrd);
	        this.dateVisite = new SimpleObjectProperty<>(dateVisite);
	        this.listeMedicaments = new SimpleStringProperty(listeMedicaments);
	        this.codeDocteur = new SimpleIntegerProperty(codeDocteur);
	        this.numMalade = new SimpleIntegerProperty(numMalade);
	    }

	    public Ordonnance() {
	        this.codeOrd = new SimpleIntegerProperty();
	        this.dateVisite = new SimpleObjectProperty<>();
	        this.listeMedicaments = new SimpleStringProperty();
	        this.codeDocteur = new SimpleIntegerProperty();
	        this.numMalade = new SimpleIntegerProperty();
	    }

	    // Getters

	    public int getCodeOrd() {
	        return codeOrd.get();
	    }

	    public IntegerProperty codeOrdProperty() {
	        return codeOrd;
	    }

	    public LocalDate getDateVisite() {
	        return dateVisite.get();
	    }

	    public ObjectProperty<LocalDate> dateVisiteProperty() {
	        return dateVisite;
	    }

	    public String getListeMedicaments() {
	        return listeMedicaments.get();
	    }

	    public StringProperty listeMedicamentsProperty() {
	        return listeMedicaments;
	    }

	    public int getCodeDocteur() {
	        return codeDocteur.get();
	    }

	    public IntegerProperty codeDocteurProperty() {
	        return codeDocteur;
	    }

	    public int getNumMalade() {
	        return numMalade.get();
	    }

	    public IntegerProperty numMaladeProperty() {
	        return numMalade;
	    }

	    // Setters

	    public void setCodeOrd(int codeOrd) {
	        this.codeOrd.set(codeOrd);
	    }

	    public void setDateVisite(LocalDate dateVisite) {
	        this.dateVisite.set(dateVisite);
	    }

	    public void setListeMedicaments(String listeMedicaments) {
	        this.listeMedicaments.set(listeMedicaments);
	    }

	    public void setCodeDocteur(int codeDocteur) {
	        this.codeDocteur.set(codeDocteur);
	    }

	    public void setNumMalade(int numMalade) {
	        this.numMalade.set(numMalade);
	    }

		
}
