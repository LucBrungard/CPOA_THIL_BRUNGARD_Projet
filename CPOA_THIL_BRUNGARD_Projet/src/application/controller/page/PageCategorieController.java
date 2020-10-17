package application.controller.page;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.MainController;
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
	@FXML private TableView<Categorie> tabCateg;
	@FXML private TableColumn<Categorie, String> titreCateg = new TableColumn<Categorie, String>("Titre");
	@FXML private TableColumn<Categorie, String> visuelCateg = new TableColumn<Categorie, String>("Visuel");
	
	@FXML private Button addCateg;
	@FXML private Button deleteCateg;
	@FXML private Button editCateg;
	
	private MainController main;
	
	CategorieDAO categDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	
	
	
	public void init(MainController mainController) {
		main = mainController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.titreCateg.setCellValueFactory(new PropertyValueFactory<Categorie, String>("titre"));
		this.visuelCateg.setCellValueFactory(new PropertyValueFactory<Categorie, String>("visuel"));
		
		try {
			this.tabCateg.getItems().addAll(categDAO.findAll());
		} catch (Exception e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		
		this.tabCateg.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.deleteCateg.setDisable(newValue == null);
					this.editCateg.setDisable(newValue == null);
				});
		
		this.deleteCateg.setDisable(true);
		this.editCateg.setDisable(true);
		
	}
	
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
	
	public void modifCateg() {
		
		
		Stage nStage = new Stage();
		try {
			URL fxmlURL=getClass().getResource("/fxml/ModifCateg.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			Scene scene = new Scene((AnchorPane) root, 700, 440);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Modifier un client");
			nStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void chercherCateg() {
		System.out.println("chercher");
	}
}
