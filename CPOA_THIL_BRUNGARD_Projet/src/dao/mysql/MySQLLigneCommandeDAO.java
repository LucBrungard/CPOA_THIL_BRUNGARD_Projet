package dao.mysql;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connexion.Connexion;
import dao.modele.LigneCommandeDAO;
import modele.LigneCommande;

public class MySQLLigneCommandeDAO implements LigneCommandeDAO<LigneCommande>{

	//Instance of the class
	private static MySQLLigneCommandeDAO instance;
	
	//Insure to get only 1 instance
	public static MySQLLigneCommandeDAO getInstance() {
		if (instance == null)
			instance = new MySQLLigneCommandeDAO();
		
		return instance;
	}
	
	@Override
	public boolean create(LigneCommande ligneCom) throws SQLException {
		Connection laConnexion = Connexion.creeConnexion();
		boolean existe = false; 
		
		int nbLignes = 0;
		
		PreparedStatement getAll = laConnexion.prepareStatement("select * from `Ligne_commande` ");
		ResultSet getAllres = getAll.executeQuery();
		
		while (getAllres.next()) {
			LigneCommande lcRajout = new LigneCommande(ligneCom.getIdCommande(), ligneCom.getIdProduit(), ligneCom.getQuantite(), ligneCom.getPrixUnitaire());
			LigneCommande lcCompare = new LigneCommande(getAllres.getInt(1), getAllres.getInt(2), getAllres.getInt(3), getAllres.getFloat(4));
			
			if (lcRajout.equals(lcCompare)) 
				existe=true;
		}
		
		if(existe) throw new IllegalArgumentException("Ligne commande déjà existante"); 
		else {
		PreparedStatement requete = laConnexion.prepareStatement("insert into `Ligne_commande` (`id_commande`, `id_produit`, `quantite`, `tarif_unitaire`) values (?, ?, ?, ?)");
		
		boolean idCom = verifIdCom(ligneCom.getIdCommande());
		if (idCom)
			requete.setInt(1, ligneCom.getIdCommande());
		else
			throw new IllegalArgumentException("Aucune commande ne possede cet identifiant");
		
		
		boolean idProd = verifIdProd(ligneCom.getIdProduit());
		if (idProd)
			requete.setInt(2, ligneCom.getIdProduit());
		else
			throw new IllegalArgumentException("Aucun produit ne possede cet identifiant"); 
		
		
		requete.setInt(3, ligneCom.getQuantite());
		requete.setFloat(4, ligneCom.getPrixUnitaire());
		
		nbLignes = requete.executeUpdate();
		}
		
		if (nbLignes == 0)
			throw new IllegalArgumentException("Erreur lors de la creation de la ligne de commande"); 
		
		if (laConnexion != null)
			laConnexion.close();

	return nbLignes==1;
	}

	@Override
	public boolean update(LigneCommande ligneCom) throws SQLException {
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes =0;
		
		PreparedStatement requete = laConnexion.prepareStatement("update `Ligne_commande` set quantite=? where id_commande=? and id_produit=? ");
		
		requete.setInt(1, ligneCom.getQuantite());
	
		boolean idCom = verifIdCom(ligneCom.getIdCommande());
		if (idCom)
			requete.setInt(3, ligneCom.getIdCommande());
		else
			throw new IllegalArgumentException("Aucune commande ne possede cet identifiant");
		
		
		boolean idProd = verifIdProd(ligneCom.getIdProduit());
		if (idProd)
			requete.setInt(4, ligneCom.getIdProduit());
		else
			throw new IllegalArgumentException("Aucun produit ne possede cet identifiant"); 
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0) 
			throw new IllegalArgumentException("Tentative de modification d'une ligne de commande non existante"); 
		
		
		if (laConnexion != null)
			laConnexion.close();
		
		return nbLignes==1;
	}

	@Override
	public boolean delete(LigneCommande ligneCom) throws SQLException {
		Connection laConnexion = Connexion.creeConnexion();
		
		int nbLignes = 0;
		
		PreparedStatement requete = laConnexion.prepareStatement("delete from `Ligne_commande` where id_commande=? and id_produit=? ");

		boolean idCom = verifIdCom(ligneCom.getIdCommande());
		if (idCom)
			requete.setInt(1, ligneCom.getIdCommande());
		else
			throw new IllegalArgumentException("Aucune commande ne possede cet identifiant");
		
		
		boolean idProd = verifIdProd(ligneCom.getIdProduit());
		if (idProd)
			requete.setInt(2, ligneCom.getIdProduit());
		else
			throw new IllegalArgumentException("Aucun produit ne possede cet identifiant"); 
		
		nbLignes = requete.executeUpdate();
		
		if (nbLignes == 0) 
			throw new IllegalArgumentException("Tentative de suppression d'une ligne de commande non existante"); 
		
		if (laConnexion != null)
			laConnexion.close();

		return nbLignes==1;
	}

	@Override
	public LigneCommande getById(int idCom, int idProd) {
		
		LigneCommande ligneCommande = null;
			
		Connection laConnexion = Connexion.creeConnexion();
		  
		//requete pour obtenir la commande desiree
		try {
			PreparedStatement requete = laConnexion.prepareStatement("select * from `Ligne_commande` where id_commande=? and id_produit=?");
			
			boolean idComGet = verifIdCom(idCom);
			if (idComGet)
				requete.setInt(1, idCom);
			else
				throw new IllegalArgumentException("Aucune commande ne possede cet identifiant");
			
			
			boolean idProdGet = verifIdProd(idProd);
			if (idProdGet)
				requete.setInt(2, idProd);
			else
				throw new IllegalArgumentException("Aucun produit ne possede cet identifiant"); 
			  
			ResultSet res = requete.executeQuery();
			  
			if (res.next()) { 
				ligneCommande = new LigneCommande(res.getInt(1), res.getInt(2), res.getInt(3), res.getFloat(4)); 
			}
			else {
				throw new IllegalArgumentException("Aucune ligne de commande ne correspond a ces identifiants"); 
			}
			  
			if (laConnexion != null) 
				laConnexion.close();
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		  
		return ligneCommande;
	}

	@Override
	public ArrayList<LigneCommande> findAll() throws SQLException{
		ArrayList<LigneCommande> listeLigneCommande = new ArrayList<LigneCommande>();;
		
	    Connection laConnexion = Connexion.creeConnexion();
	  
		PreparedStatement requete = laConnexion.prepareStatement("select * from `Ligne_commande`");
		ResultSet res = requete.executeQuery();
  
		while (res.next()) { 
			listeLigneCommande.add(new LigneCommande(res.getInt(1), res.getInt(2), res.getInt(3), res.getFloat(4))) ;
		}
  
		if (laConnexion != null) 
			laConnexion.close();
		 
	    return listeLigneCommande;
	}
	
	private boolean verifIdCom(int id) throws SQLException{
		Connection laConnexion = Connexion.creeConnexion();
		
		boolean idCom = false;
		
		PreparedStatement verifIdCom = laConnexion.prepareStatement("select id_commande from Commande");
		ResultSet verifIdComRes = verifIdCom.executeQuery();
		
		while (verifIdComRes.next()) {
			if (verifIdComRes.getInt(1) == id)
				idCom = true;
		}
		
		return idCom;
	}
	
	
	private boolean verifIdProd(int id) throws SQLException {
		Connection laConnexion = Connexion.creeConnexion();
		
		boolean idProd = false;
		
		PreparedStatement verifIdProd = laConnexion.prepareStatement("select id_produit from Produit");
		ResultSet verifIdProdRes = verifIdProd.executeQuery();
		
		while (verifIdProdRes.next()) {
			if (verifIdProdRes.getInt(1) == id)
				idProd = true;
		}
		
		return idProd;
	}
}


