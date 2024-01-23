package controller;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class SidebarController implements Initializable {


    @FXML
    private AnchorPane contentPane;
    @FXML
    private Text role;

    @FXML
    private Text name;
	   private Consumer<String> navigateMethod;

	    // Setter for navigateMethod
	    public void setNavigateMethod(Consumer<String> navigateMethod) {
	        this.navigateMethod = navigateMethod;
	    }

	   @FXML
	    void handleDocteursButton(ActionEvent event) {
           
		   navigateTo("/view/Docteurs.fxml",event);
		  
	    }
         @FXML
	    private Button infiermierbtn;
	    @FXML
	    void handleInfirmiersButton(ActionEvent event) {
	    	 navigateTo("/view/Infirmiers.fxml",event);
	    }

	    @FXML
	    void handleSurveillancesButton(ActionEvent event) {
	    	 navigateTo("/view/surveillances.fxml",event);
	    }

	    @FXML
	    void handleMaladesButton(ActionEvent event) {
	    	 navigateTo("/view/Malades.fxml",event);
	    }

	    @FXML
	    void handleServicesButton(ActionEvent event) {
	    	 navigateTo("/view/Services.fxml",event);
	    }
	    @FXML
	    void handleSallesButton(ActionEvent event) {
	    	navigateTo("/view/Salles.fxml",event);
	    }
	    
	    @FXML
	    void handleDashboardButton(ActionEvent event) {
	    	navigateTo("/view/dashboard.fxml",event);
	    }
	    

	    @FXML
	    void handleDisconnect(ActionEvent event) {
	    	
	    	
	    	   try {
		             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/login.fxml")) ;
		             Parent root = (Parent) fxmlLoader.load() ;
		             Scene scene = new Scene(root,564,463);

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
		
		
		name.setText(UserSession.getInstance().getNom() +" "+ UserSession.getInstance().getPrenom());
		role.setText(UserSession.getInstance().getRole());
		
	/*	if(UserSession.getInstance().getRole().equals("Docteur")){
			System.out.println("doctor connected");
			this.infiermierbtn.setVisible(false);
			
		}
		
		System.out.println(UserSession.getInstance().getRole());*/
	}
	    
	    public void navigateTo(String path,ActionEvent event) {
	    	
	         try {
	             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path)) ;
	             Parent root = (Parent) fxmlLoader.load() ;
		            Scene scene = new Scene(root,1260,774);

	 			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());

	             Stage stage = new Stage();
	             stage.setScene(scene);
	             stage.show();
	             ((Node)(event.getSource())).getScene().getWindow().hide();

	         } catch (Exception e) {
	             e.printStackTrace();
	         }	
	    }



}
