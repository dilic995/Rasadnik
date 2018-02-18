package org.unibl.etf.gui.task.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.controlsfx.control.table.TableRowExpanderColumn;
import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.ActivityTableItem;
import org.unibl.etf.dto.Employee;
import org.unibl.etf.dto.EmployeeHasTask;
import org.unibl.etf.dto.EmployeeTableItem;
import org.unibl.etf.gui.util.OrBinder;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class EmployeeViewController extends BaseController implements Initializable{

    @FXML
    private TableView<EmployeeTableItem> tvRadnici;

    @FXML
    private TableColumn<EmployeeTableItem, Integer> tcSifra;

    @FXML
    private TableColumn<EmployeeTableItem, String> tcIme;

    @FXML
    private TableColumn<EmployeeTableItem, String> tcPrezime;

    @FXML
    private TableView<ActivityTableItem> tvAktivnosti;

    @FXML
    private TableColumn<ActivityTableItem, Integer> tcRegion;

    @FXML
    private TableColumn<ActivityTableItem, String> tcDatum;

    @FXML
    private TableColumn<ActivityTableItem, String> tcAktivnost;

    @FXML
    private TableColumn<ActivityTableItem, Integer> tcBrojSati;

    @FXML
    private TableColumn<ActivityTableItem, Double> tcSatnica;

    @FXML
    private TableColumn<ActivityTableItem, Double> tcUplata;

    @FXML
    private TableColumn<ActivityTableItem, Boolean> tcIsplaceno;

    @FXML
    private TextField txtIme;

    @FXML
    private TextField txtPrezime;
    
    @FXML
    private TextField txtSearch;
    
    @FXML
    private Button btnAddEmployee;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeTableRadnici();
		initializeTableAktivnosti();
		initializeSearch();
		
		OrBinder binder = new OrBinder();
		btnAddEmployee.disableProperty().bind(binder.bindAll(txtIme.textProperty().isEmpty(), txtPrezime.textProperty().isEmpty()));
	}
	
	
	//RADI
	public void deleteEmployee() {
		EmployeeTableItem item = tvRadnici.getSelectionModel().getSelectedItem();
		if(item != null) {
			item.setIsDeleted(true);
			try {
				item.update();
			} catch (DAOException e) {
				e.printStackTrace();
			}
			tvRadnici.getItems().remove(item);
		}
	}
	
	//RADI
	public void revoke() {
		for(ActivityTableItem item : tvAktivnosti.getSelectionModel().getSelectedItems()) {
			if(item.getPaidOff()) {
				item.setPaidOff(false);
				try {
					item.update();
				} catch (DAOException e) {
					e.printStackTrace();
				}
				tvAktivnosti.refresh();
			}
		}
	}
	
	//RADI
	public void pay() {
		for(ActivityTableItem item : tvAktivnosti.getSelectionModel().getSelectedItems()) {
			if(!item.getPaidOff()) {
				item.setPaidOff(true);
				try {
					item.update();
				} catch (DAOException e) {
					e.printStackTrace();
				}
				tvAktivnosti.refresh();
			}
		}
	}
	
	//RADI
	public void addEmployee() {
		List<Employee> tmpList = DAOFactory.getInstance().getEmployeeDAO().getByFirstName(txtIme.getText());
		for(Employee employee : tmpList) {
			if(employee.getLastName().equals(txtPrezime.getText())) {
				
				Alert alert = new Alert(AlertType.WARNING, "Postoji radnik sa tim imenom i prezimenom. Da li ste sigurni da �elite da nastavite?",
						ButtonType.YES, ButtonType.NO);
				alert.setTitle("Upozorenje");
				alert.setHeaderText("Upozorenje!");
				if(alert.showAndWait().equals(ButtonType.NO))
					return;
				
			}
		}
		Employee newEmployee = new Employee(null, txtIme.getText(), txtPrezime.getText());
		newEmployee.setDeleted(false);
		int generatedId = DAOFactory.getInstance().getEmployeeDAO().insert(newEmployee);
		newEmployee.setEmployeeId(generatedId);
		
		employeeList.add(new EmployeeTableItem(newEmployee)); 
		
		txtIme.clear();
		txtPrezime.clear();
	}
	
	public void initializeSearch() {
		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			if("".equals(newValue)) {
				tvRadnici.setItems(employeeList);
			}
			else {
				ObservableList<EmployeeTableItem> tempList = FXCollections.observableArrayList(employeeList.stream()
						.filter(x -> (x.getFirstName() + " " + x.getLastName()).toLowerCase().contains(newValue.toLowerCase())).collect(Collectors.toList()));
				tvRadnici.setItems(tempList);
			}
		});
	}
	
	public void initializeTableRadnici() {
		tcSifra.setCellValueFactory(new PropertyValueFactory<EmployeeTableItem, Integer>("id"));
		tcIme.setCellValueFactory(new PropertyValueFactory<EmployeeTableItem, String>("firstName"));
		tcPrezime.setCellValueFactory(new PropertyValueFactory<EmployeeTableItem, String>("lastName"));
		
		tcIme.setCellFactory(TextFieldTableCell.forTableColumn());
		tcPrezime.setCellFactory(TextFieldTableCell.forTableColumn());
		
		tcIme.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeTableItem, String> t) -> {
                    t.getRowValue().setFirstName(t.getNewValue());
                    try {
						t.getRowValue().update();
					} catch (DAOException e) {
						e.printStackTrace();
					}
                });
		tcPrezime.setOnEditCommit(
                (TableColumn.CellEditEvent<EmployeeTableItem, String> t) -> {
                    t.getRowValue().setLastName(t.getNewValue());
                    try {
						t.getRowValue().update();
					} catch (DAOException e) {
						e.printStackTrace();
					}
                });
		
		List<Employee> employees = null;
		employees = DAOFactory.getInstance().getEmployeeDAO().getByIsDeleted(false);
		
		employeeList = FXCollections.observableArrayList(EmployeeTableItem.convert(employees));
		tvRadnici.setItems(employeeList);
	}
	
	public void tableRadniciOnMouseClicked() {
		EmployeeTableItem item = tvRadnici.getSelectionModel().getSelectedItem();
		if(item != null) {
			List<EmployeeHasTask> tempList = DAOFactory.getInstance().getEmployeeHasTaskDAO().getByEmployeeId(item.getId());
			activityList = FXCollections.observableArrayList(ActivityTableItem.convert(tempList));
			tvAktivnosti.setItems(activityList);
		}
	}
	
	public void initializeTableAktivnosti() {
		tcRegion.setCellValueFactory(new PropertyValueFactory<ActivityTableItem, Integer>("region"));
		tcDatum.setCellValueFactory(new PropertyValueFactory<ActivityTableItem, String>("date"));
		tcAktivnost.setCellValueFactory(new PropertyValueFactory<ActivityTableItem, String>("activity"));
		tcBrojSati.setCellValueFactory(new PropertyValueFactory<ActivityTableItem, Integer>("hours"));
		tcSatnica.setCellValueFactory(new PropertyValueFactory<ActivityTableItem, Double>("hourlyWage"));
		tcUplata.setCellValueFactory(new PropertyValueFactory<ActivityTableItem, Double>("payment"));
		tcIsplaceno.setCellValueFactory(new PropertyValueFactory<ActivityTableItem, Boolean>("paidOff"));
		tcIsplaceno.setCellFactory(col -> new TableCell<ActivityTableItem, Boolean>() {
		    @Override
		    protected void updateItem(Boolean item, boolean empty) {
		        super.updateItem(item, empty) ;
		        setText(empty ? null : item ? "Da" : "Ne" );
		    }
		});
		
		tcBrojSati.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
			@Override
			public Integer fromString(String value) {
				try {
					return super.fromString(value);
				}catch(NumberFormatException e) {
					e.printStackTrace();
				}
				return null;
			}
		}));
		tcSatnica.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
			@Override
			public Double fromString(String value) {
				try {
					return super.fromString(value);
				}catch(NumberFormatException e) {
					e.printStackTrace();
				}
				return null;
			}
		}));
		
		tcBrojSati.setOnEditCommit(
                (TableColumn.CellEditEvent<ActivityTableItem, Integer> t) -> {
                    t.getRowValue().setHours(t.getNewValue());
                    tvAktivnosti.refresh();
                    try {
						t.getRowValue().update();
					} catch (DAOException e) {
						e.printStackTrace();
					}
                });
		
		tcSatnica.setOnEditCommit(
                (TableColumn.CellEditEvent<ActivityTableItem, Double> t) -> {
                    t.getRowValue().setHourlyWage(t.getNewValue());
                    tvAktivnosti.refresh();
                    try {
						t.getRowValue().update();
					} catch (DAOException e) {
						e.printStackTrace();
					}
                });
		
		
		activityList = FXCollections.observableArrayList();
		tvAktivnosti.setItems(activityList);
	}
	
	ObservableList<EmployeeTableItem> employeeList;
	ObservableList<ActivityTableItem> activityList;

}
