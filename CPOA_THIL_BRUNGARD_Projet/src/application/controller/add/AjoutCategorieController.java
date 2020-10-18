package application.controller.add;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Categorie;

public class AjoutCategorieController implements Initializable {
	@FXML private TextField editTitre;
	@FXML private TextField editVisuel;
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	
	CategorieDAO categorieDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.editTitre.setText("");
		this.editVisuel.setText("");
		
	}
	
	public void creerCateg() {
		String titre = this.editTitre.getText().trim();
		String visuel = this.editVisuel.getText().trim();
		
		Stage nStage = new Stage();
		try {
			//On charge URL de la PageCategorie.fxml
			URL fxmlURL=getClass().getResource("/fxml/page/PageCategorie.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			PageCategorieController controller = fxmlLoader.getController();
			
			//On creer dans la DAO l'objet Categorie
			categorieDAO.create(new Categorie(1, titre, visuel));
			
			//On vide les donnees du tableau et on le reremplit
			controller.clearAll();
			controller.initData();
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnCreer.getScene().getWindow();
			stage.close();
			
			URL fxmlURL2=getClass().getResource("/fxml/Main.fxml");
			FXMLLoader fxmlLoader2 = new FXMLLoader(fxmlURL2);
			Node root2 = fxmlLoader2.load();
			//Et on rouvre la fenetre Main.fxml avec les nouvelles donnees
			Scene scene = new Scene((AnchorPane) root2, 700, 440);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.show();
			
		} catch (Exception e1) {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(e1.getMessage());
		}
	}
}
