package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import cnxDB.connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import model.Docteur;
import model.Surveillance;




public class SurveillancesController implements Initializable {

	
	@FXML
    private TableColumn<Surveillance, Integer> colnumSalle;

    @FXML
    private TableColumn<Surveillance, Integer> colcodeInfirmier;

    @FXML
    private TableColumn<Surveillance, LocalDate> colheureFin;

    @FXML
    private TableColumn<Surveillance, Integer> colcodeServeillance;

    @FXML
    private TableView<Surveillance> tableView;

    @FXML
    private TableColumn<Surveillance, LocalDate> colheureDebut;
    ObservableList<Surveillance> surveillances = FXCollections.observableArrayList();
    @FXML
    void handleAjoutSalle(ActionEvent event) {
    	 try {
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AjouterSurveillance.fxml")) ;

	            Parent root = (Parent) fxmlLoader.load() ;

	            Stage stage = new Stage();
	            Scene scene = new Scene(root,1260,774);
	    		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());

	            stage.setScene(scene);
	            stage.show();
	           ((Node)(event.getSource())).getScene().getWindow().hide();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		colnumSalle.setCellValueFactory(new PropertyValueFactory<>("numSalle"));
		colcodeInfirmier.setCellValueFactory(new PropertyValueFactory<>("codeInfirmier"));
		colheureFin.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
		colcodeServeillance.setCellValueFactory(new PropertyValueFactory<>("codeService"));
		colheureDebut.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
		
		
		colheureDebut.setOnEditCommit(event -> {
	        Surveillance surveillance = event.getRowValue();
	        surveillance.setHeureDebut(event.getNewValue());
	        //updateSurveillance(surveillance);
	    });
		
		
		
		
		
		fetchSurveillanceData();
		tableView.setEditable(true);
		
	
	}

	
	private void fetchSurveillanceData() {
	    // Créer une connexion à la base de données
	    connexion cn = new connexion();
	    Connection connection =(Connection) cn.getCn();

	    try {
	        // Exécuter la requête SQL pour récupérer les données de surveillance
	        String query = "SELECT * FROM surveillance";
	        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();

	        

	        // Parcourir le résultat de la requête et créer des objets Surveillance
	        while (resultSet.next()) {
	            int numSalle = resultSet.getInt("numSalle");
	            int codeInfirmier = resultSet.getInt("codeInfirmier");
	            LocalDate heureDebut = resultSet.getDate("heureDebut").toLocalDate();
	            LocalDate heureFin = resultSet.getDate("heureFin").toLocalDate();
	            int codeService = resultSet.getInt("codeServ");

	            Surveillance surveillance = new Surveillance(codeService, heureDebut, heureFin, codeInfirmier, numSalle);
	            surveillances.add(surveillance);
	        }
	        // Ajouter les objets Surveillance à la TableView
	        tableView.getItems().addAll(surveillances);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Gérer l'exception ou afficher un message d'erreur
	    }
	}


	

	
	
	
	
	
}
