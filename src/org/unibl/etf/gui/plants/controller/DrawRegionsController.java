package org.unibl.etf.gui.plants.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Basis;
import org.unibl.etf.dto.EmployeeHasTask;
import org.unibl.etf.dto.MaintenancePlanItem;
import org.unibl.etf.dto.Plan;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.dto.PlantMaintanceActivity;
import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.ReproductionCutting;
import org.unibl.etf.dto.Sale;
import org.unibl.etf.dto.SaleItem;
import org.unibl.etf.dto.Task;
import org.unibl.etf.dto.TaskTableItem;
import org.unibl.etf.gui.sales.controller.ChooseCustomerController;
import org.unibl.etf.gui.task.controller.TaskDetailsViewController;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.ErrorLogger;
import org.unibl.etf.util.ResourceBundleManager;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
	private TabPane tabPane;
	@FXML
	private Tab tabGeneral;
	@FXML
	private Tab tabMaintenance;
	@FXML
	private Tab tabPlaning;
	@FXML
	private CheckBox cbShowGrid;
	@FXML
	private AnchorPane regionsPane;
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
	@FXML
	private ComboBox<Basis> cbBases;
	@FXML
	private DatePicker dpDate;
	@FXML
	private TextField txtProduced;
	@FXML
	private TextField txtSuccess;
	@FXML
	private Button btnAddPlants;
	@FXML
	private Button btnZavrsiAktivnost;
	@FXML
	private Button btnAktivirajAktivnost;
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
	@FXML
	private ComboBox<PlantMaintanceActivity> cbSearchCategory;
	@FXML
	private Button btnAddMaintanceActivity;
	@FXML
	private Label lblError;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnClear;
	@FXML
	private Tab tabSale;
	@FXML
	private ImageView imgRegionSale;
	@FXML
	private Label lblRegionSale;
	@FXML
	private Label lblPlantSale;
	@FXML
	private Label lblNumSale;
	@FXML
	private Label lblErrorSale;
	@FXML
	private Button btnDodajAktivnost;
	@FXML
	private Button btnUkloniAktivnost;
	@FXML
	private Button btnDodajPlan;
	@FXML
	private Button btnObrisitePlan;
	@FXML
	private Button btnZakljucitePlan;
	@FXML
	private Button btnDetalji;
	@FXML
	private Button btnPretrazitePlanove;
	@FXML
	private RadioButton rbAktivni;
	@FXML
	private RadioButton rbZakljuceni;
	@FXML
	private DatePicker dpDatumOdPretraga;
	@FXML
	private DatePicker dpDatumDoPretraga;
	@FXML
	private Button btnAddBuyer;

	// TODO initialize
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Basis> basesItems = FXCollections.observableArrayList();
		basesItems.addAll(DAOFactory.getInstance().getBasisDAO().selectAll());
		cbBases.setItems(basesItems);
		cbBases.getSelectionModel().select(0);
		initRegions(DAOFactory.getInstance().getRegionDAO().selectAll());
		cbBases.setDisable(true);
		// TABOVI
		initializeMaintenanceTab();

		// TABELA
		initializeTable();
		ObservableList<SaleItem> saleItems = FXCollections.observableArrayList();
		lstSaleItems.setItems(saleItems);

		// COMBOBOX
		initializeComboBox();

		// DATE PICKER FORMAT
		formatDatePicker();

		// TABELA SELEKTOVANIH TASKOVA
		listaTaskova = FXCollections.observableArrayList();

		// BINDING - TAB PLANIRANJE
		btnDodajAktivnost.disableProperty()
				.bind(lblChosedRegion.textProperty().isEmpty()
						.or(dpTaskDateFrom.valueProperty().isNull()
								.or(dpPlanDateFrom.valueProperty().isNull().or(dpPlanDateTo.valueProperty().isNull()
										.or(cbActivity.getSelectionModel().selectedItemProperty().isNull())))));
		btnDodajPlan.disableProperty().bind(txtPlanName.textProperty().isEmpty()
				.or(dpPlanDateFrom.valueProperty().isNull().or(dpPlanDateTo.valueProperty().isNull())));
		btnUkloniAktivnost.disableProperty().bind(lstChosedTasks.getSelectionModel().selectedItemProperty().isNull());

		// BINDING - TAB ODRZAVANJE
		btnObrisitePlan.disableProperty().bind(lstActivePlans.getSelectionModel().selectedItemProperty().isNull());
		btnZakljucitePlan.disableProperty().bind(lstActivePlans.getSelectionModel().selectedItemProperty().isNull());
		btnDetalji.disableProperty().bind(tblTasks.getSelectionModel().selectedItemProperty().isNull());
		btnPretrazitePlanove.disableProperty()
				.bind(dpDatumOdPretraga.valueProperty().isNull().and(dpDatumDoPretraga.valueProperty().isNull()));

		// RADIO BUTTON
		ToggleGroup group = new ToggleGroup();
		rbAktivni.setToggleGroup(group);
		rbZakljuceni.setToggleGroup(group);

		btnAddPlants.disableProperty().bind(dpDate.valueProperty().isNull()
				.or(txtProduced.textProperty().isEmpty().or(txtSuccess.textProperty().isEmpty())));
		btnAddSaleItem.disableProperty()
				.bind(txtHeight.textProperty().isEmpty().or(txtNumSold.textProperty().isEmpty()));
		btnSaveSale.disableProperty().bind(Bindings.isEmpty(lstSaleItems.getItems()));
		btnAddBuyer.disableProperty().bind(Bindings.isEmpty(lstSaleItems.getItems()));
	}

	// TODO prelazi tabova
	@FXML
	public void generalTabSelected() {
		if (canvasEditor != null) {
			canvasEditor.invalidate();
			canvasEditor = new SelectTool(regionsMap, outlinesMap, this, undoCommands, redoCommands);
		}
		if (tabGeneral.isSelected()) {
			initRegions(DAOFactory.getInstance().getRegionDAO().selectAll());
			setDisabled(false, btnSetSelectTool, btnSetPolygonTool, btnRedo, btnUndo, btnDelete, btnClear);
		} else {
			setDisabled(true, btnSetSelectTool, btnSetPolygonTool, btnRedo, btnUndo, btnDelete, btnClear);
		}
	}

	@FXML
	public void maintenanceTabSelected() {
		initializeMaintenanceTab();
	}

	@FXML
	public void planingTabSelected() {
		if (canvasEditor != null) {
			canvasEditor.invalidate();
			canvasEditor = new SelectTool(regionsMap, outlinesMap, this, undoCommands, redoCommands);
		}
		if (tabPlaning.isSelected()) {
			initRegions(DAOFactory.getInstance().getRegionDAO().selectAll());
			setDisabled(false, btnSetSelectTool, btnSetPolygonTool, btnRedo, btnUndo);
		} else {
			setDisabled(true, btnSetSelectTool, btnSetPolygonTool, btnRedo, btnUndo);
		}
	}

	@FXML
	public void createSale() {
		setDisabled(true, btnSetSelectTool, btnSetPolygonTool, btnDelete, btnClear);
		setDisabled(false, btnUndo, btnRedo);
	}

	// TODO akcije na klik u toolbar

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

	@FXML
	public void delete(ActionEvent event) {
		if (selectedRegion != null) {
			if (ButtonType.YES
					.equals(DisplayUtil.showConfirmationDialog(ResourceBundleManager.getString("confirmQuestion")))) {
				if (DAOFactory.getInstance().getRegionDAO().delete(selectedRegion) > 0) {
					Polygon poly = null;
					for (Polygon p : regionsMap.keySet()) {
						if (selectedRegion.equals(regionsMap.get(p))) {
							poly = p;
							break;
						}
					}
					regionsMap.remove(poly);
					Polyline outline = outlinesMap.get(poly);
					elements.getChildren().remove(outline);
					elements.getChildren().remove(poly);
					outlinesMap.remove(poly);
					clear();
				}
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

	@FXML
	public void clean(ActionEvent event) {
		if (selectedRegion != null && selectedRegion.getBasisId() != null) {
			if (ButtonType.YES
					.equals(DisplayUtil.showConfirmationDialog(ResourceBundleManager.getString("confirmQuestion")))) {
				selectedRegion.setNumberOfPlants(0);
				selectedRegion.setBasisId(0);
				DAOFactory.getInstance().getRegionDAO().update(selectedRegion);
				displayInfo(selectedRegion);
			}
		}
	}

	// TODO reakcije na klikove

	@FXML
	public void click(MouseEvent event) {
		canvasEditor.click(event);
	}

	@FXML
	public void pressed(MouseEvent event) {
		canvasEditor.startDrawing(event);
	}

	@FXML
	public void released(MouseEvent event) {
		canvasEditor.finishDrawing(event);
	}

	// TODO akcije u prvom tabu

	@FXML
	public void addPlants(ActionEvent event) {
		try {
			if (selectedRegion != null) {
				Date date = DisplayUtil.convert(dpDate.getValue());
				Basis basis = cbBases.getSelectionModel().getSelectedItem();
				Integer produced = Integer.parseInt(txtProduced.getText());
				Integer takeARoot = Integer.parseInt(txtSuccess.getText());
				if (produced < 0) {
					lblError.setText("Broj posijanih mora biti veci od 0");
				} else if (produced < takeARoot) {
					lblError.setText("Broj uspjelih ne moze biti veci od broja posijanih");
				} else if (dpDate.getValue().compareTo(LocalDate.now()) > 0) {
					lblError.setText("Datum ne moze biti poslije danasnjeg");
				} else {
					if (DAOFactory.getInstance().getReproductionCuttingDAO().insert(
							new ReproductionCutting(basis, date, produced, takeARoot, basis.getBasisId(), false)) > 0) {
						if (selectedRegion.getBasis() == null) {
							selectedRegion.setBasis(basis);
							selectedRegion.setBasisId(basis.getBasisId());
						}
						selectedRegion.setNumberOfPlants(selectedRegion.getNumberOfPlants() + takeARoot);
						DAOFactory.getInstance().getRegionDAO().update(selectedRegion);
						displayInfo(selectedRegion);
					}
					lblError.setText("");
				}
			} else {
				lblError.setText("Morate oznaciti region");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			lblError.setText(ResourceBundleManager.getString("numberFormat"));
		}
	}

	// TODO akcije u drugom tabu

	@FXML
	public void refreshTabOdrzavanje() {
		initializeMaintenanceTab();
	}

	@FXML
	public void pretragaPlanova() {
		String statement = null;
		if (rbAktivni.isSelected())
			statement = " active = true and";
		else
			statement = " active = false and";
		try {
			List<Object> variables = new ArrayList<Object>();
			if (dpDatumOdPretraga.getValue() != null) {
				statement += " date_from >= ?";
				String dateString = dpDatumOdPretraga.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				variables.add(date);
			}
			if (dpDatumDoPretraga.getValue() != null) {
				if (dpDatumDoPretraga.getValue() != null) {
					statement += " and";
				}
				statement += " date_to <= ?";
				String dateString = dpDatumDoPretraga.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				variables.add(date);
			}

			ObservableList<Plan> activePlans = FXCollections.observableArrayList();
			ObservableList<Plan> donePlans = FXCollections.observableArrayList();

			if (rbAktivni.isSelected()) {
				activePlans.addAll(DAOFactory.getInstance().getPlanDAO().select(statement, variables.toArray()));
				lstActivePlans.setItems(activePlans);
			} else {
				donePlans.addAll(DAOFactory.getInstance().getPlanDAO().select(statement, variables.toArray()));
				lstDonePlans.setItems(donePlans);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void selectActive(MouseEvent event) {
		currentPlan = lstActivePlans.getSelectionModel().getSelectedItem();
		if (currentPlan != null) {
			List<Region> regions = DAOFactory.getInstance().getRegionDAO().getByPlanId(currentPlan.getPlanId());
			initRegions(regions);
			currentPlan.updateState();
			this.canvasEditor.invalidate();
			this.canvasEditor = new EmptyTool(regionsMap, tblTasks, currentPlan);
			btnZavrsiAktivnost.setDisable(false);
			btnAktivirajAktivnost.setDisable(false);
			tblTasks.setItems(null);
		}
	}

	@FXML
	public void selectDone(MouseEvent event) {
		currentPlan = lstDonePlans.getSelectionModel().getSelectedItem();
		if (currentPlan != null) {
			List<Region> regions = DAOFactory.getInstance().getRegionDAO().getByPlanId(currentPlan.getPlanId());
			initRegions(regions);
			currentPlan.updateState();
			this.canvasEditor.invalidate();
			this.canvasEditor = new EmptyTool(regionsMap, tblTasks, currentPlan);
			btnZavrsiAktivnost.setDisable(true);
			btnAktivirajAktivnost.setDisable(true);
			tblTasks.setItems(null);
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
			tblTasks.setItems(null);
			initRegions(DAOFactory.getInstance().getRegionDAO().selectAll());
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
	void filterTasks(ActionEvent event) {
		PlantMaintanceActivity item = cbSearchCategory.getValue();
		if (item != null) {
			if (item.getActivity().equals("Sve aktivnosti")) {
				tblTasks.setItems(listaTaskova);
			} else {
				ObservableList<TaskTableItem> items = FXCollections.observableArrayList(listaTaskova.stream()
						.filter(x -> x.getActivity().equals(item.getActivity())).collect(Collectors.toList()));
				tblTasks.setItems(items);
			}
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
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void taskSetUndone(ActionEvent event) {
		TaskTableItem task = tblTasks.getSelectionModel().getSelectedItem();
		if (task != null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Da li želite da obrišete sve angažmane vezane za odabranu aktivnost?");
			alert.setHeaderText("Warning");
			alert.setTitle("Warning");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().add(ButtonType.YES);
			alert.getButtonTypes().add(ButtonType.NO);
			alert.getButtonTypes().add(ButtonType.CANCEL);
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get().equals(ButtonType.YES)) {
				currentPlan.setDone(task.getTask().getRegion(), task.getTask(), false);
				task.setDone(false);
				task.setDate_to(null);
				DAOFactory.getInstance().getTaskDAO().update(task.getTask());
				tblTasks.refresh();
				this.canvasEditor.invalidate();
				this.canvasEditor = new EmptyTool(regionsMap, tblTasks, currentPlan);
				List<EmployeeHasTask> list = DAOFactory.getInstance().getEmployeeHasTaskDAO()
						.getByTaskId(task.getTask().getTaskId());
				for (EmployeeHasTask em : list) {
					DAOFactory.getInstance().getEmployeeHasTaskDAO().delete(em);
				}
			} else if (result.get().equals(ButtonType.NO)) {
				currentPlan.setDone(task.getTask().getRegion(), task.getTask(), false);
				task.setDone(false);
				task.setDate_to(null);
				DAOFactory.getInstance().getTaskDAO().update(task.getTask());
				tblTasks.refresh();
				this.canvasEditor.invalidate();
				this.canvasEditor = new EmptyTool(regionsMap, tblTasks, currentPlan);
			}
		}
	}

	@FXML
	void taskSetDone(ActionEvent event) {
		TaskTableItem task = tblTasks.getSelectionModel().getSelectedItem();
		if (task != null) {
			if (DAOFactory.getInstance().getEmployeeHasTaskDAO().getByTaskId(task.getTask().getTaskId()).size() == 0) {
				ButtonType result = DisplayUtil.showWarningDialog(
						"Nemoguće je zaključiti aktivnost ukoliko na toj aktivnosti nije angažovan niti jedan radnik. Bićete preusmjereni na formu za dodavanje.");
				if (result.equals(ButtonType.YES)) {
					taskDetails(event);
				}
				return;
			}

			currentPlan.setDone(task.getTask().getRegion(), task.getTask(), true);
			task.setDone(true);
			task.setDate_to(Calendar.getInstance().getTime());
			DAOFactory.getInstance().getTaskDAO().update(task.getTask());
			tblTasks.refresh();
			this.canvasEditor.invalidate();
			this.canvasEditor = new EmptyTool(regionsMap, tblTasks, currentPlan);
		}
	}

	// TODO akcije u trecem tabu

	@FXML
	void addMaintanceActivity(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader()
					.getResource("org/unibl/etf/gui/plants/view/AddNewMaintanceActivityDialog.fxml"));
			AnchorPane root;
			root = (AnchorPane) loader.load();
			AddNewMaintanceActivityDialogController control = loader
					.<AddNewMaintanceActivityDialogController>getController();
			control.setPrimaryStage(stage);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Dodavanje aktivnosti");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.showAndWait();
			if (control.getResult().equals(ButtonType.OK)) {
				initializeComboBox();
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	@FXML
	void addTaskInPlan(ActionEvent event) {
		if (lblChosedRegion.getText() == "" || cbActivity.getSelectionModel().getSelectedItem() == null
				|| dpTaskDateFrom.getValue() == null) {
			DisplayUtil.showErrorDialog("Odaberite region i popunite polja!");
			return;
		}

		if (dpPlanDateFrom.getValue() == null || dpPlanDateTo.getValue() == null) {
			DisplayUtil.showErrorDialog("Molimo unesite datume početka i kraja plana!");
			return;
		}

		if (dpPlanDateFrom.getValue().isAfter(dpTaskDateFrom.getValue())
				|| dpPlanDateTo.getValue().isBefore(dpTaskDateFrom.getValue())) {
			DisplayUtil.showErrorDialog("Datum početka aktivnosti mora biti u toku plana!");
			return;
		}
		if (dpTaskDateFrom.getValue().isBefore(LocalDate.now())) {
			DisplayUtil.showErrorDialog("Datum početka aktivnosti ne može biti prije današnjeg!");
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
		lstChosedTasks.setItems(null);
	}

	// TODO akcije u cetvrtom tabu

	@FXML
	public void addSaleItem(ActionEvent event) {
		if (selectedRegion != null && selectedRegion.getBasis() != null) {
			try {
				Integer num = Integer.parseInt(txtNumSold.getText());
				BigDecimal height = new BigDecimal(txtHeight.getText());
				if (num.compareTo(Integer.valueOf(0)) <= 0) {
					lblErrorSale.setText("Broj prodatih mora biti veci od 0");
				} else if (num.compareTo(selectedRegion.getNumberOfPlants()) > 0) {
					lblErrorSale.setText("Nema dovoljno biljaka u regionu");
				} else if (txtHeight.getText().startsWith("-")) {
					lblErrorSale.setText("Visina ne moze biti manja od 0");
				} else {
					Plant plant = selectedRegion.getBasis().getPlant();
					BigDecimal price = plant.getPrice(height).multiply(new BigDecimal(num));
					BigDecimal heightMin = plant.getHeightMin(height);
					SaleItem item = new SaleItem(num, plant, null, plant.getPlantId(), null, price, heightMin);
					changedRegions.add(selectedRegion);
					Command command = new AddSaleItemCommand(selectedRegion, item, lstSaleItems.getItems(), this);
					command.execute();
					canvasEditor.push(command);
					canvasEditor.clearRedo();
					lblErrorSale.setText("");
				}
			} catch (NumberFormatException e) {
				lblErrorSale.setText(ResourceBundleManager.getString("numberFormat"));
			}
		} else {
		}
	}

	@FXML
	public void addBuyer(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/sales/view/ChooseCustomerView.fxml");
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			ChooseCustomerController controller = DisplayUtil.<ChooseCustomerController>getController(loader);
			controller.setSale(sale);
			DisplayUtil.switchStage(root, 400, 295, true, "Dodavanje kupca", true);
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	@FXML
	public void saveSale(ActionEvent event) {
		if (sale.getCustomerId() != null) {
			BigDecimal price = BigDecimal.ZERO;
			for (SaleItem item : lstSaleItems.getItems()) {
				price = price.add(item.getPrice());
			}
			if (lstSaleItems.getItems().size() > 0) {
				sale.setDate(Calendar.getInstance().getTime());
				sale.setPrice(price);
				sale.setPaidOff(cbPaidOff.isSelected());
				sale.setDeleted(false);
				if (DAOFactory.getInstance().getSaleDAO().insert(sale) > 0) {
					for (SaleItem item : lstSaleItems.getItems()) {
						item.setSaleId(sale.getSaleId());
						DAOFactory.getInstance().getSaleItemDAO().insert(item);
					}
					for (Region reg : changedRegions) {
						DAOFactory.getInstance().getRegionDAO().update(reg);
					}
					sale = new Sale();
				}
			}
			changedRegions.clear();
			lstSaleItems.getItems().clear();
			lstSaleItems.refresh();
		} else {
			lblErrorSale.setText("Niste dodali kupca");
		}
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

	// TODO pomocne metode

	private void initializeMaintenanceTab() {
		ObservableList<Plan> activePlans = FXCollections.observableArrayList();
		ObservableList<Plan> donePlans = FXCollections.observableArrayList();
		activePlans.addAll(DAOFactory.getInstance().getPlanDAO().getByActive(true));
		donePlans.addAll(DAOFactory.getInstance().getPlanDAO().getByActive(false));
		lstActivePlans.setItems(activePlans);
		lstDonePlans.setItems(donePlans);
		if (tabMaintenance.isSelected()) {
			setDisabled(true, btnSetSelectTool, btnSetPolygonTool, btnRedo, btnUndo, btnDelete, btnClear);
		}
	}

	private void initializeComboBox() {
		ObservableList<PlantMaintanceActivity> activities = FXCollections
				.observableArrayList(DAOFactory.getInstance().getPlantMaintanceActivityDAO().selectAll());
		cbActivity.setItems(activities);
		PlantMaintanceActivity sveAktivnosti = new PlantMaintanceActivity();
		ObservableList<PlantMaintanceActivity> activitiesSearch = FXCollections.observableArrayList(activities);
		sveAktivnosti.setActivity("Sve aktivnosti");
		activitiesSearch.add(0, sveAktivnosti);
		cbSearchCategory.setItems(activitiesSearch);
		cbSearchCategory.getSelectionModel().select(0);
	}

	public static void setListaTaskova(ObservableList<TaskTableItem> list) {
		listaTaskova = list;
	}

	private void formatDatePicker() {
		dpPlanDateFrom.setConverter(DisplayUtil.datePickerConverter());
		dpPlanDateTo.setConverter(DisplayUtil.datePickerConverter());
		dpTaskDateFrom.setConverter(DisplayUtil.datePickerConverter());
		dpDate.setConverter(DisplayUtil.datePickerConverter());
		dpDatumDoPretraga.setConverter(DisplayUtil.datePickerConverter());
		dpDatumOdPretraga.setConverter(DisplayUtil.datePickerConverter());
	}

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
		if (basis != null) {
			cbBases.getSelectionModel().select(basis);
			cbBases.setDisable(true);
		} else {
			cbBases.getSelectionModel().select(0);
			cbBases.setDisable(false);
		}
	}

	public void clear() {
		setValues(null, "", "", "");
		selectedRegion = null;
		cbBases.setDisable(true);
	}

	private void setValues(Image image, String region, String name, String num) {
		imgPhoto.setImage(image);
		imgRegionSale.setImage(image);
		lblRegion.setText(region);
		lblRegionSale.setText(region);
		lblName.setText(name);
		lblPlantSale.setText(name);
		lblPlantsNum.setText(num);
		lblNumSale.setText(num);
	}

	public void initRegions(List<Region> regions) {
		elements.getChildren().clear();
		regionsMap = new HashMap<Polygon, Region>();
		outlinesMap = new HashMap<Polygon, Polyline>();
		undoCommands = new Stack<Command>();
		redoCommands = new Stack<Command>();
		for (Region r : regions) {
			Polygon p = new Polygon();
			p.getPoints().addAll(r.getCoords());
			Polyline pl = new Polyline();
			pl.getPoints().addAll(r.getCoords());
			pl.getPoints().add(r.getCoords()[0]);
			pl.getPoints().add(r.getCoords()[1]);
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

	public void refreshList() {
		lstSaleItems.refresh();
	}

	// TODO ostala polja

	private CanvasEditor canvasEditor;
	private static ObservableList<TaskTableItem> listaTaskova;
	private Map<Region, ObservableList<Task>> planedTasks = new HashMap<Region, ObservableList<Task>>();
	private Map<Polygon, Region> regionsMap;
	private Map<Polygon, Polyline> outlinesMap;
	private Stack<Command> undoCommands;
	private Stack<Command> redoCommands;
	private Region selectedRegion;
	private Set<Region> changedRegions = new HashSet<Region>();
	private Plan currentPlan;
	private Sale sale = new Sale();
}
