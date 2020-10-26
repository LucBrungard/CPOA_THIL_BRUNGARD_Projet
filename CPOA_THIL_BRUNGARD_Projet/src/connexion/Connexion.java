package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	
	public static Connection creeConnexion() {		
		  //Pour se connecter a un serveur local
		  String url = "jdbc:mysql://localhost/brungard3u_pullmoche"; 
		  url += "?serverTimezone=Europe/Paris"; 
		  String login = "root"; 
		  String pwd = "root";
		
//		  String url = "jdbc:mysql://localhost/brungard3u_pullmochetest"; 
//		  url += "?serverTimezone=Europe/Paris"; 
//		  String login = "root"; 
//		  String pwd = "root";
		 
		 
//		  String url =
//		  "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/brungard3u_pullmoche"; url
//		  += "?serverTimezone=Europe/Paris"; String login = "brungard3u_appli"; String
//		  pwd = "31906904";
		  
//		  String url ="jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/brungard3u_pullmocheTEST"; 
//		  url += "?serverTimezone=Europe/Paris"; 
//		  String login = "brungard3u_appli"; 
//		  String pwd = "31906904";
		 
			 
		 
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