package org.unibl.etf.gui.plants.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AddBasisController extends BaseController {
	@FXML
	private ListView<Plant> lstPlants;
	@FXML
	private ImageView imgPhoto;
	@FXML
	private DatePicker dpDate;
	@FXML
	private Button btnSave;
	@FXML
	private Label lblError;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Plant> items = FXCollections.observableArrayList();
		items.addAll(DAOFactory.getInstance().getPlantDAO().selectAll());
		Collections.sort(items, new Comparator<Plant>() {
			@Override
			public int compare(Plant o1, Plant o2) {
				return o1.getScientificName().compareTo(o2.getScientificName());
			}

		});
		lstPlants.setItems(items);
		btnSave.disableProperty().bind(
				lstPlants.getSelectionModel().selectedItemProperty().isNull().or(dpDate.valueProperty().isNull()));
	}

	// Event Listener on ListView[#lstPlants].onMouseClicked
	@FXML
	public void setImage(MouseEvent event) {
		Plant plant = lstPlants.getSelectionModel().getSelectedItem();
		if (plant != null) {
			imgPhoto.setImage(DisplayUtil.convertFromBlob(plant.getImage()));
		}
	}

	// Event Listener on Button[#btnSave].onAction
	@FXML
	public void save(ActionEvent event) {
		boolean ok = true;
		try {
			if (dpDate.getValue().compareTo(LocalDate.now()) <= 0) {
				Plant plant = lstPlants.getSelectionModel().getSelectedItem();
				String dateString = dpDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				basis = new Basis(null, date, plant.getPlantId(), plant, null, false);
				String message = "Greska prilikom dodavanja";
				if (DAOFactory.getInstance().getBasisDAO().insert(basis) > 0) {
					message = "Dodavanje uspjesno";
				}
				DisplayUtil.showMessageDialog(message);
			} else {
				lblError.setText("Datum ne moze biti poslije danasnjeg");
				ok = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (ok) {
			DisplayUtil.close(btnSave);
		}
	}

	public Basis getBasis() {
		return this.basis;
	}

	private Basis basis = null;
}
