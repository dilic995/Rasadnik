package org.unibl.etf.gui.plants.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantContainer;
import org.unibl.etf.dto.PriceHeightRatio;
import org.unibl.etf.dto.PriceHeightRatioComparatorFrom;
import org.unibl.etf.dto.PriceHeightRatioTableItem;
import org.unibl.etf.gui.util.CSSUtil;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.util.ResourceBundleManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CatalogueController extends PlantBrowserController {
	@FXML
	private TreeView<String> treePlants;
	@FXML
	private Button btnPrevious;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnRemove;
	@FXML
	private ImageView imgPhoto;
	@FXML
	private Label lblLatinName;
	@FXML
	private Label lblCommonName;
	@FXML
	private TableView<PriceHeightRatioTableItem> tblRatio;
	@FXML
	private TableColumn<PriceHeightRatioTableItem, String> colMinHeight;
	@FXML
	private TableColumn<PriceHeightRatioTableItem, String> colMaxHeight;
	@FXML
	private TableColumn<PriceHeightRatioTableItem, Double> colPrice;
	@FXML
	private Circle ownedIndicator;
	@FXML
	private Label lblOwned;
	@FXML
	private TextFlow taDescription;
	@FXML
	private Label lblTotal;
	@FXML
	private Button btnExport;
	@FXML
	private Button btnEditPlant;
	@FXML
	private Label lblCanSell;
	@FXML
	private Circle canSellIndicator;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		container.setPlants(DAOFactory.getInstance().getPlantDAO().selectAll());
		buildTable();
		populateTreeView();
		update();
	}

	@FXML
	public void editPlant(ActionEvent event) {
		buildAll = true;
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/plants/view/AddPlantView.fxml");
		AnchorPane root = DisplayUtil.getAnchorPane(loader);
		AddPlantController controller = DisplayUtil.<AddPlantController>getController(loader);
		controller.setContainer(container);
		controller.setPlant(container.current());
		controller.setType(AddPlantController.UPDATE);
		DisplayUtil.switchStage(root, 650, 600, true, "Dodavanje biljke", true);
		update();
	}
	
	// Event Listener on Button[#btnPrevious].onAction
	@FXML
	public void showPreviousPlant(ActionEvent event) {
		container.previous();
	}

	// Event Listener on Button[#btnNext].onAction
	@FXML
	public void showNextPlant(ActionEvent event) {
		container.next();
	}

	// Event Listener on Button[#btnAdd].onAction
	@FXML
	public void addPlant(ActionEvent event) {
		buildAll = true;
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/plants/view/AddPlantView.fxml");
		AnchorPane root = DisplayUtil.getAnchorPane(loader);
		AddPlantController controller = DisplayUtil.<AddPlantController>getController(loader);
		controller.setContainer(container);
		controller.setPlant(new Plant(null, "", "", "", null, true, false, new ArrayList<PriceHeightRatio>(), false));
		controller.setType(AddPlantController.INSERT);
		DisplayUtil.switchStage(root, 650, 600, true, "Dodavanje biljke", true);
	}

	// Event Listener on Button[#btnRemove].onAction
	@FXML
	public void removePlant(ActionEvent event) {
		String prompt = ResourceBundleManager.getString("confirmQuestion");
		if (DisplayUtil.showConfirmationDialog(prompt) == ButtonType.YES) {
			String message = "";
			if(DAOFactory.getInstance().getPlantDAO().delete(container.current()) > 0) {
				buildAll = true;
				//message = "Brisanje uspjesno!";
				message =  ResourceBundleManager.getString("deleteOk");
				container.remove(container.current());
			} else {
				//message = "Greska prilikom brisanja!";
				 ResourceBundleManager.getString("deleteNotOk");
			}
			DisplayUtil.showMessageDialog(message);
		}
	}

	@FXML
	public void exportToPricelist(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/plants/view/PricelistView.fxml");
		AnchorPane root = DisplayUtil.getAnchorPane(loader);
		PricelistController controller = DisplayUtil.<PricelistController>getController(loader);
		PlantContainer newContainer = new PlantContainer(
				container.getPlants().stream().filter(x -> x.getOwned() == true).collect(Collectors.toList()));
		controller.setContainer(newContainer);
		DisplayUtil.switchStage(root, 800, 500, true, "Pregled cjenovnika", true);
	}

	// Event Listener on Circle[#ownedIndicator].onMouseClicked
	@FXML
	public void setOwned(MouseEvent event) {
		
		if(!container.current().getOwned()) {
			//String message = "Biljka ne postoji u maticnjaku. Zelite li da je dodate?";
			String message =  ResourceBundleManager.getString("addPlantQuestion");;
			if (DisplayUtil.showConfirmationDialog(message) == ButtonType.YES) {
				container.current().setOwned(true);
				DAOFactory.getInstance().getPlantDAO().update(container.current());
				Basis basis = new Basis(null, Calendar.getInstance().getTime(), container.current().getPlantId(),
						container.current(), null, false);
				if (DAOFactory.getInstance().getBasisDAO().insert(basis) > 0) {
				//	DisplayUtil.showMessageDialog("Dodavanje u maticnjak uspjesno");
					DisplayUtil.showMessageDialog(ResourceBundleManager.getString("insertOk"));
				} else {
				//	DisplayUtil.showMessageDialog("Dodavanje u maticnjak neuspjesno");
					DisplayUtil.showMessageDialog(ResourceBundleManager.getString("insertNotOk"));
				}
				displaySelectedItem();
			}
		}
	}

	@FXML
	public void getSelectedPlant(MouseEvent event) {
		TreeItem<String> item = treePlants.getSelectionModel().getSelectedItem();
		if (item != null && item.getParent() != null && !item.getParent().getValue().equals("Biljke")) {
			for (Plant plant : container.getPlants()) {
				if (item.getValue().equals(plant.getScientificName())) {
					container.setCurrent(container.getPlants().indexOf(plant));
					break;
				}
			}
		}
	}
	
	@Override
	public void update() {
		if (buildAll) {
			populateTreeView();
			buildAll = false;
		}
		displaySelectedItem();
		setSelected();
	}
	
	private void buildTable() {
		colMinHeight.setCellValueFactory(new PropertyValueFactory<PriceHeightRatioTableItem, String>("minHeight"));
		colMaxHeight.setCellValueFactory(new PropertyValueFactory<PriceHeightRatioTableItem, String>("maxHeight"));
		colPrice.setCellValueFactory(new PropertyValueFactory<PriceHeightRatioTableItem, Double>("price"));
	}

	private void setPlantOwned(Boolean condition) {
		if (condition) {
			CSSUtil.setNewStyleClass(ownedIndicator, "green-fill");
			CSSUtil.setNewStyleClass(lblOwned, "plantOwned");
			lblOwned.setText("U matičnjaku");
		} else {
			CSSUtil.setNewStyleClass(ownedIndicator, "red-fill");
			CSSUtil.setNewStyleClass(lblOwned, "plant-not-owned");
			lblOwned.setText("Nije u matičnjaku");
		}
	}
	
	private void displaySelectedItem() {
		Plant plant = container.current();
		if (plant != null) {
			lblLatinName.setText(plant.getScientificName());
			lblCommonName.setText(plant.getKnownAs());
			taDescription.getChildren().clear();
			taDescription.getChildren().add(new Text(plant.getDescription()));
			setPlantOwned(plant.getOwned());
			imgPhoto.setImage(DisplayUtil.convertFromBlob(plant.getImage()));
			ObservableList<PriceHeightRatioTableItem> ratios = FXCollections.observableArrayList();
			List<PriceHeightRatio> ratiosFromPlant = plant.getRatios();
			Collections.sort(ratiosFromPlant, new PriceHeightRatioComparatorFrom());
			for (PriceHeightRatio ratio : ratiosFromPlant) {
				ratios.add(new PriceHeightRatioTableItem(ratio));
			}
			tblRatio.setItems(ratios);
			int count = DAOFactory.getInstance().getPlantDAO().getNumInRegions(plant);
			if(count > 0) {
				CSSUtil.setNewStyleClass(canSellIndicator, "green-fill");
				CSSUtil.setNewStyleClass(lblCanSell, "plantOwned");
				lblCanSell.setText("Na stanju: " + count);
			} else {
				CSSUtil.setNewStyleClass(canSellIndicator, "red-fill");
				CSSUtil.setNewStyleClass(lblCanSell, "plant-not-owned");
				lblCanSell.setText("Nema na stanju");
			}
		}
	}

	private void setSelected() {
		Plant plant = container.current();
		if (plant != null) {
			Character c = plant.getScientificName().toCharArray()[0];
			int index = c - 'A';
			TreeItem<String> item = treePlants.getRoot().getChildren().get(index);
			item.setExpanded(true);
			for (TreeItem<String> it : item.getChildren()) {
				if (it.getValue().equals(plant.getScientificName())) {
					treePlants.getSelectionModel().select(it);
					break;
				}
			}
		}
	}

	private void populateTreeView() {
		TreeItem<String> rootItem = new TreeItem<String>("Biljke");
		rootItem.setExpanded(true);
		ObservableList<TreeItem<String>> rootItems = FXCollections.observableArrayList();
		char c = 'A';
		for (; c <= 'Z'; c++) {
			TreeItem<String> item = new TreeItem<String>(String.valueOf(c));
			rootItems.add(item);
		}
		rootItem.getChildren().addAll(rootItems);
		for (Plant plant : container.getPlants()) {
			String firstLetter = plant.getScientificName().substring(0, 1);
			for (TreeItem<String> item : rootItems) {
				if (item.getValue().equals(firstLetter)) {
					item.getChildren().add(new TreeItem<String>(plant.getScientificName()));
				}
			}
		}
		treePlants.setRoot(rootItem);
		lblTotal.setText(DAOFactory.getInstance().getPlantDAO().selectCount() + "");
	}
}
