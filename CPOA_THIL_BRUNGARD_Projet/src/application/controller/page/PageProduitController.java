package application.controller.page;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import application.controller.MainController;
import application.controller.add.AjoutProduitController;
import application.controller.edit.EditProduitController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modele.LigneCommande;
import modele.Produit;

public class PageProduitController implements Initializable {
	
	@FXML private TableView<Produit> tabProduit;
	@FXML private TableColumn<Produit, String> nomProduit = new TableColumn<Produit, String>("Nom");
	@FXML private TableColumn<Produit, Float> tarifProduit = new TableColumn<Produit, Float>("Tarif");
	@FXML private TableColumn<Produit, String> visuelProduit = new TableColumn<Produit, String>("Visuel");
	@FXML private TableColumn<Produit, String> categProduit = new TableColumn<Produit, String>("Categorie");
	@FXML private TableColumn<Produit, Integer> qteCommandee = new TableColumn<Produit, Integer>("Quantite commandee");
	
	@FXML private Button addProduit;
	@FXML private Button deleteProduit;
	@FXML private Button editProduit;
	@FXML private Button actualiser;
	@FXML private TextField searchNom;
	@FXML private TextField searchTarif;
	@FXML private TextField searchCateg;
	
	private Produit produit;
	
	public void actualiser() {
		try {
			this.tabProduit.getItems().setAll(MainController.produitDAO.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Initialisation des donnees + ajout des listeners
	public void initData() {
		
		//Definit si les boutons sont desactives ou non
		this.tabProduit.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.addProduit.setDisable(newValue != null);
					this.deleteProduit.setDisable(newValue == null);
					this.editProduit.setDisable(newValue == null);
					this.produit = tabProduit.getSelectionModel().getSelectedItem();
				});
		
		//event qui annule la selection actuelle de la tableView si on selectionne une ligne vide
		this.tabProduit.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
		    Node source = evt.getPickResult().getIntersectedNode();
		   
		    // move up through the node hierarchy until a TableRow or scene root is found 
		    while (source != null && !(source instanceof TableRow)) {
		        source = source.getParent();
		    }

		    // clear selection on click anywhere but on a filled row
		    if (source == null || (source instanceof TableRow && ((TableRow<?>) source).isEmpty())) {
		    	tabProduit.getSelectionModel().clearSelection();
		    }
		});
		
		this.deleteProduit.setDisable(true);
		this.editProduit.setDisable(true);
		
		nomProduit.setCellValueFactory(new PropertyValueFactory<>("nom"));
		tarifProduit.setCellValueFactory(new PropertyValueFactory<>("tarif"));
		visuelProduit.setCellValueFactory(new PropertyValueFactory<>("visuel"));
		categProduit.setCellValueFactory(new Callback<CellDataFeatures<Produit, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Produit, String> p) {
				try {
					return new ReadOnlyStringWrapper( MainController.categorieDAO.getById(p.getValue().getIdCateg()).getTitre() );
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		qteCommandee.setCellValueFactory(new Callback<CellDataFeatures<Produit, Integer>, ObservableValue<Integer>>() {
			public ObservableValue<Integer> call(CellDataFeatures<Produit, Integer> p) {
				int quantite = 0;
				int idProduit = p.getValue().getId();
				try {
					for (LigneCommande lc : MainController.ligneCommandeDAO.findAll()) {
						if (lc.getIdProduit() == idProduit) 
							quantite += lc.getQuantite();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new SimpleObjectProperty<Integer>(quantite);
			}
		});
		
		try {
			this.tabProduit.getItems().setAll(MainController.produitDAO.findAll());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initData();
	}
	
	//Renvoie une liste des produits qui possedent un nom correspondant a la demande
	public ArrayList<Produit> filtrerNom() {
		String nom = searchNom.getText().trim().toLowerCase();
		ArrayList<Produit> listeProd = new ArrayList<Produit>();
		try {
			if (nom.equals("")) {
				listeProd.addAll(MainController.produitDAO.findAll());
			}
			else {
				for (Produit produit : MainController.produitDAO.findAll()) {
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
	
	//Renvoie une liste des produits qui possedent un tarif correspondant a la demande
	public ArrayList<Produit> filtrerTarif() {
		ArrayList<Produit> listeProd = new ArrayList<Produit>();
		float tarif = 0;
		
		//Si le tarif rentre est un caractere ou une chaine vide on va dans le catch
		//Sinon si le tarif est superieur a 0 on recupere les produit dont tarif < valeur rentree
		try {
			tarif = Float.parseFloat(searchTarif.getText().trim());
			if (tarif > 0) {
				for (Produit produit : MainController.produitDAO.findAll()) {
					if (produit.getTarif() <= tarif) {
						listeProd.add(produit);
					}
				}
			}
			
		} catch (Exception e) {
			if (searchTarif.getText().trim().isEmpty()) {
				try {
					listeProd.addAll(MainController.produitDAO.findAll());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		return listeProd;
	}
	
	//Renvoie une liste des produits qui possedent un titre de categorie correspondant a la demande
	public ArrayList<Produit> filtrerCateg() {
		String categ = searchCateg.getText().trim().toLowerCase();
		ArrayList<Produit> listeProd = new ArrayList<Produit>();
		try {
			if (categ.equals("")) {
				listeProd.addAll(MainController.produitDAO.findAll());
			}
			else {
				for (Produit produit : MainController.produitDAO.findAll()) {
					if (MainController.categorieDAO.getById(produit.getIdCateg()).getTitre().toLowerCase().contains(categ)) {
						listeProd.add(produit);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listeProd;
	}
	
	//renvoie la liste la plus longue
	private ArrayList<Produit> max(ArrayList<Produit> l1, ArrayList<Produit> l2, ArrayList<Produit> l3) {
		if (l1.size() >= l2.size() && l1.size() >= l3.size())
			return l1;
		if (l2.size() >= l1.size() && l2.size() >= l3.size())
			return l2;
		if (l3.size() >= l1.size() && l3.size() >= l2.size())
			return l3;
		return l3;
	}
	
	//Rassemble toute les listes des donnees filtrees et fait un ET exclusif des donnees
	public void filtrer() {
		ArrayList<Produit> prodNom = filtrerNom();
		ArrayList<Produit> prodTarif = filtrerTarif();
		ArrayList<Produit> prodCateg = filtrerCateg();
		ObservableList<Produit> listeProdSelect = FXCollections.observableArrayList();
		
		ObservableList<Produit> listeProduitSurplus = FXCollections.observableArrayList();
		ObservableList<Produit> listeProduitMino = FXCollections.observableArrayList();
		
		for (Produit produit : max(prodNom, prodTarif, prodCateg)) {
			if (prodNom.contains(produit) 
					&& prodTarif.contains(produit) 
					&& prodCateg.contains(produit) )
				listeProdSelect.add(produit);
		}
		//On enleve de la tableView tout produit non present dans listeProdSelect mais present dans la tableView
		ObservableList<Produit> trans1 = tabProduit.getItems();
		
		for (Produit produit : trans1) {
			if (!listeProdSelect.contains(produit))
				listeProduitSurplus.add(produit);
		}
		
		//On rajoute dans la tableView tout produit present dans listeProdSelect mais non present dans la tableView
		ObservableList<Produit> trans2 = tabProduit.getItems();
		for (Produit produit : listeProdSelect ) {
			if (!trans2.contains(produit))
				listeProduitMino.add(produit);
		}
		
		tabProduit.getItems().removeAll(listeProduitSurplus);
		tabProduit.getItems().addAll(listeProduitMino);
	}
	
	//Charge la page AJoutProduit et recupere les donnees pour les ajouter dans le tableau
	public void ajoutProd() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page AjoutProduit.fxml
			URL fxmlURL=getClass().getResource("/fxml/add/AjoutProduit.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			AjoutProduitController controller = fxmlLoader.getController();
			
			//On affiche la fenetre AjoutProduit
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Creer un produit");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
			if (controller.getProduitAjout() != null)
				tabProduit.getItems().add(controller.getProduitAjout());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Charge la page ModifProduit et recupere les donnees pour les modifier dans le tableau
	public void modifProd() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/edit/ModifProduit.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			EditProduitController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabProduit.getSelectionModel().getSelectedItem());
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Modififer un produit");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
			//On modifie l'objet dans le tableau
			tabProduit.getItems().set(
					tabProduit.getItems().indexOf(controller.getSelectedItem()), 
					controller.getSelectedItem());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Supprime la valeur dans le tableau et dans la dao
	public void supprProd() {
		
		//Ouvre une fenetre d'alerte pour confirer la suppresion
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerte suppression");
		alert.setContentText("Etes vous certain de supprimer ce produit ?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			try {
				boolean utilise = false;
				int i = 0;
				ArrayList<LigneCommande> listeLc = MainController.ligneCommandeDAO.findAll();
				while (!utilise && i < listeLc.size()) {
					if (listeLc.get(i).getIdProduit() == tabProduit.getSelectionModel().getSelectedItem().getId())
						utilise = true;
					i+=1;
				}

				if (utilise) {
					Alert nonSuppr = new Alert(AlertType.WARNING);
					nonSuppr.setTitle("Impossibilite de suppression");
					nonSuppr.setContentText("Vous ne pouvez pas supprimer ce produit car il est utilisee dans au moins une commande");
					nonSuppr.showAndWait();
				} 
				else {
					MainController.produitDAO.delete(produit);
					tabProduit.getItems().remove(produit);
				}
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
		tabProduit.getSelectionModel().clearSelection();
	}
}
