package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import cnxDB.connexion;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Docteur;
import model.Infirmier;
import model.Service;

public class ServicesController implements Initializable {
	   @FXML
	    private TableColumn<Service, String> colnom;

	    @FXML
	    private TableColumn<Service, String> colchef;

	    @FXML
	    private TableView<Service> tableView;

	    @FXML
	    private TableColumn<Service, String> colbloc;
	    @FXML
	    private ComboBox<Docteur> listedocteurs;
	    int serviceIdtoModify;
	    @FXML
	    private ComboBox<Infirmier> listeInfirmiers;

	    @FXML
	    private TableColumn<Service, String> colinfirmier;

	    @FXML
	    void handleAjoutService(ActionEvent event) {
	    	 try {
		            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AjouterService.fxml")) ;

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
	    ObservableList<Service> services = FXCollections.observableArrayList();
	    ObservableList<Docteur> docteurs = FXCollections.observableArrayList();
	    ObservableList<Infirmier> infirmiers = FXCollections.observableArrayList();
	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			fetchDocteurs();
			getInfirmierList();
			// TODO Auto-generated method stub
			// Lier les colonnes de la TableView aux propriétés de la classe Infirmier
		    colnom.setCellValueFactory(new PropertyValueFactory<Service, String>("nomService"));
		    colchef.setCellValueFactory(new PropertyValueFactory<Service, String>("chef"));
		    colbloc.setCellValueFactory(new PropertyValueFactory<Service, String>("bloc"));
		    colinfirmier.setCellValueFactory(new PropertyValueFactory<Service, String>("codeInfirmier"));
		    
		    
		    // Set the event handlers for editing the columns
	        colnom.setOnEditCommit(event -> {
	            Service service = event.getRowValue();
	            service.setNomService(event.getNewValue());
	            updateService(service);
	        });

	        

	        colchef.setCellValueFactory(new PropertyValueFactory<>("chef"));
	        colchef.setCellValueFactory(cellData -> {
	            ObjectProperty<String> chefProperty = new SimpleObjectProperty<>();
	            int chefId = cellData.getValue().getChef();
	            String chefName = "yassine";
	            chefName = getDocteur(chefId);
	            chefProperty.set(chefName);
	            return chefProperty;
	        });
	        
	        colinfirmier.setCellValueFactory(new PropertyValueFactory<>("infirmier"));
	        colinfirmier.setCellValueFactory(cellData -> {
	            ObjectProperty<String> infProperty = new SimpleObjectProperty<>();
	            int infId = cellData.getValue().getCodeInfirmier();
	            String infName = "yassine";
	            infName = getInfirmier(infId);
	            infProperty.set(infName);
	            return infProperty;
	        });
	        
	        
	        
	        colbloc.setOnEditCommit(event -> {
	            Service service = event.getRowValue();
	            service.setBloc(event.getNewValue());
	            updateService(service);
	        });

	      
	        
	        tableView.setEditable(true);

	        fetchServices();
		   
		   
		  
		}
		
		private String getInfirmier(int infirmierId) {
	    try {
		        // Créer une instance de connexion à la base de données
		        connexion cn = new connexion();
		        Connection connection =(Connection) cn.getCn();

		        // Récupérer le nom de l'infirmier à partir de l'ID
		        String query = "SELECT nom,prenom FROM infirmier WHERE codeInfirmier = ?";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
		        statement.setInt(1, infirmierId);
		        ResultSet resultSet = statement.executeQuery();

		        // Récupérer le nom de l'infirmier s'il existe
		        if (resultSet.next()) {
		            String infirmierNom = resultSet.getString("nom");
		            String infirmierPrenom = resultSet.getString("prenom");
		            return infirmierNom+" "+infirmierPrenom;
		        }

		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Gérer l'exception ou afficher un message d'erreur
		    }

		    return null;
		}

		private String getDocteur(int docteurId) {
		    try {
		        // Créer une instance de connexion à la base de données
		        connexion cn = new connexion();
		        Connection connection =(Connection) cn.getCn();

		        // Récupérer le nom du docteur à partir de l'ID
		        String query = "SELECT nom,prenom FROM docteur WHERE codeDocteur = ?";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
		        statement.setInt(1, docteurId);
		        ResultSet resultSet = statement.executeQuery();

		        // Récupérer le nom du docteur s'il existe
		        if (resultSet.next()) {
		            String docteurNom = resultSet.getString("nom");
		            String docteurPrenom = resultSet.getString("prenom");
		            return docteurNom+" "+docteurPrenom;
		        }

		       
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Gérer l'exception ou afficher un message d'erreur
		    }

		    return null;
		}
public void fetchDocteurs() {
			

	        try {
	        	connexion cn = new connexion();
	            Connection connection =(Connection) cn.getCn();
	            String query = "SELECT * FROM docteur";
	            PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                int codeDocteur = resultSet.getInt("codeDocteur");
	                String nom = resultSet.getString("nom");
	                String prenom = resultSet.getString("prenom");
	                String adresse = resultSet.getString("adresse");
	                LocalDate dateNaiss = resultSet.getDate("dateNaiss").toLocalDate();
	                String numTel = resultSet.getString("numTel");
	                String specialite = resultSet.getString("specialite");
	                int serviceAffecte = resultSet.getInt("serviceAffecte");
	                String email = resultSet.getString("email");
	                String password = resultSet.getString("password");

	                Docteur docteur = new Docteur(codeDocteur, nom, prenom, adresse, dateNaiss, numTel, specialite, serviceAffecte, email, password);
	                docteurs.add(docteur);
	            }

	            // Set the retrieved data to the TableView
	            listedocteurs.setItems(docteurs);

	         
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			
		}
		public void updateService(Service s) {
			 try {
		            connexion cn = new connexion();
		            Connection connection =(Connection) cn.getCn();
//		            String query = "UPDATE docteur SET nom=?, prenom=?, adresse=?, dateNaiss=?, numTel=?, specialite=?, serviceAffecte=?, email=?, password=? WHERE codeDocteur=?";
		            String query = "UPDATE service SET nomService=?, bloc=?, chef=?, codeInfirmier=? WHERE codeService=?";

		            PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
		            
		            // Set the values for the prepared statement
		            statement.setString(1, s.getNomService());
		            statement.setString(2, s.getBloc());
		            statement.setInt(3, s.getChef());
		            statement.setInt(4, s.getCodeInfirmier());
		            statement.setInt(5, s.getCodeService());
		            

		            
		            
		            // Execute the update statement
		            int rowsAffected = statement.executeUpdate();
		            
		            if (rowsAffected > 0) {
		                System.out.println("service updated successfully!");
		                fetchServices();
		            } else {
		                System.out.println("Failed to update the docteur.");
		            }
		            
		          
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			
			
		}

		@FXML
	    void changeChef(ActionEvent event) {
		//	serviceIdtoModify= tableView.getSelectionModel().getSelectedItem().getCodeService();
	    	
            
            
            if(tableView.getSelectionModel().getSelectedItem()!=null) {
            	Service service = tableView.getSelectionModel().getSelectedItem();
                service.setChef(listedocteurs.getValue().getCodeDocteur());
                System.out.println("aaaa"+tableView.getSelectionModel().getSelectedItem()+"docteur "+listedocteurs.getValue() );
                updateService(service);
            }
            else {
            	
            	Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("pas de champ selectionné");
                alert.setHeaderText(null);
                alert.setContentText("s'il vous plait veillez selectionner un champ");
                alert.showAndWait();
            	
            }
            
            
	    }

	    @FXML
	    void changeInfirmier(ActionEvent event) {
	    //	serviceIdtoModify= tableView.getSelectionModel().getSelectedItem().getCodeService();
	    	Service service = tableView.getSelectionModel().getSelectedItem();
            service.setCodeInfirmier(listeInfirmiers.getValue().getCodeInfirmier());
            updateService(service);
	    }
	    @FXML
	    void changeInfirmierBtn(ActionEvent event) {
	    	serviceIdtoModify= tableView.getSelectionModel().getSelectedItem().getCodeService();
	    }

	    @FXML
	    void changeChefBtn(ActionEvent event) {
	    	serviceIdtoModify= tableView.getSelectionModel().getSelectedItem().getCodeService();
			System.out.println("code = "+serviceIdtoModify);
	    }

private void getInfirmierList() {
    try {
        // Create a connection to the database
        connexion cn = new connexion();
        Connection connection =(Connection) cn.getCn();

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
        listeInfirmiers.setItems(infirmiers);
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception or display an error message
    }
}





public void fetchServices() {
	services.clear();
    try {
        // Create a database connection
        connexion cn = new connexion();
        Connection connection =(Connection) cn.getCn();

        // Retrieve the service data from the database
        String query = "SELECT * FROM service";
        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        // Iterate through the resultSet and add services to the list
        while (resultSet.next()) {
            int serviceId = resultSet.getInt("codeService");
            String serviceName = resultSet.getString("nomService");
            int chefId = resultSet.getInt("chef");
            String bloc = resultSet.getString("bloc");
            int infirmierId = resultSet.getInt("codeInfirmier");
            
            Service service = new Service(serviceId, serviceName, bloc,chefId, infirmierId);
            
            services.add(service);
        }
        
        tableView.setItems(services);

       
        
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception or display an error message
    }
}
}



