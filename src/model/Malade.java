package model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Malade {

	
	 private IntegerProperty numMalade;
	    private StringProperty nom;
	    private StringProperty prenom;
	    private StringProperty adresse;
	    private StringProperty numTel;
	    private IntegerProperty idDocteur;
	    private ObjectProperty<LocalDate> dateVisite;

	 // Constructors

	    public Malade(int numMalade, String nom, String prenom, String adresse, String numTel,int idDocteur,LocalDate dateV) {
	        this.numMalade = new SimpleIntegerProperty(numMalade);
	        this.nom = new SimpleStringProperty(nom);
	        this.prenom = new SimpleStringProperty(prenom);
	        this.adresse = new SimpleStringProperty(adresse);
	        this.numTel = new SimpleStringProperty(numTel);
	        this.idDocteur=new SimpleIntegerProperty(idDocteur);
	        this.dateVisite=new SimpleObjectProperty<>(dateV);
	        
	    }

	    public Malade() {
	        this.numMalade = new SimpleIntegerProperty();
	        this.nom = new SimpleStringProperty();
	        this.prenom = new SimpleStringProperty();
	        this.adresse = new SimpleStringProperty();
	        this.numTel = new SimpleStringProperty();
	        this.dateVisite = new SimpleObjectProperty<>();
	    }
	    
	 // Getters

	    public int getNumMalade() {
	        return numMalade.get();
	    }

	    public IntegerProperty numMaladeProperty() {
	        return numMalade;
	    }

	    public String getNom() {
	        return nom.get();
	    }

	    public StringProperty nomProperty() {
	        return nom;
	    }

	    public String getPrenom() {
	        return prenom.get();
	    }

	    public StringProperty prenomProperty() {
	        return prenom;
	    }

	    public String getAdresse() {
	        return adresse.get();
	    }

	    public StringProperty adresseProperty() {
	        return adresse;
	    }

	    public String getNumTel() {
	        return numTel.get();
	    }

	    public StringProperty numTelProperty() {
	        return numTel;
	    }

	    // Setters

	    public void setNumMalade(int numMalade) {
	        this.numMalade.set(numMalade);
	    }

	    public void setNom(String nom) {
	        this.nom.set(nom);
	    }

	    public void setPrenom(String prenom) {
	        this.prenom.set(prenom);
	    }

	    public void setAdresse(String adresse) {
	        this.adresse.set(adresse);
	    }

	    public void setNumTel(String numTel) {
	        this.numTel.set(numTel);
	    }
	    
	    public int getIdDocteur() {
	    	return idDocteur.get();
	    }

	    public void setIdDocteur(int idDocteur) {
	    	this.idDocteur.set(idDocteur);
	    }

		public LocalDate getDateVisite() {
			return dateVisite.get();
		}

		public void setDateVisite(LocalDate dateVisite) {
			this.dateVisite.set(dateVisite);
		}
	  
	
}
