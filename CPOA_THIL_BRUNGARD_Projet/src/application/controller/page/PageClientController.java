package application.controller.page;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.MainController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ClientDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	@FXML private Button searchClient;
	@FXML private Button detailClient;
	
	private MainController main;
	
	ClientDAO clientDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO();
	
	public void init(MainController mainController) {
		main = mainController;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("ok");
		
		nomClient.setCellValueFactory(new PropertyValueFactory<>("nom"));
		prenomClient.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		cpClient.setCellValueFactory(new PropertyValueFactory<>("codePostal"));
		villeClient.setCellValueFactory(new PropertyValueFactory<>("ville"));
		paysClient.setCellValueFactory(new PropertyValueFactory<>("pays"));
		
		try {
			tabClient.getItems().addAll(clientDAO.findAll());
		} catch (Exception e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		
		this.tabClient.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.deleteClient.setDisable(newValue == null);
					this.editClient.setDisable(newValue == null);
					this.detailClient.setDisable(newValue == null);
				});
		
		this.deleteClient.setDisable(true);
		this.editClient.setDisable(true);
		this.detailClient.setDisable(true);
	}

	
}
