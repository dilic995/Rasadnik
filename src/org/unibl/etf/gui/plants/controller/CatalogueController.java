package org.unibl.etf.gui.plants.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dto.HeightPriceRatio;
import org.unibl.etf.dto.HeightPriceRatioTableItem;
import org.unibl.etf.dto.MyPlant;
import org.unibl.etf.gui.util.CSSUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.Circle;

import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class CatalogueController extends PlantBrowserController {
	@FXML
	private TreeView<String> treePlants;
	@FXML
	private Button btnPrevious;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnRemove;
	@FXML
	private ImageView imgPhoto;
	@FXML
	private Label lblLatinName;
	@FXML
	private Label lblCommonName;
	@FXML
	private TableView<HeightPriceRatioTableItem> tblRatio;
	@FXML
	private TableColumn<HeightPriceRatioTableItem, Double> colMinHeight;
	@FXML
	private TableColumn<HeightPriceRatioTableItem, String> colMaxHeight;
	@FXML
	private TableColumn<HeightPriceRatioTableItem, Double> colPrice;
	@FXML
	private Circle ownedIndicator;
	@FXML
	private Label lblOwned;
	@FXML
	private TextFlow taDescription;
	@FXML
	private Label lblTotal;
	@FXML
	private Button btnExport;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		buildInitialWindow();
	}

	// Event Listener on Button[#btnPrevious].onAction
	@FXML
	public void showPreviousPlant(ActionEvent event) {
		displayPlantInfo(plants.get((plants.indexOf(selectedPlant) - 1 + plants.size()) % plants.size()));
		setSelected(selectedPlant.getLatinName());
	}

	// Event Listener on Button[#btnNext].onAction
	@FXML
	public void showNextPlant(ActionEvent event) {
		displayPlantInfo(plants.get((plants.indexOf(selectedPlant) + 1) % plants.size()));
		setSelected(selectedPlant.getLatinName());
	}

	// Event Listener on Button[#btnAdd].onAction
	@FXML
	public void addPlant(ActionEvent event) {
		try {
			BaseController.changeScene("/org/unibl/etf/gui/plants/view/AddPlantView.fxml", primaryStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void exportToPricelist(ActionEvent event) {
		for (MyPlant plant : plants) {
			System.out.println(plant);
		}
	}

	// Event Listener on Button[#btnRemove].onAction
	@FXML
	public void removePlant(ActionEvent event) {
		// TODO Autogenerated
	}

	// Event Listener on Circle[#ownedIndicator].onMouseClicked
	@FXML
	public void setOwned(MouseEvent event) {
		selectedPlant.setOwned(!selectedPlant.getOwned());
		setPlantOwned(selectedPlant.getOwned());
	}

	@FXML
	public void getSelectedPlant(MouseEvent event) {
		TreeItem<String> item = treePlants.getSelectionModel().getSelectedItem();
		if (item != null && item.getParent() != null && !item.getParent().getValue().equals("Biljke")) {
			for (MyPlant plant : plants) {
				if (item.getValue().equals(plant.getLatinName())) {
					displayPlantInfo(plant);
					break;
				}
			}
		}
	}

	@Override
	public void displayPlantInfo(MyPlant plant) {
		selectedPlant = plant;
		lblLatinName.setText(plant.getLatinName());
		lblCommonName.setText(plant.getCommonName());
		taDescription.getChildren().clear();
		taDescription.getChildren().add(new Text(plant.getDescription()));
		setPlantOwned(plant.getOwned());
		imgPhoto.setImage(plant.getImage());
		ObservableList<HeightPriceRatioTableItem> ratios = FXCollections.observableArrayList();
		for (HeightPriceRatio ratio : plant.getRatios()) {
			ratios.add(new HeightPriceRatioTableItem(ratio));
		}
		tblRatio.setItems(ratios);
	}

	@Override
	public void displayAllPlants() {
		populateTreeView();
		lblTotal.setText(plants.size() + "");
	}

	private void buildTable() {
		colMinHeight.setCellValueFactory(new PropertyValueFactory<HeightPriceRatioTableItem, Double>("minHeight"));
		colMaxHeight.setCellValueFactory(new PropertyValueFactory<HeightPriceRatioTableItem, String>("maxHeight"));
		colPrice.setCellValueFactory(new PropertyValueFactory<HeightPriceRatioTableItem, Double>("price"));
	}

	private void populateTreeView() {
		TreeItem<String> rootItem = new TreeItem<String>("Biljke");
		rootItem.setExpanded(true);
		ObservableList<TreeItem<String>> rootItems = FXCollections.observableArrayList();
		char c = 'A';
		for (; c <= 'Z'; c++) {
			TreeItem<String> item = new TreeItem<String>(String.valueOf(c));
			rootItems.add(item);
		}
		rootItem.getChildren().addAll(rootItems);
		for (MyPlant plant : plants) {
			String firstLetter = plant.getLatinName().substring(0, 1);
			for (TreeItem<String> item : rootItems) {
				if (item.getValue().equals(firstLetter)) {
					item.getChildren().add(new TreeItem<String>(plant.getLatinName()));
				}
			}
		}
		treePlants.setRoot(rootItem);
	}

	private void setPlantOwned(Boolean condition) {
		if (condition) {
			CSSUtil.setNewStyleClass(ownedIndicator, "green-fill");
			CSSUtil.setNewStyleClass(lblOwned, "plantOwned");
			lblOwned.setText("U posjedu");
		} else {
			CSSUtil.setNewStyleClass(ownedIndicator, "red-fill");
			CSSUtil.setNewStyleClass(lblOwned, "plant-not-owned");
			lblOwned.setText("Nije u posjedu");
		}
	}

	private void setSelected(String latinName) {
		Character c = latinName.toCharArray()[0];
		int index = c - 'A';
		TreeItem<String> item = treePlants.getRoot().getChildren().get(index);
		item.setExpanded(true);
		for (TreeItem<String> it : item.getChildren()) {
			if (it.getValue().equals(latinName)) {
				treePlants.getSelectionModel().select(it);
				break;
			}
		}
	}
}
