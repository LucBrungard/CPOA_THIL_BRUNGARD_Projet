package application.controller.page;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.controller.MainController;
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
import javafx.stage.Stage;
import modele.Categorie;

public class PageCategorieController implements Initializable {
	private Categorie categorie;
	
	@FXML private TableView<Categorie> tabCateg;
	@FXML private TableColumn<Categorie, String> titreCateg = new TableColumn<Categorie, String>("Titre");
	@FXML private TableColumn<Categorie, String> visuelCateg = new TableColumn<Categorie, String>("Visuel");
	
	@FXML private Button addCateg;
	@FXML private Button deleteCateg;
	@FXML private Button editCateg;
	@FXML private TextField searchTitre;
	@FXML private TextField searchVisuel;
	
	private MainController main;
	
	CategorieDAO categDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	
	ObservableList<Categorie> trans = FXCollections.observableArrayList();
	
	
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
		
		this.tabCateg.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.addCateg.setDisable(newValue != null);
					this.deleteCateg.setDisable(newValue == null);
					this.editCateg.setDisable(newValue == null);
					this.categorie = this.tabCateg.getSelectionModel().getSelectedItem();
				});
		
		this.tabCateg.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
		    Node source = evt.getPickResult().getIntersectedNode();
		   
		    // move up through the node hierarchy until a TableRow or scene root is found 
		    while (source != null && !(source instanceof TableRow)) {
		        source = source.getParent();
		    }

		    // clear selection on click anywhere but on a filled row
		    if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
		        tabCateg.getSelectionModel().clearSelection();
		    }
		});
		
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
			//On charge l'url de la page ModifCateg.fxml
			URL fxmlURL=getClass().getResource("/fxml/add/AjoutCategorie.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			
			//On affiche la fenetre ModifCateg
			Scene scene = new Scene((AnchorPane) root, 350, 200);
			nStage.setScene(scene);
			nStage.setResizable(false);
			nStage.setTitle("Creer un client");
			nStage.show();
			
			//On ferme la fenetre PageCategorie.fxml
			Stage stage = (Stage) this.tabCateg.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Fonction appelee lors du clic sur le bouton Supprimer
	public void supprCateg() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alerte suppression");
		alert.setContentText("Etes vous certain de supprimer cette categorie ?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				categDAO.delete(categorie);
				clearAll();
				initData();
			} 
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}	
		else {
			tabCateg.getSelectionModel().clearSelection();
			//alert.close();
		}
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
			nStage.show();
			
			//On recupere le controleur de la page ModifCateg.fxml
			EditCategorieController controller = fxmlLoader.getController();
			
			//On charge les donnees de la ligne selectionnee dans la classe controleur EditCategorieController
			controller.initData(tabCateg.getSelectionModel().getSelectedItem());
			
			//On ferme la fenetre PageCategorie.fxml
			Stage stage = (Stage) this.tabCateg.getScene().getWindow();
			stage.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void refresh() {
		trans.clear();
		String titre = searchTitre.getText().trim().toLowerCase();
		String visuel = searchVisuel.getText().trim().toLowerCase();
		
		if(titre.equals("")) {
			if(visuel.equals("")) {
				try {
					trans.addAll(categDAO.findAll());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					for (Categorie categorie : categDAO.findAll()) {
						/*Si le titre de categorie contient la chaine rentree dans le text field, 
						alors on la rajoute a la liste de transition*/
						if (categorie.getVisuel().toLowerCase().contains(visuel)) {
							trans.add(categorie);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			if(visuel.equals("")) {
				try {
					for (Categorie categorie : categDAO.findAll()) {
						/*Si le titre de categorie contient la chaine rentree dans le text field, 
						alors on la rajoute a la liste de transition*/
						if (categorie.getTitre().toLowerCase().contains(titre)) {
							trans.add(categorie);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					for (Categorie categorie : categDAO.findAll()) {
						/*Si le titre de categorie contient la chaine rentree dans le text field, 
						alors on la rajoute a la liste de transition*/
						if (categorie.getTitre().toLowerCase().contains(titre)) {
							if (categorie.getVisuel().toLowerCase().contains(visuel)) {
								trans.add(categorie);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//On vide les donnees actuelles du tableau
		clearAll();
		
		//Et on remet les nouvelles categories selectionnees
		tabCateg.getItems().addAll(trans);
	}
}
