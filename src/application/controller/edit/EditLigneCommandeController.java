package application.controller.edit;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import application.controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Commande;
import modele.LigneCommande;
import modele.Produit;

public class EditLigneCommandeController implements Initializable {
	
	@FXML private Button btnModif;
	@FXML private Label lblAffichage;
	@FXML private TextField editQuantite;
	
	private LigneCommande selectedItem;
	private Commande selectedCommande; 
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		lblAffichage.setText("");
	}
	   
	public void initData(LigneCommande ligneCommande, Commande commande) {
		
		selectedItem = ligneCommande; 
		selectedCommande = commande; 
		editQuantite.setText(String.valueOf(selectedItem.getQuantite()));
		
	}
	
	@FXML
	public void modifLigneCommande() {
		
		int quantite = 0; 
		
		
		try {
			quantite = Integer.parseInt(editQuantite.getText().trim()); 
		
			//On creer dans la DAO l'objet Ligne Commande
				
			HashMap <Produit, LigneCommande> lc = selectedCommande.getLigneCommande(); 
			LigneCommande ligneCommande = new LigneCommande(selectedItem.getIdCommande(), selectedItem.getIdProduit(), quantite, selectedItem.getPrixUnitaire()); 
			
			//on parcourt la hashmap et on met l'élément à jour
			for (Map.Entry<Produit,LigneCommande> m : lc.entrySet()) {
				if(m.getValue()==selectedItem) lc.replace((Produit) m.getKey(), ligneCommande); 
	        }
 			
			MainController.ligneCommandeDAO.update(ligneCommande); 
			
			//on met la commande à jour
			Commande commande = new Commande(selectedCommande.getId(), selectedCommande.getDate(), selectedCommande.getIdClient(), lc); 
			MainController.commandeDAO.update(commande);
			
			this.selectedItem = ligneCommande;
			
			//On recupere la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnModif.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			this.lblAffichage.setTextFill(Color.RED);
			this.lblAffichage.setText(e.getMessage());
		}
		
	}

	public LigneCommande getSelectedItem() {
		return selectedItem;
	}



}
