package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import cnxDB.connexion;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import model.Docteur;
import model.Service;

public class DocteursController implements Initializable {
    @FXML
    private TableView<Docteur> tableView;

    @FXML
    private TableColumn<Docteur, LocalDate> coldateNaiss;

    @FXML
    private TableColumn<Docteur, String> colspecialite;
    @FXML
    private TableColumn<Docteur, String> colnom;
    @FXML
    private TableColumn<Docteur, String> colprenom;
    @FXML
    private TableColumn<Docteur, String> colserviceAffecte;
    @FXML
    private TableColumn<Docteur, String> colnumTel;

    @FXML
    private TableColumn<Docteur, String> coladresse;

    @FXML
    private TableColumn<Docteur, String> colemail;

    private ObservableList<Docteur> docteurs = FXCollections.observableArrayList();
    ObservableList<String> serviceList = FXCollections.observableArrayList();
 // Récupérer les services disponibles depuis la base de données et les ajouter à serviceList

 

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	//serviceList.add(new Service(1,"abcd",  "bloc",1, 1));
    	//System.out.println("ser = "+serviceList.get(0));
        // Set up the TableView and its columns
        coldateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
        colspecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colnumTel.setCellValueFactory(new PropertyValueFactory<>("numTel"));
        coladresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colprenom.setCellFactory(TextFieldTableCell.forTableColumn());
        colprenom.setOnEditCommit(event -> {
            Docteur docteur = event.getRowValue();
            docteur.setPrenom(event.getNewValue());
            updateDocteur(docteur);
        });

        colnom.setCellFactory(TextFieldTableCell.forTableColumn());
        colnom.setOnEditCommit(event -> {
            Docteur docteur = event.getRowValue();
            docteur.setNom(event.getNewValue());
            updateDocteur(docteur);
        });

        colnumTel.setCellFactory(TextFieldTableCell.forTableColumn());
        colnumTel.setOnEditCommit(event -> {
            Docteur docteur = event.getRowValue();
            docteur.setNumTel(event.getNewValue());
            updateDocteur(docteur);
        });

        coladresse.setCellFactory(TextFieldTableCell.forTableColumn());
        coladresse.setOnEditCommit(event -> {
            Docteur docteur = event.getRowValue();
            docteur.setAdresse(event.getNewValue());
            updateDocteur(docteur);
        });

        colemail.setCellFactory(TextFieldTableCell.forTableColumn());
        colemail.setOnEditCommit(event -> {
            Docteur docteur = event.getRowValue();
            docteur.setEmail(event.getNewValue());
            updateDocteur(docteur);
        });
       colspecialite.setCellFactory(TextFieldTableCell.forTableColumn());
        colspecialite.setOnEditCommit(event -> {
            Docteur docteur = event.getRowValue();
            docteur.setSpecialite(event.getNewValue());
            
            updateDocteur(docteur);
        });
        
        
       
        
        colserviceAffecte.setCellFactory(ComboBoxTableCell.forTableColumn(serviceList));
        colserviceAffecte.setOnEditCommit(event -> {
            Docteur docteur = event.getRowValue();
            String newService = event.getNewValue();
            // Update the docteur object with the new service value
          //  docteur.setServiceAffecte(newService.getCodeService());
            // Perform the necessary update operation in the database using the docteur object
            updateDocteur(docteur);
        });

        
        
        
        
       // coldateNaiss.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

coldateNaiss.setCellFactory(column -> {
    TableCell<Docteur, LocalDate> cell = new TableCell<Docteur, LocalDate>() {
        private DatePicker datePicker;

        {
            // Créer un DatePicker pour la cellule
            datePicker = new DatePicker();
            datePicker.setConverter(new LocalDateStringConverter());
            datePicker.setPromptText("yyyy-MM-dd");

            // Appliquer les modifications lorsque la date est sélectionnée
           datePicker.setOnAction(event -> {
                commitEdit(datePicker.getValue());
                // Perform the necessary update operation in the database using the docteur object
                Docteur docteur = getTableView().getItems().get(getIndex());
                docteur.setDateNaiss(datePicker.getValue());
                updateDocteur(docteur);
            });
        }

        @Override
        protected void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            if (empty) {
                setGraphic(null);
            } else {
                datePicker.setValue(date);
                setGraphic(datePicker);
            }
        }
    };

    return cell;
});
        /*coldateNaiss.setOnEditCommit(event -> {
            Docteur docteur = event.getRowValue();
            
            
            
            LocalDate newDate = event.getNewValue();
            // Update the docteur object with the new date value
            docteur.setDateNaiss(newDate);
            // Perform the necessary update operation in the database using the docteur object
            updateDocteur(docteur);
        });*/
        colserviceAffecte.setCellValueFactory(new PropertyValueFactory<>("serviceAffecte"));
        colserviceAffecte.setCellValueFactory(cellData -> {
            ObjectProperty<String> serviceProperty = new SimpleObjectProperty<>();
            int serviceId = cellData.getValue().getServiceAffecte();
            String serviceName=Integer.toString(serviceId);
			try {
				serviceName = getServiceName(serviceId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Remplacez cette ligne par votre logique pour obtenir le nom du service en fonction de son identifiant
            serviceProperty.set(serviceName);
            return serviceProperty;
        });
        
        
        
        
        
       tableView.setEditable(true);
        
      
        try {
        	connexion cn = new connexion();
            Connection connection = cn.getCn();
            String query = "SELECT * FROM docteur";
            PreparedStatement statement = connection.prepareStatement(query);
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
            tableView.setItems(docteurs);

         
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    private String getServiceName( int serviceAffecte) throws SQLException {
    	connexion cn = new connexion();
        Connection connection = cn.getCn();
        String query = "SELECT nomService FROM service WHERE codeService = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, serviceAffecte);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("nomService");
        }
        return "";
    }
    
    
    public void getServices() {
    	   try {
           	connexion cn = new connexion();
               Connection connection = cn.getCn();
               String query = "SELECT * FROM service";
               PreparedStatement statement = connection.prepareStatement(query);
               ResultSet resultSet = statement.executeQuery();

               while (resultSet.next()) {
                   int codeS = resultSet.getInt("codeService");
                   String nomS = resultSet.getString("nomService");
                   

                   Service s = new Service();
                   s.setCodeService(codeS);
                   s.setNomService(nomS);
                		 //  serviceList.add(s);
               }

               // Set the retrieved data to the TableView
               tableView.setItems(docteurs);

            
               
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }
    
    
    public void updateDocteur(Docteur docteur) {
    	System.out.println("code serv = "+docteur.getServiceAffecte());
        try {
            connexion cn = new connexion();
            Connection connection = cn.getCn();
//            String query = "UPDATE docteur SET nom=?, prenom=?, adresse=?, dateNaiss=?, numTel=?, specialite=?, serviceAffecte=?, email=?, password=? WHERE codeDocteur=?";
            String query = "UPDATE docteur SET nom=?, prenom=?, adresse=?, dateNaiss=?, numTel=?, specialite=?, email=?, password=? WHERE codeDocteur=?";

            PreparedStatement statement = connection.prepareStatement(query);
            
            // Set the values for the prepared statement
            statement.setString(1, docteur.getNom());
            statement.setString(2, docteur.getPrenom());
            statement.setString(3, docteur.getAdresse());
            statement.setDate(4, java.sql.Date.valueOf(docteur.getDateNaiss()));
            statement.setString(5, docteur.getNumTel());
            statement.setString(6, docteur.getSpecialite());
          //  statement.setInt(7, docteur.getServiceAffecte());
            statement.setString(7, docteur.getEmail());
            statement.setString(8, docteur.getPassword());
            statement.setInt(9, docteur.getCodeDocteur());
            
            // Execute the update statement
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Docteur updated successfully!");
            } else {
                System.out.println("Failed to update the docteur.");
            }
            
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    void handleAjoutDocteur(ActionEvent event) {
    	 try {
	            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AjouterDocteur.fxml")) ;

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
    
    
    
    
    
    
    
    
    
}
