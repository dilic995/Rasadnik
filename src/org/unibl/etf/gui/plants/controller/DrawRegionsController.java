package org.unibl.etf.gui.plants.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.ActivityTableItem;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.MaintenancePlan;
import org.unibl.etf.dto.Plan;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.Sale;
import org.unibl.etf.dto.SaleItem;
import org.unibl.etf.dto.TaskTableItem;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class DrawRegionsController extends BaseController {
	@FXML
	private AnchorPane parentPane;
	@FXML
	private Group elements;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnSetSelectTool;
	@FXML
	private Button btnSetPolygonTool;
	@FXML
	private ImageView imgPhoto;
	@FXML
	private Label lblRegion;
	@FXML
	private Label lblName;
	@FXML
	private Label lblPlantsNum;
	@FXML
	private Button btnUndo;
	@FXML
	private Button btnRedo;
	@FXML
	private ListView<Plan> lstActivePlans;
	@FXML
	private ListView<Plan> lstDonePlans;
	@FXML
	private TableView<TaskTableItem> tblTasks;
	@FXML
	private TableColumn<TaskTableItem, String> colActivity;
	@FXML
	private TableColumn<TaskTableItem, String> colDateFrom;
	@FXML
	private TableColumn<TaskTableItem, String> colDateTo;
	@FXML
	private TableColumn<TaskTableItem, Boolean> colDone;
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab tabGeneral;
	@FXML
	private CheckBox cbShowGrid;
	@FXML
	private AnchorPane regionsPane;
	@FXML
	private Button btnCreateSale;
	@FXML
	private TextField txtNumSold;
	@FXML
	private TextField txtHeight;
	@FXML
	private ListView<SaleItem> lstSaleItems;
	@FXML
	private Button btnAddSaleItem;
	@FXML
	private Button btnRemoveSaleItem;
	@FXML
	private Button btnSaveSale;
	@FXML
	private CheckBox cbPaidOff;
	private Plan currentPlan;

	@FXML
	public void createSale(ActionEvent event) {
		setDisabled(false, txtHeight, txtNumSold, btnAddSaleItem, btnRemoveSaleItem, lstSaleItems, cbPaidOff,
				btnSaveSale);
		setDisabled(true, btnSetSelectTool, btnSetPolygonTool);
	}

	@FXML
	public void keyTyped(KeyEvent event) {

	}

	@FXML
	public void addSaleItem(ActionEvent event) {
		if (selectedRegion != null) {
			try {
				Integer num = Integer.parseInt(txtNumSold.getText());
				BigDecimal height = new BigDecimal(txtHeight.getText());
				Plant plant = selectedRegion.getBasis().getPlant();
				BigDecimal price = plant.getPrice(height).multiply(new BigDecimal(num));
				BigDecimal heightMin = plant.getHeightMin(height);
				SaleItem item = new SaleItem(num, plant, null, plant.getPlantId(), null, price, heightMin);
				changedRegions.add(selectedRegion);
				Command command = new AddSaleItemCommand(selectedRegion, item, lstSaleItems.getItems(), this);
				command.execute();
				canvasEditor.push(command);
				canvasEditor.clearRedo();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} else {
		}
	}

	@FXML
	public void removeSaleItem(ActionEvent event) {

	}

	@FXML
	public void saveSale(ActionEvent event) {
		setDisabled(true, txtHeight, txtNumSold, btnAddSaleItem, btnRemoveSaleItem, lstSaleItems, cbPaidOff,
				btnSaveSale);
		setDisabled(false, btnSetSelectTool, btnSetPolygonTool);
		BigDecimal price = BigDecimal.ZERO;
		for (SaleItem item : lstSaleItems.getItems()) {
			price = price.add(item.getPrice());
		}
		if (lstSaleItems.getItems().size() > 0) {
			Sale sale = new Sale(null, Calendar.getInstance().getTime(), price, cbPaidOff.isSelected(), null, 1, false);
			if (DAOFactory.getInstance().getSaleDAO().insert(sale) > 0) {
				for (SaleItem item : lstSaleItems.getItems()) {
					item.setSaleId(sale.getSaleId());
					DAOFactory.getInstance().getSaleItemDAO().insert(item);
				}
			}
			for (Region reg : changedRegions) {
				DAOFactory.getInstance().getRegionDAO().update(reg);
			}
		}
		changedRegions.clear();
		lstSaleItems.getItems().clear();
		lstSaleItems.refresh();
	}

	@FXML
	public void showGrid(ActionEvent event) {
		String styleClass;
		if (cbShowGrid.isSelected()) {
			styleClass = "grid-bck";
		} else {
			styleClass = "white-bck";
		}
		regionsPane.getStyleClass().clear();
		regionsPane.getStyleClass().add(styleClass);
	}

	@FXML
	public void generalTabSelected() {
		if (canvasEditor != null) {
			canvasEditor.invalidate();
			canvasEditor = new SelectTool(regionsMap, outlinesMap, this, undoCommands, redoCommands);
		}
		if (tabGeneral.isSelected()) {
			initRegions();
			setDisabled(false, btnSetSelectTool, btnSetPolygonTool, btnRedo, btnUndo);
		} else {
			setDisabled(true, btnSetSelectTool, btnSetPolygonTool, btnRedo, btnUndo);
		}
	}

	@FXML
	public void selectActive(MouseEvent event) {
		currentPlan = lstActivePlans.getSelectionModel().getSelectedItem();
		if (currentPlan != null) {
			currentPlan.updateState();
			this.canvasEditor.invalidate();
			this.canvasEditor = new EmptyTool(regionsMap, tblTasks, currentPlan);
			// Set<Region> setRegiona = currentPlan.getTasks().keySet();
			// for (Region r : setRegiona) {
			// for (Polygon p : regionsMap.keySet()) {
			// if (r.equals(regionsMap.get(p))) {
			// p.getStyleClass().clear();
			// p.getStyleClass().add(currentPlan.getTasks().get(r).getState().getStyle());
			// break;
			// }
			// }
			// }
		}
	}

	@FXML
	public void selectDone(MouseEvent event) {
		Plan plan = lstDonePlans.getSelectionModel().getSelectedItem();
		if (plan != null) {

		}
	}

	@FXML
	void taskDetails(ActionEvent event) {

	}

	@FXML
	void deletePlan(ActionEvent event) {

	}

	@FXML
	void taskSetDone(ActionEvent event) {
		TaskTableItem task = tblTasks.getSelectionModel().getSelectedItem();
		if (task != null) {
			currentPlan.setDone(task.getTask().getRegion(), task.getTask(), true);
			task.setDone(true);
			task.setDate_to(Calendar.getInstance().getTime());
			DAOFactory.getInstance().getTaskDAO().update(task.getTask());
			tblTasks.refresh();
			this.canvasEditor.invalidate();
			this.canvasEditor = new EmptyTool(regionsMap, tblTasks, currentPlan);
		}
	}

	@FXML
	void taskSetUndone(ActionEvent event) {
		TaskTableItem task = tblTasks.getSelectionModel().getSelectedItem();
		if (task != null) {
			currentPlan.setDone(task.getTask().getRegion(), task.getTask(), false);
			task.setDone(false);
			task.setDate_to(null);
			DAOFactory.getInstance().getTaskDAO().update(task.getTask());
			tblTasks.refresh();
			this.canvasEditor.invalidate();
			this.canvasEditor = new EmptyTool(regionsMap, tblTasks, currentPlan);
		}
	}

	@FXML
	void setPlanDone(ActionEvent event) {
		currentPlan = lstActivePlans.getSelectionModel().getSelectedItem();
		if (currentPlan != null) {
			if (DisplayUtil.showWarningDialog("Da li ste sigurni?").equals(ButtonType.YES)) {
				currentPlan.setActive(false);
				DAOFactory.getInstance().getPlanDAO().update(currentPlan);
				lstActivePlans.getItems().remove(currentPlan);
				lstActivePlans.refresh();
				lstDonePlans.getItems().add(currentPlan);
				lstDonePlans.refresh();
				currentPlan = null;
			}
		}
	}

	@FXML
	public void undo(ActionEvent event) {
		canvasEditor.undo();
	}

	public void redo(ActionEvent event) {
		canvasEditor.redo();
	}

	private CanvasEditor canvasEditor;

	@FXML
	public void setSelectTool(ActionEvent event) {
		canvasEditor.invalidate();
		canvasEditor = new SelectTool(regionsMap, outlinesMap, this, undoCommands, redoCommands);
	}

	@FXML
	public void setPolygonTool(ActionEvent event) {
		canvasEditor.invalidate();
		canvasEditor = new PolygonTool(regionsMap, outlinesMap, elements, undoCommands, redoCommands);
	}

	// Event Listener on AnchorPane[#parentPane].onMouseClicked
	@FXML
	public void click(MouseEvent event) {
		canvasEditor.click(event);
	}

	@FXML
	public void save(Event event) {
	}

	// Event Listener on AnchorPane[#parentPane].onMousePressed
	@FXML
	public void press(MouseEvent event) {
	}

	@FXML
	public void aPressed(MouseEvent event) {
		canvasEditor.startDrawing(event);
	}

	// Event Listener on AnchorPane[#parentPane].onMouseReleased
	@FXML
	public void release(MouseEvent event) {
	}

	@FXML
	public void aReleased(MouseEvent event) {
		canvasEditor.finishDrawing(event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			defaultImage = new Image(new FileInputStream("resources/images/add_image.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		initRegions();

		// TABOVI
		ObservableList<Plan> activePlans = FXCollections.observableArrayList();
		ObservableList<Plan> donePlans = FXCollections.observableArrayList();

		activePlans.addAll(DAOFactory.getInstance().getPlanDAO().getByActive(true));
		donePlans.addAll(DAOFactory.getInstance().getPlanDAO().getByActive(false));

		lstActivePlans.setItems(activePlans);
		lstDonePlans.setItems(donePlans);

		// TABELA
		initializeTable();
		ObservableList<SaleItem> saleItems = FXCollections.observableArrayList();
		lstSaleItems.setItems(saleItems);
		setDisabled(true, txtHeight, txtNumSold, btnAddSaleItem, btnRemoveSaleItem, lstSaleItems, cbPaidOff,
				btnSaveSale);
	}

	private void initializeTable() {
		colActivity.setCellValueFactory(new PropertyValueFactory<TaskTableItem, String>("activity"));
		colDateFrom.setCellValueFactory(new PropertyValueFactory<TaskTableItem, String>("date_from"));
		colDateTo.setCellValueFactory(new PropertyValueFactory<TaskTableItem, String>("date_to"));
		colDone.setCellValueFactory(new PropertyValueFactory<TaskTableItem, Boolean>("done"));
		colDone.setCellFactory(col -> new TableCell<TaskTableItem, Boolean>() {
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? null : item ? "Da" : "Ne");
			}
		});
	}

	public void displayInfo(Region region) {
		selectedRegion = region;
		Basis basis = region.getBasis();
		setValues(
				basis == null ? null
						: (basis.getPlant().getImage() == null ? null
								: DisplayUtil.convertFromBlob(region.getBasis().getPlant().getImage())),
				"REGION " + region.getRegionId(),
				basis == null ? "Nema biljaka"
						: region.getBasis().getPlant().getScientificName() + " ("
								+ region.getBasis().getPlant().getKnownAs() + ")",
				"" + region.getNumberOfPlants());
	}

	public void clear() {
		setValues(null, "", "", "");
		selectedRegion = null;
	}

	private void setValues(Image image, String region, String name, String num) {
		imgPhoto.setImage(image);
		lblRegion.setText(region);
		lblName.setText(name);
		lblPlantsNum.setText(num);
	}

	private void initRegions() {
		elements.getChildren().clear();
		regionsMap = new HashMap<Polygon, Region>();
		outlinesMap = new HashMap<Polygon, Polyline>();
		undoCommands = new Stack<Command>();
		redoCommands = new Stack<Command>();
		List<Region> regions = DAOFactory.getInstance().getRegionDAO().selectAll();
		for (Region r : regions) {
			Polygon p = new Polygon();
			p.getPoints().addAll(r.getCoords());
			Polyline pl = new Polyline();
			pl.getPoints().addAll(r.getCoords());
			pl.getPoints().add(r.getCoords()[0]);
			pl.getPoints().add(r.getCoords()[1]);
			// TODO eliminisati HC
			// p.setFill(new Color(0.9, 0.9, 0.9, 1));
			p.getStyleClass().add("transparent-brown");
			elements.getChildren().add(pl);
			elements.getChildren().add(p);
			regionsMap.put(p, r);
			outlinesMap.put(p, pl);
		}
		canvasEditor = new SelectTool(regionsMap, outlinesMap, this, undoCommands, redoCommands);
	}

	private void setDisabled(boolean disabled, Node... buttons) {
		for (Node button : buttons) {
			button.setDisable(disabled);
		}
	}

	private void setDisplayed(boolean displayed, Node... nodes) {
		for (Node node : nodes) {
			node.setVisible(displayed);
		}
	}

	public void refreshList() {
		lstSaleItems.refresh();
	}

	private Map<Polygon, Region> regionsMap;
	private Map<Polygon, Polyline> outlinesMap;
	private Stack<Command> undoCommands;
	private Stack<Command> redoCommands;
	private Image defaultImage;
	private Region selectedRegion;
	private Map<SaleItem, Region> soldItems = new HashMap<SaleItem, Region>();
	private Set<Region> changedRegions = new HashSet<Region>();
	// TODO observer na evente
}
