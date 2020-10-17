package application.controller.add;

import java.net.URL;
import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import modele.Categorie;
import modele.Produit;

public class AjoutProduitController implements Initializable{
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextArea editDesc; 
	@FXML private TextField editTarif;
	@FXML private ChoiceBox<Categorie> cbxCategorie;
	
	DAOFactory dao = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE);
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	    try {
	    	this.lblAffichage.setText("");
	    	this.editNom.setText("");
	    	this.editDesc.setText("");
	    	this.editTarif.setText("");
	    	//this.cbxCategorie.setSelectionModel(null);
			this.cbxCategorie.setItems(FXCollections.observableArrayList(dao.getCategorieDAO().findAll()));
			
		} catch (Exception e) {
		}
	}
	   
	@FXML
	public void creerProduit() {
		String nom = editNom.getText().trim();
		String desc = editDesc.getText().trim(); 
		float tarif = 0;
		String erreur="";
		
		//Objet de type Categorie qui correspond a l'objet selectionne dans le choice box
		Categorie selectItem = cbxCategorie.getSelectionModel().getSelectedItem(); 
		
		//on convertit le tarif qui est en String en int 
		try {
			tarif = Float.parseFloat(editTarif.getText().trim());
		}
		catch (NumberFormatException e) {
		}
		
		if (nom.isEmpty()) {
			erreur = erreur + "\nLe nom est vide";
		}
		if (desc.isEmpty()) {
			erreur = erreur + "\nLa description est vide";
		}
		if (tarif <= 0) {
			erreur = erreur + "\nVeuillez saisir un tarif raisonnable";
		}
		if (selectItem == null) {
			erreur = erreur + "\nVeuillez selectionner une categorie";
		}
		
		if (erreur != "") {
			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
			this.lblAffichage.setText(erreur);
		}
		else {
			//on creer une instance de produit 
			Produit p1 = new Produit(1, nom, desc, tarif, nom.concat(".png"), selectItem.getId());
			
			try {
				DAOFactory.getDAOFactory(Persistance.MYSQL).getProduitDAO().create(p1);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO().create(p1); 
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			initialize(null, null);
			this.lblAffichage.setTextFill(Color.web("#000000"));
			if(tarif > 1) 
				this.lblAffichage.setText(p1.toStringUtilisateur() + "s");
			else 
				this.lblAffichage.setText(p1.toStringUtilisateur());
		}
	}
}
