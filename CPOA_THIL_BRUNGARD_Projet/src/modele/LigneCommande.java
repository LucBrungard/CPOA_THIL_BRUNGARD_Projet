package modele;

public class LigneCommande {
	
	private int idCommande;
	private int idProduit; 
	private int quantite; 
	private float prixUnitaire;
	
	
	public LigneCommande(int idCommande, int idProduit, int quantite, float prixUnitaire) {
		this.setIdCommande(idCommande);
		this.setIdProduit(idProduit);
		this.setQuantite(quantite);
		this.setPrixUnitaire(prixUnitaire);
	}


	public int getIdCommande() {
		return idCommande;
	}


	public void setIdCommande(int idCommande) {
		if (idCommande > 0)
			this.idCommande = idCommande;
	}


	public int getIdProduit() {
		return idProduit;
	}


	public void setIdProduit(int idProduit) {
		if (idProduit > 0) 
			this.idProduit = idProduit;
	}


	public int getQuantite() {
		return quantite;
	}


	public void setQuantite(int quantite) {
		if (quantite > 0) 
			this.quantite = quantite;
	}


	public float getPrixUnitaire() {
		return prixUnitaire;
	}


	public void setPrixUnitaire(float prixUnitaire) {
		if (prixUnitaire > 0) 
			this.prixUnitaire = prixUnitaire; 
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LigneCommande other = (LigneCommande) obj;
		if (idCommande != other.idCommande)
			return false;
		if (idProduit != other.idProduit)
			return false;
		
		return true;
	}


	@Override
	public String toString() {
		return "LigneCommande [idCommande=" + idCommande + ", idProduit=" + idProduit + ", quantite=" + quantite + ", prixUnitaire=" + prixUnitaire + "]";
	} 
}
