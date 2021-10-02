package application.controller;

import java.net.URL
;
import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import modele.LigneCommande;

public class MainController implements Initializable {
	@FXML ChoiceBox<String> cbxPersistance;
	
	public static ProduitDAO produitDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO();
	public static CategorieDAO categorieDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	public static CommandeDAO commandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
	public static LigneCommandeDAO<LigneCommande> ligneCommandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getLigneCommandeDAO();
	public static ClientDAO clientDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO();
	
	private ObservableList<String> persistance = FXCollections.observableArrayList("MYSQL", "LISTE_MEMOIRE");
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbxPersistance.setItems(persistance);
		cbxPersistance.setValue("LISTE_MEMOIRE");
		
		cbxPersistance.getSelectionModel().selectedItemProperty().addListener(
				(observale, odlValue, newValue) -> {
					choixPersistance(cbxPersistance.getSelectionModel().getSelectedItem());
				});
	}

	public void choixPersistance(String selectedItem) {
		if (selectedItem.equals("MYSQL")) {
			produitDAO = DAOFactory.getDAOFactory(Persistance.MYSQL).getProduitDAO();
			categorieDAO = DAOFactory.getDAOFactory(Persistance.MYSQL).getCategorieDAO();
			commandeDAO = DAOFactory.getDAOFactory(Persistance.MYSQL).getCommandeDAO();
			ligneCommandeDAO = DAOFactory.getDAOFactory(Persistance.MYSQL).getLigneCommandeDAO();
			clientDAO = DAOFactory.getDAOFactory(Persistance.MYSQL).getClientDAO();
		}
		else {
			produitDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getProduitDAO();
			categorieDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
			commandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCommandeDAO();
			ligneCommandeDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getLigneCommandeDAO();
			clientDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getClientDAO();
		}
	}
}
