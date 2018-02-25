package org.unibl.etf.gui.plants.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.event.ActionEvent;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EditBasisController extends BaseController {
	@FXML
	private DatePicker dpDate;
	@FXML
	private Button btnEditPlant;
	@FXML
	private Button btnSave;
	@FXML
	private Label lblError;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindDisable();
	}

	// Event Listener on Button[#btnEditPlant].onAction
	@FXML
	public void editPlant(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/plants/view/AddPlantView.fxml");
		AnchorPane root = DisplayUtil.getAnchorPane(loader);
		AddPlantController controller = DisplayUtil.<AddPlantController>getController(loader);
		controller.setPlant(basis.getPlant());
		controller.setType(AddPlantController.UPDATE);
		DisplayUtil.switchStage(root, 650, 600, true, "Dodavanje biljke", true);
	}

	// Event Listener on Button[#btnSave].onAction
	@FXML
	public void save(ActionEvent event) {
		String message = "Došlo je do greške prilikom ažuriranja.";
		boolean ok = true;
		try {
			if (dpDate.getValue().compareTo(LocalDate.now()) <= 0) {
				String dateString = dpDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				basis.setPlantingDate(date);
				if (DAOFactory.getInstance().getBasisDAO().update(basis) > 0) {
					message = "Ažuriranje uspješno.";
				}
			} else {
				lblError.setText("Datum ne može biti poslije današnjeg");
				ok = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (ok) {
			DisplayUtil.showMessageDialog(message);
			DisplayUtil.close(btnSave);
		}
	}

	public Basis getBasis() {
		return basis;
	}

	public void setBasis(Basis basis) {
		this.basis = basis;
	}

	private void bindDisable() {
		btnSave.disableProperty().bind(dpDate.valueProperty().isNull());
	}

	private Basis basis;

}
