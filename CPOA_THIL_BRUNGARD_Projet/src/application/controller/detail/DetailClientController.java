package application.controller.detail;

import java.net.URL;

import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ClientDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modele.Client;

public class DetailClientController implements Initializable {
	
	@FXML private Label lblNom; 
	@FXML private Label lblPrenom; 
	@FXML private Label lblNo; 
	@FXML private Label lblRue; 
	@FXML private Label lblCodePostal; 
	@FXML private Label lblVille;
	@FXML private Label lblPays;
	
	ClientDAO clientDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO();
	private Client selectedItem;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void initData(Client client) {
		selectedItem = client;
		
		lblNom.setText(client.getNom());
		lblPrenom.setText(client.getPrenom());
		lblNo.setText(client.getNumero());
		lblRue.setText(client.getRue());
		lblCodePostal.setText(client.getCodePostal());
		lblVille.setText(client.getVille());
		lblPays.setText(client.getPays());
		
	}

	public void detailClient() {
	}
	
	public Client getSelectedItem() {
		return selectedItem;
	}

}
