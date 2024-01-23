package model;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Infirmier {
	private IntegerProperty codeInfirmier;
    private StringProperty nom;
    private StringProperty prenom;
    private StringProperty adresse;
    private ObjectProperty<LocalDate> dateNaiss;
    private StringProperty numTel;
    private DoubleProperty salaire;
    private StringProperty grade;
    private StringProperty email;
    private StringProperty password;
  
	public Infirmier(int codeInfirmier, String nom, String prenom, String adresse, LocalDate dateNaiss,
            String numTel, double salaire, String grade, String email, String password) {
this.codeInfirmier = new SimpleIntegerProperty(codeInfirmier);
this.nom = new SimpleStringProperty(nom);
this.prenom = new SimpleStringProperty(prenom);
this.adresse = new SimpleStringProperty(adresse);
this.dateNaiss = new SimpleObjectProperty<>(dateNaiss);
this.numTel = new SimpleStringProperty(numTel);
this.salaire = new SimpleDoubleProperty(salaire);
this.grade = new SimpleStringProperty(grade);
this.email = new SimpleStringProperty(email);
this.password = new SimpleStringProperty(password);
}

public Infirmier() {
this.codeInfirmier = new SimpleIntegerProperty();
this.nom = new SimpleStringProperty();
this.prenom = new SimpleStringProperty();
this.adresse = new SimpleStringProperty();
this.dateNaiss = new SimpleObjectProperty<>();
this.numTel = new SimpleStringProperty();
this.salaire = new SimpleDoubleProperty();
this.grade = new SimpleStringProperty();
this.email = new SimpleStringProperty();
this.password = new SimpleStringProperty();
}
// Getters

public int getCodeInfirmier() {
    return codeInfirmier.get();
}

public IntegerProperty codeInfirmierProperty() {
    return codeInfirmier;
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

public LocalDate getDateNaiss() {
    return dateNaiss.get();
}

public ObjectProperty<LocalDate> dateNaissProperty() {
    return dateNaiss;
}

public String getNumTel() {
    return numTel.get();
}

public StringProperty numTelProperty() {
    return numTel;
}

public double getSalaire() {
    return salaire.get();
}

public DoubleProperty salaireProperty() {
    return salaire;
}

public String getGrade() {
    return grade.get();
}

public StringProperty gradeProperty() {
    return grade;
}

public String getEmail() {
    return email.get();
}

public StringProperty emailProperty() {
    return email;
}

public String getPassword() {
    return password.get();
}

public StringProperty passwordProperty() {
    return password;
}

// Setters

public void setCodeInfirmier(int codeInfirmier) {
    this.codeInfirmier.set(codeInfirmier);
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

public void setDateNaiss(LocalDate dateNaiss) {
    this.dateNaiss.set(dateNaiss);
}

public void setNumTel(String numTel) {
    this.numTel.set(numTel);
}

public void setSalaire(double salaire) {
    this.salaire.set(salaire);
}

public void setGrade(String grade) {
    this.grade.set(grade);
}

public void setEmail(String email) {
    this.email.set(email);
}

public void setPassword(String password) {
    this.password.set(password);
}

public String toString() {
	return nom.get()+" "+prenom.get();
}
	
}
