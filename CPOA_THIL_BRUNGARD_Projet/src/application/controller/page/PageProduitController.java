package application.controller.page;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.MainController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ProduitDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import modele.Produit;

public class PageProduitController implements Initializable {
	
	@FXML private TableView<Produit> tabProduit;
	@FXML private TableColumn<Produit, String> nomProduit = new TableColumn<Produit, String>("Nom");
	@FXML private TableColumn<Produit, Float> tarifProduit = new TableColumn<Produit, Float>("Tarif");
	@FXML private TableColumn<Produit, String> visuelProduit = new TableColumn<Produit, String>("Visuel");
	@FXML private TableColumn<Produit, String> categProduit = new TableColumn<Produit, String>("Categorie");
	
	@FXML private Button addProduit;
	@FXML private Button deleteProduit;
	@FXML private Button editProduit;
	@FXML private Button searchProduit;
	
	private MainController main;
	
	ProduitDAO produitDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.nomProduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
		this.tarifProduit.setCellValueFactory(new PropertyValueFactory<>("tarif"));
		this.visuelProduit.setCellValueFactory(new PropertyValueFactory<>("visuel"));
		//Comme titreCateg n'est pas attribut de Produit, on créer une méthode getTitreCateg et on dit a la colonne d'utiliser cette methode
		this.categProduit.setCellValueFactory(new Callback<CellDataFeatures<Produit, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Produit, String> p) {
				return new ReadOnlyStringWrapper(p.getValue().getTitreCateg());
			}
		});
		
		try {
			this.tabProduit.getItems().addAll(produitDAO.findAll());
		} catch (Exception e) {
			// TODO Bloc catch gÃ©nÃ©rÃ© automatiquement
			e.printStackTrace();
		}
		
		this.tabProduit.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.deleteProduit.setDisable(newValue == null);
					this.editProduit.setDisable(newValue == null);
				});
		
		this.deleteProduit.setDisable(true);
		this.editProduit.setDisable(true);
		
	}
	

	public void init(MainController mainController) {
		main = mainController;
		
	}
}
