package application.controller.detail;

import java.net.URL;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import application.controller.MainController;
import application.controller.add.AjoutLigneCommandeController;
import application.controller.edit.EditLigneCommandeController;
import application.controller.page.PageProduitController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modele.Commande;
import modele.LigneCommande;
import modele.Produit;

public class DetailCommandeController implements Initializable {
	
	@FXML private TableView<LigneCommande> tabLigneCommande;
	@FXML private TableColumn<LigneCommande, String> idProduit = new TableColumn<LigneCommande, String>("Identifiant Produit");
	@FXML private TableColumn<LigneCommande, Integer> quantite = new TableColumn<LigneCommande, Integer>("Quantite");
	@FXML private TableColumn<LigneCommande, String> prixUnitaire = new TableColumn<LigneCommande, String>("Prix Unitaire");
	
	@FXML private Button addLigneCommande;
	@FXML private Button deleteLigneCommande;
	@FXML private Button editLigneCommande;
	
	@SuppressWarnings("unused")
	private MainController main;
	private Commande commande; 
	private LigneCommande selectedItem; 

	
	public void initData() {
	}
	
	
	public void initialize(URL location, ResourceBundle resources) {
		
		this.tabLigneCommande.getSelectionModel().selectedItemProperty().addListener(
			(observale, odlValue, newValue) -> {
				this.addLigneCommande.setDisable(newValue != null); 
				this.deleteLigneCommande.setDisable(newValue == null);
				this.editLigneCommande.setDisable(newValue == null);
			});
		
		//event qui annule la selection actuelle de la tableView si on selectionne une ligne vide
		this.tabLigneCommande.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
		    Node source = evt.getPickResult().getIntersectedNode();
		   
		    // move up through the node hierarchy until a TableRow or scene root is found 
		    while (source != null && !(source instanceof TableRow)) {
		        source = source.getParent();
		    }

		    // clear selection on click anywhere but on a filled row
		    if (source == null || (source instanceof TableRow && ((TableRow<?>) source).isEmpty())) {
		    	tabLigneCommande.getSelectionModel().clearSelection();
		    }}); 
		
		this.deleteLigneCommande.setDisable(true);
		this.editLigneCommande.setDisable(true);
	}
	
	public void init(MainController mainController) {
		main = mainController;
		
	}

	@SuppressWarnings("rawtypes")
	public void initData(int i) {
		this.idProduit.setCellValueFactory(new Callback<CellDataFeatures<LigneCommande, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<LigneCommande, String> p) {
				try {
					return new ReadOnlyStringWrapper(MainController.produitDAO.getById(p.getValue().getIdProduit()).getNom());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		this.quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
		this.prixUnitaire.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
		
		commande =null;
		try {
			commande = MainController.commandeDAO.getById(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator iterator = commande.getLigneCommande().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			this.tabLigneCommande.getItems().add((LigneCommande) mapEntry.getValue());
			
		}
		
	}
	
	public void ajoutLigneCommande() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page AjoutProduit.fxml
			URL fxmlURL=getClass().getResource("/fxml/add/AjoutLigneCommande.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxtml
			AjoutLigneCommandeController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(commande);
			
			//On affiche la fenetre AjoutProduit
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Creer une ligne de commande");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
			if (controller.getLigneCommandeAjout() != null)
				tabLigneCommande.getItems().add(controller.getLigneCommandeAjout());
			
			//On charge l'url de la page PageProduit.fxml pour actualiser les quantite
			URL fxmlURL2=getClass().getResource("/fxml/page/PageProduit.fxml");
			FXMLLoader fxmlLoader2 = new FXMLLoader(fxmlURL2);
			fxmlLoader2.load();
			
			//On recupere le controleur de la page PageProduit.fxml
			PageProduitController controller2 = fxmlLoader2.getController();
			controller2.initData();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Supprime la valeur dans le tableau et dans la dao
	public void supprLigneCommande() {
		//Ouvre une fenetre d'alerte pour confirer la suppresion
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerte suppression");
		alert.setContentText("Etes vouloir supprimer cette ligne de commande");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				selectedItem = tabLigneCommande.getSelectionModel().getSelectedItem(); 
				HashMap<Produit, LigneCommande> newLigneCommande = commande.getLigneCommande(); 
				Iterator<Entry<Produit, LigneCommande>> iterator = newLigneCommande.entrySet().iterator();
				while (iterator.hasNext()) {
					@SuppressWarnings("rawtypes")
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					if ((LigneCommande) mapEntry.getValue() == selectedItem) iterator.remove();
					
				}
				Commande c = new Commande(commande.getId(), commande.getDate(), commande.getIdClient(), newLigneCommande);
				MainController.commandeDAO.update(c); 
				MainController.ligneCommandeDAO.delete(selectedItem);
				tabLigneCommande.getItems().remove(selectedItem);
				tabLigneCommande.getSelectionModel().clearSelection();
				
				//On charge l'url de la page PageProduit.fxml pour actualiser les quantite
				URL fxmlURL2=getClass().getResource("/fxml/page/PageProduit.fxml");
				FXMLLoader fxmlLoader2 = new FXMLLoader(fxmlURL2);
				fxmlLoader2.load();
				
				//On recupere le controleur de la page PageProduit.fxml
				PageProduitController controller2 = fxmlLoader2.getController();
				controller2.initData();
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
		else {
			tabLigneCommande.getSelectionModel().clearSelection();
		}
	}
	
	//Charge la page ModifProduit et recupere les donnees pour les modifier dans le tableau
	public void modifLigneCommande() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/edit/ModifLigneCommande.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			EditLigneCommandeController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabLigneCommande.getSelectionModel().getSelectedItem(), commande);
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Modifier une ligne de commande");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
			//On modifie l'objet dans le tableau
			tabLigneCommande.getItems().set(
					tabLigneCommande.getItems().indexOf(controller.getSelectedItem()), 
					controller.getSelectedItem());
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
