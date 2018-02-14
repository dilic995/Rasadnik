package org.unibl.etf.gui.plants.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantContainer;
import org.unibl.etf.dto.PlantTableItem;
import org.unibl.etf.util.PDFCreator;

import com.itextpdf.text.DocumentException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PricelistController extends PlantBrowserController {
	@FXML
	private TableView<PlantTableItem> tblPlants;
	@FXML
	private TableColumn<PlantTableItem, String> colScientificName;
	@FXML
	private TableColumn<PlantTableItem, String> colKnownAs;
	@FXML
	private TableColumn<PlantTableItem, String> colPrices;
	@FXML
	private Button btnPrint;
	@FXML
	private Button btnDelete;

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
		for (Plant plant : container.getPlants()) {
			plants.add(new PlantTableItem(plant));
		}
		tblPlants.setItems(plants);
	}

	@FXML
	public void print(ActionEvent event) {
		PDFCreator creator = new PDFCreator("./resources/cjenovnik_" + System.currentTimeMillis() + ".pdf");
		try {
			creator.open();
			creator.createPricelist(container.getPlants());
			creator.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void selectItem(MouseEvent event) {
		int index = tblPlants.getSelectionModel().getSelectedIndex();
		if (index > -1 && index < container.getPlants().size()) {
			container.setCurrent(index);
		}
	}
	@FXML
	public void delete(ActionEvent event) {
		if(container.current() != null) {
			int index = tblPlants.getSelectionModel().getSelectedIndex();
			container.setCurrent(index);
			container.remove(container.current());
			tblPlants.getItems().remove(tblPlants.getSelectionModel().getSelectedIndex());
		}
	}
}
