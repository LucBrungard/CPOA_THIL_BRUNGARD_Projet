package dao.factory;

import dao.listememoire.*;
import dao.modele.*;
import modele.LigneCommande;

public class ListeMemoireDAOFactory extends DAOFactory {

	@Override
	public ClientDAO getClientDAO() { return ListeMemoireClientDAO.getInstance(); }

	@Override
	public ProduitDAO getProduitDAO() {	return ListeMemoireProduitDAO.getInstance(); }

	@Override
	public CategorieDAO getCategorieDAO() { return ListeMemoireCategorieDAO.getInstance(); }
	
	@Override
	public CommandeDAO getCommandeDAO() { return ListeMemoireCommandeDAO.getInstance(); }
	
	@Override
	public LigneCommandeDAO<LigneCommande> getLigneCommandeDAO() {	return ListeMemoireLigneCommandeDAO.getInstance(); }
}
