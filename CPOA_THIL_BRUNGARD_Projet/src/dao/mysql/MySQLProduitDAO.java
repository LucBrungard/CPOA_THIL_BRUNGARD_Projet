package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connexion.Connexion;
import dao.modele.ProduitDAO;
import modele.Produit;

public class MySQLProduitDAO implements ProduitDAO{

	//Instance of the class
	private static MySQLProduitDAO instance;
	
	//Insure to get only 1 instance
	public static MySQLProduitDAO getInstance() {
		if (instance == null)
			instance = new MySQLProduitDAO();
		
		return instance;
	}
	
	@Override
	public boolean create(Produit produit) throws SQLException {
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
		
		PreparedStatement requete = laConnexion.prepareStatement("insert into `Produit` (`nom`, `description`, `tarif`, `visuel`, `id_categorie`) values (?, ?, ?, ?, ?);");
					
		requete.setString(1,produit.getNom());
		requete.setString(2,produit.getDescription());
		requete.setFloat(3, produit.getTarif());
		requete.setString(4, produit.getVisuel()); 
		requete.setInt(5, produit.getIdCateg());
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("Erreur lors de la creation du produit");
		
		if (laConnexion != null)
			laConnexion.close();
		
		return nbLignes==1;
	}

	@Override
	public boolean update(Produit produit) throws SQLException {
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
		
		PreparedStatement requete = laConnexion.prepareStatement("update `Produit` set nom=?, description=?, tarif=?, visuel=? where id_produit=?");
		requete.setString(1,produit.getNom());
		requete.setString(2,produit.getDescription());
		requete.setFloat(3, produit.getTarif());
		requete.setString(4, produit.getVisuel()); 
		requete.setInt(5, produit.getId());
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("Tentative de modification d'un produit non existante");
		
		if (laConnexion != null)
			laConnexion.close();
		
		return nbLignes==1;
	}

	@Override
	public boolean delete(Produit produit) throws SQLException {
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
		
		PreparedStatement requete = laConnexion.prepareStatement("delete from `Produit` where id_produit=?");
		requete.setInt(1,produit.getId());
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("Tentative de suppression d'un produit non existante");
		
		if (laConnexion != null)
			laConnexion.close();
		
		return nbLignes==1;
	}

	@Override
	public Produit getById(int id) throws SQLException {
		Produit produit = null;
		
		Connection laConnexion = Connexion.creeConnexion();
		
		PreparedStatement requete = laConnexion.prepareStatement("select * from `Produit` where id_produit =" + id);
		ResultSet res = requete.executeQuery();
		
		//S'il y a une valeur dans le resultat
		if (res.next()) 
			produit = new Produit(res.getInt(1), res.getString(2),  res.getString(3), res.getFloat(4), res.getString(5), res.getInt(6));
		else 
			throw new IllegalArgumentException("Aucun produit ne correspond a cet indentifiant");
			
		if (laConnexion != null)
			laConnexion.close();
		
		return produit;
	}

	public ArrayList<Produit> findAll() throws SQLException {
		ArrayList<Produit> listeProduit = new ArrayList<Produit>();

		Connection laConnexion = Connexion.creeConnexion();
		
		PreparedStatement requete = laConnexion.prepareStatement("select * from `Produit` ");
		
		ResultSet res = requete.executeQuery();
		
		while (res.next()) {
			listeProduit.add(new Produit(res.getInt(1), res.getString(2), res.getString(3), res.getFloat(4), res.getString(5), res.getInt(6)));
		}
		
		if (laConnexion != null)
			laConnexion.close();
		
		return listeProduit;		
	}	
}
