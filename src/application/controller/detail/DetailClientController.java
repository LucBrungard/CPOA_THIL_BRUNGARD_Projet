package application.controller.detail;

import java.net.URL

;


import java.util.ResourceBundle;
import application.controller.page.PageCommandeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Client;

public class DetailClientController implements Initializable {
	
	@FXML private Label lblNom; 
	@FXML private Label lblPrenom; 
	@FXML private Label lblIdentifiant; 
	@FXML private Label lblMdp; 
	@FXML private Label lblNo; 
	@FXML private Label lblRue; 
	@FXML private Label lblCodePostal; 
	@FXML private Label lblVille;
	@FXML private Label lblPays;
	@FXML private Button voirCommande; 
	
	private Client selectedItem;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void initData(Client client) {
		selectedItem = client;
		
		lblNom.setText(client.getNom());
		lblPrenom.setText(client.getPrenom());
		lblIdentifiant.setText(client.getIdentifiant()); 
		lblMdp.setText(client.getMotDePasse()); 
		lblNo.setText(client.getNumero());
		lblRue.setText(client.getRue());
		lblCodePostal.setText(client.getCodePostal());
		lblVille.setText(client.getVille());
		lblPays.setText(client.getPays());
		
	}

	// on ouvre une nouvelle fenetre contenant les infos du client
	public void detailClient() {
	}
	
	//on affiche les commandes du client sélectionné
	public void voirCommande() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page PageCommande.fxml
			URL fxmlURL=getClass().getResource("/fxml/page/PageCommande.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page PageComma,de.fxml
			PageCommandeController controller = fxmlLoader.getController();
			controller.getActualiser().setVisible(false);
			controller.getRechercheCl().setVisible(false);
			controller.getRechercheProd().setVisible(false);
			controller.getAddCommande().setVisible(false);
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur PageCommandeController
			controller.initData(selectedItem); 
			
			//On affiche la fenetre contenant les commandes
			Scene scene = new Scene((AnchorPane) root, 900, 440);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Commande");
			nStage.initModality(Modality.APPLICATION_MODAL);
			
			//Et on affiche la page et on attend qu'elle soit fermee
			nStage.showAndWait();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Client getSelectedItem() {
		return selectedItem;
	}

}
