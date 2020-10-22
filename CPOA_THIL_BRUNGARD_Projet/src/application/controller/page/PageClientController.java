package application.controller.page;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.controller.MainController;
import application.controller.add.AjoutClientController;
import application.controller.detail.DetailClientController;
import application.controller.edit.EditClientController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ClientDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Client;


public class PageClientController implements Initializable {

	@FXML private TableView<Client> tabClient;
	@FXML private TableColumn<Client, String> nomClient = new TableColumn<Client, String>("Nom");
	@FXML private TableColumn<Client, String> prenomClient = new TableColumn<Client, String>("Prenom");
	@FXML private TableColumn<Client, String> cpClient = new TableColumn<Client, String>("Code Postal");
	@FXML private TableColumn<Client, String> villeClient = new TableColumn<Client, String>("Ville");
	@FXML private TableColumn<Client, String> paysClient = new TableColumn<Client, String>("Pays");
	
	@FXML private Button addClient;
	@FXML private Button deleteClient;
	@FXML private Button editClient;
	@FXML private Button detailClient;
	
	@FXML private TextField searchNom;
	@FXML private TextField searchPrenom;
	
	@SuppressWarnings("unused")
	private MainController main;
	private Client client; 
	
	ClientDAO clientDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO();
	
	
	//Initialisation des donnees + ajout des listeners
	public void initData() {
		this.nomClient.setCellValueFactory(new PropertyValueFactory<>("nom"));
		this.prenomClient.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		this.cpClient.setCellValueFactory(new PropertyValueFactory<>("codePostal"));
		this.villeClient.setCellValueFactory(new PropertyValueFactory<>("ville"));
		this.paysClient.setCellValueFactory(new PropertyValueFactory<>("pays"));
		
		try {
			this.tabClient.getItems().addAll(clientDAO.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Definit si les boutons sont desactives ou non
		this.tabClient.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.addClient.setDisable(newValue != null);
					this.deleteClient.setDisable(newValue == null);
					this.editClient.setDisable(newValue == null);
					this.detailClient.setDisable(newValue == null);
					this.client = tabClient.getSelectionModel().getSelectedItem();
				});
		
		//event qui annule la selection actuelle de la tableView si on selectionne une ligne vide
		this.tabClient.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
		    Node source = evt.getPickResult().getIntersectedNode();
		   
		    // move up through the node hierarchy until a TableRow or scene root is found 
		    while (source != null && !(source instanceof TableRow)) {
		        source = source.getParent();
		    }

		    // clear selection on click anywhere but on a filled row
		    if (source == null || (source instanceof TableRow && ((TableRow<?>) source).isEmpty())) {
		    	tabClient.getSelectionModel().clearSelection();
		    }
		});
		
		this.deleteClient.setDisable(true);
		this.editClient.setDisable(true);
		this.detailClient.setDisable(true);
		
	}
	
	public void init(MainController mainController) {
		main = mainController;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initData(); 
	}
	
	//Charge la page AJoutProduit et recupere les donnees pour les ajouter dans le tableau
	public void ajoutClient() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page AjoutProduit.fxml
			URL fxmlURL=getClass().getResource("/fxml/add/AjoutClient.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			AjoutClientController controller = fxmlLoader.getController();
			
			//On affiche la fenetre AjoutProduit
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Creer un client");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
			if (controller.getClientAjout() != null)
				tabClient.getItems().add(controller.getClientAjout());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//Charge la page ModifProduit et recupere les donnees pour les modifier dans le tableau
	public void modifClient() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/edit/ModifClient.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			EditClientController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabClient.getSelectionModel().getSelectedItem());
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Modifier un client");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
			//On modifie l'objet dans le tableau
			tabClient.getItems().set(
					tabClient.getItems().indexOf(controller.getSelectedItem()), 
					controller.getSelectedItem());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Charge la page ModifProduit et recupere les donnees pour les modifier dans le tableau
	public void detailClient() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/detail/DetailClient.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			DetailClientController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabClient.getSelectionModel().getSelectedItem());
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Détail d'un client");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	//Supprime la valeur dans le tableau et dans la dao
	public void supprClient() {
		//Ouvre une fenetre d'alerte pour confirer la suppresion
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerte suppression");
		alert.setContentText("Etes vous certain de vouloir supprimer ce client ?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				clientDAO.delete(client); 
				tabClient.getItems().remove(client);
				tabClient.getSelectionModel().clearSelection();
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
		else {
			tabClient.getSelectionModel().clearSelection();
		}
	}
	
	
	//Renvoie une liste des produits qui possedent un nom correspondant a la demande
	public ArrayList<Client> filtrerNom() {
		String nom = searchNom.getText().trim().toLowerCase();
		ArrayList<Client> listeClient = new ArrayList<Client>();
		try {
			if (nom.equals("")) {
				listeClient.addAll(clientDAO.findAll());
			}
			else {
				for (Client client : clientDAO.findAll()) {
					if (client.getNom().toLowerCase().contains(nom)) {
						listeClient.add(client);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeClient;
	}
	
	
	//Renvoie une liste des produits qui possedent un titre de categorie correspondant a la demande
	public ArrayList<Client> filtrerPrenom() {
		String prenom = searchPrenom.getText().trim().toLowerCase();
		ArrayList<Client> listeClient = new ArrayList<Client>();
		try {
			if (prenom.equals("")) {
				listeClient.addAll(filtrerNom());
			}
			else {
				for (Client client : filtrerNom()) {
					if (client.getPrenom().toLowerCase().contains(prenom)) {
						listeClient.add(client);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listeClient;
	}
		
	//renvoie la liste la plus longue
	private ArrayList<Client> max(ArrayList<Client> l1, ArrayList<Client> l2) {
		if (l1.size() >= l2.size())
			return l1;
		if (l2.size() >= l1.size())
			return l2;
		return l2;
	}
	
	
	//Rassemble toute les listes des donnees filtrees et fait un ET exclusif des donnees
	public void filtrer() {
		ArrayList<Client> prodNom = filtrerNom();
		ArrayList<Client> prodPrenom = filtrerPrenom();
		ObservableList<Client> listeClientSelect = FXCollections.observableArrayList();
		
		ObservableList<Client> listeClientSurplus = FXCollections.observableArrayList();
		ObservableList<Client> listeClientMino = FXCollections.observableArrayList();
		
		for (Client client : max(prodNom, prodPrenom)) {
			if (prodNom.contains(client) 
					&& prodPrenom.contains(client))
				listeClientSelect.add(client);
		}
		//On enleve de la tableView tout produit non present dans listeProdSelect mais present dans la tableView
		ObservableList<Client> trans1 = tabClient.getItems();
		
		for (Client client : trans1) {
			if (!listeClientSelect.contains(client))
				listeClientSurplus.add(client);
		}
		
		//On rajoute dans la tableView tout produit present dans listeProdSelect mais non present dans la tableView
		ObservableList<Client> trans2 = tabClient.getItems();
		for (Client produit : listeClientSelect ) {
			if (!trans2.contains(produit))
				listeClientMino.add(produit);
		}
		
		tabClient.getItems().removeAll(listeClientSurplus);
		tabClient.getItems().addAll(listeClientMino);
	}
	
}
