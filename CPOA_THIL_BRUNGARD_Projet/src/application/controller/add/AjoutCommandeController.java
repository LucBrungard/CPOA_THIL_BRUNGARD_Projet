package application.controller.add;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ClientDAO;
import dao.modele.CommandeDAO;
import dao.modele.LigneCommandeDAO;
import dao.modele.ProduitDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Client;
import modele.Commande;
import modele.Produit;
import modele.LigneCommande;

public class AjoutCommandeController implements Initializable {
	
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	@FXML private TextField editDate;
	@FXML private TextField editQuantite; 
	@FXML private ChoiceBox<Client> cbxClient;
	@FXML private ChoiceBox<Produit> cbxProduit;
	
	CommandeDAO commandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
	LigneCommandeDAO<LigneCommande> ligneCommandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getLigneCommandeDAO();
	ClientDAO clientDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO();
	ProduitDAO produitDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO();
	private Commande commandeAjout;
	
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		ObservableList<Client> listeClient = FXCollections.observableArrayList();
		ObservableList<Produit> listeProduit = FXCollections.observableArrayList();
	
	    try {
	    	this.editDate.setText("");
	    	this.editQuantite.setText("");
	 
	    	for (Client client : clientDAO.findAll()) {
	    		listeClient.add(client);
	    	}
			this.cbxClient.setItems(listeClient); 
			
			for (Produit produit : produitDAO.findAll()) {
	    		listeProduit.add(produit);
	    	}
			this.cbxProduit.setItems( listeProduit );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	   
	@FXML
	public void ajoutCommande() {
		String date = editDate.getText().trim();
		int quantite = 0;
		
		//Objet de type Categorie qui correspond a l'objet selectionne dans le choice box
		Client selectClient = cbxClient.getSelectionModel().getSelectedItem(); 
		int idClient = 0;
		
		Produit selectProduit = cbxProduit.getSelectionModel().getSelectedItem(); 
		int idProduit = 0;
		
		if (selectClient != null) {
			idClient = selectClient.getId();
		}
		
		if (selectProduit != null) {
			idProduit = selectProduit.getId();
		}
		
		//on convertit le tarif qui est en String en int 
		try {
			quantite = Integer.parseInt(editQuantite.getText().trim());
		}
		catch (NumberFormatException e) {
			//this.lblAffichage.setText(e.getMessage());
		}
		
		//On creer le produit. Si erreur, elle sera affichee dans le label a cet effet
		//On enregistre l'instance de produit que l'on vient de creer pour la recuperer sur la page PageProduitController
		try {
			DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			//On creer dans la DAO l'objet Produit
			
			HashMap<Produit, LigneCommande> ligneCommande = new HashMap<Produit, LigneCommande>(); 
			Commande commande = new Commande(1, LocalDate.parse(date, formatage), idClient, ligneCommande);
			commandeDAO.create(commande);
			LigneCommande lc = new LigneCommande(commande.getId(), idProduit, quantite, produitDAO.getById(idProduit).getTarif());
			ligneCommande.put(produitDAO.getById(idProduit), lc); 
			ligneCommandeDAO.create(lc); 
			
			this.commandeAjout = commande;
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnCreer.getScene().getWindow();
			stage.close();
			
		}
		catch (Exception e) {
			this.lblAffichage.setTextFill(Color.RED);
			this.lblAffichage.setText(e.getMessage());
			e.printStackTrace();
		}
	}

	public Commande getCommandeAjout() {
		return commandeAjout;
	}

}
