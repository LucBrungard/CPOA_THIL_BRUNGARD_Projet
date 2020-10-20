package application.controller.edit;

import java.net.URL;
import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
import dao.modele.ProduitDAO;
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
			tarif = Float.parseFloat(editTarif.getText().trim());
		}
		catch (NumberFormatException e) {
			this.lblAffichage.setText(e.getMessage());
		}
		
		try {
			//On creer dans la DAO l'objet Produit
			Produit produit = new Produit(selectedItem.getId(), nom, desc, tarif, nom.concat(".png"), idCateg);
			produitDAO.update(produit);
			
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
