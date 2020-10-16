package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Categorie;

public class MainController implements Initializable{
	@FXML private TableView<Categorie> tabCateg;
	@FXML private TableColumn<Categorie, String> titreCateg = new TableColumn<Categorie, String>("Titre");
	@FXML private TableColumn<Categorie, String> visuelCateg = new TableColumn<Categorie, String>("Visuel");
	
	CategorieDAO daoLM = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	
	

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		titreCateg.setCellValueFactory(new PropertyValueFactory<Categorie, String>("titre"));
		visuelCateg.setCellValueFactory(new PropertyValueFactory<Categorie, String>("visuel"));
		
		this.tabCateg.getColumns().setAll(titreCateg, visuelCateg);
		
		try {
			this.tabCateg.getItems().addAll(daoLM.findAll());
		} catch (Exception e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
	}

}
