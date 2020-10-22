package application.controller.add;

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
import javafx.stage.Stage;
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
	
	private Client clientAjout; 
	
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
		
		//on creer une instance de produit 
		this.lblAffichage.setTextFill(Color.web("#000000"));
			
			try {
				Client client = new Client(1, nom, prenom, numero, rue, codePostal, ville, pays);
				DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO().create(client); 
				this.clientAjout = client;
				this.lblAffichage.setText(client.toStringUtilisateur()); 
							
				//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
				Stage stage = (Stage) btnCreer.getScene().getWindow();
				stage.close();
					
			} catch (Exception e) {
				erreur = e.getMessage();
				this.lblAffichage.setText(erreur);
				this.lblAffichage.setTextFill(Color.web("#000000"));
			} 
			
	}

	public Client getClientAjout() {
		return clientAjout;
	}
			
}
