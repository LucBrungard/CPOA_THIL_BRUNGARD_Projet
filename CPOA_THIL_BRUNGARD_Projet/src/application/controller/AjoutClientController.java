package application.controller;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AjoutClientController implements Initializable {
	@FXML private Button btnCreer;
	@FXML private Label lblAffichage;
	@FXML private TextField editNom;
	@FXML private TextField editPrenom; 
	@FXML private TextField editRue;
	@FXML private TextField editNo;
	@FXML private TextField editCodePostal;
	@FXML private TextField editVille;
	@FXML private TextField editPays;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
	    try {
	    	//this.lblAffichage.setText("");
			
		} catch (Exception e) {
		}
	}
	
	@FXML
	public void creerClient() {
		
	}
}
