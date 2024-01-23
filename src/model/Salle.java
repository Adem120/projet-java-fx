package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Salle {
	 private IntegerProperty numS;
	    private IntegerProperty nbLits;
	    private IntegerProperty service;

	    // Constructors

	    public Salle(int numS, int nbLits, int service) {
	        this.numS = new SimpleIntegerProperty(numS);
	        this.nbLits = new SimpleIntegerProperty(nbLits);
	        this.service = new SimpleIntegerProperty(service);
	    }

	    public Salle() {
	        this.numS = new SimpleIntegerProperty();
	        this.nbLits = new SimpleIntegerProperty();
	        this.service = new SimpleIntegerProperty();
	    }

	    // Getters

	    public int getNumS() {
	        return numS.get();
	    }

	    public IntegerProperty numSProperty() {
	        return numS;
	    }

	    public int getNbLits() {
	        return nbLits.get();
	    }

	    public IntegerProperty nbLitsProperty() {
	        return nbLits;
	    }

	    public int getService() {
	        return service.get();
	    }

	    public IntegerProperty serviceProperty() {
	        return service;
	    }

	    // Setters

	    public void setNumS(int numS) {
	        this.numS.set(numS);
	    }

	    public void setNbLits(int nbLits) {
	        this.nbLits.set(nbLits);
	    }

	    public void setService(int service) {
	        this.service.set(service);
	    }
	    public String toString() {
	    	return numS.get()+" ";
	    }
}
