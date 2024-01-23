package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import model.Docteur;
import model.Malade;
import model.Service;

public class AjouterMaladeController implements Initializable{
    @FXML
    private ComboBox<Docteur> docteurSoin;

    @FXML
    private TextField adresse;


    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField numTel;
    ObservableList<Docteur> docteurs = FXCollections.observableArrayList();

    @FXML
    void handleValiderAjout(ActionEvent event) {
        // Create a new Malade object from the entered data
        String maladeNom = nom.getText();
        String maladePrenom = prenom.getText();
        String maladeNumTel = numTel.getText();
        String Maladeadresse =  adresse.getText();

        Docteur selectedDocteur = docteurSoin.getValue();
        int codeDocteur = selectedDocteur.getCodeDocteur();
 
        Malade malade = new Malade();
        malade.setIdDocteur(codeDocteur);
        malade.setAdresse(Maladeadresse);
        malade.setNom(maladePrenom);
        malade.setNumTel(maladeNumTel);
        malade.setPrenom(maladePrenom);

        // Save the Malade to the database
        saveMalade(malade);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		fetchDocteurData();
	}

	private void fetchDocteurData() {
        
        // Create a connection to the database
        connexion cn = new connexion();
        Connection connection = (Connection) cn.getCn();

        try {
            // Execute the SQL query to retrieve the doctors data
            String query = "SELECT * FROM docteur";
            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Iterate over the result set and create Docteur objects
            while (resultSet.next()) {
                int codeDocteur = resultSet.getInt("codeDocteur");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
               
                Docteur docteur = new Docteur();
                docteur.setNom(nom);
                docteur.setPrenom(prenom);
                docteur.setCodeDocteur(codeDocteur);
                docteurs.add(docteur);
            }
            docteurSoin.setItems(docteurs);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception or display an error message
        } 

      
    }
 
	
	
	
	 private void saveMalade(Malade malade) {
	        // Create a connection to the database
	        connexion cn = new connexion();
	        Connection connection = (Connection) cn.getCn();

	        try {
	            // Prepare the SQL statement to insert the malade data into the database
	            String query = "INSERT INTO malade (nom, prenom, numTel,adresse, docteur) VALUES (?,?, ?, ?, ?)";
	            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
	            statement.setString(1, malade.getNom());
	            statement.setString(2, malade.getPrenom());
	            statement.setString(3, malade.getNumTel());
	            statement.setString(4, malade.getAdresse());
	            statement.setInt(5, malade.getIdDocteur());

	            // Execute the SQL statement
	            int rowsAffected = statement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Malade added successfully");
	            } else {
	                System.out.println("Failed to add malade");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception or display an error message
	        } 
	    }
	 
	 
	 
	}
	

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	

