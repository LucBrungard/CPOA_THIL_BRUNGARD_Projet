package application.controller.edit;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.page.PageCategorieController;
import application.controller.page.PageProduitController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
import dao.modele.ProduitDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Categorie;
import modele.Produit;

public class EditProduitController implements Initializable{
	@FXML private Button btnModif;
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextArea editDesc; 
	@FXML private TextField editTarif;
	@FXML private ChoiceBox<Categorie> cbxCategorie;
	ObservableList<Categorie> listeCateg = FXCollections.observableArrayList();
	
	ProduitDAO produitDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO();
	CategorieDAO categorieDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	
	private Produit selectedItem;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
	   
	public void initData(Produit produit) {
		selectedItem = produit;
		
		editNom.setText(produit.getNom());
		editDesc.setText(produit.getDescription());
		editTarif.setText(Float.toString(produit.getTarif()));
		try {
			cbxCategorie.getItems().setAll(categorieDAO.findAll());
			cbxCategorie.setValue(categorieDAO.getById(selectedItem.getIdCateg()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lblAffichage.setText("");
		
	}

	@FXML
	public void modifProduit() {
		String nom = editNom.getText().trim();
		String desc = editDesc.getText().trim(); 
		float tarif = 0;
		
		//Objet de type Categorie qui correspond a l'objet selectionne dans le choice box
		Categorie selectCateg = cbxCategorie.getSelectionModel().getSelectedItem(); 
		int idCateg = 0;
		
		if (selectCateg != null) {
			idCateg = selectCateg.getId();
		}
		
		//on convertit le tarif qui est en String en int 
		try {
			if (!editTarif.getText().trim().equals(""))
				tarif = Float.parseFloat(editTarif.getText().trim());
		}
		catch (NumberFormatException e) {
			this.lblAffichage.setText("Veuillez rentrer un tarif raisonnable !");
		}
		
		Stage nStage = new Stage();
		try {
			//On charge URL de la PageProduit.fxml
			URL fxmlURL=getClass().getResource("/fxml/page/PageProduit.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			PageProduitController controller = fxmlLoader.getController();
			
			//On creer dans la DAO l'objet Produit
			produitDAO.update(new Produit(selectedItem.getId(), nom, desc, tarif, nom.concat(".png"), idCateg));
			
			//On vide les donnees du tableau et on le reremplit
			controller.clearAll();
			controller.initData();
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnModif.getScene().getWindow();
			stage.close();
			
			URL fxmlURL2=getClass().getResource("/fxml/Main.fxml");
			FXMLLoader fxmlLoader2 = new FXMLLoader(fxmlURL2);
			Node root2 = fxmlLoader2.load();
			//Et on rouvre la fenetre Main.fxml avec les nouvelles donnees
			Scene scene = new Scene((AnchorPane) root2, 700, 440);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.show();
		}
		catch (Exception e) {
			this.lblAffichage.setTextFill(Color.RED);
			this.lblAffichage.setText(e.getMessage());
		}
	}
}
