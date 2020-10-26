package dao.listememoire;
import java.util.ArrayList;

import java.util.List;

import dao.Persistance;
import dao.factory.DAOFactory;
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


	public boolean create(LigneCommande objet) {

		boolean idx = true;
		
		try {
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO().getById(objet.getIdCommande());
		} catch (Exception e) {
			idx = false;
			System.out.println(e.getMessage());
		}
		
		try {
			DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().getById(objet.getIdProduit());
		} catch (Exception e) {
			idx = false;
			System.out.println(e.getMessage());
		}
		
		if (idx) {
			if (this.donnees.contains(objet))
				throw new IllegalArgumentException("Cette commande existe deja, veuillez faire une modification si ce n'est pas une erreur");
			else 
				this.donnees.add(objet);
		}
			
		return idx;
	}

	@Override
	public boolean update(LigneCommande objet) {
		
		// Ne fonctionne que si l'objet metier est bien fait...
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'une ligne de commande inexistante");
		} else {
			
			this.donnees.set(idx, objet);
		}
		
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
