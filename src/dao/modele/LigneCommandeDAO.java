package dao.modele;

import java.util.ArrayList;

public interface LigneCommandeDAO<LigneCommande> {
	public abstract boolean create (LigneCommande ligneCommande) throws Exception; 
	public abstract boolean update (LigneCommande ligneCommande) throws Exception; 
	public abstract boolean delete (LigneCommande ligneCommande) throws Exception; 
	public abstract LigneCommande getById (int idCom, int idProd) throws Exception; 
	public abstract ArrayList<LigneCommande> findAll() throws Exception;
}
