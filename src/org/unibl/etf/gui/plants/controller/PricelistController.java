package org.unibl.etf.gui.plants.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
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
	@FXML
	private Button btnSave;
	@FXML
	private Button btnRefresh;
	@FXML
	private DatePicker dpFrom;
	@FXML
	private DatePicker dpTo;
	@FXML
	private Button btnSearch;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		buildTable();
		bindDisable();
	}

	@FXML
	public void delete(ActionEvent event) {
		if (container.current() != null) {
			int index = tblPlants.getSelectionModel().getSelectedIndex();
			container.setCurrent(index);
			container.remove(container.current());
			tblPlants.getItems().remove(tblPlants.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	public void save(ActionEvent event) {
		if (DisplayUtil.showConfirmationDialog("Zelite li da sacuvate cjenovnik?").equals(ButtonType.YES)) {
			String message = "";
			Pricelist pricelist = new Pricelist(null, Calendar.getInstance().getTime(), null, true, false);
			if (DAOFactory.getInstance().getPricelistDAO().insert(pricelist) > 0) {
				for (Plant plant : container.getPlants()) {
					DAOFactory.getInstance().getPricelistHasPlantDAO().insert(new PricelistHasPlant(pricelist, plant,
							pricelist.getPricelistId(), plant.getPlantId(), false));
				}
				message = "Dodavanje uspjesno.";
			} else {
				message = "Greska pri dodavanju.";
			}
			DisplayUtil.showMessageDialog(message);
		}
	}

	@FXML
	public void print(ActionEvent event) {
		PDFCreator creator = new PDFCreator("./resources/cjenovnik_" + System.currentTimeMillis() + ".pdf");
		try {
			creator.open();
			creator.createPricelist(container.getPlants());
			creator.close();
			DisplayUtil.showMessageDialog("Stampanje zavrseno");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void refresh(ActionEvent event) {
		this.container.setPlants(basicPlants);
		this.container.setCurrent(0);
		populateTable();
		btnSave.setDisable(false);
	}

	@FXML
	public void showPricelist(ActionEvent event) {
		Pricelist p = cmbPricelist.getSelectionModel().getSelectedItem();
		if(p!=null) {
			List<Plant> plants = DAOFactory.getInstance().getPlantDAO().getPlantByPricelistId(p.getPricelistId());
			container.setPlants(plants);
			container.setCurrent(0);
			populateTable();
			btnSave.setDisable(true);
		}
	}

	@FXML
	public void search(ActionEvent event) {
		try {
			String statement = "";
			List<Object> variables = new ArrayList<Object>();
			if (dpFrom.getValue() != null) {
				statement += " date_from >= ?";
				String dateString = dpFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				variables.add(date);
			}
			if(dpTo.getValue() != null) {
				if(dpFrom.getValue() != null) {
					statement += " and";
				}
				statement += " date_from <= ?";
				String dateString = dpTo.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				variables.add(date);
			}
			Object[] vars = new Object[variables.size()];
			vars = variables.toArray(vars);
			ObservableList<Pricelist> pricelists = FXCollections.observableArrayList();
			pricelists.addAll(DAOFactory.getInstance().getPricelistDAO().select(statement, vars));
			cmbPricelist.setItems(pricelists);
			cmbPricelist.getSelectionModel().select(0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
	}

	private void buildTable() {
		colScientificName.setCellValueFactory(new PropertyValueFactory<PlantTableItem, String>("scientificName"));
		colKnownAs.setCellValueFactory(new PropertyValueFactory<PlantTableItem, String>("knownAs"));
		colPrices.setCellValueFactory(new PropertyValueFactory<PlantTableItem, String>("prices"));
	}

	private void populateTable() {
		ObservableList<PlantTableItem> plants = FXCollections.observableArrayList();
		for (Plant plant : container.getPlants()) {
			plants.add(new PlantTableItem(plant));
		}
		tblPlants.setItems(plants);
	}

	@FXML
	public void selectItem(MouseEvent event) {
		int index = tblPlants.getSelectionModel().getSelectedIndex();
		if (index > -1 && index < container.getPlants().size()) {
			container.setCurrent(index);
		}
	}
	
	public void setContainer(PlantContainer container) {
		this.container = container;
		basicPlants = container.getPlants();
		this.container.setCurrent(0);
		populateTable();
	}

	private void bindDisable() {
		btnDelete.disableProperty().bind(tblPlants.getSelectionModel().selectedItemProperty().isNull());
		btnSearch.disableProperty().bind(dpFrom.valueProperty().isNull().and(dpTo.valueProperty().isNull()));
	}

	private List<Plant> basicPlants;
}
