package org.unibl.etf.gui.plants.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.BasisTableItem;
import org.unibl.etf.dto.ReproductionCutting;
import org.unibl.etf.dto.ReproductionCuttingTableItem;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;

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
	private DatePicker dpDate;
	@FXML
	private TextField txtProducedNum;
	@FXML
	private TextField txtSuccessfulNum;
	@FXML
	private Button btnAddRep;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		populateTable();
		bindDisable();
	}

	@FXML
	public void select(MouseEvent event) {
		BasisTableItem selectedItem = tblBases.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			imgPhoto.setImage(DisplayUtil.convertFromBlob(selectedItem.getBasis().getPlant().getImage()));
			List<ReproductionCutting> rcItems = DAOFactory.getInstance().getReproductionCuttingDAO()
					.getByBasisId(selectedItem.getBasis().getBasisId());
			ObservableList<ReproductionCuttingTableItem> rctItems = FXCollections.observableArrayList();
			for (ReproductionCutting item : rcItems) {
				rctItems.add(new ReproductionCuttingTableItem(item));
			}
			tblSeeds.setItems(rctItems);
		}
	}

	@FXML
	public void addRep(ActionEvent event) {
		// TODO promjena na bazi - ne mora se unijeti koliko se primilo
		// TODO provjera za datum i primilo < posijano
		// TODO dodavanje na isti dan
		// TODO veza regiona i sijanja
		try {
			Basis basis = tblBases.getSelectionModel().getSelectedItem().getBasis();
			String dateString = dpDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			Integer produced = Integer.parseInt(txtProducedNum.getText());
			Integer takeARoot = Integer.parseInt(txtSuccessfulNum.getText());
			ReproductionCutting cutting = new ReproductionCutting(basis, date, produced, takeARoot, basis.getBasisId(), false);
			if (DAOFactory.getInstance().getReproductionCuttingDAO().insert(cutting) > 0) {
				tblSeeds.getItems().add(new ReproductionCuttingTableItem(cutting));
				tblBases.refresh();
				DisplayUtil.showMessageDialog("Dodavanje uspjesno!");
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	private List<Basis> bases;

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

	private void populateTable() {
		List<Basis> bases = DAOFactory.getInstance().getBasisDAO().selectAll();
		ObservableList<BasisTableItem> tableItems = FXCollections.observableArrayList();
		for (Basis basis : bases) {
			tableItems.add(new BasisTableItem(basis));
		}
		tblBases.setItems(tableItems);
	}

	private void bindDisable() {
		btnAddRep.disableProperty().bind(tblBases.getSelectionModel().selectedItemProperty().isNull()
				.or(dpDate.valueProperty().isNull().or(txtProducedNum.textProperty().isEmpty())));
	}
}
