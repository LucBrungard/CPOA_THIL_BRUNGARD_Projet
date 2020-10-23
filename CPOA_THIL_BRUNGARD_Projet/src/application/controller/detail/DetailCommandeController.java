package application.controller.detail;

import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import application.controller.MainController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CommandeDAO;
import dao.modele.ProduitDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import modele.Commande;
import modele.LigneCommande;

public class DetailCommandeController {
	
	@FXML private TableView<LigneCommande> tabLigneCommande;
	@FXML private TableColumn<LigneCommande, String> idProduit = new TableColumn<LigneCommande, String>("Identifiant Produit");
	@FXML private TableColumn<LigneCommande, Integer> quantite = new TableColumn<LigneCommande, Integer>("Quantité");
	@FXML private TableColumn<LigneCommande, String> prixUnitaire = new TableColumn<LigneCommande, String>("Prix Unitaire");
	
	@FXML private Button addCommande;
	@FXML private Button deleteCommande;
	@FXML private Button editCommande;
	@FXML private Button detailCommande;
	
	@SuppressWarnings("unused")
	private MainController main;
	
	CommandeDAO commandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
	ProduitDAO produitDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO();
	
	public void initialize(URL location, ResourceBundle resources) {
		
		this.tabLigneCommande.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					this.deleteCommande.setDisable(newValue == null);
					this.editCommande.setDisable(newValue == null);
					this.detailCommande.setDisable(newValue == null);
				});
		
		this.deleteCommande.setDisable(true);
		this.editCommande.setDisable(true);
		this.detailCommande.setDisable(true);
	}
	
	public void init(MainController mainController) {
		main = mainController;
		
	}

	public void initData(int i) {
		this.idProduit.setCellValueFactory(new Callback<CellDataFeatures<LigneCommande, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<LigneCommande, String> p) {
				try {
					return new ReadOnlyStringWrapper(produitDAO.getById(p.getValue().getIdProduit()).getNom());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		this.quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
		this.prixUnitaire.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
		
		Commande commande =null;
		try {
			commande = commandeDAO.getById(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator iterator = commande.getLigneCommande().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			this.tabLigneCommande.getItems().add((LigneCommande) mapEntry.getValue());
			
		}
		
	}
	
	
	

}
