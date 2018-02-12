package org.unibl.etf.gui.plants.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantContainer;
import org.unibl.etf.dto.PriceHeightRatio;
import org.unibl.etf.dto.PriceHeightRatioComparatorFrom;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.util.OrBinder;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
	private ListView<PriceHeightRatio> lstRatios;
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
			e.printStackTrace();
		}
		btnSave.disableProperty()
				.bind(new OrBinder().bindAll(txtCommonName.textProperty().isEmpty(),
						txtLatinName.textProperty().isEmpty(), taDescription.textProperty().isEmpty(),
						imgPhoto.imageProperty().isEqualTo(defaultImage)));
		btnRemoveRatio.disableProperty().bind(lstRatios.getSelectionModel().selectedItemProperty().isNull());
		btnAddRatio.disableProperty().bind(new OrBinder().bindAll(txtFrom.textProperty().isEmpty(),
				txtTo.textProperty().isEmpty(), txtPrice.textProperty().isEmpty()));
	}

	// Event Listener on Button[#btnRemoveImage].onAction
	@FXML
	public void removeImage(ActionEvent event) {
		imgPhoto.setImage(defaultImage);
		imageFile = null;
	}

	// Event Listener on Button[#btnAddRatio].onAction
	@FXML
	public void addRatio(ActionEvent event) {
		// dodati provjeravanje opsega
		PriceHeightRatio ratio = new PriceHeightRatio(new BigDecimal(txtFrom.getText()),
				new BigDecimal(txtTo.getText()), new BigDecimal(txtPrice.getText()), true,
				Calendar.getInstance().getTime(), 0, null);
		if (!lstRatios.getItems().contains(ratio)) {
			lstRatios.getItems().add(ratio);
			Collections.sort(lstRatios.getItems(), new PriceHeightRatioComparatorFrom());
		}
	}

	// Event Listener on Button[#btnRemoveRatio].onAction
	@FXML
	public void removeRatio(ActionEvent event) {
		PriceHeightRatio selected = lstRatios.getSelectionModel().getSelectedItem();
		txtFrom.setText(selected.getHeightMin() + "");
		txtTo.setText(selected.getHeightMax() + "");
		txtPrice.setText(selected.getPrice() + "");
		lstRatios.getItems().remove(lstRatios.getItems().indexOf(selected));
	}

	@FXML
	public void addImage(MouseEvent event) {
		File file = DisplayUtil.configureFileChooser("Izaberite sliku", DisplayUtil.IMAGE_EXTENSIONS)
				.showOpenDialog(primaryStage);
		if (file != null) {
			try {
				imageFile = file;
				imgPhoto.setImage(new Image(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	// Event Listener on Button[#btnSave].onAction
	@FXML
	public void save(ActionEvent event) {
		Blob imageBlob = DisplayUtil.convertToBlob(imageFile);
		String latin = txtLatinName.getText();
		latin = latin.substring(0, 1).toUpperCase() + latin.substring(1);
		Plant plant = new Plant(null, latin, txtCommonName.getText(), taDescription.getText(),
				imageBlob, rbConifer.isSelected(), cbOwned.isSelected(), lstRatios.getItems());
		if(DAOFactory.getInstance().getPlantDAO().insert(plant) > 0) {
			List<PriceHeightRatio> ratios = lstRatios.getItems();
			for(PriceHeightRatio ratio : ratios) {
				ratio.setPlant(plant);
				ratio.setPlantId(plant.getPlantId());
				DAOFactory.getInstance().getPriceHeightRatioDAO().insert(ratio);
			}
			container.addPlant(plant);
		}
		DisplayUtil.close(btnSave);
	}

	public PlantContainer getContainer() {
		return container;
	}

	public void setContainer(PlantContainer container) {
		this.container = container;
	}

	private Image defaultImage;
	private File imageFile = null;
	private PlantContainer container;
}
