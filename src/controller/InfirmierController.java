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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import model.Docteur;
import model.Infirmier;

public class InfirmierController implements Initializable{

	
    @FXML
    private TableColumn<Infirmier, String> colnom;

    @FXML
    private TableColumn<Infirmier, LocalDate> coldateNaiss;

    @FXML
    private TableColumn<Infirmier, String> colgrade;

    @FXML
    private TableColumn<Infirmier, String> colprenom;

    @FXML
    private TableColumn<Infirmier, String> colnumTel;

    @FXML
    private TableColumn<Infirmier, String> coladresse;

    @FXML
    private TableView<Infirmier> tableView;

    @FXML
    private TableColumn<Infirmier, String> colemail;

    @FXML
    private TableColumn<Infirmier, Double> colsalaire;
    private ObservableList<Infirmier> infs = FXCollections.observableArrayList();

    @FXML
    void handleAjoutInfirmier(ActionEvent event) {
    	 try {
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AjouterInfirmier.fxml")) ;

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
		
		 // Lier les colonnes de la TableView aux propriétés de la classe Infirmier
	    colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
	    coldateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
	    colgrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
	    colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
	    colnumTel.setCellValueFactory(new PropertyValueFactory<>("numTel"));
	    coladresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
	    colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
	    colsalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));

	    colnom.setCellFactory(TextFieldTableCell.forTableColumn());
	    colnom.setOnEditCommit(event -> {
	        Infirmier infirmier = event.getRowValue();
	        infirmier.setNom(event.getNewValue());
	        updateInfirmier(infirmier);
	    });


	    colgrade.setCellFactory(TextFieldTableCell.forTableColumn());
	    colgrade.setOnEditCommit(event -> {
	        Infirmier infirmier = event.getRowValue();
	        infirmier.setGrade(event.getNewValue());
	        updateInfirmier(infirmier);
	    });

	    colprenom.setCellFactory(TextFieldTableCell.forTableColumn());
	    colprenom.setOnEditCommit(event -> {
	        Infirmier infirmier = event.getRowValue();
	        infirmier.setPrenom(event.getNewValue());
	        updateInfirmier(infirmier);
	    });

	    colnumTel.setCellFactory(TextFieldTableCell.forTableColumn());
	    colnumTel.setOnEditCommit(event -> {
	        Infirmier infirmier = event.getRowValue();
	        infirmier.setNumTel(event.getNewValue());
	        updateInfirmier(infirmier);
	    });

	    coladresse.setCellFactory(TextFieldTableCell.forTableColumn());
	    coladresse.setOnEditCommit(event -> {
	        Infirmier infirmier = event.getRowValue();
	        infirmier.setAdresse(event.getNewValue());
	        updateInfirmier(infirmier);
	    });

	    colemail.setCellFactory(TextFieldTableCell.forTableColumn());
	    colemail.setOnEditCommit(event -> {
	        Infirmier infirmier = event.getRowValue();
	        infirmier.setEmail(event.getNewValue());
	        updateInfirmier(infirmier);
	    });
	    
	    colsalaire.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
	    colsalaire.setOnEditCommit(event -> {
	        Infirmier infirmier = event.getRowValue();
	        infirmier.setSalaire(event.getNewValue());
	        updateInfirmier(infirmier);
	    });
	    
	    this.tableView.setEditable(true);
		
		 // Créer une instance de connexion à la base de données
	    connexion cn = new connexion();
	    Connection connection = (Connection) cn.getCn();

	    try {
	        // Récupérer la liste des infirmiers depuis la base de données
	        String query = "SELECT * FROM infirmier";
	        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();

	       

	        // Parcourir les résultats du resultSet et ajouter les infirmiers à la liste
	        while (resultSet.next()) {
	            int id = resultSet.getInt("codeInfirmier");
	            String nom = resultSet.getString("nom");
	            LocalDate dateNaiss = resultSet.getDate("dateNaiss").toLocalDate();
	            String grade = resultSet.getString("grade");
	            String prenom = resultSet.getString("prenom");
	            String numTel = resultSet.getString("numTel");
	            String adresse = resultSet.getString("adresse");
	            String email = resultSet.getString("email");
	            double salaire = resultSet.getDouble("salaire");
	            String password = resultSet.getString("password");
	            
	            
	            
	            Infirmier infirmier = new Infirmier(id, nom, prenom, adresse,dateNaiss, numTel, salaire,grade,  email,password);
	            infs.add(infirmier);
	        }

	        

	        // Assigner l'ObservableList au TableView
	        tableView.setItems(infs);

	        // Fermer le resultSet et la ressource PreparedStatement
	        resultSet.close();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Gérer l'exception ou afficher un message d'erreur
	    }
	}
	
	public void updateInfirmier(Infirmier infirmier) {
	    try {
	        // Créer une instance de connexion à la base de données
	        connexion cn = new connexion();
	        Connection connection =(Connection) cn.getCn();

	        // Préparer la requête SQL pour mettre à jour l'infirmier
	        String query = "UPDATE infirmier SET nom = ?, dateNaiss = ?, grade = ?, prenom = ?, numTel = ?, adresse = ?, email = ?, salaire = ? WHERE codeInfirmier = ?";
	        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
	        statement.setString(1, infirmier.getNom());
	        statement.setDate(2, java.sql.Date.valueOf(infirmier.getDateNaiss()));
	        statement.setString(3, infirmier.getGrade());
	        statement.setString(4, infirmier.getPrenom());
	        statement.setString(5, infirmier.getNumTel());
	        statement.setString(6, infirmier.getAdresse());
	        statement.setString(7, infirmier.getEmail());
	        statement.setDouble(8, infirmier.getSalaire());
	        statement.setInt(9, infirmier.getCodeInfirmier());

	        // Exécuter la requête de mise à jour
	        statement.executeUpdate();

	     

	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Gérer l'exception ou afficher un message d'erreur
	    }
	}
    
    
    
    
}
