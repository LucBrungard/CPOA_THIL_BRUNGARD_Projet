package application.controller.add;

import java.net.URL;


import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import application.controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Client;
import modele.Commande;
import modele.Produit;
import modele.LigneCommande;

public class AjoutCommandeController implements Initializable {
	
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	@FXML private DatePicker editDate;
	@FXML private TextField editQuantite; 
	@FXML private ChoiceBox<Client> cbxClient;
	@FXML private ChoiceBox<Produit> cbxProduit;
	
	private Commande commandeAjout;
	
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
		ObservableList<Client> listeClient = FXCollections.observableArrayList();
		ObservableList<Produit> listeProduit = FXCollections.observableArrayList();
	
		editDate.setEditable(false);
		lblAffichage.setText(""); 
		
	    try {
	    	this.editQuantite.setText("");
	 
	    	for (Client client : MainController.clientDAO.findAll()) {
	    		listeClient.add(client);
	    	}
			this.cbxClient.setItems(listeClient); 
			
			for (Produit produit : MainController.produitDAO.findAll()) {
	    		listeProduit.add(produit);
	    	}
			this.cbxProduit.setItems( listeProduit );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	   
	@FXML
	public void ajoutCommande() {

		int quantite = 0;

		//Objet de type Client qui correspond a l'objet selectionne dans le choice box
		Client selectClient = cbxClient.getSelectionModel().getSelectedItem(); 
		int idClient = 0;
		if (selectClient != null) {
			idClient = selectClient.getId();
		}

		//Objet de type Produit qui correspond a l'objet selectionne dans le choice box
		Produit selectProduit = cbxProduit.getSelectionModel().getSelectedItem(); 
		int idProduit = 0;
		if (selectProduit != null) {
			idProduit = selectProduit.getId();
		}

		//on convertit la quantite qui est en String en int 
		try {
			quantite = Integer.parseInt(editQuantite.getText().trim());
		}
		catch (NumberFormatException e) {
			this.lblAffichage.setText(e.getMessage());
		}

		//On cree la commande. Si erreur, elle sera affichee dans le label a cet effet
		//On enregistre l'instance de commande que l'on vient de creer pour la recuperer sur la page PageCommandeController
		try {
			//on vérifie que la date a ete entree 
			if (editDate.getValue()!=null) {

				//On creer dans la DAO l'objet Commande avec une ligne commande

				HashMap<Produit, LigneCommande> ligneCommande = new HashMap<Produit, LigneCommande>(); 
				Commande commande = new Commande(1, editDate.getValue(), idClient, ligneCommande);
				MainController.commandeDAO.create(commande);
				LigneCommande lc = new LigneCommande(commande.getId(), idProduit, quantite, MainController.produitDAO.getById(idProduit).getTarif());
				ligneCommande.put(MainController.produitDAO.getById(idProduit), lc); 
				MainController.ligneCommandeDAO.create(lc); 

				this.commandeAjout = commande;

				//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
				Stage stage = (Stage) btnCreer.getScene().getWindow();
				stage.close();
			}

			else {
				this.lblAffichage.setTextFill(Color.RED);
				this.lblAffichage.setText("Merci de saisir la date");
			}

		}
		catch (Exception e) {
			this.lblAffichage.setTextFill(Color.RED);
			this.lblAffichage.setText(e.getMessage());
			System.out.println(e.getMessage());
			
			//si le client à déjà une commande à cette date, on l'informe donc que le produit sera ajouté a la commande existante
			try {
				for (Commande commande : MainController.commandeDAO.findAll()) {
					if ((commande.getIdClient() == idClient) && (commande.getDate().equals(editDate.getValue()))){
						int idCommande = commande.getId(); 
						
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Alerte doublon");
						alert.setContentText("Ce client a déjà une commande à cette date, si vous cliquez sur OK, le produit sera ajouté à la commande déjà existante");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK){
							try {
									LigneCommande lc = new LigneCommande(idCommande, idProduit, quantite, MainController.produitDAO.getById(idProduit).getTarif()); 
									MainController.ligneCommandeDAO.create(lc); 
									commande.getLigneCommande().put(MainController.produitDAO.getById(idProduit), lc); 
									MainController.commandeDAO.update(commande); 
									//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
									Stage stage = (Stage) btnCreer.getScene().getWindow();
									stage.close();
							} catch (Exception e1) {
								lblAffichage.setText(e1.getMessage());;
							}

						}
					}
				}
			} catch (Exception e1) {
				lblAffichage.setText(e.getMessage()); 
			}
		}
			
		}

	public Commande getCommandeAjout() {
		return commandeAjout;
	}

}
