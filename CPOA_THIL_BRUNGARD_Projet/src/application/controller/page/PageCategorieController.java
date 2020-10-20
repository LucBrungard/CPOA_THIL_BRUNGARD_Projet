package application.controller.page;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.controller.MainController;
import application.controller.add.AjoutCategorieController;
import application.controller.edit.EditCategorieController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Categorie;

public class PageCategorieController implements Initializable {
	@FXML private TableView<Categorie> tabCateg;
	@FXML private TableColumn<Categorie, String> titreCateg = new TableColumn<Categorie, String>("Titre");
	@FXML private TableColumn<Categorie, String> visuelCateg = new TableColumn<Categorie, String>("Visuel");
	
	@FXML private Button addCateg;
	@FXML private Button deleteCateg;
	@FXML private Button editCateg;
	@FXML private TextField searchTitre;
	@FXML private TextField searchVisuel;
	
	@SuppressWarnings("unused")
	private MainController main;
	private Categorie categorie;
	CategorieDAO categDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	
	//Instancie la classe MainController
	public void init(MainController mainController) {
		main = mainController;
	}
	
	
	//Initialisation des donnees (tableau + boutons) + rajout des listeners sur les tableaux
	public void initData() {
		this.deleteCateg.setDisable(true);
		this.editCateg.setDisable(true);
		
		this.titreCateg.setCellValueFactory(new PropertyValueFactory<Categorie, String>("titre"));
		this.visuelCateg.setCellValueFactory(new PropertyValueFactory<Categorie, String>("visuel"));
		
		//Listener qui definit si les boutons sont desactives ou non
		this.tabCateg.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.addCateg.setDisable(newValue != null);
					this.deleteCateg.setDisable(newValue == null);
					this.editCateg.setDisable(newValue == null);
					this.categorie = this.tabCateg.getSelectionModel().getSelectedItem();
				});
		
		//evenement qui permet d'enlever la selection actuelle dans la tableView si on clique sur une ligne vide 
		this.tabCateg.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
		    Node source = evt.getPickResult().getIntersectedNode();
		   
		    // move up through the node hierarchy until a TableRow or scene root is found 
		    while (source != null && !(source instanceof TableRow)) {
		        source = source.getParent();
		    }

		    // clear selection on click anywhere but on a filled row
		    if (source == null || (source instanceof TableRow && ((TableRow<?>) source).isEmpty())) {
		        tabCateg.getSelectionModel().clearSelection();
		    }
		});
		
		//On remplit le tableau des donnees necessaires
		try {
			this.tabCateg.getItems().addAll(categDAO.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initData();
	}
	
	
	//Fonction appelee lors du clic sur le bouton Ajout
	public void ajoutCateg() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/add/AjoutCategorie.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On recupere le controlleur de la classe AjoutCategorie.fxml
			AjoutCategorieController controller = fxmlLoader.getController();
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 350, 200);
			
			//initModality permet de stoper l'interaction avec la page en cours 
			//pour garder le focus avec la nouvelle fentre qui va s'ouvrir
			nStage.initModality(Modality.APPLICATION_MODAL);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Creer un client");
			nStage.showAndWait();
			
			//on recupere l'objet que l'on vient de creer et on l'ajoute dans le tableau
			if (controller.getCategorie() != null) 
				this.tabCateg.getItems().add(controller.getCategorie());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Fonction appelee lors du clic sur le bouton Supprimer
	public void supprCateg() {
		//Ouverture d'une page d'alerte pour confirmer la suppression
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerte suppression");
		alert.setContentText("Etes vous certain de supprimer cette categorie ?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				categDAO.delete(categorie);
				this.tabCateg.getItems().remove(categorie);
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
		tabCateg.getSelectionModel().clearSelection();
	}
	
	
	//Fonction appelee lors du clic sur le bouton Modifier
	public void modifCateg() {
		Stage nStage = new Stage();
		try {
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/edit/ModifCateg.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 350, 200);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Modififer un client");
			nStage.initModality(Modality.APPLICATION_MODAL);
			
			//On recupere le controleur de la page ModifCateg.fxml
			EditCategorieController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabCateg.getSelectionModel().getSelectedItem());
			
			//Et on affiche la page et on attend qu'elle soit fermee
			nStage.showAndWait();
			
			//On actualise les donnees
			tabCateg.getSelectionModel().clearSelection();
			int posItem = tabCateg.getItems().indexOf(controller.getSelectedItem());
			this.tabCateg.getItems().set(posItem, controller.getSelectedItem());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Fonction qui renvoie la liste des categorie possedant un titre correspondant a la demande
	public ObservableList<Categorie> filtrerTitre() {
		ObservableList<Categorie> listeCateg = FXCollections.observableArrayList();
		
		String titre = searchTitre.getText().trim().toLowerCase();
		
		try {
			if (titre.equals("")) 
					listeCateg.addAll(categDAO.findAll());
			else {
				for (Categorie categorie : categDAO.findAll()) {
					if (categorie.getTitre().toLowerCase().contains(titre)) {
						listeCateg.add(categorie);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listeCateg;
	}
	
	//Fonction qui renvoie la liste des categorie possedant un visuel correspondant a la demande
	public ObservableList<Categorie> filtrerVisuel() {
		ObservableList<Categorie> listeCateg = FXCollections.observableArrayList();
		
		String visuel = searchVisuel.getText().trim().toLowerCase();
		
		try {
			if (visuel.equals("")) 
					listeCateg.addAll(categDAO.findAll());
			else {
				for (Categorie categorie : categDAO.findAll()) {
					if (categorie.getVisuel().toLowerCase().contains(visuel)) 
						listeCateg.add(categorie);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listeCateg;
	}
	
	//Renvoie la liste la plus longue
	private ObservableList<Categorie> max(ObservableList<Categorie> l1, ObservableList<Categorie>l2) {
		if (l1.size() > l2.size())
			return l1;
		return l2;
	}
	
	
	//Renvoie la liste la plus courte
	private ObservableList<Categorie> min(ObservableList<Categorie> l1, ObservableList<Categorie>l2) {
		if (l1.size() > l2.size())
			return l2;
		return l1;
	}
	
	//regroupe toutes les listes des donnees filtree et en fait un ET exclusif 
	public void filtrer() {
		ObservableList<Categorie> l1 = filtrerTitre();
		ObservableList<Categorie> l2 = filtrerVisuel();
		ObservableList<Categorie> listeCategSelect = FXCollections.observableArrayList();
		
		ObservableList<Categorie> listeCategSurplus = FXCollections.observableArrayList();
		ObservableList<Categorie> listeCategMino = FXCollections.observableArrayList();

		//On recupere les categories presentes dans l1 ET l2
		for (Categorie c1 : max(l1, l2)) {
			if (min(l1, l2).contains(c1)) 
				listeCategSelect.add(c1);
		}
		
		//On enleve de la tableView toute categorie non presente dans listeCategSelect mais presente dans la tableView
		ObservableList<Categorie> trans1 = tabCateg.getItems();
		for (Categorie categorie : trans1) {
			if (!listeCategSelect.contains(categorie))
				listeCategSurplus.add(categorie);
		}
		
		//On rajoute dans la tableView toute categorie presente dans listeCategSelect mais non presente dans la tableView
		ObservableList<Categorie> trans2 = tabCateg.getItems();
		for (Categorie categorie : listeCategSelect ) {
			if (!trans2.contains(categorie))
				listeCategMino.add(categorie);
		}
		
		tabCateg.getItems().removeAll(listeCategSurplus);
		tabCateg.getItems().addAll(listeCategMino);
	}
}
