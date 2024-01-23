package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cnxDB.connexion;

public class LoginController implements Initializable {
    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = email.getText();
        String passwords = password.getText();
        connexion cn = new connexion();
        Connection connection = (Connection) cn.getCn();

        try {
            if (email.getText() != null && password.getText() != null) {
                // Récupérer la liste des infirmiers depuis la base de données
                String query = "SELECT * FROM Docteur WHERE email = ? AND password = ?";
                String query2 = "SELECT * FROM admin WHERE email = ? AND password = ?";
                String query3 = "SELECT * FROM infirmier WHERE email = ? AND password = ?";
                PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
                PreparedStatement statement2 = (PreparedStatement) connection.prepareStatement(query2);
                PreparedStatement statement3 = (PreparedStatement) connection.prepareStatement(query3);
                statement.setString(1, username);
                statement.setString(2, hashPassword(passwords));
                ResultSet resultSet = statement.executeQuery();
                statement2.setString(1, username);
                statement2.setString(2, hashPassword(passwords));
                ResultSet resultSet2 = statement2.executeQuery();
                statement3.setString(1, username);
                statement3.setString(2, hashPassword(passwords));
                ResultSet resultSet3 = statement3.executeQuery();

                // Parcourir les résultats du resultSet et ajouter les infirmiers à la liste
                if (resultSet.next()) {
                	System.out.println("docteur");
                    String email = resultSet.getString("email");
                    UserSession session = UserSession.getInstance();
                    session.setRole("Docteur");
                    session.setNom(resultSet.getString("nom"));
                    session.setPrenom(resultSet.getString("prenom"));
                    session.setUsername(resultSet.getString(("nom")));

                    openRegistrationScreen(event);
                } else if (resultSet2.next()) {
                    String email = resultSet2.getString("email");
                    UserSession session = UserSession.getInstance();
                    session.setRole("admin");
                    session.setNom(resultSet2.getString("nom"));
                    session.setPrenom(resultSet2.getString("prenom"));
                    session.setUsername(resultSet2.getString(("nom")));

                    openRegistrationScreen(event);
                   /* String s=hashPassword("12345678");
                    System.out.println(s);*/

                } else if (resultSet3.next()) {
                    String email = resultSet3.getString("email");
                    UserSession session = UserSession.getInstance();
                    session.setRole("infirmier");
                    session.setNom(resultSet3.getString("nom"));
                    session.setPrenom(resultSet3.getString("prenom"));
                    session.setUsername(resultSet3.getString("nom"));
                    openRegistrationScreen(event);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Email ou mot de passe incorrect");
                    alert.showAndWait();
                }

                // Fermer le resultSet et la ressource PreparedStatement
               /* resultSet.close();
                statement.close();
                resultSet2.close();
                statement2.close();
                resultSet3.close();
                statement3.close();*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception ou afficher un message d'erreur
        }
    }

    private void openRegistrationScreen(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml")) ;
            

            Parent root = (Parent) fxmlLoader.load() ;
			Scene scene = new Scene(root,1260,827);
			

 			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());


            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }	
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
