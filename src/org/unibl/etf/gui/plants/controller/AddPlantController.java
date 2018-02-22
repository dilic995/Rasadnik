package org.unibl.etf.gui.plants.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantContainer;
import org.unibl.etf.dto.PriceHeightRatio;
import org.unibl.etf.dto.PriceHeightRatioComparatorFrom;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.util.OrBinder;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

// TODO vidjeti sta sa slikom kad se izbrise u azuriranju
// TODO svuda gdje se prikazuje slika napraviti da se prikazuje default image ako je slika null

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
	@FXML
	private DatePicker dpDateFrom;
	
	
	public static final int INSERT = 1;
	public static final int UPDATE = 2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			defaultImage = new Image(new FileInputStream("resources/images/add_image.png"));
			imgPhoto.setImage(defaultImage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/*
		btnSave.disableProperty()
				.bind(new OrBinder().bindAll(txtCommonName.textProperty().isEmpty(),
						txtLatinName.textProperty().isEmpty(), taDescription.textProperty().isEmpty(),
						imgPhoto.imageProperty().isEqualTo(defaultImage)));*/
		btnSave.disableProperty().bind(txtCommonName.textProperty().isEmpty().or(txtLatinName.textProperty().isEmpty().
				or(taDescription.textProperty().isEmpty())).or(cbOwned.selectedProperty().and(dpDateFrom.valueProperty().isNull())));
		btnRemoveRatio.disableProperty().bind(lstRatios.getSelectionModel().selectedItemProperty().isNull());
		btnAddRatio.disableProperty().bind(new OrBinder().bindAll(txtFrom.textProperty().isEmpty(),
				txtTo.textProperty().isEmpty(), txtPrice.textProperty().isEmpty()));
		dpDateFrom.disableProperty().bind(cbOwned.selectedProperty().not());
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
				Calendar.getInstance().getTime(), 0, null, false);
		if (!lstRatios.getItems().contains(ratio)) {
			lstRatios.getItems().add(ratio);
			Collections.sort(lstRatios.getItems(), new PriceHeightRatioComparatorFrom());
			newRatios.add(ratio);
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
		if(!newRatios.contains(selected)) {
			deletedRatios.add(selected);
		} else {
			newRatios.remove(selected);
		}
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
		Blob imageBlob = 
				type == INSERT ? (imageFile == null ? null : DisplayUtil.convertToBlob(imageFile)) :
					(imageFile == null ? plant.getImage() : DisplayUtil.convertToBlob(imageFile));
		String latin = txtLatinName.getText();
		latin = latin.substring(0, 1).toUpperCase() + latin.substring(1);
		plant.setIsConifer(rbConifer.isSelected());
		plant.setOwned(cbOwned.isSelected());
		plant.setScientificName(latin);
		plant.setKnownAs(txtCommonName.getText());
		plant.setRatios(lstRatios.getItems());
		plant.setDescription(taDescription.getText());
		plant.setImage(imageBlob);
		String message = "";
		if (type == INSERT) {
			if (DAOFactory.getInstance().getPlantDAO().insert(plant) > 0) {
				List<PriceHeightRatio> ratios = lstRatios.getItems();
				for (PriceHeightRatio ratio : ratios) {
					ratio.setPlant(plant);
					ratio.setPlantId(plant.getPlantId());
					DAOFactory.getInstance().getPriceHeightRatioDAO().insert(ratio);
				}
				container.addPlant(plant);
				message = "Dodavanje uspjesno!";
			} else {
				message = "Doslo je do greske prilikom dodavanja!";
			}
		} else {
			if (DAOFactory.getInstance().getPlantDAO().update(plant) > 0) {
				for(PriceHeightRatio ratio : deletedRatios) {
					DAOFactory.getInstance().getPriceHeightRatioDAO().delete(ratio);
				}
				for(PriceHeightRatio ratio: newRatios) {
					ratio.setPlant(plant);
					ratio.setPlantId(plant.getPlantId());
					DAOFactory.getInstance().getPriceHeightRatioDAO().insert(ratio);
				}
				message = "Azuriranje uspjesno!";
			} else {
				message = "Doslo je do greske prilikom azuriranja";
			}
		}
		if(cbOwned.isSelected() && (type==INSERT || (type==UPDATE && !plant.getOwned()))) {
			try {
				String dateString = dpDateFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				Basis basis = new Basis(null, date, plant.getPlantId(), plant, null, false);
				DAOFactory.getInstance().getBasisDAO().insert(basis);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		DisplayUtil.showMessageDialog(message);
		DisplayUtil.close(btnSave);
	}

	public PlantContainer getContainer() {
		return container;
	}

	public void setContainer(PlantContainer container) {
		this.container = container;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
		rbConifer.setSelected(this.plant.getIsConifer());
		rbDecidous.setSelected(!this.plant.getIsConifer());
		cbOwned.setSelected(this.plant.getOwned());
		txtLatinName.setText(this.plant.getScientificName());
		txtCommonName.setText(this.plant.getKnownAs());
		ObservableList<PriceHeightRatio> ratios = FXCollections.observableArrayList();
		ratios.addAll(this.plant.getRatios());
		lstRatios.setItems(ratios);
		taDescription.setText(this.plant.getDescription());
		imgPhoto.setImage(
				this.plant.getImage() == null ? defaultImage : DisplayUtil.convertFromBlob(this.plant.getImage()));
	}

	public void setType(int type) {
		this.type = type;
	}

	private Image defaultImage;
	private File imageFile = null;
	private PlantContainer container;
	private Plant plant;
	private int type;
	private List<PriceHeightRatio> newRatios = new ArrayList<PriceHeightRatio>();
	private List<PriceHeightRatio> deletedRatios = new ArrayList<PriceHeightRatio>();
}
