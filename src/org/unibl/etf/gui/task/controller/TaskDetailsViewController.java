package org.unibl.etf.gui.task.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Employee;
import org.unibl.etf.dto.EmployeeHasTask;
import org.unibl.etf.dto.EmployeeTableItem;
import org.unibl.etf.dto.Task;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.ErrorLogger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class TaskDetailsViewController extends BaseController {

	@FXML
	private TableView<EmployeeTableItem> tvEmployee;

	@FXML
	private TableColumn<EmployeeTableItem, Integer> colEmId;

	@FXML
	private TableColumn<EmployeeTableItem, String> colEmFirstName;

	@FXML
	private TableColumn<EmployeeTableItem, String> colEmLastName;

	@FXML
	private TextField txtSearch;

	@FXML
	private TableView<EngagementTableItem> tvEngagement;

	@FXML
	private TableColumn<EngagementTableItem, String> colDate;

	@FXML
	private TableColumn<EngagementTableItem, Integer> colId;

	@FXML
	private TableColumn<EngagementTableItem, String> colFirstName;

	@FXML
	private TableColumn<EngagementTableItem, String> colLastName;

	@FXML
	private TableColumn<EngagementTableItem, Integer> colHours;

	@FXML
	private TableColumn<EngagementTableItem, Double> colHourlyWage;

	@FXML
	private TableColumn<EngagementTableItem, Double> colTotal;

	@FXML
	private TableColumn<EngagementTableItem, Boolean> colPaid;

	@FXML
	private Label lblRegion;

	@FXML
	private Label lblAktivnost;

	@FXML
	private DatePicker dpDate;

	@FXML
	private Button btnAngazujte;

	@FXML
	private TextField txtHourlyWage;

	@FXML
	private Button btnPay;

	@FXML
	private Button btnDeleteWork;

	@FXML
	private Button btnRevokePayment;

	@FXML
	private DatePicker dpDatumOd;

	@FXML
	private DatePicker dpDatumDo;

	@FXML
	private Button btnPretrazite;

	@FXML
	void deleteWork(ActionEvent event) {
		EngagementTableItem item = tvEngagement.getSelectionModel().getSelectedItem();
		if (item != null) {
			if (item.getHours() != 0) {
				DisplayUtil.showErrorDialog("Samo angažmani sa 0 sati mogu biti obrisani!");
				return;
			}
			item.getTask().setDeleted(true);
			DAOFactory.getInstance().getEmployeeHasTaskDAO().update(item.getTask());
			tvEngagement.getItems().remove(item);
		}
	}

	@FXML
	void engageEmployee(ActionEvent event) {
		EmployeeTableItem employeeTableItem = tvEmployee.getSelectionModel().getSelectedItem();
		if (employeeTableItem == null || dpDate.getValue() == null || txtHourlyWage.getText() == "") {
			DisplayUtil.showErrorDialog("Odaberite radnika i unesite podatke!");
			return;
		}
		Double hourlyWage;
		try {
			hourlyWage = Double.valueOf(txtHourlyWage.getText());
		} catch (NumberFormatException e) {
			DisplayUtil.showErrorDialog("Satnica nije u dobrom formatu!");
			return;
		}
		Date date = Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

		if (dpDate.getValue().isBefore(LocalDate.now())) {
			DisplayUtil.showErrorDialog("Datum može biti današnji ili stariji!");
			return;
		}
		for (EngagementTableItem item : tvEngagement.getItems()) {
			if (item.getId() == employeeTableItem.getId() && item.getTask().getDate().equals(date)) {
				DisplayUtil.showErrorDialog("Nemoguće je angažovati radnika na isti dan više puta!");
				return;
			}
		}

		EmployeeHasTask employeeHasTask = new EmployeeHasTask(BigDecimal.valueOf(hourlyWage), 0, false, date,
				task.getTaskId(), task, employeeTableItem.getEmployee().getEmployeeId(),
				employeeTableItem.getEmployee(), false);
		EngagementTableItem item = new EngagementTableItem(employeeHasTask);
		int result = DAOFactory.getInstance().getEmployeeHasTaskDAO().insert(employeeHasTask);
		if(result == 0) {
			DAOFactory.getInstance().getEmployeeHasTaskDAO().update(employeeHasTask);
			for(EngagementTableItem temp : tvEngagement.getItems()) {
				if(temp.getId() == item.getId()) {
					tvEngagement.getItems().remove(temp);
					break;
				}
			}
			tvEngagement.getItems().add(item);
		}
		else {
			tvEngagement.getItems().add(item);
		}
		txtHourlyWage.clear();
		dpDate.setValue(null);
		tvEmployee.getSelectionModel().clearSelection();
	}

	@FXML
	void payWork(ActionEvent event) {
		for (EngagementTableItem item : tvEngagement.getSelectionModel().getSelectedItems()) {
			if (!item.isPaidOff()) {
				item.setPaidOff(true);
				try {
					item.update();
				} catch (DAOException e) {
					e.printStackTrace();
					new ErrorLogger().log(e);
				}
				tvEngagement.refresh();
			}
		}
	}

	@FXML
	void revokePayment(ActionEvent event) {
		for (EngagementTableItem item : tvEngagement.getSelectionModel().getSelectedItems()) {
			if (item.isPaidOff()) {
				item.setPaidOff(false);
				try {
					item.update();
				} catch (DAOException e) {
					e.printStackTrace();
					new ErrorLogger().log(e);
				}
				tvEngagement.refresh();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnPretrazite.disableProperty()
				.bind(dpDatumOd.valueProperty().isNull().and(dpDatumDo.valueProperty().isNull()));
		btnAngazujte.disableProperty().bind(dpDate.valueProperty().isNull().or(txtHourlyWage.textProperty().isEmpty()
				.or(tvEmployee.getSelectionModel().selectedItemProperty().isNull())));
		btnPay.disableProperty().bind(tvEngagement.getSelectionModel().selectedItemProperty().isNull());
		btnRevokePayment.disableProperty().bind(tvEngagement.getSelectionModel().selectedItemProperty().isNull());
		btnDeleteWork.disableProperty().bind(tvEngagement.getSelectionModel().selectedItemProperty().isNull());
	}

	public void initializeSearch() {
		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			if ("".equals(newValue)) {
				tvEmployee.setItems(employeeList);
			} else {
				ObservableList<EmployeeTableItem> tempList = FXCollections.observableArrayList(
						employeeList.stream().filter(x -> (x.getFirstName() + " " + x.getLastName()).toLowerCase()
								.contains(newValue.toLowerCase())).collect(Collectors.toList()));
				tvEmployee.setItems(tempList);
			}
		});
	}

	public void initializeView() {
		initializeTableEmployee();
		initializeTableEngagement();
		initializeSearch();

		// REGION
		lblRegion.setText("REGION " + task.getRegionId());

		// AKTIVNOST
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		lblAktivnost.setText(task.getPlantMaintanceActivity().getActivity() + " : [" + df.format(task.getDateFrom())
				+ " - " + (task.getDateTo() == null ? "" : df.format(task.getDateTo())) + "]");

		// DISABLE BUTTONS IF TASK IS DONE
		if (task.getDateTo() != null || task.getPlan().getActive() == false) {
			btnAngazujte.disableProperty().unbind();
			btnAngazujte.setDisable(true);
			btnDeleteWork.disableProperty().unbind();
			btnDeleteWork.setDisable(true);
			dpDate.setDisable(true);
			txtHourlyWage.setDisable(true);
		}

		// FORMAT DATE PICKER
		dpDate.setConverter(DisplayUtil.datePickerConverter());
		dpDate.setValue(LocalDate.now());
		dpDatumDo.setConverter(DisplayUtil.datePickerConverter());
		dpDatumOd.setConverter(DisplayUtil.datePickerConverter());

	}

	@FXML
	public void pretrazivanjePoDatumu(ActionEvent event) {
		try {
			String statement = " task_id = ? and";
			List<Object> variables = new ArrayList<Object>();
			variables.add(task.getTaskId());
			if (dpDatumOd.getValue() != null) {
				statement += " date >= ?";
				String dateString = dpDatumOd.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				variables.add(date);
			}
			if (dpDatumDo.getValue() != null) {
				if (dpDatumDo.getValue() != null) {
					statement += " and";
				}
				statement += " date <= ?";
				String dateString = dpDatumDo.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
				variables.add(date);
			}

			List<EmployeeHasTask> list = DAOFactory.getInstance().getEmployeeHasTaskDAO().select(statement,
					variables.toArray());
			engagementList = FXCollections.observableArrayList(EngagementTableItem.convert(list));
			tvEngagement.setItems(engagementList);

		} catch (Exception e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	@FXML
	public void refreshTable() {
		List<EmployeeHasTask> list = DAOFactory.getInstance().getEmployeeHasTaskDAO().getByTaskId(task.getTaskId());
		engagementList = FXCollections.observableArrayList(EngagementTableItem.convert(list));
		tvEngagement.setItems(engagementList);
	}

	public void initializeTableEngagement() {
		colDate.setCellValueFactory(new PropertyValueFactory<EngagementTableItem, String>("date"));
		colId.setCellValueFactory(new PropertyValueFactory<EngagementTableItem, Integer>("id"));
		colFirstName.setCellValueFactory(new PropertyValueFactory<EngagementTableItem, String>("firstName"));
		colLastName.setCellValueFactory(new PropertyValueFactory<EngagementTableItem, String>("lastName"));
		colHours.setCellValueFactory(new PropertyValueFactory<EngagementTableItem, Integer>("hours"));
		colHourlyWage.setCellValueFactory(new PropertyValueFactory<EngagementTableItem, Double>("hourlyWage"));
		colTotal.setCellValueFactory(new PropertyValueFactory<EngagementTableItem, Double>("payment"));
		colPaid.setCellValueFactory(new PropertyValueFactory<EngagementTableItem, Boolean>("paidOff"));
		colPaid.setCellFactory(col -> new TableCell<EngagementTableItem, Boolean>() {
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? null : item ? "Da" : "Ne");
			}
		});

		colHours.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
			@Override
			public Integer fromString(String value) {
				try {
					return super.fromString(value);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					new ErrorLogger().log(e);
				}
				return null;
			}
		}));
		colHourlyWage.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
			@Override
			public Double fromString(String value) {
				try {
					return super.fromString(value);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				return null;
			}
		}));

		colHours.setOnEditCommit((TableColumn.CellEditEvent<EngagementTableItem, Integer> t) -> {
			t.getRowValue()
					.setHours(t.getNewValue() == null || t.getNewValue() < 0 ? t.getOldValue() : t.getNewValue());
			tvEngagement.refresh();
			try {
				t.getRowValue().update();
			} catch (DAOException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			}
		});

		colHourlyWage.setOnEditCommit((TableColumn.CellEditEvent<EngagementTableItem, Double> t) -> {
			t.getRowValue()
					.setHourlyWage(t.getNewValue() == null || t.getNewValue() < 0 ? t.getOldValue() : t.getNewValue());
			tvEngagement.refresh();
			try {
				t.getRowValue().update();
			} catch (DAOException e) {
				e.printStackTrace();
				new ErrorLogger().log(e);
			}
		});

		List<EmployeeHasTask> list = DAOFactory.getInstance().getEmployeeHasTaskDAO().getByTaskId(task.getTaskId());
		engagementList = FXCollections.observableArrayList(EngagementTableItem.convert(list));
		tvEngagement.setItems(engagementList);
	}

	public void initializeTableEmployee() {
		colEmId.setCellValueFactory(new PropertyValueFactory<EmployeeTableItem, Integer>("id"));
		colEmFirstName.setCellValueFactory(new PropertyValueFactory<EmployeeTableItem, String>("firstName"));
		colEmLastName.setCellValueFactory(new PropertyValueFactory<EmployeeTableItem, String>("lastName"));

		colEmFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
		colEmLastName.setCellFactory(TextFieldTableCell.forTableColumn());

		List<Employee> employees = null;
		employees = DAOFactory.getInstance().getEmployeeDAO().getByIsDeleted(false);

		employeeList = FXCollections.observableArrayList(EmployeeTableItem.convert(employees));
		tvEmployee.setItems(employeeList);
	}

	public void setTask(Task task) {
		this.task = task;
	}

	private ObservableList<EmployeeTableItem> employeeList;
	private ObservableList<EngagementTableItem> engagementList;
	private Task task;

}
