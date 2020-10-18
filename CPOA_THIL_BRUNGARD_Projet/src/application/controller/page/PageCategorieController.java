package application.controller.page;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.MainController;
import application.controller.edit.EditCategorieController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modele.Categorie;

public class PageCategorieController implements Initializable {
	@FXML EditCategorieController editCategorie;
	private Categorie categorie;
	
	@FXML private TableView<Categorie> tabCateg;
	@FXML private TableColumn<Categorie, String> titreCateg = new TableColumn<Categorie, String>("Titre");
	@FXML private TableColumn<Categorie, String> visuelCateg = new TableColumn<Categorie, String>("Visuel");
	
	@FXML private Button addCateg;
	@FXML private Button deleteCateg;
	@FXML private Button editCateg;
	
	private MainController main;
	
	CategorieDAO categDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	
	
	//Instancie la classe MainController
	public void init(MainController mainController) {
		main = mainController;
	}
	
	//Initialisation des donnees (tableau + boutons) + rajout des listeners sur les tableaux
	public void initData() {
		this.titreCateg.setCellValueFactory(new PropertyValueFactory<Categorie, String>("titre"));
		this.visuelCateg.setCellValueFactory(new PropertyValueFactory<Categorie, String>("visuel"));
		
		this.tabCateg.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.deleteCateg.setDisable(newValue == null);
					this.editCateg.setDisable(newValue == null);
				});
		
		//On ajoute un listener pour renvoyer l'item selectionne dans une variable private
		this.tabCateg.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	this.categorie = this.tabCateg.getSelectionModel().getSelectedItem();
		    }
		});
		
		this.deleteCateg.setDisable(true);
		this.editCateg.setDisable(true);
		
		try {
			this.tabCateg.getItems().addAll(categDAO.findAll());
		} catch (Exception e) {
			// TODO Bloc catch genere automatiquement
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initData();
	}
	
	//On vide les donnees du tableau
	public void clearAll() {
		this.tabCateg.getItems().clear();
	}
	
	//Fonction appelee lors du clic sur le bouton Ajout
	public void ajoutCateg() {
		Stage nStage = new Stage();
		try {
			URL fxmlURL=getClass().getResource("/fxml/AjoutClient.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			Scene scene = new Scene((AnchorPane) root, 700, 440);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Creer un client");
			nStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void supprCateg() {
		System.out.println("supprimer");
	}
	
	
	//Fonction appelee lors du clic sur le bouton Modifier
	public void modifCateg() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/edit/ModifCateg.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			EditCategorieController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabCateg.getSelectionModel().getSelectedItem());
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 350, 200);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Modifier un client");
			nStage.show();
			
			//On ferme la fenetre PageCategorie.fxml
			Stage stage = (Stage) this.tabCateg.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void chercherCateg() {
		System.out.println("chercher");
	}
}
