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


	@Override
	public boolean create(Categorie categorie) {

		categorie.setId(3);
		
		// Ne fonctionne que si l'objet metier est bien fait...
		while (donnees.contains(categorie)) {

			categorie.setId(categorie.getId() + 1);
		}
		boolean ok = donnees.add(categorie);
		
		return ok;
	}

	@Override
	public boolean update(Categorie objet) {
		
		// Ne fonctionne que si l'objet m�tier est bien fait...
		int idx = donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'une categorie inexistante");
		} else {
			
			donnees.set(idx, objet);
		}
		
		return true;
	}

	@Override
	public boolean delete(Categorie objet) {

		// Ne fonctionne que si l'objet m�tier est bien fait...
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