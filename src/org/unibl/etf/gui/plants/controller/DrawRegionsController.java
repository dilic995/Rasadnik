package org.unibl.etf.gui.plants.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
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
import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.TaskTableItem;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

	private Plan currentPlan;

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
			if(DisplayUtil.showWarningDialog("Da li ste sigurni?").equals(ButtonType.YES)) {
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

		// TABOVI
		ObservableList<Plan> activePlans = FXCollections.observableArrayList();
		ObservableList<Plan> donePlans = FXCollections.observableArrayList();

		activePlans.addAll(DAOFactory.getInstance().getPlanDAO().getByActive(true));
		donePlans.addAll(DAOFactory.getInstance().getPlanDAO().getByActive(false));

		lstActivePlans.setItems(activePlans);
		lstDonePlans.setItems(donePlans);

		// TABELA
		initializeTable();
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
		Basis basis = region.getBasis();
		setValues(
				basis == null ? null
						: (basis.getPlant().getImage() == null ? null
								: DisplayUtil.convertFromBlob(region.getBasis().getPlant().getImage())),
				"REGION " + region.getRegionId(),
				basis == null ? "Nema biljaka"
						: region.getBasis().getPlant().getScientificName() + " ("
								+ region.getBasis().getPlant().getKnownAs() + ")",
				"Broj biljaka: " + region.getNumberOfPlants());
	}

	public void clear() {
		setValues(null, "", "", "");
	}

	private void setValues(Image image, String region, String name, String num) {
		imgPhoto.setImage(image);
		lblRegion.setText(region);
		lblName.setText(name);
		lblPlantsNum.setText(num);
	}

	private Map<Polygon, Region> regionsMap;
	private Map<Polygon, Polyline> outlinesMap;
	private Stack<Command> undoCommands;
	private Stack<Command> redoCommands;
	private Image defaultImage;
	// TODO observer na evente
}
