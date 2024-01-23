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
import model.Salle;
import model.Service;

public class AjouterSalleController implements Initializable{

	
	  @FXML
	    private ComboBox<Service> service;

	    @FXML
	    private TextField numS;

	    @FXML
	    private TextField nbLits;
	    ObservableList<Service> services = FXCollections.observableArrayList();
	    @FXML
	    void handleValiderAjout(ActionEvent event) {
	    	 // Create a new Salle object with the entered data
	        int numSalle = Integer.parseInt(numS.getText());
	        int nombreLits = Integer.parseInt(nbLits.getText());
	        
	        Salle salle = new Salle();
	        salle.setNbLits(nombreLits);
	        salle.setNumS(numSalle);
	        if(service.getValue()!=null) {
	        int  selectedService = service.getValue().getCodeService();
	        salle.setService(selectedService);
	    }
	  

	        // Save the salle to the database
	        saveSalle(salle);

	        // Clear the input fields
	        numS.clear();
	        nbLits.clear();
	        service.getSelectionModel().clearSelection();
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		getServiceList();
	}

	private void getServiceList() {
	    try {
	        // Créer une instance de connexion à la base de données
	        connexion cn = new connexion();
	        Connection connection = cn.getCn();

	        // Récupérer la liste des services depuis la base de données
	        String query = "SELECT * FROM service";
	        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();

	       

	        // Parcourir les résultats du resultSet et ajouter les services à la liste temporaire
	        while (resultSet.next()) {
	            int serviceId = resultSet.getInt("codeService");
	            String serviceName = resultSet.getString("nomService");
	            Service service = new Service();
	            service.setCodeService(serviceId);
	            service.setNomService(serviceName);
	            services.add(service);
	        }

	       
	        // Mettre à jour le ComboBox avec la liste des services
	        service.getItems().setAll(services);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Gérer l'exception ou afficher un message d'erreur
	    }
	}
		
	   
		
		
		
private void saveSalle(Salle salle) {
    // Create a connection to the database
    connexion cn = new connexion();
    Connection connection = cn.getCn();

    try {
        // Prepare the SQL statement to insert the salle data into the database
        String query = "INSERT INTO salle (numS, nbLits, Service) VALUES (?, ?, ?)";
        PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
        statement.setInt(1, salle.getNumS());
        statement.setInt(2, salle.getNbLits());
        
        statement.setInt(3, salle.getService());

        // Execute the SQL statement
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Salle added successfully");
        } else {
            System.out.println("Failed to add salle");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception or display an error message
    } 
    
    
}	

}
		
		
		
		
		
		
		
		
		
		
		
		
	

