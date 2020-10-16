package dao.mysql;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connexion.Connexion;
import dao.modele.CategorieDAO;
import modele.Categorie;

public class MySQLCategorieDAO implements CategorieDAO {
	
	//Instance of the class
	private static MySQLCategorieDAO instance;
	
	private MySQLCategorieDAO() {}
	
	//Insure to get only 1 instance
	public static MySQLCategorieDAO getInstance() {
		if (instance == null)
			instance = new MySQLCategorieDAO();
		
		return instance;
	}
	
	@Override
	public boolean create(Categorie categorie) throws SQLException {
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
		
		PreparedStatement requete = laConnexion.prepareStatement("insert into `Categorie` (`titre`, `visuel`) values (?, ?);");
		requete.setString(1,categorie.getTitre());
		requete.setString(2,categorie.getVisuel());
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nEchec de la creation<<");
		
		if (laConnexion != null)
			laConnexion.close();
		
		return nbLignes==1;
	}

	@Override
	public boolean update(Categorie categorie) throws SQLException {
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
		
		PreparedStatement requete = laConnexion.prepareStatement("update `Categorie` set titre=?, visuel=? where id_categorie=?");
		requete.setString(1,categorie.getTitre());
		requete.setString(2,categorie.getVisuel());
		requete.setInt(3,categorie.getId());
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nTentative de modification d'une categorie inexistante");
		
		if (laConnexion != null)
			laConnexion.close();
	
		return nbLignes==1;
	}

	@Override
	public boolean delete(Categorie categorie) throws SQLException{
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
		
		PreparedStatement requete = laConnexion.prepareStatement("delete from `Categorie` where id_categorie=?");
		requete.setInt(1,categorie.getId());
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("\nTentative de suppression d'une categorie inexistante");
		
		if (laConnexion != null)
			laConnexion.close();
		
		return nbLignes==1;
	}

	@Override
	public Categorie getById(int id) throws SQLException{
		Categorie categorie = null;
		
		Connection laConnexion = Connexion.creeConnexion();
		
		PreparedStatement requete = laConnexion.prepareStatement("select * from `Categorie` where id_categorie=" + id);
		
		ResultSet res = requete.executeQuery();
		
		if (res.next()) {
			categorie = new Categorie(res.getInt(1), res.getString(2), res.getString(3));
		}
		else {
			throw new IllegalArgumentException("\nAucune categorie ne correspond a cet identifiant");
		}
		
		if (laConnexion != null)
			laConnexion.close();
		
		return categorie;
	}

	@Override
	public ArrayList<Categorie> findAll() throws SQLException{
		ArrayList<Categorie> listeCategorie = new ArrayList<Categorie>();

		Connection laConnexion = Connexion.creeConnexion();

		PreparedStatement requete = laConnexion.prepareStatement("select * from `Categorie` ");
			
		ResultSet res = requete.executeQuery();
		
		while (res.next()) {
			listeCategorie.add(new Categorie(res.getInt(1), res.getString(2), res.getString(3)));
		}
		
		if (laConnexion != null)
			laConnexion.close();
	
		return listeCategorie;	
	}
}
