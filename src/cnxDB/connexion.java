package cnxDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class connexion {

	private static String login = "root";
	private static String password = "";
	private static String url = "jdbc:mysql://localhost/gestion_hopital";
	private static Connection cn = null;
	static {
	try {
	Class. forName("com.mysql.jdbc.Driver") ;
	cn= (Connection) DriverManager.getConnection(url, login, password);
	System.out.println("connected");
	} catch (ClassNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	} catch (SQLException e) {	
		
		e.printStackTrace();
	}
	}
	public static Connection getCn() {
	return cn; 
	}
	
	public static void main(String[] args) {
		connexion con = new connexion();
		con.getCn();
	}
	    

}
