package controller;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.PreparedStatement;

import cnxDB.connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Docteur;
import model.Service;

public class AjouterDocteurController implements Initializable{

	
	   @FXML
	    private TextField password;

	    @FXML
	    private TextField specialite;

	    @FXML
	    private TextField adresse;

	    @FXML
	    private DatePicker dateNaiss;

	    @FXML
	    private TextField nom;

	    @FXML
	    private TextField prenom;

	    @FXML
	    private TextField numTel;

	    @FXML
	    private ComboBox<Service> serviceAffecte;

	    @FXML
	    private TextField email;
	    
	    ObservableList<Service> services = FXCollections.observableArrayList();
	    // Appelé lorsque le bouton "Valider" est cliqué
	    @FXML
	    void handleValiderAjout(ActionEvent event) {
	        // Récupérer les valeurs des champs saisis par l'utilisateur
	        String enteredPassword = hashPassword(password.getText());
	        String enteredSpecialite = specialite.getText();
	        String enteredAdresse = adresse.getText();
	        LocalDate enteredDateNaiss = dateNaiss.getValue();
	        String enteredNom = nom.getText();
	        String enteredPrenom = prenom.getText();
	        String enteredNumTel = numTel.getText();
	        Service enteredServiceAffecte = serviceAffecte.getValue();
	        String enteredEmail = email.getText();

	        // Vérifier si tous les champs sont remplis
	        if (enteredPassword.isEmpty() || enteredSpecialite.isEmpty() || enteredAdresse.isEmpty()
	                || enteredDateNaiss == null || enteredNom.isEmpty() || enteredPrenom.isEmpty()
	                || enteredNumTel.isEmpty() || enteredServiceAffecte == null || enteredEmail.isEmpty()) {
	            // Afficher un message d'erreur ou effectuer une action appropriée
	        	
	        	
	        	 Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setTitle("veillez remplir tous les champs!");
	                alert.setHeaderText(null);
	                alert.setContentText("veillez remplir tous les champs!");
	                alert.showAndWait();
	                return;
	        }
	        else if(!isPhoneNumberValid(enteredNumTel)) {
	        	
	        	Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("num tel invalide!");
                alert.setHeaderText(null);
                alert.setContentText("num tel invalide!");
                alert.showAndWait();
                return;
	        }else if(!isEmailValid(enteredEmail)) {
	        	Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("email invalide!");
                alert.setHeaderText(null);
                alert.setContentText("email invalide!");
                alert.showAndWait();
                return;
	        	
	        }

	        // Créer un nouvel objet Docteur avec les valeurs saisies
	        Docteur nouveauDocteur = new Docteur();
	        nouveauDocteur.setPassword(enteredPassword);
	        nouveauDocteur.setSpecialite(enteredSpecialite);
	        nouveauDocteur.setAdresse(enteredAdresse);
	        nouveauDocteur.setDateNaiss(enteredDateNaiss);
	        nouveauDocteur.setNom(enteredNom);
	        nouveauDocteur.setPrenom(enteredPrenom);
	        nouveauDocteur.setNumTel(enteredNumTel);
	        nouveauDocteur.setServiceAffecte(enteredServiceAffecte.getCodeService());
	        nouveauDocteur.setEmail(enteredEmail);

	        // Effectuer l'opération d'ajout du docteur dans la base de données ou effectuer une action appropriée
	        ajouterDocteurDansLaBaseDeDonnees(nouveauDocteur);

	        // Effacer les valeurs des champs après l'ajout du docteur
	        password.clear();
	        specialite.clear();
	        adresse.clear();
	        dateNaiss.setValue(null);
	        nom.clear();
	        prenom.clear();
	        numTel.clear();
	        serviceAffecte.setValue(null);
	        email.clear();

	        // Afficher un message de succès ou effectuer une action appropriée
	    }
	    
	    private void ajouterDocteurDansLaBaseDeDonnees(Docteur docteur) {
	        try {
	            // Créer une instance de connexion à la base de données
	        	connexion cn = new connexion();
	              Connection connection = cn.getCn();
	            // Préparer la requête SQL pour l'insertion du docteur
	            String query = "INSERT INTO docteur (nom, prenom, adresse, dateNaiss, numTel, specialite, serviceAffecte, email, password) "
	                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            
	            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
	            statement.setString(1, docteur.getNom());
	            statement.setString(2, docteur.getPrenom());
	            statement.setString(3, docteur.getAdresse());
	            statement.setDate(4, Date.valueOf(docteur.getDateNaiss()));
	            statement.setString(5, docteur.getNumTel());
	            statement.setString(6, docteur.getSpecialite());
	            statement.setInt(7, docteur.getServiceAffecte());
	            statement.setString(8, docteur.getEmail());
	            statement.setString(9, docteur.getPassword());

	            // Exécuter la requête d'insertion
	            statement.executeUpdate();
	            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("nouveau docteur ajouté avec succes!");
                alert.setHeaderText(null);
                alert.setContentText("nouveau docteur ajouté avec succes!");
                alert.showAndWait();
	           

	            // Afficher un message de succès ou effectuer une action appropriée
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Gérer l'exception ou afficher un message d'erreur
	        }
	    }

	    @Override
	    public void initialize(URL arg0, ResourceBundle arg1) {
	        // Appeler la méthode pour récupérer la liste des services et la mettre à jour dans le ComboBox
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
		        serviceAffecte.getItems().setAll(serviceList);
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Gérer l'exception ou afficher un message d'erreur
		    }
		}
		
		
		
		public boolean isEmailValid(String email) {
		    // Regular expression pattern to match email addresses
		    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

		    // Create a pattern object
		    Pattern pattern = Pattern.compile(emailRegex);

		    // Create a matcher object with the given email string
		    Matcher matcher = pattern.matcher(email);

		    // Check if the email matches the pattern
		    return matcher.matches();
		}	
		
		
		
		
		
		public boolean isPhoneNumberValid(String phoneNumber) {
		    // Check if the phone number has exactly 8 digits and consists of only digits
		    return phoneNumber.length() == 8 && phoneNumber.matches("\\d+");
		}	
		
		
		   public String hashPassword (String password) {
		        String generatedPassword = null ;
		        try {
		            MessageDigest md = MessageDigest.getInstance("MD5") ;

		            md.update(password.getBytes());

		            byte[] bytes = md.digest() ;

		            StringBuilder sb = new StringBuilder();
		            for (int i = 0; i < bytes.length; i++) {
		                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		            }

		            // Get complete hashed password in hex format
		            generatedPassword = sb.toString();

		        } catch (NoSuchAlgorithmException e) {
		            e.printStackTrace();
		        }
		System.out.println(generatedPassword);
		        return generatedPassword ;
		    }		
		
		
	
}
