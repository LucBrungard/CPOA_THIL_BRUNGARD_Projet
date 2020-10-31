package application.controller.edit;

import java.net.URL;


import java.util.ResourceBundle;

import application.controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Client;
import modele.Commande;

public class EditCommandeController implements Initializable{
	
	@FXML private Button btnModif;
	@FXML private Label lblAffichage;
	@FXML private DatePicker editDate;
	@FXML private ChoiceBox<Client> cbxClient;
	ObservableList<Client> listeClient = FXCollections.observableArrayList();
	
	private Commande selectedItem;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
	   
	public void initData(Commande commande) {
		
		selectedItem = commande; 
		
		editDate.setValue(commande.getDate());
		editDate.setEditable(false);
 
    	try {
			for (Client client : MainController.clientDAO.findAll()) {
				listeClient.add(client);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.cbxClient.setItems(listeClient); 
		
		
		try {
			cbxClient.getItems().setAll(MainController.clientDAO.findAll());
			cbxClient.setValue(MainController.clientDAO.getById(selectedItem.getIdClient()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lblAffichage.setText("");
	}

	@FXML
	public void modifCommande() {
		
		//Objet de type Client qui correspond a l'objet selectionne dans le choice box
		Client selectClient = cbxClient.getSelectionModel().getSelectedItem(); 
		int idClient = 0;
		
		if (selectClient != null) {
			idClient = selectClient.getId();
		}
		
		
		try {
			//On creer dans la DAO l'objet Commande
			Commande commande = new Commande(selectedItem.getId(), editDate.getValue(), idClient, selectedItem.getLigneCommande()); 
			MainController.commandeDAO.update(commande); 
			
			this.selectedItem = commande;
			
			//On recupere la scene sur laquelle le btnModif est place et on ferme cette fenetre
			Stage stage = (Stage) btnModif.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			this.lblAffichage.setTextFill(Color.RED);
			this.lblAffichage.setText(e.getMessage());
		}
	}

	public Commande getSelectedItem() {
		return selectedItem;
	}


}
