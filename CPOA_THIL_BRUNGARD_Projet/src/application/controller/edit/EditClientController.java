package application.controller.edit;

import java.net.URL;


import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ClientDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Client;


public class EditClientController implements Initializable {
	@FXML private Button btnModif;
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextField editPrenom; 
	@FXML private TextField editNo;
	@FXML private TextField editRue;
	@FXML private TextField editCodePostal;
	@FXML private TextField editVille;
	@FXML private TextField editPays;
	
	ClientDAO clientDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO();
	private Client selectedItem;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void initData(Client client) {
		selectedItem = client;
		
		editNom.setText(client.getNom());
		editPrenom.setText(client.getPrenom());
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
		String numero = editNo.getText().trim();
		String rue = editRue.getText().trim();
		String codePostal = editCodePostal.getText().trim();
		String ville = editVille.getText().trim();
		String pays = editPays.getText().trim();
		
		try {
			//On creer dans la DAO l'objet Produit
			Client client = new Client(selectedItem.getId(), nom, prenom, numero, rue, codePostal, ville, pays);
			clientDAO.update(client);
			
			this.selectedItem = client;
			
			//On récupère la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnModif.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			this.lblAffichage.setTextFill(Color.RED);
			this.lblAffichage.setText(e.getMessage());
		}
	}
	
	public Client getSelectedItem() {
		return selectedItem;
	}
}
