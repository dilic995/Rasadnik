package org.unibl.etf.gui.plants.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.dao.implementation.DBUtil;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantContainer;
import org.unibl.etf.dto.PriceHeightRatio;
import org.unibl.etf.dto.PriceHeightRatioComparatorFrom;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.util.OrBinder;
import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.ResourceBundleManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

// TODO svuda gdje se prikazuje slika napraviti da se prikazuje default image ako je slika null

public class AddPlantController extends BaseController {

	public static final int INSERT = 1;
	public static final int UPDATE = 2;
	private static final String messages = "org/unibl/etf/util/messages";

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
	@FXML
	private Label errorLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			defaultImage = DisplayUtil.getDefaultImage();
			imgPhoto.setImage(defaultImage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bindDisable();
	}

	// Event Listener on Button[#btnRemoveImage].onAction
	@FXML
	public void removeImage(ActionEvent event) {
		imgPhoto.setImage(defaultImage);
		imageFile = null;
		if (type == UPDATE && !imageUpdated) {
			imageUpdated = true;
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

	// Event Listener on Button[#btnAddRatio].onAction
	@FXML
	public void addRatio(ActionEvent event) {
		try {
			BigDecimal from = new BigDecimal(txtFrom.getText());
			BigDecimal to = "".equals(txtTo.getText()) ? null : new BigDecimal(txtTo.getText());
			BigDecimal price = new BigDecimal(txtPrice.getText());
			if (from.compareTo(BigDecimal.ZERO) < 0) {
				//errorLabel.setText("Pocetna visina ne moze biti manja od 0");
				errorLabel.setText(ResourceBundleManager.getString("startHeight", messages));
			} else if (to != null && to.compareTo(from) <= 0) {
				//errorLabel.setText("Krajnja visina ne moze biti manja od pocetne");
				errorLabel.setText(ResourceBundleManager.getString("endHeight", messages));
			} else if (price.compareTo(BigDecimal.ZERO) <= 0) {
				//errorLabel.setText("Cijena ne moze biti manja ili jednaka od 0");
				errorLabel.setText(ResourceBundleManager.getString("price", messages));
			} else {
				LocalDate today = LocalDate.now();
				String todayString = today.getYear() + "-" + today.getMonthValue() + "-" + today.getDayOfMonth();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = format.parse(todayString);
				PriceHeightRatio ratio = new PriceHeightRatio(from, to, price, true, date, 0, null, false);
				if (lstRatios.getItems().contains(ratio)) {
					//errorLabel.setText("Unos vec postoji");
					errorLabel.setText(ResourceBundleManager.getString("inputExists", messages));
				} else if (ratio.overlaps(lstRatios.getItems())) {
					//errorLabel.setText("Unos se preklapa sa postojecim");
					errorLabel.setText(ResourceBundleManager.getString("inputOverrides", messages));
				} else {
					lstRatios.getItems().add(ratio);
					Collections.sort(lstRatios.getItems(), new PriceHeightRatioComparatorFrom());
					newRatios.add(ratio);
					errorLabel.setText("");
				}
			}
		} catch (NumberFormatException e) {
			//errorLabel.setText("Pogresan format broja!");
			errorLabel.setText(ResourceBundleManager.getString("numberFormat", messages));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	// Event Listener on Button[#btnRemoveRatio].onAction
	@FXML
	public void removeRatio(ActionEvent event) {
		PriceHeightRatio selected = lstRatios.getSelectionModel().getSelectedItem();
		txtFrom.setText(selected.getHeightMin() + "");
		txtTo.setText((selected.getHeightMax() == null ? "" : selected.getHeightMax() + ""));
		txtPrice.setText(selected.getPrice() + "");
		lstRatios.getItems().remove(lstRatios.getItems().indexOf(selected));
		if (!newRatios.contains(selected)) {
			deletedRatios.add(selected);
		} else {
			newRatios.remove(selected);
		}
	}

	// Event Listener on Button[#btnSave].onAction
	@FXML
	public void save(ActionEvent event) {

		Blob imageBlob = type == INSERT ? (imageFile == null ? null : DisplayUtil.convertToBlob(imageFile))
				: (imageFile == null ? (imageUpdated ? null : plant.getImage()) : DisplayUtil.convertToBlob(imageFile));
		String latin = txtLatinName.getText();
		latin = latin.substring(0, 1).toUpperCase() + latin.substring(1);
		plant.setIsConifer(rbConifer.isSelected());
		if (type == INSERT) {
			plant.setOwned(cbOwned.isSelected());
		}
		plant.setScientificName(latin);
		plant.setKnownAs(txtCommonName.getText());
		plant.setRatios(lstRatios.getItems());
		plant.setDescription(taDescription.getText());
		plant.setImage(imageBlob);
		plant.setDeleted(false);
		String message = "";
		boolean ok = true;
		if (type == INSERT) {
			LocalDate today = LocalDate.now();
			if (cbOwned.isSelected() && dpDateFrom.getValue().compareTo(today) > 0) {
				message = ResourceBundleManager.getString("date", messages);
				ok = false;
			} else {
				if (DAOFactory.getInstance().getPlantDAO().insert(plant) > 0) {
					List<PriceHeightRatio> ratios = lstRatios.getItems();
					for (PriceHeightRatio ratio : ratios) {
						ratio.setPlant(plant);
						ratio.setPlantId(plant.getPlantId());
						DAOFactory.getInstance().getPriceHeightRatioDAO().insert(ratio);
					}
					container.addPlant(plant);
					if (cbOwned.isSelected()) {
						try {
							String dateString = dpDateFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
							Basis basis = new Basis(null, date, plant.getPlantId(), plant, null, false);
							DAOFactory.getInstance().getBasisDAO().insert(basis);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					message =ResourceBundleManager.getString("insertOk", messages);
				} else {
					message = ResourceBundleManager.getString("insertNotOk", messages);
				}
			}
		} else {
			if (DAOFactory.getInstance().getPlantDAO().update(plant) > 0) {
				for (PriceHeightRatio ratio : deletedRatios) {
					DAOFactory.getInstance().getPriceHeightRatioDAO().delete(ratio);
				}
				for (PriceHeightRatio ratio : newRatios) {
					ratio.setPlant(plant);
					ratio.setPlantId(plant.getPlantId());
					if (DAOFactory.getInstance().getPriceHeightRatioDAO().insert(ratio) == DBUtil.DUPLICATE_KEYS) {
						DAOFactory.getInstance().getPriceHeightRatioDAO().update(ratio);
					}
				}
				message = ResourceBundleManager.getString("updateOk", messages);
			} else {
				message = ResourceBundleManager.getString("updateNotOk", messages);
			}
		}
		DisplayUtil.showMessageDialog(message);
		if (ok)
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
		if (type == UPDATE) {
			cbOwned.setSelected(false);
			cbOwned.setDisable(true);
		}
	}

	private void bindDisable() {
		btnSave.disableProperty()
				.bind(txtCommonName.textProperty().isEmpty()
						.or(txtLatinName.textProperty().isEmpty().or(taDescription.textProperty().isEmpty()))
						.or(cbOwned.selectedProperty().and(dpDateFrom.valueProperty().isNull())));
		btnRemoveRatio.disableProperty().bind(lstRatios.getSelectionModel().selectedItemProperty().isNull());
		btnAddRatio.disableProperty()
				.bind(new OrBinder().bindAll(txtFrom.textProperty().isEmpty(), txtPrice.textProperty().isEmpty()));
		dpDateFrom.disableProperty().bind(cbOwned.selectedProperty().not());
	}

	private Image defaultImage;
	private File imageFile = null;
	private PlantContainer container;
	private Plant plant;
	private int type;
	private boolean imageUpdated = false;
	private List<PriceHeightRatio> newRatios = new ArrayList<PriceHeightRatio>();
	private List<PriceHeightRatio> deletedRatios = new ArrayList<PriceHeightRatio>();
}
