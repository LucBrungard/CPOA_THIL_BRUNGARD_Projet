package application.controller.edit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.controller.page.PageCategorieController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modele.Categorie;

public class EditCategorieController implements Initializable {
	
	@FXML private TextField editTitre;
	@FXML private TextField editVisuel;
	@FXML private Button btnModif;
	@FXML private Label lblAffichage;
	
	private Categorie selectedItem;
	
	CategorieDAO categorieDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	
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
		
		Stage nStage = new Stage();
		try {
			//On charge URL de la PageCategorie.fxml
			URL fxmlURL=getClass().getResource("/fxml/page/PageCategorie.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			PageCategorieController controller = fxmlLoader.getController();
			
			//On modifie dans la DAO l'objet Categorie
			categorieDAO.update(new Categorie(selectedItem.getId(), titre, visuel));
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnModif.getScene().getWindow();
			stage.close();
			
			//On vide les donnees du tableau et on le reremplit
			controller.clearAll();
			controller.initData();
			
			//Et on rouvre la fenetre PageCateg.fxml avec les nouvelles donnees
			Scene scene = new Scene((AnchorPane) root, 700, 440);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.show();
			
		} catch (Exception e1) {
			this.lblAffichage.setText(e1.getMessage());
		}
	}
}
