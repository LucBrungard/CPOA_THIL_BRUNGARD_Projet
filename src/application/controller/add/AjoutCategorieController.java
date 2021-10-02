package application.controller.add;

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

public class AjoutCategorieController implements Initializable {
	@FXML private TextField editTitre;
	@FXML private TextField editVisuel;
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	
	private Categorie categorie;
	
	public Categorie getCategorie() {
		return categorie;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.editTitre.setText("");
		this.editVisuel.setText("");
		
	}
	
	//On recupere instance de categorie creee pour la recuperer sur la page PageCategorieController
	public void creerCateg() {
		String titre = this.editTitre.getText().trim();
		String visuel = this.editVisuel.getText().trim();
		
		try {
			//On creer dans la DAO l'objet Categorie
			this.categorie = new Categorie(1, titre, visuel);
			MainController.categorieDAO.create(categorie);
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnCreer.getScene().getWindow();
			stage.close();
			
		} catch (Exception e1) {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(e1.getMessage());
		}
	}
}
