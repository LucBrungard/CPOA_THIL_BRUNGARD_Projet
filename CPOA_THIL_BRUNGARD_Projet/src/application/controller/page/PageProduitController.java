package application.controller.page;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.controller.MainController;
import application.controller.edit.EditProduitController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.ProduitDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import modele.Categorie;
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
	@FXML private TextField searchNom;
	@FXML private TextField searchTarif;
	@FXML private TextField searchCateg;
	
	private MainController main;
	private Produit produit;
	
	ProduitDAO produitDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO();
	
	public void initData() {
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
					this.addProduit.setDisable(newValue != null);
					this.deleteProduit.setDisable(newValue == null);
					this.editProduit.setDisable(newValue == null);
					this.produit = tabProduit.getSelectionModel().getSelectedItem();
				});
		
		this.tabProduit.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
		    Node source = evt.getPickResult().getIntersectedNode();
		   
		    // move up through the node hierarchy until a TableRow or scene root is found 
		    while (source != null && !(source instanceof TableRow)) {
		        source = source.getParent();
		    }

		    // clear selection on click anywhere but on a filled row
		    if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
		    	tabProduit.getSelectionModel().clearSelection();
		    }
		});
		
		this.deleteProduit.setDisable(true);
		this.editProduit.setDisable(true);
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initData();
	}
	

	public void init(MainController mainController) {
		main = mainController;
		
	}
	
	public ArrayList<Produit> refreshNom() {
		String nom = searchNom.getText().trim().toLowerCase();
		ArrayList<Produit> listeProd = new ArrayList<Produit>();
		try {
			if (nom.equals("")) {
				listeProd.addAll(produitDAO.findAll());
			}
			else {
				for (Produit produit : produitDAO.findAll()) {
					if (produit.getNom().toLowerCase().contains(nom)) {
						listeProd.add(produit);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeProd;
	}
	
	public ArrayList<Produit> refreshTarif() {
		ArrayList<Produit> listeProd = new ArrayList<Produit>();
		float tarif = 0;
		
		//Si le tarif rentre est un caractere ou une chaine vide on va dans le catch
		//Sinon si le tarif est superieur a 0 on recupere les produit dont tarif < valeur rentree
		try {
			tarif = Float.parseFloat(searchTarif.getText().trim());
			if (tarif > 0) {
				for (Produit produit : produitDAO.findAll()) {
					if (produit.getTarif() <= tarif) {
						listeProd.add(produit);
					}
				}
			}
			
		} catch (Exception e) {
			if (searchTarif.getText().trim().isEmpty()) {
				try {
					listeProd.addAll(produitDAO.findAll());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		return listeProd;
	}
	
	public ArrayList<Produit> refreshCateg() {
		String categ = searchCateg.getText().trim().toLowerCase();
		ArrayList<Produit> listeProd = new ArrayList<Produit>();
		try {
			if (categ.equals("")) {
				listeProd.addAll(produitDAO.findAll());
			}
			else {
				for (Produit produit : produitDAO.findAll()) {
					if (produit.getTitreCateg().toLowerCase().contains(categ)) {
						listeProd.add(produit);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listeProd;
	}
	
	public void refresh() {
		ArrayList<Produit> prodNom = refreshNom();
		ArrayList<Produit> prodTarif = refreshTarif();
		ArrayList<Produit> prodCateg = refreshCateg();
		ObservableList<Produit> trans = FXCollections.observableArrayList();
		
		int t1 = prodNom.size();
		int t2 = prodTarif.size();
		int t3 = prodCateg.size();
		
		int max = Math.max(t1, t2);
		max = Math.max(max, t3);
		
		if (t1 == max) {
			for (Produit produit : prodNom) {
				if (prodTarif.contains(produit) && prodCateg.contains(produit)) {
					trans.add(produit);
				}
			}
		}
		else if (t2 == max) {
			for (Produit produit : prodTarif) {
				if (prodNom.contains(produit) && prodCateg.contains(produit)) {
					trans.add(produit);
				}
			}
		}
		else if (t3 == max) {
			for (Produit produit : prodCateg) {
				if (prodTarif.contains(produit) && prodNom.contains(produit)) {
					trans.add(produit);
				}
			}
		}
		
		
		clearAll();
		tabProduit.getItems().addAll(trans);
	}

	public void ajoutProd() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/add/AjoutProduit.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Creer un produit");
			nStage.show();
			
			//On ferme la fenetre PageCategorie.fxml
			Stage stage = (Stage) this.tabProduit.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modifProd() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/edit/ModifProduit.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Modififer un produit");
			nStage.show();
			
			//On recupere le controleur de la page ModifCateg.fxml
			EditProduitController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabProduit.getSelectionModel().getSelectedItem());
			
			//On ferme la fenetre PageCategorie.fxml
			Stage stage = (Stage) this.tabProduit.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void supprProd() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerte suppression");
		alert.setContentText("Etes vous certain de supprimer ce produit ?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				produitDAO.delete(produit);
				clearAll();
				initData();
			} 
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}	
		else {
			tabProduit.getSelectionModel().clearSelection();
		}
	}


	public void clearAll() {
		this.tabProduit.getItems().clear();
	}
}
