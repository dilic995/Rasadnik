package org.unibl.etf.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.gui.plants.controller.BasesController;
import org.unibl.etf.gui.plants.controller.CatalogueController;
import org.unibl.etf.gui.plants.controller.DrawRegionsController;
import org.unibl.etf.gui.sales.controller.SalesController;
import org.unibl.etf.gui.tool.controller.ToolViewController;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.ErrorLogger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EntryViewController extends BaseController{

	@FXML
	private Button btnLogout;
	@FXML
	public void logout(ActionEvent event) {
		
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void showCatalogue(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/plants/view/CatalogueView.fxml");
		BorderPane root;
		try {
			root = (BorderPane)loader.load();
			CatalogueController controller = DisplayUtil.<CatalogueController>getController(loader);
			DisplayUtil.switchStage(root, 800, 600, true, "Katalog", true);
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void showBases(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/plants/view/BasesView.fxml");
		BorderPane root;
		try {
			root = (BorderPane)loader.load();
			BasesController controller = DisplayUtil.<BasesController>getController(loader);
			DisplayUtil.switchStage(root, 900, 600, true, "Pregled matičnjaka", true);
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void showRegions(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/plants/view/DrawRegionsView.fxml");
		AnchorPane root;
		try {
			root = (AnchorPane)loader.load();
			DrawRegionsController controller = DisplayUtil.<DrawRegionsController>getController(loader);
			DisplayUtil.switchStage(root, 1200, 650, true, "Pregled regiona", true);
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}
	
	@FXML
	public void showSales(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/sales/view/SalesView.fxml");
		AnchorPane root;
		try {
			root = (AnchorPane)loader.load();
			SalesController controller = DisplayUtil.<SalesController>getController(loader);
			DisplayUtil.switchStage(root, 800, 600, true, "Novčana sredstva", true);
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}
	@FXML
	public void showTools(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/tool/view/ToolView.fxml");
		AnchorPane root;
		try {
			root = (AnchorPane)loader.load();
			ToolViewController controller = DisplayUtil.<ToolViewController>getController(loader);
			DisplayUtil.switchStage(root, 752, 746, true, "Pregled alata", true);
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
