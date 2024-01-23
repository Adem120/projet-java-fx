package model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Surveillance {
	private IntegerProperty codeService;
    private ObjectProperty<LocalDate> heureDebut;
    private ObjectProperty<LocalDate> heureFin;
    private IntegerProperty codeInfirmier;
    private IntegerProperty numSalle;

    // Constructors

    public Surveillance(int codeService, LocalDate heureDebut, LocalDate heureFin, int codeInfirmier, int numSalle) {
        this.codeService = new SimpleIntegerProperty(codeService);
        this.heureDebut = new SimpleObjectProperty<>(heureDebut);
        this.heureFin = new SimpleObjectProperty<>(heureFin);
        this.codeInfirmier = new SimpleIntegerProperty(codeInfirmier);
        this.numSalle = new SimpleIntegerProperty(numSalle);
    }

    public Surveillance() {
        this.codeService = new SimpleIntegerProperty();
        this.heureDebut = new SimpleObjectProperty<>();
        this.heureFin = new SimpleObjectProperty<>();
        this.codeInfirmier = new SimpleIntegerProperty();
        this.numSalle = new SimpleIntegerProperty();
    }

    // Getters

    public int getCodeService() {
        return codeService.get();
    }

    public IntegerProperty codeServiceProperty() {
        return codeService;
    }

    public LocalDate getHeureDebut() {
        return heureDebut.get();
    }

    public ObjectProperty<LocalDate> heureDebutProperty() {
        return heureDebut;
    }

    public LocalDate getHeureFin() {
        return heureFin.get();
    }

    public ObjectProperty<LocalDate> heureFinProperty() {
        return heureFin;
    }

    public int getCodeInfirmier() {
        return codeInfirmier.get();
    }

    public IntegerProperty codeInfirmierProperty() {
        return codeInfirmier;
    }

    public int getNumSalle() {
        return numSalle.get();
    }

    public IntegerProperty numSalleProperty() {
        return numSalle;
    }

    // Setters

    public void setCodeService(int codeService) {
        this.codeService.set(codeService);
    }

    public void setHeureDebut(LocalDate heureDebut) {
        this.heureDebut.set(heureDebut);
    }

    public void setHeureFin(LocalDate heureFin) {
        this.heureFin.set(heureFin);
    }

    public void setCodeInfirmier(int codeInfirmier) {
        this.codeInfirmier.set(codeInfirmier);
    }

    public void setNumSalle(int numSalle) {
        this.numSalle.set(numSalle);
    }
}
