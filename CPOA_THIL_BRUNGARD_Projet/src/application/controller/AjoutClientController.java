package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modele.Categorie;

public class AjoutClientController implements Initializable {
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextArea editDesc; 
	@FXML private TextField editTarif;
	@FXML private ChoiceBox<Categorie> cbxCategorie;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	    try {
	    	this.lblAffichage.setText("");
			
		} catch (Exception e) {
		}
	}
}
