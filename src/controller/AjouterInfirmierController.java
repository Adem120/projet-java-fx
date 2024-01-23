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
import model.Infirmier;
import model.Service;

public class AjouterInfirmierController implements Initializable{

	
	 @FXML
	    private TextField password;

	    @FXML
	    private TextField grade;

	    @FXML
	    private TextField adresse;

	    @FXML
	    private DatePicker dateNaiss;

	    @FXML
	    private TextField salaire;

	    @FXML
	    private TextField nom;

	    @FXML
	    private TextField prenom;

	    @FXML
	    private TextField numTel;

	    @FXML
	    private TextField email;

	    @FXML
	    void handleValiderAjout(ActionEvent event) {
	    	 // Get the input values from the text fields
	        String infirmierNom = nom.getText();
	        String infirmierPrenom = prenom.getText();
	        String infirmierAdresse = adresse.getText();
	        String infirmierNumTel = numTel.getText();
	        String infirmierEmail = email.getText();
	        LocalDate infirmierDateNaiss = dateNaiss.getValue();
	        double infirmierSalaire = Double.parseDouble(salaire.getText());
	        String infirmierGrade = grade.getText();
	        String infirmierPassword = password.getText();

	        // Create a new Infirmier object with the input values
	        Infirmier infirmier = new Infirmier(0,infirmierNom, infirmierPrenom, infirmierAdresse,infirmierDateNaiss, 
	        		infirmierNumTel
	                , infirmierSalaire, infirmierGrade,infirmierEmail, infirmierPassword);

	        // Save the Infirmier object to the database
	        saveInfirmier(infirmier);
	    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	
	
	private void saveInfirmier(Infirmier infirmier) {
       

        try {
        	 // Create a database connection
            connexion cn = new connexion();
            Connection connection = cn.getCn();
            // Prepare the SQL statement to insert the Infirmier into the database
            String query = "INSERT INTO infirmier (nom, prenom, adresse, numTel, email, dateNaiss, salaire, grade, password) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);

            // Set the parameter values for the prepared statement
            statement.setString(1, infirmier.getNom());
            statement.setString(2, infirmier.getPrenom());
            statement.setString(3, infirmier.getAdresse());
            statement.setString(4, infirmier.getNumTel());
            statement.setString(5, infirmier.getEmail());
            statement.setDate(6, Date.valueOf(infirmier.getDateNaiss()));
            statement.setDouble(7, infirmier.getSalaire());
            statement.setString(8, infirmier.getGrade());
            statement.setString(9, infirmier.getPassword());
            

            // Execute the prepared statement
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Infirmier saved successfully
                System.out.println("Infirmier saved successfully.");
            } else {
                // Failed to save the Infirmier
                System.out.println("Failed to save the Infirmier.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception or display an error message
        }
    }
	  
	
		
		
		
		
	
}
