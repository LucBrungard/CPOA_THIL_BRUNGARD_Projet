package application.controller;

import java.net.URL;

import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import modele.Client;

public class AjoutClientController implements Initializable {
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextField editPrenom; 
	@FXML private TextField editNo;
	@FXML private TextField editRue;
	@FXML private TextField editCodePostal;
	@FXML private TextField editVille;
	@FXML private TextField editPays;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	    try {
	    	this.editNom.setText("");
	    	this.editPrenom.setText("");
	    	this.editRue.setText("");
	    	this.editNo.setText("");
	    	this.editCodePostal.setText("");
	    	this.editVille.setText("");
	    	this.editPays.setText("");
		} catch (Exception e) {
		}
	}
	
	@FXML
	public void creerClient() {
		String nom = editNom.getText().trim();
    	String prenom = editPrenom.getText().trim();
    	String numero = editNo.getText().trim();
    	String rue = editRue.getText().trim();
    	String codePostal = editCodePostal.getText().trim();
    	String ville = editVille.getText().trim();
    	String pays = editPays.getText().trim();
		String erreur="";
		
		
//		//on convertit le tarif qui est en String en int 
//		try {
//			tarif = Float.parseFloat(editTarif.getText().trim());
//		}
//		catch (NumberFormatException e) {
//		}
//		
//		if (nom.isEmpty()) {
//			erreur = erreur + "\nLe nom est vide";
//		}
//		if (desc.isEmpty()) {
//			erreur = erreur + "\nLa description est vide";
//		}
//		if (tarif <= 0) {
//			erreur = erreur + "\nVeuillez saisir un tarif raisonnable";
//		}
//		if (selectItem == null) {
//			erreur = erreur + "\nVeuillez selectionner une categorie";
//		}
//		
//		if (erreur != "") {
//			this.lblAffichage.setTextFill(Color.web("#bb0b0b"));
//			this.lblAffichage.setText(erreur);
//		}
//		else {
		
			//on creer une instance de produit 
		this.lblAffichage.setTextFill(Color.web("#000000"));
			
			try {
				Client c1 = new Client(1, nom, prenom, numero, rue, codePostal, ville, pays);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO().create(c1); 
				this.lblAffichage.setText(c1.toStringUtilisateur()); 
				System.out.println(c1.toString());
					
			} catch (Exception e) {
				erreur = e.getMessage();
				this.lblAffichage.setText(erreur);
				this.lblAffichage.setTextFill(Color.web("#000000"));
			} 
			initialize(null, null);
			this.lblAffichage.setTextFill(Color.web("#000000"));
			
	}
}
