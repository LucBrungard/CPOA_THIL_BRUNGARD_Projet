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

		this.donnees.add(new Client(1, "LAROCHE", "Pierre", "pl@ul.fr", "toto", "12", "rue des etudiants", "57990", "Metz", "France"));
	}
	
	//Fonction de verification si le titre de categ existe deja
	private boolean duplicata(Client client) {
		String nom = client.getNom();
		String prenom = client.getPrenom();
		String identifiant = client.getIdentifiant();
		
		for (int i = 0; i < donnees.size(); i++) {
			if ((donnees.get(i).getNom().equals(nom)) && (donnees.get(i).getPrenom().equals(prenom)) && (donnees.get(i).getIdentifiant().equals(identifiant)))
				return true;
		}
		return false;
	}


	@Override
	public boolean create(Client client) throws IllegalArgumentException {

		client.setId(2);
		// Ne fonctionne que si l'objet metier est bien fait...
		
		while (donnees.contains(client)) {
			client.setId(client.getId() + 1);
		}
		
		if (!duplicata(client))
			donnees.add(client);
		else 
			throw new IllegalArgumentException("Ce client existe deja !");
		
		return true;
	}

	@Override
	public boolean update(Client client) throws IllegalArgumentException {
		
		
		int idx = donnees.indexOf(client);
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un client inexistant");
		} else {
			//si le nom n'a pas ete change
			if ((donnees.get(idx).getNom().equals(client.getNom())) && (donnees.get(idx).getPrenom().equals(client.getPrenom())) && (donnees.get(idx).getIdentifiant().equals(client.getIdentifiant())) )
				donnees.set(idx, client);
			//sinon si le nom a ete change, on regarde si le nom existe deja
			else if (!duplicata(client))
				donnees.set(idx, client);
			else
				throw new IllegalArgumentException("Ce client existe deja !");
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
		int idx = this.donnees.indexOf(new Client(id, "TEST", "Test", "Test", "Test", "Test", "Test", "Test", "Test", "Test"));
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
