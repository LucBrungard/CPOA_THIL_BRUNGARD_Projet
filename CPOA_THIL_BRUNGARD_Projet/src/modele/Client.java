package modele;

public class Client {
	private int id;
	private String nom ;
	private String prenom;
	
	public Client(int id, String nom, String prenom) {
		super();
		this.setId(id);
		this.setNom(nom);
		this.setPrenom(prenom);
	}
	
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		if (id > 0) this.id = id;
	}
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		if (nom == null || nom.trim().length() == 0) {
			throw new IllegalArgumentException("Nom vide !");
		}
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		if (prenom == null || prenom.trim().length() == 0) {
			throw new IllegalArgumentException("Prenom vide !");
		}
		this.prenom = prenom;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + "]";
	}

	
}
