package application.controller.add;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Categorie;
import modele.Produit;

public class AjoutProduitController implements Initializable{
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextArea editDesc; 
	@FXML private TextField editTarif;
	@FXML private ChoiceBox<Categorie> cbxCategorie;
	
	private Produit produitAjout;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Categorie> listeCateg = FXCollections.observableArrayList();
	    try {
	    	this.editNom.setText("");
	    	this.editDesc.setText("");
	    	this.editTarif.setText("");
	    	for (Categorie categorie : MainController.categorieDAO.findAll()) {
	    		listeCateg.add(categorie);
	    	}
			this.cbxCategorie.setItems( listeCateg );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	   
	@FXML
	public void creerProduit() {
		String nom = editNom.getText().trim();
		String desc = editDesc.getText().trim(); 
		float tarif = 0;
		//on convertit le tarif qui est en String en int 
		try {
			tarif = Float.parseFloat(editTarif.getText().trim());
		}
		catch (NumberFormatException e) {
			this.lblAffichage.setText(e.getMessage());
		}
		
		//Objet de type Categorie qui correspond a l'objet selectionne dans le choice box
		int idCateg = 0;
		
		if (cbxCategorie.getSelectionModel().getSelectedItem() != null) {
			idCateg = cbxCategorie.getSelectionModel().getSelectedItem().getId();
		}
		
		
		
		//On creer le produit. Si erreur, elle sera affichee dans le label a cet effet
		//On enregistre l'instance de produit que l'on vient de creer pour la recuperer sur la page PageProduitController
		try {
			//On creer dans la DAO l'objet Produit
			Produit produit = new Produit(1, nom, desc, tarif, nom.concat(".png"), idCateg);
			
			MainController.produitDAO.create(produit);
			
			this.produitAjout = produit;
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnCreer.getScene().getWindow();
			stage.close();
			
		}
		catch (Exception e) {
			this.lblAffichage.setTextFill(Color.RED);
			this.lblAffichage.setText(e.getMessage());
		}
	}

	public Produit getProduitAjout() {
		return produitAjout;
	}
}
