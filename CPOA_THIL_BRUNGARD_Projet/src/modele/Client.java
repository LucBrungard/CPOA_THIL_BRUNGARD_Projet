package modele;


public class Client {
	private int id;
	private String nom ;
	private String prenom;
	private String identifiant; 
	private String motDePasse; 
	private String numero;
	private String rue;  
	private String codePostal;
	private String ville; 
	private String pays; 
	
	public Client(int id, String nom, String prenom, String identifiant, String motDePasse,  String numero, String rue, String codePostal, String ville, String pays ) {
		super();
		this.setId(id);
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setIdentifiant(identifiant); 
		this.setMotDePasse(motDePasse); 
		this.setNumero(numero);
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

	
	public String getIdentifiant() {
		return identifiant;
	}


	public void setIdentifiant(String identifiant) {
		if (identifiant == null || identifiant.trim().length() == 0) {
			throw new IllegalArgumentException("Adresse mail vide !");
		}
		this.identifiant= identifiant;
	}


	public String getMotDePasse() {
		return motDePasse;
	}


	public void setMotDePasse(String motDePasse) {
		if (motDePasse == null || motDePasse.trim().length() == 0) {
			throw new IllegalArgumentException("Mot de passe vide !");
		}
		this.motDePasse = motDePasse;
	}


	public String getNumero() {
		return this.numero;
	}


	public void setNumero(String numero) {
		if (numero == null || numero.trim().length() == 0) {
			throw new IllegalArgumentException("Numero vide !");
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
		if (id != other.id)
			return false;
		return true;
	}


	
	
	@Override
	public String toString() {
		return nom + ", " + prenom;
	}
	
}
