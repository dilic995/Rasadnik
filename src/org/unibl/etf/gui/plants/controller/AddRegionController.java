package org.unibl.etf.gui.plants.controller;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.ReproductionCutting;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddRegionController extends BaseController {
	@FXML
	private ComboBox<Basis> cbBasis;
	@FXML
	private TextField txtPlantsNum;
	@FXML
	private Button btnSave;
	@FXML
	private TextField txtSuccessful;
	@FXML
	private DatePicker dpDate;
	@FXML
	private Label lblError;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Basis> bases = DAOFactory.getInstance().getBasisDAO().selectAll();
		ObservableList<Basis> cbItems = FXCollections.observableArrayList();
		cbItems.addAll(bases);
		cbBasis.setItems(cbItems);
		cbBasis.getSelectionModel().select(0);
		bindDisable();
	}

	@FXML
	public void save(ActionEvent event) {
		boolean ok = true;
		try {
			Integer numberOfPlants = Integer.parseInt(txtPlantsNum.getText());
			Integer successFull = Integer.parseInt(txtSuccessful.getText());
			Date date = DisplayUtil.convert(dpDate.getValue());
			if (numberOfPlants <= 0 || successFull <= 0) {
				ok = false;
				lblError.setText("Broj biljaka ne može biti manji od 1");
			} else if (successFull > numberOfPlants) {
				ok = false;
				lblError.setText("Broj uspjelih biljaka ne može biti veći od ukupnog broja");
			} else if (dpDate.getValue().compareTo(LocalDate.now()) > 0) {
				lblError.setText("Datum ne može biti poslije današnjeg");
				ok = false;
			} else {
				cutting.setBasis(cbBasis.getSelectionModel().getSelectedItem());
				cutting.setDate(date);
				cutting.setProduces(numberOfPlants);
				cutting.setTakeARoot(successFull);
				cutting.setBasisId(cbBasis.getSelectionModel().getSelectedItem().getBasisId());
				cutting.setDeleted(false);
				region.setBasis(cbBasis.getSelectionModel().getSelectedItem());
				region.setBasisId(cbBasis.getSelectionModel().getSelectedItem().getBasisId());
				region.setNumberOfPlants(cutting.getTakeARoot());
			}
		} catch (NumberFormatException ex) {
			ok = false;
			lblError.setText("Format pogrešan");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (ok) {
			DisplayUtil.close(btnSave);
		}
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	private void bindDisable() {
		btnSave.disableProperty().bind(txtPlantsNum.textProperty().isEmpty()
				.or(txtSuccessful.textProperty().isEmpty().or(dpDate.valueProperty().isNull())));
	}

	public ReproductionCutting getCuttings() {
		return cutting;
	}

	public void setCutting(ReproductionCutting cutting) {
		this.cutting = cutting;
	}

	private Region region;
	private ReproductionCutting cutting;
}
