package application.controller.add;

import java.net.URL;



import java.util.ResourceBundle;

import application.controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Client;

public class AjoutClientController implements Initializable {
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextField editPrenom; 
	@FXML private TextField editIdentifiant;
	@FXML private PasswordField editMdp; 
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
	    	this.editIdentifiant.setText("");
	    	this.editMdp.setText("");
	    	this.editRue.setText("");
	    	this.editNo.setText("");
	    	this.editCodePostal.setText("");
	    	this.editVille.setText("");
	    	this.editPays.setText("");
	    	this.lblAffichage.setText("");
		} catch (Exception e) {
		}
	}
	
	@FXML
	public void creerClient() {
		String nom = editNom.getText().trim();
    	String prenom = editPrenom.getText().trim();
    	String numero = editNo.getText().trim();
    	String identifiant = editIdentifiant.getText().trim();
    	String mdp = editMdp.getText(); 
    	String rue = editRue.getText().trim();
    	String codePostal = editCodePostal.getText().trim();
    	String ville = editVille.getText().trim();
    	String pays = editPays.getText().trim();
		String erreur="";
		int cp = 0;
		int num = 0; 
		
		//on essaye de convertir en entier le num�ro et le code postal
		try {
			cp = Integer.parseInt(codePostal); 
			num = Integer.parseInt(numero); 
		} catch (IllegalArgumentException e) {
			this.lblAffichage.setTextFill(Color.web("#000000"));
			this.lblAffichage.setText(e.getMessage());
		}
		
		//on creer une instance de client
		this.lblAffichage.setTextFill(Color.web("#000000"));
			
			try {
				//si le code postal et le num�ro est sup�rieur � 0 on cr�e le client
				if (cp>0 && num>0) {
				Client client = new Client(1, nom, prenom, identifiant, mdp, numero, rue, codePostal, ville, pays);
				MainController.clientDAO.create(client); 
				this.clientAjout = client; 
							
				//On r�cup�re la scene sur laquelle le btnModif est place et on ferme cette fenetre
				Stage stage = (Stage) btnCreer.getScene().getWindow();
				stage.close();
				}
				else if (cp == 0) this.lblAffichage.setText("Code Postal non valide");
				else this.lblAffichage.setText("Numero non valide");
					
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
