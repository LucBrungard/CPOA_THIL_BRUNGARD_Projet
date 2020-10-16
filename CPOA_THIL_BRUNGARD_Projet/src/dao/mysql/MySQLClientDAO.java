package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connexion.Connexion;
import dao.modele.ClientDAO;
import modele.Client;

public class MySQLClientDAO implements ClientDAO{

	//Instance of the class
	private static MySQLClientDAO instance;
	
	//Insure to get only 1 instance
	public static MySQLClientDAO getInstance() {
		if (instance == null)
			instance = new MySQLClientDAO();
		
		return instance;
	}
	
	@Override
	public boolean create(Client client) throws SQLException{
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
		
		PreparedStatement requete = laConnexion.prepareStatement("insert into `Client` (`nom`, `prenom`, `identifiant`, `mot_de_passe`, `adr_numero`, `adr_voie`, `adr_code_postal`, `adr_ville`, `adr_pays`) "
				+ "VALUES (?, ?,'identifiant', 'mdp' ,? ,? ,? ,?, ?)");	
		requete.setString(1, client.getNom());	
		requete.setString(2, client.getPrenom());	
		requete.setString(5, client.getNumero());	
		requete.setString(6, client.getRue());	
		requete.setString(7, client.getCodePostal());	
		requete.setString(8, client.getVille());	
		requete.setString(9, client.getPays());		
		
		nbLignes = requete.executeUpdate(); 
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nEchec lors de la creation du client");
		
		if (laConnexion != null)
			laConnexion.close();
			
		return nbLignes==1;
	}

	@Override
	public boolean update(Client client) throws SQLException{
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
		
		PreparedStatement requete = laConnexion.prepareStatement("update `Client` set nom=?, "
																				+ "prenom=? "
																				+ "where id_client=?");
		requete.setString(1,client.getNom());
		requete.setString(2,client.getPrenom());
		requete.setInt(3,client.getId());
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nTentative de modification d'un client inexistant");
		
		if (laConnexion != null)
			laConnexion.close();
		
		return nbLignes==1;
	}

	@Override
	public boolean delete(Client client) throws SQLException{				
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
			
		PreparedStatement requete = laConnexion.prepareStatement("delete from `Client` where id_client=?");
		requete.setInt(1,client.getId());
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nTentative de modification d'un client inexistant");
		
		if (laConnexion != null)
			laConnexion.close();
		
		return nbLignes==1;
	}

	
	@Override
	public Client getById(int id) throws SQLException{
		Client client = null;
		
		Connection laConnexion = Connexion.creeConnexion();
		
		PreparedStatement requete = laConnexion.prepareStatement("select id_client, nom, prenom, adr_numero, adr_voie, adr_code_postal, adr_ville, adr_pays from `Client` where id_client =" + id);
		
		ResultSet res = requete.executeQuery();
		
		//S'il y a une valeur dans le resultat
		if (res.next()) {
			client = new Client(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8));
		}
		else {
			throw new IllegalArgumentException("\nAucun client ne correspond a cet identifiant");
		}
			
		if (laConnexion != null)
			laConnexion.close();

		return client;
	}

	@Override
	public ArrayList<Client> findAll() throws SQLException{
		ArrayList<Client> listeClient = new ArrayList<Client>();

		Connection laConnexion = Connexion.creeConnexion();
		PreparedStatement requete = laConnexion.prepareStatement("select * from `Client` ");
		
		ResultSet res = requete.executeQuery();
		
		while (res.next()) {
			listeClient.add(new Client(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8)));
		}
		
		if (laConnexion != null)
			laConnexion.close();

		return listeClient;
	}
}
