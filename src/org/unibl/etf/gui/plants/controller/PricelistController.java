package org.unibl.etf.gui.plants.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantContainer;
import org.unibl.etf.dto.PlantTableItem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PricelistController extends PlantBrowserController {
	@FXML
	private TableView<PlantTableItem> tblPlants;
	@FXML
	private TableColumn<PlantTableItem, String> colScientificName;
	@FXML
	private TableColumn<PlantTableItem, String> colKnownAs;
	@FXML
	private TableColumn<PlantTableItem, String> colPrices;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		container.setPlants(DAOFactory.getInstance().getPlantDAO().selectAll());
		buildTable();
	}

	public void setContainer(PlantContainer container) {
		this.container = container;
		populateTable();
	}
	
	@Override
	public void update() {
	}
	private void buildTable() {
		colScientificName.setCellValueFactory(new PropertyValueFactory<PlantTableItem, String>("scientificName"));
		colKnownAs.setCellValueFactory(new PropertyValueFactory<PlantTableItem, String>("knownAs"));
		colPrices.setCellValueFactory(new PropertyValueFactory<PlantTableItem, String>("prices"));
		populateTable();
	}
	private void populateTable() {
		ObservableList<PlantTableItem> plants = FXCollections.observableArrayList();
		for(Plant plant : container.getPlants()) {
			plants.add(new PlantTableItem(plant));
		}
		tblPlants.setItems(plants);
	}
}
