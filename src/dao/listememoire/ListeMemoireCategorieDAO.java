package dao.listememoire;

import java.util.ArrayList;
import java.util.List;

import dao.modele.CategorieDAO;
import modele.Categorie;

public class ListeMemoireCategorieDAO implements CategorieDAO {

	private static ListeMemoireCategorieDAO instance;

	private static List<Categorie> donnees;

	public static ListeMemoireCategorieDAO getInstance() {

		if (instance == null) {
			instance = new ListeMemoireCategorieDAO();
		}

		return instance;
	}

	private ListeMemoireCategorieDAO() {

		donnees = new ArrayList<Categorie>();

		donnees.add(new Categorie(1, "Pulls", "pulls.png"));
		donnees.add(new Categorie(2, "Bonnets", "bonnets.png"));
	}

	//Fonction de verification si le titre de categ existe deja
	private boolean duplicata(Categorie categorie) {
		String titre = categorie.getTitre();
		
		for (int i = 0; i < donnees.size(); i++) {
			if (donnees.get(i).getTitre().equals(titre))
				return true;
		}
		return false;
	}

	@Override
	public boolean create(Categorie categorie) {
		categorie.setId(3);
		
		// Ne fonctionne que si l'objet metier est bien fait...
		while (donnees.contains(categorie)) {
			categorie.setId(categorie.getId() + 1);
		}
		
		if (!duplicata(categorie))
			donnees.add(categorie);
		else 
			throw new IllegalArgumentException("Ce titre de categorie existe deja !");
		
		return true;
	}

	@Override
	public boolean update(Categorie objet) {
		
		// Ne fonctionne que si l'objet metier est bien fait...
		int idx = donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'une categorie inexistante");
		} else {
			//si le nom n'a pas ete change
			if (donnees.get(idx).getTitre().equals(objet.getTitre())) 
				donnees.set(idx, objet);
			//sinon si le nom a pas ete change, on regarde si le nom existe deja
			else if (!duplicata(objet))
				donnees.set(idx, objet);
			else
				throw new IllegalArgumentException("Ce titre de categorie existe deja !");
		}
		
		return true;
	}

	@Override
	public boolean delete(Categorie objet) {

		// Ne fonctionne que si l'objet metier est bien fait...
		boolean idx = donnees.remove(objet);
		if (!idx) 
			throw new IllegalArgumentException("Tentative de suppression d'une categorie inexistante");
		
		return idx;
	}

	@Override
	public Categorie getById(int id) {
		// Ne fonctionne que si l'objet metier est bien fait...
		int idx = donnees.indexOf(new Categorie(id, "test", "test.png"));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucune categorie ne possede cet identifiant");
		} else {
			return donnees.get(idx);
		}
	}

	@Override
	public ArrayList<Categorie> findAll() {
		return (ArrayList<Categorie>) donnees;
	}
}