package org.unibl.etf.gui.plants.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantContainer;
import org.unibl.etf.dto.PlantTableItem;
import org.unibl.etf.dto.Pricelist;
import org.unibl.etf.dto.PricelistHasPlant;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.util.PDFCreator;

import com.itextpdf.text.DocumentException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
	@FXML
	private Button btnLoad;
	@FXML
	private ComboBox<Pricelist> cmbPricelist;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		// TODO obrisati pa testirati
		container.setPlants(DAOFactory.getInstance().getPlantDAO().selectAll());
		ObservableList<Pricelist> pricelists = FXCollections.observableArrayList();
		pricelists.addAll(DAOFactory.getInstance().getPricelistDAO().selectAll());
		cmbPricelist.setItems(pricelists);
		cmbPricelist.getSelectionModel().select(0);
		buildTable();
		bindDisable();
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
			if(DisplayUtil.showConfirmationDialog("Zelite li da sacuvate cjenovnik?").equals(ButtonType.YES)) {
				Pricelist pricelist = new Pricelist(null, Calendar.getInstance().getTime(), null, true, false);
				DAOFactory.getInstance().getPricelistDAO().insert(pricelist);
				for(Plant plant : container.getPlants()) {
					DAOFactory.getInstance().getPricelistHasPlantDAO().insert(new PricelistHasPlant(pricelist, plant, pricelist.getPricelistId(), plant.getPlantId(), false));
				}
			}
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
	@FXML
	public void loadPricelist(ActionEvent event) {
		Pricelist pricelist = cmbPricelist.getSelectionModel().getSelectedItem();
		List<PricelistHasPlant> items = DAOFactory.getInstance().getPricelistHasPlantDAO().getByPricelistId(pricelist.getPricelistId());
		DAOF
	}
	private void bindDisable() {
		btnDelete.disableProperty().bind(tblPlants.getSelectionModel().selectedItemProperty().isNull());
		btnLoad.disableProperty().bind(cmbPricelist.itemsProperty().isNull());
	}
}
