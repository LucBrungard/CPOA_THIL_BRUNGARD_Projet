package modele;

public class Categorie {
	private int id;
	private String titre;
	private String visuel;

	public Categorie (int id, String titre, String visuel) {
		this.setId(id);
		this.setTitre(titre);
		this.setVisuel(visuel);
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		if (id > 0) this.id = id;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		if (titre == null || titre.trim().length() == 0) {
			throw new IllegalArgumentException("Titre de la categorie vide !");
		}
		this.titre = titre;
	}

	public String getVisuel() {
		return this.visuel;
	}

	public void setVisuel(String visuel) {
		if (visuel == null || visuel.trim().length() == 0) {
			throw new IllegalArgumentException("Nom du visuel vide !");
		}
		this.visuel = visuel;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		if (id != other.id)
			return false;
		
		return true;
	}
		
	@Override
	public String toString() {
		return "Categorie [id=" + id + ", titre=" + titre + ", visuel=" + visuel + "]";
	}
	
	
}
