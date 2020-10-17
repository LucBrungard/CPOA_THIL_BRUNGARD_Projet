package application.controller.page;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.controller.MainController;
import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CommandeDAO;
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
import modele.Commande;

public class PageCommandeController implements Initializable {
	
	@FXML private TableView<Commande> tabCommande;
	@FXML private TableColumn<Commande, Integer> idCommande = new TableColumn<Commande, Integer>("Identifiant");
	@FXML private TableColumn<Commande, LocalDate> dateCommande = new TableColumn<Commande, LocalDate>("Date Commande");
	@FXML private TableColumn<Commande, String> clientCommande = new TableColumn<Commande, String>("Client Concerne");
	
	@FXML private Button addCommande;
	@FXML private Button deleteCommande;
	@FXML private Button editCommande;
	@FXML private Button searchCommande;
	@FXML private Button detailCommande;
	
	private MainController main;
	
	CommandeDAO commandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.idCommande.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.dateCommande.setCellValueFactory(new PropertyValueFactory<>("date"));
		this.clientCommande.setCellValueFactory(new Callback<CellDataFeatures<Commande, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Commande, String> p) {
				return new ReadOnlyStringWrapper(p.getValue().getNomClient());
			}
		});
		
		try {
			this.tabCommande.getItems().addAll(commandeDAO.findAll());
		} catch (Exception e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		
		this.tabCommande.getSelectionModel().selectedItemProperty().addListener(
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
}
