package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.mysql.jdbc.PreparedStatement;

import cnxDB.connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Docteur;
import model.Infirmier;
import model.Salle;
import model.Service;
import model.Surveillance;

public class AjouterSurveillanceController implements Initializable{
	@FXML
    private TextField hDebut;

    @FXML
    private TextField minDebut;
    @FXML
    private DatePicker dateDebut;

    @FXML
    private ComboBox<Salle> salle;

    @FXML
    private ComboBox<Infirmier> infirmier;

    @FXML
    private DatePicker dateFin;
    ObservableList<Infirmier> infirmiers = FXCollections.observableArrayList();
    ObservableList<Salle> salles = FXCollections.observableArrayList();
    @FXML
    void handleValiderAjout(ActionEvent event) {
        // Create a new Surveillance object with the entered data
        LocalDate debut = dateDebut.getValue();
        LocalDate fin = dateFin.getValue();
        int selectedSalle = salle.getValue().getNumS();
        int selectedInfirmier = infirmier.getValue().getCodeInfirmier();

        Surveillance surveillance = new Surveillance(0,debut,fin, selectedInfirmier,selectedSalle);

        // Save the surveillance to the database
        saveSurveillance(surveillance);

        // Clear the input fields
        dateDebut.setValue(null);
        dateFin.setValue(null);
        salle.getSelectionModel().clearSelection();
        infirmier.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
        

    
        // Get the list of salles and infirmiers
        getSalleList();
        getInfirmierList();
    }

	 private void getSalleList() {
	        try {
	            // Create a connection to the database
	            connexion cn = new connexion();
	            Connection connection = cn.getCn();

	            // Retrieve the list of salles from the database
	            String query = "SELECT * FROM salle";
	            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
	            ResultSet resultSet = statement.executeQuery();

	            
	          

	            // Iterate over the result set and add the salles to the list
	            while (resultSet.next()) {
	                int numSalle = resultSet.getInt("numS");
	                int nbLits = resultSet.getInt("nbLits");
	                int codeService = resultSet.getInt("service");
	                Salle salle = new Salle(numSalle, nbLits, codeService);
	                salles.add(salle);
	            }

	            // Update the ComboBox with the list of salles
	            salle.setItems(salles);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception or display an error message
	        }
	    }	
	 private void getInfirmierList() {
	        try {
	            // Create a connection to the database
	            connexion cn = new connexion();
	            Connection connection = cn.getCn();

	            // Retrieve the list of infirmiers from the database
	            String query = "SELECT * FROM infirmier";
	            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
	            ResultSet resultSet = statement.executeQuery();

	          

	            // Iterate over the result set and add the infirmiers to the list
	            while (resultSet.next()) {
	                int codeInfirmier = resultSet.getInt("codeInfirmier");
	                String nom = resultSet.getString("nom");
	                String prenom = resultSet.getString("prenom");
	            
	                Infirmier infirmier = new Infirmier();
	                infirmier.setNom(nom);
	                infirmier.setPrenom(prenom);
	                infirmier.setCodeInfirmier(codeInfirmier);
	                infirmiers.add(infirmier);
	            }

	            // Update the ComboBox with the list of infirmiers
	            infirmier.setItems(infirmiers);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception or display an error message
	        }
	    }
	
	 private void saveSurveillance(Surveillance surveillance) {
		    try {
		        // Create a connection to the database
		        connexion cn = new connexion();
		        Connection connection = cn.getCn();

		        // Prepare the SQL statement to insert the surveillance data
		        String query = "INSERT INTO surveillance (numSalle, codeInfirmier, heureDebut, heureFin) VALUES (?, ?, ?, ?)";
		        PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
		        statement.setInt(1, surveillance.getNumSalle());
		        statement.setInt(2, surveillance.getCodeInfirmier());
		        statement.setDate(3, Date.valueOf(surveillance.getHeureDebut()));
		        statement.setDate(4, Date.valueOf(surveillance.getHeureFin()));

		        // Execute the SQL statement
		        statement.executeUpdate();

		       
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle the exception or display an error message
		    }
		}
		
	
}
