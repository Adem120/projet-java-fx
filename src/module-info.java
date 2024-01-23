module Projet_JAVAFX_2023 {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires mysql.connector.java;
	
	opens application to javafx.graphics, javafx.fxml,javafx.base;
	opens controller to javafx.fxml;
	exports controller;
	opens model to javafx.base;

}
