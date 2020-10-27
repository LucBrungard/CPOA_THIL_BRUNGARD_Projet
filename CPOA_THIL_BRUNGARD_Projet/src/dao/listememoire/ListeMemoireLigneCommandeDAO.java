package dao.listememoire;
import java.util.ArrayList;


import java.util.List;

import dao.modele.LigneCommandeDAO;
import modele.LigneCommande;



public class ListeMemoireLigneCommandeDAO implements LigneCommandeDAO<LigneCommande> {
	
	private static ListeMemoireLigneCommandeDAO instance;

	private List<LigneCommande> donnees;
	
	public static ListeMemoireLigneCommandeDAO getInstance() {

		if (instance == null) {
			instance = new ListeMemoireLigneCommandeDAO();
		}

		return instance;
	}
	
	private ListeMemoireLigneCommandeDAO() {

		this.donnees = new ArrayList<LigneCommande>();

		this.donnees.add(new LigneCommande(1,2,2,(float)41.5)); 
		this.donnees.add(new LigneCommande(1,6,1,15)); 
		this.donnees.add(new LigneCommande(2,12,4,35)); 
		
	}

	
	//Fonction de verification si le titre de categ existe deja
	private boolean duplicata(LigneCommande ligneCommande) {
		int idCommande = ligneCommande.getIdCommande(); 
		int idProduit = ligneCommande.getIdProduit(); 
		
		for (int i = 0; i < donnees.size(); i++) {
			if ((donnees.get(i).getIdCommande() == idCommande) && (donnees.get(i).getIdProduit() == idProduit))
				return true;
		}
		return false;
	}
	

	public boolean create(LigneCommande ligneCommande) {
		
		if (!duplicata(ligneCommande)) 
			this.donnees.add(ligneCommande);
		else
			throw new IllegalArgumentException("Cette commande existe deja, veuillez faire une modification si ce n'est pas une erreur");
		
		return true;
	}

	@Override
	public boolean update(LigneCommande ligneCommande) {
		
		// Ne fonctionne que si l'objet metier est bien fait...int idx = donnees.indexOf(ligneCommande);
		int idx = this.donnees.indexOf(ligneCommande);
		if (idx == -1) 
			throw new IllegalArgumentException("Tentative de modification  d'une ligne de commande inexistante");
		else 
			donnees.set(idx, ligneCommande);
		
		return true;
	}

	@Override
	public boolean delete(LigneCommande objet) {

		// Ne fonctionne que si l'objet metier est bien fait...
		boolean idx = this.donnees.remove(objet);
		if (!idx) 
			throw new IllegalArgumentException("Tentative de suppression d'une ligne de commande inexistante");
		
		return idx;
	}

	@Override
	public LigneCommande getById(int id, int idProduit) {
		// Ne fonctionne que si l'objet metier est bien fait...
		int idx = this.donnees.indexOf(new LigneCommande(id, idProduit, 0,0));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucune ligne de commande ne poss�de cet identifiant");
		} else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public ArrayList<LigneCommande> findAll() {
		return (ArrayList<LigneCommande>) this.donnees;
	}


}
