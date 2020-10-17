package modele;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import dao.Persistance;
import dao.factory.DAOFactory;

public class Commande {
	DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private int id; 
	private LocalDate date; 
	private int idClient;
	private HashMap<Produit, LigneCommande> ligneCommande;

	public Commande(int id, LocalDate date, int idClient, HashMap<Produit, LigneCommande> ligneCommande) {
		this.setId(id);
		this.setDate(date);
		this.setIdClient(idClient);
		this.setLigneCommande(ligneCommande);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		if (id > 0) 
			this.id = id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
			this.date = date;
	}
	
	public int getIdClient() {
		return idClient;
	}
	
	public void setIdClient(int idClient) {
		if (idClient > 0) 
			this.idClient = idClient;
	}
	
	public String getNomClient() {
		try {
			return DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO().getById(idClient).getNom();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public HashMap<Produit, LigneCommande> getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(HashMap<Produit, LigneCommande> ligneCommande) {
		this.ligneCommande = ligneCommande;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commande other = (Commande) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Commande [id=" + id + ", date=" + formatage.format(getDate()) + ", idClient=" + idClient + "\n" + ligneCommande.values() + "]\n";
	} 
}