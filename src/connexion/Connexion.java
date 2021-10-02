package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	
	public static Connection creeConnexion() {		
		
		//Pour se connecter a un serveur local
		String url = "jdbc:mysql://localhost/dbname"; 
		String login = "root"; 
		String pwd = "root";
	 
		Connection maConnexion = null;
		try { 
			maConnexion = DriverManager.getConnection(url, login, pwd);
		} 
		catch (SQLException sqle) {
			System.out.println("Erreur connexion" + sqle.getMessage());
		}
		
		return maConnexion;
	}
}
