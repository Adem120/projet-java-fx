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

public class AjouterServiceController implements Initializable{

	  @FXML
	    private TextField bloc;

	    @FXML
	    private ComboBox<Infirmier> infAffecte;

	    @FXML
	    private ComboBox<Docteur> chef;

	    @FXML
	    private TextField nom;

	    @FXML
	    private TextField numTel;
	    ObservableList<Docteur> docteurs = FXCollections.observableArrayList();
	    ObservableList<Infirmier> infirmiers = FXCollections.observableArrayList();

	    @FXML
	    void handleValiderAjout(ActionEvent event) {
	        String nomService = nom.getText();
	        String blocService = bloc.getText();
	        int chefService = chef.getValue().getCodeDocteur();
	        int infirmierAffecte = infAffecte.getValue().getCodeInfirmier();

	        // Vérifier si tous les champs sont saisis correctement
	        if (nomService.isEmpty() || blocService.isEmpty()  || chefService == 0
	                || infirmierAffecte == 0) {
	            // Afficher un message d'erreur ou gérer le cas où les champs sont vides
	            return;
	        }

	        // Créer un objet Service avec les données saisies
	        Service service = new Service();
	        service.setNomService(nomService);
	        service.setBloc(blocService);
	       
	        service.setChef(chefService);
	        service.setCodeInfirmier(infirmierAffecte);

	        // Enregistrer le service dans la base de données
	        try {
	            connexion cn = new connexion();
	            Connection connection = cn.getCn();

	            // Préparer la requête SQL pour insérer le service
	            String query = "INSERT INTO service (nomService, bloc, chef, codeInfirmier) "
	                    + "VALUES (?, ?, ?, ?)";
	            PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
	            statement.setString(1, service.getNomService());
	            statement.setString(2, service.getBloc());
	            
	            statement.setInt(3, service.getChef());
	            statement.setInt(4, service.getCodeInfirmier());

	            // Exécuter la requête SQL
	            int rowsInserted = statement.executeUpdate();
	            if (rowsInserted > 0) {
	                // Afficher un message de succès ou rediriger l'utilisateur vers une autre page
	                System.out.println("Service enregistré avec succès !");
	            } else {
	                // Gérer le cas où l'enregistrement a échoué
	                System.out.println("Erreur lors de l'enregistrement du service !");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Gérer l'exception ou afficher un message d'erreur
	        }
	    }
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		fetchDocteurData();
		getInfirmierList();
		
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
            chef.setItems(docteurs);
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
        infAffecte.setItems(infirmiers);
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception or display an error message
    }
}

		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
