package application.controller.edit;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Categorie;

public class EditCategorieController implements Initializable {
	
	@FXML private TextField editTitre;
	@FXML private TextField editVisuel;
	@FXML private Button btnModif;
	@FXML private Label lblAffichage;
	
	private Categorie selectedItem;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	//On recupere la categorie selectionnee dans le tableau 
	//On affiche les donnees dans les text field
	public void initData(Categorie categorie) {
		selectedItem = categorie;
		
		editTitre.setText(categorie.getTitre());
		editVisuel.setText(categorie.getVisuel());
	}
	
	//Fonction appelee lorsqu'on clique sur le bouton
	public void modifierCategorie() {
		String titre = this.editTitre.getText().trim();
		String visuel = this.editVisuel.getText().trim();
		
		try {
			//On modifie dans la DAO l'objet Categorie
			Categorie categorie = new Categorie(selectedItem.getId(), titre, visuel);
			
			//Si la modification porte une erreur alors on va dans le catch et "selectedItem" n'est pas modifie
			MainController.categorieDAO.update(categorie);
			this.selectedItem = categorie;
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnModif.getScene().getWindow();
			stage.close();
			
		} catch (Exception e1) {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(e1.getMessage());
		}
	}

	public Categorie getSelectedItem() {
		return selectedItem;
	}
}
