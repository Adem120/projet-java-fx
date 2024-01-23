package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import cnxDB.connexion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class DashboardController implements Initializable {

	
	@FXML
    private Text nbServices;

    @FXML
    private Text nbMalades;

    @FXML
    private Text nbDocteurs;

    @FXML
    private Text nbInfirmiers;
    
    
   


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		  // Récupérer la connexion à la base de données
        connexion cn = new connexion();
        Connection connection =(Connection) cn.getCn();

        // Calcul des totaux
        int totalServices = calculerTotalServices(connection);
        int totalMalades = calculerTotalMalades(connection);
        int totalDocteurs = calculerTotalDocteurs(connection);
        int totalInfirmiers = calculerTotalInfirmiers(connection);

        // Affichage des totaux dans les TextFields correspondants
        nbServices.setText(String.valueOf(totalServices));
        nbMalades.setText(String.valueOf(totalMalades));
        nbDocteurs.setText(String.valueOf(totalDocteurs));
        nbInfirmiers.setText(String.valueOf(totalInfirmiers));
	}
	// Méthode pour calculer le total des services
    private int calculerTotalServices(Connection connection) {
        int totalServices = 0;

        try {
            String query = "SELECT COUNT(*) FROM service";
            PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalServices = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalServices;
    }

    // Méthode pour calculer le total des malades
    private int calculerTotalMalades(Connection connection) {
        int totalMalades = 0;

        try {
            String query = "SELECT COUNT(*) FROM malade";
            PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalMalades = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalMalades;
    }

    // Méthode pour calculer le total des docteurs
    private int calculerTotalDocteurs(Connection connection) {
        int totalDocteurs = 0;

        try {
            String query = "SELECT COUNT(*) FROM docteur";
            PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalDocteurs = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalDocteurs;
    }

    // Méthode pour calculer le total des infirmiers
    private int calculerTotalInfirmiers(Connection connection) {
        int totalInfirmiers = 0;

        try {
            String query = "SELECT COUNT(*) FROM infirmier";
            PreparedStatement statement =(PreparedStatement) connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalInfirmiers = resultSet.getInt(1);
            }
        } catch (SQLException e) {
        e.printStackTrace();
        }
        return totalInfirmiers;
    }


}