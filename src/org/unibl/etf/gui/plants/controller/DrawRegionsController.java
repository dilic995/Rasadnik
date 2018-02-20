package org.unibl.etf.gui.plants.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.MaintenancePlanItem;
import org.unibl.etf.dto.Plan;
import org.unibl.etf.dto.PlantMaintanceActivity;
import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.Task;
import org.unibl.etf.dto.TaskTableItem;
import org.unibl.etf.gui.task.controller.TaskDetailsViewController;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	private TextField txtPlanName;

	@FXML
	private DatePicker dpPlanDateFrom;

	@FXML
	private DatePicker dpPlanDateTo;

	@FXML
	private DatePicker dpTaskDateFrom;

	@FXML
	private ComboBox<PlantMaintanceActivity> cbActivity;

	@FXML
	private Label lblChosedRegion;

	@FXML
	private ListView<Task> lstChosedTasks;

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
		TaskTableItem item = tblTasks.getSelectionModel().getSelectedItem();
		if (item != null) {
			try {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader(
						getClass().getClassLoader().getResource("org/unibl/etf/gui/task/view/TaskDetailsView.fxml"));
				AnchorPane root;
				root = (AnchorPane) loader.load();
				TaskDetailsViewController control = loader.<TaskDetailsViewController>getController();
				control.setTask(item.getTask());
				control.initializeView();
				control.setPrimaryStage(stage);
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("Detalji");
//				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//					@Override
//					public void handle(WindowEvent event) {
//						event.consume();
//					}
//				});
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	void deletePlan(ActionEvent event) {
		if (DisplayUtil.showConfirmationDialog("Da li ste sigurni?").equals(ButtonType.YES)) {
			currentPlan.setDeleted(true);
			DAOFactory.getInstance().getPlanDAO().update(currentPlan);
			for (MaintenancePlanItem item : currentPlan.getTasks().values()) {
				for (Task t : item.getPlannedTasks()) {
					t.setDeleted(true);
					DAOFactory.getInstance().getTaskDAO().update(t);
				}
			}
			lstActivePlans.getItems().remove(currentPlan);
			currentPlan = lstActivePlans.getSelectionModel().getSelectedItem();
		}
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

		// COMBOBOX
		ObservableList<PlantMaintanceActivity> activities = FXCollections
				.observableArrayList(DAOFactory.getInstance().getPlantMaintanceActivityDAO().selectAll());
		cbActivity.setItems(activities);

		// DODAVANJE TASKA

	}

	private Map<Region, ObservableList<Task>> planedTasks = new HashMap<Region, ObservableList<Task>>();

	public void updateRegionLabel() {
		if (canvasEditor instanceof SelectTool) {
			Region region = ((SelectTool) canvasEditor).getSelectedRegion();
			lblChosedRegion.setText(region == null ? "" : "REGION " + region.getRegionId());
			ObservableList<Task> list;
			if (planedTasks.get(region) != null)
				list = planedTasks.get(region);
			else
				list = FXCollections.observableArrayList();
			lstChosedTasks.setItems(list);
			lstChosedTasks.refresh();
		}
	}

	@FXML
	void addPlan(ActionEvent event) {
		if (txtPlanName.getText() == "" || dpPlanDateFrom.getValue() == null || dpPlanDateTo.getValue() == null) {
			DisplayUtil.showErrorDialog("Popunite sva polja!");
			return;
		}
		boolean fleg = false;
		for (ObservableList<Task> tasks : planedTasks.values()) {
			if (tasks != null && tasks.size() > 0) {
				fleg = true;
				break;
			}
		}
		if (!fleg) {
			DisplayUtil.showErrorDialog("Mora postojati bar jedna aktivnost koja je ukljucena u plan!");
			return;
		}
		if (dpPlanDateFrom.getValue().isAfter(dpPlanDateTo.getValue())
				|| dpPlanDateFrom.getValue().isBefore(LocalDate.now())) {
			DisplayUtil.showErrorDialog(
					"Datumi nisu dobro uneseni! Datum pocetka mora biti prije datuma kraja. Isto tako datum pocetka mora biti veci od danasnjeg datuma.");
			return;
		}
		String planName = txtPlanName.getText();
		Date dateFrom = Date.from(dpPlanDateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date dateTo = Date.from(dpPlanDateTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

		Plan plan = new Plan(null, planName, dateFrom, dateTo, true, false, null);
		DAOFactory.getInstance().getPlanDAO().insert(plan);

		for (ObservableList<Task> tasks : planedTasks.values()) {
			if (tasks != null) {
				for (Task t : tasks) {
					t.setPlanId(plan.getPlanId());
					t.setPlan(plan);
					DAOFactory.getInstance().getTaskDAO().insert(t);
				}
			}
		}

		txtPlanName.clear();
		dpPlanDateFrom.setValue(null);
		dpPlanDateTo.setValue(null);
		cbActivity.getSelectionModel().clearSelection();
		dpTaskDateFrom.setValue(null);
		planedTasks = new HashMap<Region, ObservableList<Task>>();
	}

	@FXML
	void addTaskInPlan(ActionEvent event) {
		if (lblChosedRegion.getText() == "" || cbActivity.getSelectionModel().getSelectedItem() == null
				|| dpTaskDateFrom.getValue() == null) {
			DisplayUtil.showErrorDialog("Odaberite region i popunite polja!");
			return;
		}
		if (canvasEditor instanceof SelectTool) {
			Region region = ((SelectTool) canvasEditor).getSelectedRegion();
			PlantMaintanceActivity activity = cbActivity.getSelectionModel().getSelectedItem();
			Date date = Date.from(dpTaskDateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Task task = new Task(0, 0, null, date, null, false, region.getRegionId(), region, false,
					activity.getPlantMaintanceActivityId(), activity);
			if (!planedTasks.keySet().contains(region)) {
				planedTasks.put(region, FXCollections.observableArrayList());
			}
			planedTasks.get(region).add(task);
			lstChosedTasks.setItems(planedTasks.get(region));
			lstChosedTasks.refresh();
		}
	}

	@FXML
	void deleteTaskFromPlan(ActionEvent event) {
		List<Integer> task = lstChosedTasks.getSelectionModel().getSelectedIndices();
		if (task != null) {
			for (int index : task) {
				lstChosedTasks.getItems().remove(index);
			}
		}
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
