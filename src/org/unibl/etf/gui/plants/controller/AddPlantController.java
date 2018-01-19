package org.unibl.etf.gui.plants.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ToggleGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import org.unibl.etf.dto.HeightPriceRatio;
import org.unibl.etf.dto.HeightPriceRatioMinHeightComparator;
import org.unibl.etf.dto.MyPlant;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.util.OrBinder;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.RadioButton;

import javafx.scene.control.CheckBox;

public class AddPlantController extends BaseController {
	@FXML
	private ImageView imgPhoto;
	@FXML
	private TextField txtLatinName;
	@FXML
	private TextField txtCommonName;
	@FXML
	private TextField txtFrom;
	@FXML
	private TextField txtTo;
	@FXML
	private TextField txtPrice;
	@FXML
	private Button btnRemoveImage;
	@FXML
	private RadioButton rbConifer;
	@FXML
	private ToggleGroup grpType;
	@FXML
	private RadioButton rbDecidous;
	@FXML
	private CheckBox cbOwned;
	@FXML
	private Button btnAddRatio;
	@FXML
	private ListView<HeightPriceRatio> lstRatios;
	@FXML
	private Button btnRemoveRatio;
	@FXML
	private Button btnSave;
	@FXML
	private TextArea taDescription;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			defaultImage = new Image(new FileInputStream("resources/images/plus.png"));
			imgPhoto.setImage(defaultImage);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnSave.disableProperty().bind(new OrBinder().bindAll(txtCommonName.textProperty().isEmpty(),
				txtLatinName.textProperty().isEmpty(), taDescription.textProperty().isEmpty(), imgPhoto.imageProperty().isEqualTo(defaultImage)));
		btnRemoveRatio.disableProperty().bind(lstRatios.getSelectionModel().selectedItemProperty().isNull());
		btnAddRatio.disableProperty().bind(new OrBinder().bindAll(txtFrom.textProperty().isEmpty(),
				txtTo.textProperty().isEmpty(), txtPrice.textProperty().isEmpty()));
	}

	// Event Listener on Button[#btnRemoveImage].onAction
	@FXML
	public void removeImage(ActionEvent event) {
		imgPhoto.setImage(defaultImage);
	}

	// Event Listener on Button[#btnAddRatio].onAction
	@FXML
	public void addRatio(ActionEvent event) {
		// dodati provjeravanje opsega
		HeightPriceRatio ratio = new HeightPriceRatio(Integer.parseInt(txtFrom.getText()),
				Integer.parseInt(txtTo.getText()), Double.parseDouble(txtPrice.getText()));
		if (!lstRatios.getItems().contains(ratio)) {
			lstRatios.getItems().add(ratio);
			Collections.sort(lstRatios.getItems(), new HeightPriceRatioMinHeightComparator());
		}
	}

	// Event Listener on Button[#btnRemoveRatio].onAction
	@FXML
	public void removeRatio(ActionEvent event) {
		HeightPriceRatio selected = lstRatios.getSelectionModel().getSelectedItem();
		txtFrom.setText(selected.getMinHeight() + "");
		txtTo.setText(selected.getMaxHeight() + "");
		txtPrice.setText(selected.getPrice() + "");
		lstRatios.getItems().remove(lstRatios.getItems().indexOf(selected));
	}

	@FXML
	public void addImage(MouseEvent event) {
		File file = DisplayUtil.configureFileChooser("Izaberite sliku", DisplayUtil.IMAGE_EXTENSIONS)
				.showOpenDialog(primaryStage);
		if (file != null) {
			try {
				imgPhoto.setImage(new Image(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	// Event Listener on Button[#btnSave].onAction
	@FXML
	public void save(ActionEvent event) {
		MyPlant plant = new MyPlant(1, txtLatinName.getText(), txtCommonName.getText(), taDescription.getText(),
				cbOwned.isSelected(), imgPhoto.getImage(), rbConifer.isSelected(), lstRatios.getItems());
		System.out.println(plant.getLatinName() + " " + plant.getCommonName() + " " + plant.getImage());
	}
	private Image defaultImage;
}
