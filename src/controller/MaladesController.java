package controller;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import cnxDB.connexion;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import model.Docteur;
import model.Infirmier;
import model.Malade;
import model.Ordonnance;
import model.Service;

public class MaladesController  implements Initializable{

	
	 @FXML
	    private TableColumn<Malade, String> colnom;

	  @FXML
	    private ComboBox<Docteur> fDocteurs;

	    @FXML
	    private TableColumn<Malade, String> colprenom;

	    @FXML
	    private TableColumn<Malade, String> colnumTel;
	    
	    @FXML
	    private TableColumn<Malade, String> coladresse;
	    @FXML
	    private TableColumn<Malade, String> colDocteur;

	    @FXML
	    private TableColumn<Malade, LocalDate> colDateVisite;
	    @FXML
	    private ComboBox<Service> fService;
	    @FXML
	    private TableView<Malade> tableView;

	    @FXML
	    private TextField rechercheField;
	    @FXML
	    private TextField maladeOrd;
	    @FXML
	    private TextField docteurOrd;
	    @FXML
	    private javafx.scene.control.TextArea medicaments;
	    @FXML
	    private TextField dateVord;
	    @FXML
	    private Text codeOrd;
	    ObservableList<Malade> malades = FXCollections.observableArrayList();
	    private ObservableList<Docteur> docteurs = FXCollections.observableArrayList();
	    ObservableList<Service> services = FXCollections.observableArrayList();
	    @FXML
	    void handleAjoutMalade(ActionEvent event) {
	    	 try {
		            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AjouterMalade.fxml")) ;

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
			fetchDocteurs();
			getServiceList();
			 // Lier les colonnes de la TableView aux propriétés de la classe Infirmier
		    colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    
		    
		    colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		    colnumTel.setCellValueFactory(new PropertyValueFactory<>("numTel"));
		    coladresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
		    colDateVisite.setCellValueFactory(new PropertyValueFactory<>("dateVisite"));
		    colnom.setCellFactory(TextFieldTableCell.forTableColumn());
		    colnom.setOnEditCommit(event -> {
		        Malade m = event.getRowValue();
		        m.setNom(event.getNewValue());
		        updateMalade(m);
		    });
		    colDocteur.setCellValueFactory(new PropertyValueFactory<>("docteur"));
		    colDocteur.setCellValueFactory(cellData -> {
	            ObjectProperty<String> chefProperty = new SimpleObjectProperty<>();
	            int Id = cellData.getValue().getIdDocteur();
	            String Name = "yassine";
	            Name = getDocteur(Id);
	            chefProperty.set(Name);
	            return chefProperty;
	        });

		    
		    colprenom.setCellFactory(TextFieldTableCell.forTableColumn());
		    colprenom.setOnEditCommit(event -> {
		    	Malade m = event.getRowValue();
		        m.setPrenom(event.getNewValue());
		        updateMalade(m);
		    });

		    colnumTel.setCellFactory(TextFieldTableCell.forTableColumn());
		    colnumTel.setOnEditCommit(event -> {
		    	Malade m = event.getRowValue();
		        m.setNumTel(event.getNewValue());
		        updateMalade(m);
		    });

		    coladresse.setCellFactory(TextFieldTableCell.forTableColumn());
		    coladresse.setOnEditCommit(event -> {
		    	Malade m = event.getRowValue();
		        m.setAdresse(event.getNewValue());
		        updateMalade(m);
		    });

		  
		    fetchMaladeData();
		    
		    this.tableView.setEditable(true);    
			
		}
		
		
		public void updateMalade(Malade malade) {
		    // Perform the update operation in the database using the provided malade object
		    try {
		        // Create a database connection and prepare the update statement
		        connexion cn = new connexion();
		        Connection connection =(Connection) cn.getCn();
		        String query = "UPDATE malade SET nom=?,  prenom=?, numTel=?, adresse=? WHERE numMalade=?";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);

		        // Set the updated values in the prepared statement
		        statement.setString(1, malade.getNom());
		       
		        statement.setString(2, malade.getPrenom());
		        statement.setString(3, malade.getNumTel());
		        statement.setString(4, malade.getAdresse());
		        statement.setInt(5, malade.getNumMalade());

		        // Execute the update statement
		        int rowsAffected = statement.executeUpdate();

		        // Check the result of the update operation
		        if (rowsAffected > 0) {
		            System.out.println("Malade updated successfully!");
		        } else {
		            System.out.println("Failed to update malade!");
		        }

		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle the exception or display an error message
		    }
		}
		private void fetchMaladeData() {
		   

		    try {
		        // Create a database connection and execute the query
		        connexion cn = new connexion();
		        Connection connection =(Connection) cn.getCn();
		        String query = "SELECT * FROM malade";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
		        ResultSet resultSet = statement.executeQuery();

		        // Iterate through the resultSet and create Malade objects
		        while (resultSet.next()) {
		            int id = resultSet.getInt("numMalade");
		            String nom = resultSet.getString("nom");
		           
		            String prenom = resultSet.getString("prenom");
		            String numTel = resultSet.getString("numTel");
		            String adresse = resultSet.getString("adresse");
		            int codeDoc = resultSet.getInt("docteur");
		           
		            
		           LocalDate dateVisite = resultSet.getDate("dateVisite").toLocalDate();
		           System.out.println(dateVisite);
		            Malade malade = new Malade(id, nom, prenom, adresse,numTel,codeDoc,dateVisite);
		            //malade.setDateVisite(dateVisite);
		            malades.add(malade);
		        }

		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle the exception or display an error message
		    }

		    tableView.setItems(malades);
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
	            fDocteurs.setItems(docteurs);

	         
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			
		}
		

	    @FXML
	    void filtrerParDocteur(ActionEvent event) {
	    	malades.clear();
	        try {
		        // Create a database connection and execute the query
		        connexion cn = new connexion();
		        Connection connection =(Connection) cn.getCn();
		        String query = "SELECT * FROM malade where docteur=?";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
		        statement.setInt(1, fDocteurs.getValue().getCodeDocteur());
		        ResultSet resultSet = statement.executeQuery();

		        // Iterate through the resultSet and create Malade objects
		        while (resultSet.next()) {
		            int id = resultSet.getInt("numMalade");
		            String nom = resultSet.getString("nom");
		           
		            String prenom = resultSet.getString("prenom");
		            String numTel = resultSet.getString("numTel");
		            String adresse = resultSet.getString("adresse");
		            int codeDoc = resultSet.getInt("docteur");
		            
		            LocalDate dateVisite = resultSet.getDate("dateVisite").toLocalDate();
			           System.out.println(dateVisite);
			            Malade malade = new Malade(id, nom, prenom, adresse,numTel,codeDoc,dateVisite);
		            malades.add(malade);
		        }

		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle the exception or display an error message
		    }

		    tableView.setItems(malades);
	    }
	    
	    
	    void getMaladesParDocteur(Docteur d) {
	    	
	    	
	    }
	    
	    
	    @FXML
	    void filtrerParService(ActionEvent event) {
	    	malades.clear();
	        try {
		        // Create a database connection and execute the query
		        connexion cn = new connexion();
		        Connection connection =(Connection) cn.getCn();
		        String query = "SELECT malade.* from malade,service,docteur where malade.docteur=docteur.codeDocteur "
		        		+ "and docteur.serviceAffecte=service.codeService and service.codeService= ?";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
		        statement.setInt(1, fService.getValue().getCodeService());
		        ResultSet resultSet = statement.executeQuery();

		        // Iterate through the resultSet and create Malade objects
		        while (resultSet.next()) {
		            int id = resultSet.getInt("numMalade");
		            String nom = resultSet.getString("nom");
		           
		            String prenom = resultSet.getString("prenom");
		            String numTel = resultSet.getString("numTel");
		            String adresse = resultSet.getString("adresse");
		            int codeDoc = resultSet.getInt("docteur");
		            LocalDate dateVisite = resultSet.getDate("dateVisite").toLocalDate();
			           System.out.println(dateVisite);
			            Malade malade = new Malade(id, nom, prenom, adresse,numTel,codeDoc,dateVisite);
		            malades.add(malade);
		        }

		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle the exception or display an error message
		    }

		    tableView.setItems(malades);
	    }
	    
	    
	    
	    @FXML
	    void rechercherParNom(ActionEvent event) {
	    	malades.clear();
	        try {
		        // Create a database connection and execute the query
		        connexion cn = new connexion();
		        Connection connection =(Connection) cn.getCn();
		        String query = "SELECT * FROM malade where nom like ?";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
		        statement.setString(1, "%" + rechercheField.getText() + "%");
		        ResultSet resultSet = statement.executeQuery();

		        // Iterate through the resultSet and create Malade objects
		        while (resultSet.next()) {
		            int id = resultSet.getInt("numMalade");
		            String nom = resultSet.getString("nom");
		           
		            String prenom = resultSet.getString("prenom");
		            String numTel = resultSet.getString("numTel");
		            String adresse = resultSet.getString("adresse");
		            int codeDoc = resultSet.getInt("docteur");
		            LocalDate dateVisite = resultSet.getDate("dateVisite").toLocalDate();
			           System.out.println(dateVisite);
			            Malade malade = new Malade(id, nom, prenom, adresse,numTel,codeDoc,dateVisite);
		            malades.add(malade);
		        }

		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle the exception or display an error message
		    }

		    tableView.setItems(malades);
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

		        // Créer une liste temporaire pour stocker les services
		        List<Service> serviceList = new ArrayList<>();

		        // Parcourir les résultats du resultSet et ajouter les services à la liste temporaire
		        while (resultSet.next()) {
		            int serviceId = resultSet.getInt("codeService");
		            String serviceName = resultSet.getString("nomService");
		            Service service = new Service();
		            service.setCodeService(serviceId);
		            service.setNomService(serviceName);
		            serviceList.add(service);
		        }

		       
		        // Mettre à jour le ComboBox avec la liste des services
		        fService.getItems().setAll(serviceList);
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Gérer l'exception ou afficher un message d'erreur
		    }
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
	    private Ordonnance getOrdonnance(int docteurId,int maladeid,java.sql.Date dateV) {
		    try {
		        // Créer une instance de connexion à la base de données
		        connexion cn = new connexion();
		        Connection connection =(Connection) cn.getCn();

		        // Récupérer le nom du docteur à partir de l'ID
		        String query = "SELECT * FROM ordonnance WHERE codeDocteur = ? and numMalade=? and dateVisite = ?";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
		        statement.setInt(1, docteurId);
		        statement.setInt(2, maladeid);
		        statement.setDate(3, dateV);
		        
		        System.out.println(statement);
		        ResultSet resultSet = statement.executeQuery();

		        // Récupérer le nom du docteur s'il existe
		        if (resultSet.next()) {
		        	
		           int codeOrd=resultSet.getInt("codeOrd");
		        	String listeMedicaments=resultSet.getString("listeMedicaments");
		        	int codeDocteur = resultSet.getInt("codeDocteur");
		        	int numMalade = resultSet.getInt("numMalade");
		        	LocalDate dateVisite=resultSet.getDate("dateVisite").toLocalDate();
		        	Ordonnance ord = new Ordonnance();
		        	ord.setCodeDocteur(codeDocteur);
		        	ord.setCodeOrd(codeOrd);
		        	ord.setDateVisite(dateVisite);
		        	ord.setListeMedicaments(listeMedicaments);
		        	ord.setNumMalade(numMalade);
		          
		            return ord;
		        }

		       
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Gérer l'exception ou afficher un message d'erreur
		    }

		    return null;
		}
	    
	    @FXML
	    void voirOrdonance(ActionEvent event) {
	    	

            if(tableView.getSelectionModel().getSelectedItem()!=null) {
            	
            	Malade m = tableView.getSelectionModel().getSelectedItem();
            	try {
             Ordonnance	ord=getOrdonnance(m.getIdDocteur(), m.getNumMalade(), java.sql.Date.valueOf(m.getDateVisite()));
             
             codeOrd.setText(String.valueOf(ord.getCodeOrd()));
             maladeOrd.setText(ord.getNumMalade()+" ");
             docteurOrd.setText(ord.getCodeDocteur()+" ");
             medicaments.setText(ord.getListeMedicaments());
            
             LocalDate localDate = ord.getDateVisite();
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
             String formattedString = localDate.format(formatter);
             dateVord.setText(formattedString);
            	}
            	catch(NullPointerException e) {
            		
            		Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("pas d'ordonnance");
                    alert.setHeaderText(null);
                    alert.setContentText("pas d'ordonnance");
                    alert.showAndWait();
            	}
             
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
	    void deleteMalade(ActionEvent event) {

	    }
	    
		public void updateOrd(int  code,String m) {
		    // Perform the update operation in the database using the provided malade object
		    try {
		        // Create a database connection and prepare the update statement
		        connexion cn = new connexion();
		        Connection connection =(Connection) cn.getCn();
		        String query = "UPDATE ordonnance SET listeMedicaments=? WHERE codeOrd=?";
		        PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);

		        // Set the updated values in the prepared statement
		        statement.setString(1, m);
		       
		        statement.setInt(2, code);
		
		        

		        // Execute the update statement
		        int rowsAffected = statement.executeUpdate();

		        // Check the result of the update operation
		        if (rowsAffected > 0) {
		            System.out.println("ordonnance updated successfully!");
		            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("ordonnance updated successfully!");
                    alert.setHeaderText(null);
                    alert.setContentText("ordonnance updated successfully!");
                    alert.showAndWait();
		        } else {
		            System.out.println("Failed to update malade!");
		        }

		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle the exception or display an error message
		    }
		}
	    
	    
	    

	    @FXML
	    void editerOrd(ActionEvent event) {
            updateOrd(Integer.parseInt(codeOrd.getText()), medicaments.getText());
	    }
	    @FXML
	    void filtrerParDate(ActionEvent event) {

	    }
	    
	    
	    
		
}
