package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.controller.page.PageCategorieController;
import application.controller.page.PageClientController;
import application.controller.page.PageCommandeController;
import application.controller.page.PageProduitController;
import javafx.fxml.FXML;

public class MainController {
	@FXML PageCategorieController pageCategorie;
	@FXML PageClientController pageClient;
	@FXML PageCommandeController pageCommande;
	@FXML PageProduitController pageProduit;

	public void initialize(URL arg0, ResourceBundle arg1) {
		pageCategorie.init(this);
		pageClient.init(this);
		pageCommande.init(this);
		pageProduit.init(this);
	}
}
