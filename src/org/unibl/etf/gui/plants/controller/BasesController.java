package org.unibl.etf.gui.plants.controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.BasisTableItem;
import org.unibl.etf.dto.ReproductionCutting;
import org.unibl.etf.dto.ReproductionCuttingTableItem;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class BasesController extends BaseController {
	@FXML
	private ImageView imgPhoto;
	@FXML
	private TableView<BasisTableItem> tblBases;
	@FXML
	private TableColumn<BasisTableItem, String> colLatin;
	@FXML
	private TableColumn<BasisTableItem, String> colCommon;
	@FXML
	private TableColumn<BasisTableItem, String> colBasisDate;
	@FXML
	private TableColumn<BasisTableItem, Integer> colCurrentNum;
	@FXML
	private TableColumn<BasisTableItem, Integer> colTotalNum;
	@FXML
	private TableColumn<BasisTableItem, String> colType;
	@FXML
	private TableView<ReproductionCuttingTableItem> tblSeeds;
	@FXML
	private TableColumn<ReproductionCuttingTableItem, String> colSeedDate;
	@FXML
	private TableColumn<ReproductionCuttingTableItem, Integer> colProducedNum;
	@FXML
	private TableColumn<ReproductionCuttingTableItem, Integer> colSuccessfulNum;
	@FXML
	private Button btnAddPlant;
	@FXML
	private Button btnEditPlant;
	@FXML
	private Button btnDeletePlant;
	@FXML
	private Button btnLoad;
	@FXML
	private Button btnSearch;
	@FXML
	private TextField txtParam;
	@FXML
	private ComboBox<String> cbModes;
	@FXML
	private DatePicker dpFrom;
	@FXML
	private DatePicker dpTo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		bindDisable();
		cbModes.setItems(cbItems);
		cbModes.getSelectionModel().select(0);
	}

	@FXML
	public void addPlant(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/plants/view/AddBasisView.fxml");
		AnchorPane root = DisplayUtil.getAnchorPane(loader);
		AddBasisController controller = DisplayUtil.<AddBasisController>getController(loader);
		DisplayUtil.switchStage(root, 600, 350, true, "Dodavanje biljke u matičnjak", true);
		Basis basis = controller.getBasis();
		if (basis != null) {
			tblBases.getItems().add(new BasisTableItem(basis));
			tblBases.refresh();
		}
	}

	@FXML
	public void deletePlant(ActionEvent event) {
		if (DisplayUtil.showConfirmationDialog("Da li ste sigurni?").equals(ButtonType.YES)) {
			String message = "";
			BasisTableItem bti = tblBases.getSelectionModel().getSelectedItem();
			int index = tblBases.getSelectionModel().getSelectedIndex();
			if (DAOFactory.getInstance().getBasisDAO().delete(bti.getBasis()) > 0) {
				tblBases.getItems().remove(bti);
				if (index >= tblBases.getItems().size()) {
					index--;
				}
				Basis item = null;
				if (index >= 0) {
					item = tblBases.getSelectionModel().getSelectedItem().getBasis();
				}
				displayInfo(item);
				message = "Brisanje uspjesno!";
			} else {
				message = "Greska prilikom brisanja!";
			}
			DisplayUtil.showMessageDialog(message);
		}
	}

	@FXML
	public void editPlant(ActionEvent event) {
		Basis basis = tblBases.getSelectionModel().getSelectedItem().getBasis();
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/plants/view/EditBasisView.fxml");
		AnchorPane root = DisplayUtil.getAnchorPane(loader);
		EditBasisController controller = DisplayUtil.<EditBasisController>getController(loader);
		controller.setBasis(basis);
		DisplayUtil.switchStage(root, 250, 110, false, "AÅ¾uriranje biljke iz matiÄ�njaka", true);
		tblBases.getSelectionModel().getSelectedItem().setBasis(basis);
		tblBases.getSelectionModel().getSelectedItem().setdate(basis.getPlantingDate());
		displayInfo(basis);
		tblBases.refresh();
	}

	@FXML
	public void load(ActionEvent event) {
		populateTable(DAOFactory.getInstance().getBasisDAO().selectAll());
	}

	@FXML
	public void search(ActionEvent event) {
		try {
			String statement = "";
			int index = cbModes.getSelectionModel().getSelectedIndex();
			List<Basis> bases = new ArrayList<Basis>();
			if (index == 0 || index == 1) {
				boolean scientific = (index == 0 ? true : false);
				bases = DAOFactory.getInstance().getBasisDAO().getByName(txtParam.getText(), scientific);
			} else {
				List<Object> variables = new ArrayList<Object>();
				if (dpFrom.getValue() != null) {
					statement += " planting_date >= ?";
					String dateString = dpFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
					variables.add(date);
				}
				if (dpTo.getValue() != null) {
					if (dpFrom.getValue() != null) {
						statement += " and";
					}
					statement += " planting_date <= ?";
					String dateString = dpTo.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
					variables.add(date);
				}
				Object[] vars = new Object[variables.size()];
				vars = variables.toArray(vars);
				bases = DAOFactory.getInstance().getBasisDAO().select(statement, vars);
			}
			ObservableList<BasisTableItem> basisItems = FXCollections.observableArrayList();
			for (Basis b : bases) {
				basisItems.add(new BasisTableItem(b));
			}
			tblBases.setItems(basisItems);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void select(MouseEvent event) {
		BasisTableItem selectedItem = tblBases.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			displayInfo(selectedItem.getBasis());
		}
	}

	private void buildTable() {
		colLatin.setCellValueFactory(new PropertyValueFactory<BasisTableItem, String>("scientificName"));
		colCommon.setCellValueFactory(new PropertyValueFactory<BasisTableItem, String>("knownAs"));
		colBasisDate.setCellValueFactory(new PropertyValueFactory<BasisTableItem, String>("date"));
		colCurrentNum.setCellValueFactory(new PropertyValueFactory<BasisTableItem, Integer>("current"));
		colTotalNum.setCellValueFactory(new PropertyValueFactory<BasisTableItem, Integer>("total"));
		colType.setCellValueFactory(new PropertyValueFactory<BasisTableItem, String>("type"));

		colSeedDate.setCellValueFactory(new PropertyValueFactory<ReproductionCuttingTableItem, String>("date"));
		colProducedNum.setCellValueFactory(new PropertyValueFactory<ReproductionCuttingTableItem, Integer>("produced"));
		colSuccessfulNum
				.setCellValueFactory(new PropertyValueFactory<ReproductionCuttingTableItem, Integer>("takeARoot"));
	}

	private void populateTable(List<Basis> bases) {
		ObservableList<BasisTableItem> tableItems = FXCollections.observableArrayList();
		for (Basis basis : bases) {
			tableItems.add(new BasisTableItem(basis));
		}
		tblBases.setItems(tableItems);
	}

	private void displayInfo(Basis basis) {
		try {
			if (basis != null) {
				imgPhoto.setImage(DisplayUtil.convertFromBlob(basis.getPlant().getImage()));
				List<ReproductionCutting> rcItems = DAOFactory.getInstance().getReproductionCuttingDAO()
						.getByBasisId(basis.getBasisId());
				ObservableList<ReproductionCuttingTableItem> rctItems = FXCollections.observableArrayList();
				for (ReproductionCutting item : rcItems) {
					rctItems.add(new ReproductionCuttingTableItem(item));
				}
				tblSeeds.setItems(rctItems);
			} else {
				imgPhoto.setImage(DisplayUtil.getDefaultImage());
				tblSeeds.setItems(FXCollections.observableArrayList());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void bindDisable() {
		BooleanBinding binding = tblBases.getSelectionModel().selectedItemProperty().isNull();
		btnEditPlant.disableProperty().bind(binding);
		btnDeletePlant.disableProperty().bind(binding);
		txtParam.disableProperty().bind(cbModes.getSelectionModel().selectedIndexProperty().isEqualTo(2));
		dpFrom.disableProperty().bind(cbModes.getSelectionModel().selectedIndexProperty().isEqualTo(0)
				.or(cbModes.getSelectionModel().selectedIndexProperty().isEqualTo(1)));
		dpTo.disableProperty().bind(cbModes.getSelectionModel().selectedIndexProperty().isEqualTo(0)
				.or(cbModes.getSelectionModel().selectedIndexProperty().isEqualTo(1)));

		btnSearch.disableProperty()
				.bind(cbModes.getSelectionModel().selectedIndexProperty().isEqualTo(0)
						.or(cbModes.getSelectionModel().selectedIndexProperty().isEqualTo(1))
						.and(txtParam.textProperty().isEmpty()).or(cbModes.getSelectionModel().selectedIndexProperty()
								.isEqualTo(2).and(dpFrom.valueProperty().isNull().and(dpTo.valueProperty().isNull()))));

	}

	private static ObservableList<String> cbItems = FXCollections.observableArrayList();
	static {
		cbItems.add("Nazivu (latinski)");
		cbItems.add("Nazivu (lokalni)");
		cbItems.add("Datumu");
	}
}
