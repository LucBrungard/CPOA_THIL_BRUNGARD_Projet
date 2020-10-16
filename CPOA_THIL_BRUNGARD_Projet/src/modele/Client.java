package modele;

public class Client {
	private int id;
	private String nom ;
	private String prenom;
	private String numero;
	private String rue;  
	private String codePostal;
	private String ville; 
	private String pays; 
	
	public Client(int id, String nom, String prenom, String numero, String rue, String codePostal, String ville, String pays ) {
		super();
		this.setId(id);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setRue(rue);
		this.setCodePostal(codePostal);
		this.setVille(ville);
		this.setPays(pays);
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

	
	public String getNumero() {
		return this.numero;
	}


	public void setNumero(String numero) {
		if (numero == null || numero.trim().length() == 0) {
			throw new IllegalArgumentException("Numéro vide !");
		}
		this.numero= numero;
	}


	public String getRue() {
		return this.rue;
	}


	public void setRue(String rue) {
		if (rue == null || rue.trim().length() == 0) {
			throw new IllegalArgumentException("Rue vide !");
		}
		this.rue = rue;
	}


	public String getCodePostal() {
		return this.codePostal;
	}


	public void setCodePostal(String codePostal) {
		if (codePostal == null || codePostal.trim().length() == 0) {
			throw new IllegalArgumentException("Code postal vide !");
		}
		this.codePostal = codePostal;
	}


	public String getVille() {
		return this.ville;
	}


	public void setVille(String ville) {
		if (ville == null || ville.trim().length() == 0) {
			throw new IllegalArgumentException("Ville vide !");
		}
		this.ville = ville;
	}


	public String getPays() {
		return this.pays;
	}


	public void setPays(String pays) {
		if (pays == null || pays.trim().length() == 0) {
			throw new IllegalArgumentException("Pays vide !");
		}
		this.pays = pays;
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
		if (codePostal == null) {
			if (other.codePostal != null)
				return false;
		} else if (!codePostal.equals(other.codePostal))
			return false;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (pays == null) {
			if (other.pays != null)
				return false;
		} else if (!pays.equals(other.pays))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (rue == null) {
			if (other.rue != null)
				return false;
		} else if (!rue.equals(other.rue))
			return false;
		if (ville == null) {
			if (other.ville != null)
				return false;
		} else if (!ville.equals(other.ville))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", numero=" + numero + ", rue=" + rue
				+ ", codePostal=" + codePostal + ", ville=" + ville + ", pays=" + pays + "]";
	}


	

	
}
