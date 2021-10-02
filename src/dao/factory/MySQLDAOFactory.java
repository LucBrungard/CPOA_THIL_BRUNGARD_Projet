package dao.factory;


import dao.modele.*;
import dao.modele.CategorieDAO;
import dao.modele.ClientDAO;
import dao.modele.ProduitDAO;
import dao.mysql.*;
import modele.LigneCommande;

public class MySQLDAOFactory extends DAOFactory {

	@Override
	public ClientDAO getClientDAO() { return MySQLClientDAO.getInstance(); }
	
	@Override
	public ProduitDAO getProduitDAO() { return MySQLProduitDAO.getInstance(); }

	@Override
	public CategorieDAO getCategorieDAO() {	return MySQLCategorieDAO.getInstance(); }
	
	@Override
	public CommandeDAO getCommandeDAO() { return MySQLCommandeDAO.getInstance(); }
	
	@Override
	public LigneCommandeDAO<LigneCommande> getLigneCommandeDAO() {	return MySQLLigneCommandeDAO.getInstance(); }
}
