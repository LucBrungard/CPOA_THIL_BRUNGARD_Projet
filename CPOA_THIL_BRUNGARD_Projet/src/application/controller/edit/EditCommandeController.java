package application.controller.edit;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ClientDAO;
import dao.modele.CommandeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modele.Client;
import modele.Commande;

public class EditCommandeController implements Initializable{
	
	@FXML private Button btnModif;
	@FXML private Label lblAffichage;
	@FXML private TextField editDate;
	@FXML private ChoiceBox<Client> cbxClient;
	ObservableList<Client> listeClient = FXCollections.observableArrayList();
	
	CommandeDAO commandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
	ClientDAO clientDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO();
	DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private Commande selectedItem;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
	   
	public void initData(Commande commande) {
		
		selectedItem = commande; 
		
		editDate.setText(commande.getDate().format(formatage));
 
    	try {
			for (Client client : clientDAO.findAll()) {
				listeClient.add(client);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		this.cbxClient.setItems(listeClient); 
		
		
		try {
			cbxClient.getItems().setAll(clientDAO.findAll());
			cbxClient.setValue(clientDAO.getById(selectedItem.getIdClient()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lblAffichage.setText("");
	}

	@FXML
	public void modifCommande() {
		String date = editDate.getText().trim();
		
		//Objet de type Categorie qui correspond a l'objet selectionne dans le choice box
		Client selectClient = cbxClient.getSelectionModel().getSelectedItem(); 
		int idClient = 0;
		
		if (selectClient != null) {
			idClient = selectClient.getId();
		}
		
		
		try {
			//On creer dans la DAO l'objet Produit
			Commande commande = new Commande(selectedItem.getId(), LocalDate.parse(date, formatage), idClient, selectedItem.getLigneCommande()); 
			commandeDAO.update(commande); 
			
			this.selectedItem = commande;
			
			//On r�cup�re la scene sur laquelle le btnModif est place et on ferme cette fenetre
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
