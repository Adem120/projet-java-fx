package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Service {
	  private IntegerProperty codeService;
	    private StringProperty nomService;
	    private StringProperty bloc;
	    private IntegerProperty chef;
	    private IntegerProperty codeInfirmier;

	    // Constructors

	    public Service(int codeService, String nomService, String bloc, int chef, int codeInfirmier) {
	        this.codeService = new SimpleIntegerProperty(codeService);
	        this.nomService = new SimpleStringProperty(nomService);
	        this.bloc = new SimpleStringProperty(bloc);
	        this.chef = new SimpleIntegerProperty(chef);
	        this.codeInfirmier = new SimpleIntegerProperty(codeInfirmier);
	    }

	    public Service() {
	        this.codeService = new SimpleIntegerProperty();
	        this.nomService = new SimpleStringProperty();
	        this.bloc = new SimpleStringProperty();
	        this.chef = new SimpleIntegerProperty();
	        this.codeInfirmier = new SimpleIntegerProperty();
	    }

	    // Getters

	    public int getCodeService() {
	        return codeService.get();
	    }

	    public IntegerProperty codeServiceProperty() {
	        return codeService;
	    }

	    public String getNomService() {
	        return nomService.get();
	    }

	    public StringProperty nomServiceProperty() {
	        return nomService;
	    }

	    public String getBloc() {
	        return bloc.get();
	    }

	    public StringProperty blocProperty() {
	        return bloc;
	    }

	    public int getChef() {
	        return chef.get();
	    }

	    public IntegerProperty chefProperty() {
	        return chef;
	    }

	    public int getCodeInfirmier() {
	        return codeInfirmier.get();
	    }

	    public IntegerProperty codeInfirmierProperty() {
	        return codeInfirmier;
	    }

	    // Setters

	    public void setCodeService(int codeService) {
	        this.codeService.set(codeService);
	    }

	    public void setNomService(String nomService) {
	        this.nomService.set(nomService);
	    }

	    public void setBloc(String bloc) {
	        this.bloc.set(bloc);
	    }

	    public void setChef(int chef) {
	        this.chef.set(chef);
	    }

	    public void setCodeInfirmier(int codeInfirmier) {
	        this.codeInfirmier.set(codeInfirmier);
	    }
	    
	    public String toString() {
	    	return this.nomService.get();
	    }
}
