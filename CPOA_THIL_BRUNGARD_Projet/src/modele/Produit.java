package modele;

import dao.Persistance;
import dao.factory.DAOFactory;

public class Produit {
    private int id ;
    private String nom;
    private String description;
    private float tarif;
    private String visuel;
    private int idCateg; 
    
    public Produit(int id, String nom, String description, float tarif, String visuel, int idCateg) {
        super();
        this.setId(id);
        this.setNom(nom);
        this.setDescription(description); 
        this.setTarif(tarif);
        this.setVisuel(visuel);
        this.setIdCateg(idCateg); 
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id>0) this.id=id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		if (nom == null || nom.trim().length() == 0) {
			throw new IllegalArgumentException("Nom vide !");
		}
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null || description.trim().length() == 0) {
			throw new IllegalArgumentException("Description vide !");
		}
		this.description=description;
	}

	public float getTarif() {
		return tarif;
	}

	public void setTarif(float tarif) {
		if (tarif > 0) this.tarif = tarif;
	}

	public String getVisuel() {
		return visuel;
	}

	public void setVisuel(String visuel) {
		if (visuel == null || visuel.trim().length() == 0) {
			throw new IllegalArgumentException("Visuel vide !");
		}
		this.visuel = visuel;
	}

	public int getIdCateg() {
		return idCateg;
	}

	public void setIdCateg(int idCateg) {
		if (idCateg > 0) this.idCateg = idCateg;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produit [id=" + id + ", nom=" + nom + ", description=" + description + ", tarif=" + tarif + ", visuel="
				+ visuel + ", idCateg=" + idCateg + "]";
	}  
	
	public String toStringUtilisateur() {
		String nomCateg = "";
		try {
			nomCateg = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO().getById(idCateg).getTitre();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nom + " (" + nomCateg + "), " + tarif + " euro";
	}
}
