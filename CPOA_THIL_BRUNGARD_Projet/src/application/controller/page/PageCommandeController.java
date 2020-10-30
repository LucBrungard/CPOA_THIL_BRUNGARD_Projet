package application.controller.page;

import java.net.URL;



import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import application.controller.MainController;
import application.controller.add.AjoutCommandeController;
import application.controller.detail.DetailCommandeController;
import application.controller.edit.EditCommandeController;
import dao.Persistance;
import dao.factory.DAOFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modele.Client;
import modele.Commande;
import modele.LigneCommande;

public class PageCommandeController implements Initializable {
	
	@FXML private TableView<Commande> tabCommande;
	@FXML private TableColumn<Commande, Integer> idCommande = new TableColumn<Commande, Integer>("Identifiant");
	@FXML private TableColumn<Commande, LocalDate> dateCommande = new TableColumn<Commande, LocalDate>("Date Commande");
	@FXML private TableColumn<Commande, String> clientCommande = new TableColumn<Commande, String>("Client Concerne");
	
	@FXML private Button addCommande;
	@FXML private Button deleteCommande;
	@FXML private Button editCommande;
	@FXML private Button detailCommande;
	@FXML Button actualiser;
	
	@SuppressWarnings("unused")
	private MainController main;
	private Commande commande;
	private Client client=null; 
	
	public void actualiser() {
		try {
			this.tabCommande.getItems().setAll(MainController.commandeDAO.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initData(Client selectedClient) {
		client = selectedClient; 
		 if (client!=null) {
			 try {
				for (Commande commande : MainController.commandeDAO.findAll()) {
					 if (commande.getIdClient() != client.getId()) this.tabCommande.getItems().remove(commande); 
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.idCommande.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.dateCommande.setCellValueFactory(new PropertyValueFactory<>("date"));
		this.clientCommande.setCellValueFactory(new Callback<CellDataFeatures<Commande, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Commande, String> p) {
				try {
					return new ReadOnlyStringWrapper(MainController.clientDAO.getById(p.getValue().getIdClient()).getNom());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		
	
			
		 try {
			 this.tabCommande.getItems().addAll(MainController.commandeDAO.findAll()); 
			 
			 if (client!=null) {
				 for (Commande commande : MainController.commandeDAO.findAll()) {
					 if (commande.getIdClient() != client.getId()) this.tabCommande.getItems().remove(commande); 
				 }
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		this.tabCommande.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.commande = tabCommande.getSelectionModel().getSelectedItem();
					this.addCommande.setDisable(newValue != null);
					this.deleteCommande.setDisable(newValue == null);
					this.editCommande.setDisable(newValue == null);
					this.detailCommande.setDisable(newValue == null);
					
				}); 
					
					//event qui annule la selection actuelle de la tableView si on selectionne une ligne vide
					this.tabCommande.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
					    Node source = evt.getPickResult().getIntersectedNode();
					   
					    // move up through the node hierarchy until a TableRow or scene root is found 
					    while (source != null && !(source instanceof TableRow)) {
					        source = source.getParent();
					    }

					    // clear selection on click anywhere but on a filled row
					    if (source == null || (source instanceof TableRow && ((TableRow<?>) source).isEmpty())) {
					    	tabCommande.getSelectionModel().clearSelection();
					    }
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
			
			//On recupere le controleur de la page ModifCateg.fxProduitml
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
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	
	//Charge la page ModifProduit et recupere les donnees pour les modifier dans le tableau
	public void detailCommande() {
	
		Stage nStage = new Stage(); 
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/detail/DetailCommande.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			DetailCommandeController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabCommande.getSelectionModel().getSelectedItem().getId());
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Detail d'une commande");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Charge la page ModifProduit et recupere les donnees pour les modifier dans le tableau
	public void modifCommande() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/edit/ModifCommande.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controleur de la page ModifCateg.fxml
			EditCommandeController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabCommande.getSelectionModel().getSelectedItem());
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 600, 350);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Modififer une commande");
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.showAndWait();
			
			//On modifie l'objet dans le tableau
			tabCommande.getItems().set(
					tabCommande.getItems().indexOf(controller.getSelectedItem()), 
					controller.getSelectedItem());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Supprime la valeur dans le tableau et dans la dao
	@SuppressWarnings("rawtypes")
	public void supprCommande() {
		//Ouvre une fenetre d'alerte pour confirer la suppresion
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerte suppression");
		alert.setContentText("Etes vouloir supprimer cette commande qui est composée d'un ou plusieurs produits ?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				for (Map.Entry mapentry : commande.getLigneCommande().entrySet()) {
					DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getLigneCommandeDAO().delete((LigneCommande) mapentry.getValue());
		        }
				MainController.commandeDAO.delete(commande);
				tabCommande.getItems().remove(commande);
				tabCommande.getSelectionModel().clearSelection();
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
		else {
			tabCommande.getSelectionModel().clearSelection();
		}
	}

	public Button getActualiser() {
		return actualiser;
	}

	public void setActualiser(Button actualiser) {
		this.actualiser = actualiser;
	}
	

}
