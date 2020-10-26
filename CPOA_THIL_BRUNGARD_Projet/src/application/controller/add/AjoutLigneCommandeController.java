package application.controller.add;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
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
import modele.Commande;
import modele.LigneCommande;
import modele.Produit;

public class AjoutLigneCommandeController implements Initializable {

	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	@FXML private TextField editQuantite; 
	@FXML private ChoiceBox<Produit> cbxProduit;
	
	LigneCommandeDAO<LigneCommande> ligneCommandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getLigneCommandeDAO();
	CommandeDAO commandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
	ProduitDAO produitDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO();
	private LigneCommande ligneCommandeAjout;
	private Commande selectedItem; 
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		ObservableList<Produit> listeProduit = FXCollections.observableArrayList();
	
	    try {
	    	this.editQuantite.setText("");
	 
	    	for (Produit produit : produitDAO.findAll()) {
	    		listeProduit.add(produit);
	    	}
			this.cbxProduit.setItems(listeProduit); 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initData(Commande commande) {
		selectedItem = commande;
	}
	   
	@FXML
	public void ajoutLigneCommande() {

		int quantite = 0;

		//Objet de type Categorie qui correspond a l'objet selectionne dans le choice box
		
		Produit selectProduit = cbxProduit.getSelectionModel().getSelectedItem(); 
		int idProduit = 0;
		
		if (selectProduit != null) {
			idProduit = selectProduit.getId();
		}
		
		//on convertit le tarif qui est en String en int 
		try {
			quantite = Integer.parseInt(editQuantite.getText().trim());
		}
		catch (NumberFormatException e) {
			this.lblAffichage.setText(e.getMessage());
		}
		
		//On creer le produit. Si erreur, elle sera affichee dans le label a cet effet
		//On enregistre l'instance de produit que l'on vient de creer pour la recuperer sur la page PageProduitController
		try {
			
			//On creer dans la DAO l'objet Produit
			LigneCommande ligneCommande = new LigneCommande(selectedItem.getId(), idProduit, quantite, produitDAO.getById(idProduit).getTarif());
			ligneCommandeDAO.create(ligneCommande); 
			
			LocalDate date = selectedItem.getDate(); 
			int client = selectedItem.getIdClient(); 
			HashMap<Produit, LigneCommande> lc = selectedItem.getLigneCommande(); 
			lc.put(produitDAO.getById(idProduit), ligneCommande); 
			Commande commande = new Commande(selectedItem.getId(), date, client, lc);
			commandeDAO.update(commande);
			
			this.ligneCommandeAjout = ligneCommande;
		
			
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnCreer.getScene().getWindow();
			stage.close();
			
		}
		catch (Exception e) {
			this.lblAffichage.setTextFill(Color.RED);
			this.lblAffichage.setText(e.getMessage());
		}
	}

	public LigneCommande getLigneCommandeAjout() {
		return ligneCommandeAjout;
	}
}
