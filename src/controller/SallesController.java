package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import cnxDB.connexion;
import javafx.beans.property.IntegerProperty;
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
import javafx.util.converter.IntegerStringConverter;
import model.Malade;
import model.Salle;
import model.Service;

public class SallesController  implements Initializable{
    @FXML
    private TableColumn<Salle, Integer> colnumS;
    @FXML
    private TableColumn<Salle, Integer> colnblits;
    @FXML
    private TableColumn<Salle, Integer> colService;
    @FXML
    private TableView<Salle> tableView;
    @FXML
    private ComboBox<Service> fService;
    ObservableList<Salle> salles = FXCollections.observableArrayList();
    ObservableList<Service> listeServices = FXCollections.observableArrayList();
	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getServiceList();
		// TODO Auto-generated method stub
		colnumS.setCellValueFactory(new PropertyValueFactory<>("numS"));
		colnblits.setCellValueFactory(new PropertyValueFactory<>("nbLits"));
		colService.setCellValueFactory(new PropertyValueFactory<>("service"));
		
		/*TableColumn<Salle, Salle> editColumn = new TableColumn<>("Edit");
		 // Define cell factory for edit column
        editColumn.setCellFactory(column -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            

            {
                // Define button click event
                editButton.setOnAction(event -> {
                    Salle salle = getTableRow().getItem();
                    if (salle != null) {
                        // Perform edit action on the selected salle
                        System.out.println("Editing salle: " + salle.getNumS());
                    }
                });
             // Set the CSS class for the button
                editButton.getStyleClass().add("edit-button");
            }

            @Override
            protected void updateItem(Salle salle, boolean empty) {
                super.updateItem(salle, empty);
                if (!empty) {
                    setGraphic(editButton);
                } else {
                    setGraphic(null);
                }
            }
        });
     // Add columns to TableView
        tableView.getColumns().add(editColumn);*/
		
		colnblits.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		colnblits.setOnEditCommit(event -> {
		        Salle s = event.getRowValue();
		        s.setNbLits(event.getNewValue());
		        updateSalle(s);
		    });
		
		fetchSalleData();
		tableView.setEditable(true);

	}
	private void fetchSalleData() {
	   

	    

	    try {
	    	// Créer une connexion à la base de données
		    connexion cn = new connexion();
		    Connection connection =(Connection) cn.getCn();
	        // Exécuter la requête SQL pour récupérer les données des salles
	        String query = "SELECT * FROM salle";
	        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();

	        // Parcourir les résultats et ajouter les salles à la liste
	        while (resultSet.next()) {
	            int numSalle = resultSet.getInt("numS");
	            int nbLits = resultSet.getInt("nbLits");
	            int codeService = resultSet.getInt("service");

	            Salle salle = new Salle(numSalle, nbLits, codeService);
	            salles.add(salle);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Gérer l'exception ou afficher un message d'erreur
	    }

	    tableView.setItems(salles);
	}
	private void updateSalle(Salle salle) {
	   

	    try {
	    	 // Créer une connexion à la base de données
		    connexion cn = new connexion();
		    Connection connection =(Connection) cn.getCn();
	        // Exécuter la requête SQL de mise à jour
	        String query = "UPDATE salle SET nbLits = ? WHERE numS = ?";
	        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
	        statement.setInt(1, salle.getNbLits());
	      
	        statement.setInt(2, salle.getNumS());
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Gérer l'exception ou afficher un message d'erreur
	    }
	}
	
	  @FXML
	    void handleAjoutSalle(ActionEvent event) {
		  try {
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AjouterSalle.fxml")) ;

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
	    @FXML
	    void filtrerParService(ActionEvent event) {
	    	salles.clear();
	    	try {
		    	// Créer une connexion à la base de données
			    connexion cn = new connexion();
			    Connection connection =(Connection) cn.getCn();
		        // Exécuter la requête SQL pour récupérer les données des salles
		        String query = "SELECT * FROM salle where service= ?";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
		        statement.setInt(1, fService.getValue().getCodeService());
		        ResultSet resultSet = statement.executeQuery();

		        // Parcourir les résultats et ajouter les salles à la liste
		        while (resultSet.next()) {
		            int numSalle = resultSet.getInt("numS");
		            int nbLits = resultSet.getInt("nbLits");
		            int codeService = resultSet.getInt("service");

		            Salle salle = new Salle(numSalle, nbLits, codeService);
		            salles.add(salle);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Gérer l'exception ou afficher un message d'erreur
		    }

		    tableView.setItems(salles);
	    }
	    
	    private void getServiceList() {
		    try {
		        // Créer une instance de connexion à la base de données
		        connexion cn = new connexion();
		        Connection connection = (Connection)cn.getCn();

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
		            listeServices.add(service);
		        }

		       
		        // Mettre à jour le ComboBox avec la liste des services
		        fService.getItems().setAll(listeServices);
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Gérer l'exception ou afficher un message d'erreur
		    }
		}


	    private String getServiceName( int serviceAffecte) throws SQLException {
	    	connexion cn = new connexion();
	        Connection connection =(Connection) cn.getCn();
	        String query = "SELECT nomService FROM service WHERE codeService = ?";
	        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
	        statement.setInt(1, serviceAffecte);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getString("nomService");
	        }
	        return "";
	    }
}
