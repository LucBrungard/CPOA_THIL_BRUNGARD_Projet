package application.controller.edit;

import java.net.URL;
import java.util.ResourceBundle;

import dao.Persistance;
import dao.factory.DAOFactory;
import dao.modele.CategorieDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modele.Categorie;

public class EditCategorieController implements Initializable{
	
	@FXML private TextField editTitre;
	@FXML private TextField editVisuel;
	@FXML private Button btnModif;
	@FXML private Label lblAffichage;
	
	CategorieDAO categorieDAO = DAOFactory.getDAOFactory(Persistance.LISTE_MEMOIRE).getCategorieDAO();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.editTitre.setText("");
		this.editVisuel.setText("");
	}
	
	public void modifier() {
		String titre = this.editTitre.getText().trim();
		String visuel = this.editVisuel.getText().trim();
		
		try {
			categorieDAO.update(new Categorie(1, titre, visuel));
		} catch (Exception e) {
			this.lblAffichage.setText(e.getMessage());
		}
		
		System.out.println(titre + " " + visuel);
		
		try {
			for (Categorie categ : categorieDAO.findAll()) {
				System.out.println(categ.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
