package application.controller.page;

import java.net.URL;

import java.time.LocalDate;
import java.util.ResourceBundle;

import application.controller.MainController;
import application.controller.add.AjoutCommandeController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CommandeDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modele.Commande;

public class PageCommandeController implements Initializable {
	
	@FXML private TableView<Commande> tabCommande;
	@FXML private TableColumn<Commande, Integer> idCommande = new TableColumn<Commande, Integer>("Identifiant");
	@FXML private TableColumn<Commande, LocalDate> dateCommande = new TableColumn<Commande, LocalDate>("Date Commande");
	@FXML private TableColumn<Commande, String> clientCommande = new TableColumn<Commande, String>("Client Concerne");
	
	@FXML private Button addCommande;
	@FXML private Button deleteCommande;
	@FXML private Button editCommande;
	@FXML private Button searchCommande;
	@FXML private Button detailCommande;
	
	@SuppressWarnings("unused")
	private MainController main;
	
	CommandeDAO commandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.idCommande.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.dateCommande.setCellValueFactory(new PropertyValueFactory<>("date"));
		this.clientCommande.setCellValueFactory(new Callback<CellDataFeatures<Commande, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Commande, String> p) {
				return new ReadOnlyStringWrapper(p.getValue().getNomClient());
			}
		});
		
		try {
			this.tabCommande.getItems().addAll(commandeDAO.findAll());
		} catch (Exception e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		
		this.tabCommande.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.deleteCommande.setDisable(newValue == null);
					this.editCommande.setDisable(newValue == null);
					this.detailCommande.setDisable(newValue == null);
				});
		
		this.deleteCommande.setDisable(true);
		this.editCommande.setDisable(true);
		this.detailCommande.setDisable(true);
	}
	
	public void init(MainController mainController) {
		main = mainController;
		
	}
	
	//Charge la page AJoutProduit et recupere les donnees pour les ajouter dans le tableau
	public void ajoutCommande() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page AjoutProduit.fxml
			URL fxmlURL=getClass().getResource("/fxml/add/AjoutCommande.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			AjoutCommandeController controller = fxmlLoader.getController();
			
			//On affiche la fenetre AjoutProduit
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Creer une commande");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
			if (controller.getCommandeAjout() != null)
				tabCommande.getItems().add(controller.getCommandeAjout());
			System.out.println(controller.getCommandeAjout());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
}
