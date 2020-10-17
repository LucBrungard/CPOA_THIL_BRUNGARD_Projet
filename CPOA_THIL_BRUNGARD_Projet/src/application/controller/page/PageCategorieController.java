package application.controller.page;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.MainController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
}
