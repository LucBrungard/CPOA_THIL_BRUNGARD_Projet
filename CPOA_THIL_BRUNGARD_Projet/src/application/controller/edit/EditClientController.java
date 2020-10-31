package application.controller.edit;

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


public class EditClientController implements Initializable {
	@FXML private Button btnModif;
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

	private Client selectedItem;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void initData(Client client) {
		selectedItem = client;
		
		editNom.setText(client.getNom());
		editPrenom.setText(client.getPrenom());
		editIdentifiant.setText(client.getPrenom());
    	editMdp.setText(client.getMotDePasse());
		editNo.setText(client.getNumero());
		editRue.setText(client.getRue());
		editCodePostal.setText(client.getCodePostal());
		editVille.setText(client.getVille());
		editPays.setText(client.getPays());
		
		lblAffichage.setText("");
	}
	
	@FXML
	public void modifClient() {
		String nom = editNom.getText().trim();
		String prenom = editPrenom.getText().trim();
		String identifiant = editIdentifiant.getText().trim();
    	String mdp = editMdp.getText().trim();
		String numero = editNo.getText().trim();
		String rue = editRue.getText().trim();
		String codePostal = editCodePostal.getText().trim();
		String ville = editVille.getText().trim();
		String pays = editPays.getText().trim();
		
		int cp = 0;
		int num = 0; 
		
		//on essaye de convertir le code postal et le num�ro en entier
		try {
			cp = Integer.parseInt(codePostal); 
			num = Integer.parseInt(numero); 
		} catch (IllegalArgumentException e) {
			this.lblAffichage.setTextFill(Color.web("#000000"));
			this.lblAffichage.setText(e.getMessage());
		}
		
		
		try {
			this.lblAffichage.setTextFill(Color.RED);
			
			if (cp>0 && num>0) {
			//On creer dans la DAO l'objet Client si le cp et le num�ro sont sup�rieurs � 0
			Client client = new Client(selectedItem.getId(), nom, prenom, identifiant, mdp, numero, rue, codePostal, ville, pays);
			MainController.clientDAO.update(client);
			
			this.selectedItem = client;
			
			//On r�cup�re la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnModif.getScene().getWindow();
			stage.close();
			}
			
			else if (cp == 0) this.lblAffichage.setText("Code Postal non valide");
			else this.lblAffichage.setText("Numero non valide");
		}
		catch (Exception e) {
			this.lblAffichage.setText(e.getMessage());
		}
	}
	
	public Client getSelectedItem() {
		return selectedItem;
	}
}
