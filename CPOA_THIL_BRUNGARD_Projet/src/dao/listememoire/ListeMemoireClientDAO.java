package dao.listememoire;

import java.util.ArrayList;
import java.util.List;

import dao.modele.ClientDAO;
import modele.Client;

public class ListeMemoireClientDAO implements ClientDAO {
	
	private static ListeMemoireClientDAO instance;

	private List<Client> donnees;


	public static ListeMemoireClientDAO getInstance() {

		if (instance == null) {
			instance = new ListeMemoireClientDAO();
		}

		return instance;
	}

	private ListeMemoireClientDAO() {

		this.donnees = new ArrayList<Client>();

		this.donnees.add(new Client(1, "LAROCHE", "Pierre"));
	}


	@Override
	public boolean create(Client objet) throws IllegalArgumentException {

		objet.setId(2);
		// Ne fonctionne que si l'objet metier est bien fait...
		while (this.donnees.contains(objet)) {

			objet.setId(objet.getId() + 1);
		}
		boolean ok = this.donnees.add(objet);
		
		if (!ok)
			throw new IllegalArgumentException("Erreur lors de la creation du client");
		
		return ok;
	}

	@Override
	public boolean update(Client objet) throws IllegalArgumentException {
		
		// Ne fonctionne que si l'objet metier est bien fait...
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un client inexistant");
		} else {
			this.donnees.set(idx, objet);
		}
		
		return true;
	}

	@Override
	public boolean delete(Client objet) throws IllegalArgumentException {

		Client supprime = null;
		
		// Ne fonctionne que si l'objet metier est bien fait...
		int idx = this.donnees.indexOf(objet);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un client inexistant");
		} else {
			supprime = this.donnees.remove(idx);
		}
		
		return objet.equals(supprime);
	}

	@Override
	public Client getById(int id) throws IllegalArgumentException {
		// Ne fonctionne que si l'objet metier est bien fait...
		int idx = this.donnees.indexOf(new Client(id, "TEST", "Test"));
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun client ne possede cet identifiant");
		} else {
			return this.donnees.get(idx);
		}
	}

	@Override
	public ArrayList<Client> findAll() {
		return (ArrayList<Client>) this.donnees;
	}
}
