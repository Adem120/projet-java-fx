package model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Docteur {

	   private IntegerProperty codeDocteur;
	    private StringProperty nom;
	    private StringProperty prenom;
	    private StringProperty adresse;
	    private ObjectProperty<LocalDate> dateNaiss;
	    private StringProperty numTel;
	    private StringProperty specialite;
	    private IntegerProperty serviceAffecte;
	    private StringProperty email;
	    private StringProperty password;

	    public Docteur(int codeDocteur, String nom, String prenom, String adresse, LocalDate dateNaiss,
	                   String numTel, String specialite, int serviceAffecte, String email, String password) {
	        this.codeDocteur = new SimpleIntegerProperty(codeDocteur);
	        this.nom = new SimpleStringProperty(nom);
	        this.prenom = new SimpleStringProperty(prenom);
	        this.adresse = new SimpleStringProperty(adresse);
	        this.dateNaiss = new SimpleObjectProperty<>(dateNaiss);
	        this.numTel = new SimpleStringProperty(numTel);
	        this.specialite = new SimpleStringProperty(specialite);
	        this.serviceAffecte = new SimpleIntegerProperty(serviceAffecte);
	        this.email = new SimpleStringProperty(email);
	        this.password = new SimpleStringProperty(password);
	    }

	    public Docteur() {
	        this.codeDocteur = new SimpleIntegerProperty();
	        this.nom = new SimpleStringProperty();
	        this.prenom = new SimpleStringProperty();
	        this.adresse = new SimpleStringProperty();
	        this.dateNaiss = new SimpleObjectProperty<>();
	        this.numTel = new SimpleStringProperty();
	        this.specialite = new SimpleStringProperty();
	        this.serviceAffecte = new SimpleIntegerProperty();
	        this.email = new SimpleStringProperty();
	        this.password = new SimpleStringProperty();
	    }
	    
	    public int getCodeDocteur() {
	        return codeDocteur.get();
	    }

	    public IntegerProperty codeDocteurProperty() {
	        return codeDocteur;
	    }

	    public void setCodeDocteur(int codeDocteur) {
	        this.codeDocteur.set(codeDocteur);
	    }

	    public String getNom() {
	        return nom.get();
	    }

	    public StringProperty nomProperty() {
	        return nom;
	    }

	    public void setNom(String nom) {
	        this.nom.set(nom);
	    }

	    public String getPrenom() {
	        return prenom.get();
	    }

	    public StringProperty prenomProperty() {
	        return prenom;
	    }

	    public void setPrenom(String prenom) {
	        this.prenom.set(prenom);
	    }

	    public String getAdresse() {
	        return adresse.get();
	    }

	    public StringProperty adresseProperty() {
	        return adresse;
	    }

	    public void setAdresse(String adresse) {
	        this.adresse.set(adresse);
	    }

	    public LocalDate getDateNaiss() {
	        return dateNaiss.get();
	    }

	    public ObjectProperty<LocalDate> dateNaissProperty() {
	        return dateNaiss;
	    }

	    public void setDateNaiss(LocalDate dateNaiss) {
	        this.dateNaiss.set(dateNaiss);
	    }

	    public String getNumTel() {
	        return numTel.get();
	    }

	    public StringProperty numTelProperty() {
	        return numTel;
	    }

	    public void setNumTel(String numTel) {
	        this.numTel.set(numTel);
	    }

	    public String getSpecialite() {
	        return specialite.get();
	    }

	    public StringProperty specialiteProperty() {
	        return specialite;
	    }
	    public void setSpecialite(String specialite) {
	        this.specialite.set(specialite);
	    }

	    public int getServiceAffecte() {
	        return serviceAffecte.get();
	    }

	    public IntegerProperty serviceAffecteProperty() {
	        return serviceAffecte;
	    }

	    public void setServiceAffecte(int serviceAffecte) {
	        this.serviceAffecte.set(serviceAffecte);
	    }

	    public String getEmail() {
	        return email.get();
	    }

	    public StringProperty emailProperty() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email.set(email);
	    }

	    public String getPassword() {
	        return password.get();
	    }

	    public StringProperty passwordProperty() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password.set(password);
	    }
	    
	    public String toString() {
	    	return (nom.get()+" "+prenom.get());
	    }
	
	
}
