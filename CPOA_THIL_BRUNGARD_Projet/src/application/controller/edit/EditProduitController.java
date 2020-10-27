package application.controller.edit;

import application.controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Categorie;
import modele.Produit;

public class EditProduitController {
	@FXML private Button btnModif;
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextArea editDesc; 
	@FXML private TextField editTarif;
	@FXML private ChoiceBox<Categorie> cbxCategorie;
	ObservableList<Categorie> listeCateg = FXCollections.observableArrayList();
	
	private Produit selectedItem;
	   
	public void initData(Produit produit) {
		selectedItem = produit;
		
		editNom.setText(produit.getNom());
		editDesc.setText(produit.getDescription());
		editTarif.setText(Float.toString(produit.getTarif()));
		try {
			cbxCategorie.getItems().setAll(MainController.categorieDAO.findAll());
			cbxCategorie.setValue(MainController.categorieDAO.getById(selectedItem.getIdCateg()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lblAffichage.setText("");
	}

	@FXML
	public void modifProduit() {
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
		
		try {
			//On creer dans la DAO l'objet Produit
			Produit produit = new Produit(selectedItem.getId(), nom, desc, tarif, nom.concat(".png"), idCateg);
			MainController.produitDAO.update(produit);
			
			this.selectedItem = produit;
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnModif.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			this.lblAffichage.setTextFill(Color.RED);
			this.lblAffichage.setText(e.getMessage());
		}
	}

	public Produit getSelectedItem() {
		return selectedItem;
	}
}
