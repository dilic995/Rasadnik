package org.unibl.etf.tool.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import org.unibl.etf.application.BaseController;
import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Condition;
import org.unibl.etf.dto.Tool;
import org.unibl.etf.dto.ToolItem;
import org.unibl.etf.dto.ToolMaintanceActivity;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextArea;

import javafx.scene.control.CheckBox;

import javafx.scene.control.TableView;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TableColumn;

public class ToolViewController extends BaseController{
	@FXML
	private RadioButton radioMachine;
	@FXML
	private RadioButton radioTool;
	@FXML
	private ComboBox<Tool> comboBoxTool;
	@FXML
	private Button btnAddTool;
	@FXML
	private TableView<ToolItem> tableToolItems;
	@FXML
	private TableColumn<ToolItem,Integer> tableColumnId;
	@FXML
	private TableColumn<ToolItem, String> tableColumnCondition;
	@FXML
	private TableColumn<ToolItem, Date> tableColumnNextServiceDate;
	@FXML
	private Button btnDelete;
	@FXML
	private TableView<ToolMaintanceActivity> tableActivities;
	@FXML
	private TableColumn<ToolMaintanceActivity, String> tableColumnDate;
	@FXML
	private TableColumn<ToolMaintanceActivity, String> tableColumnDescription;
	@FXML
	private TableColumn<ToolMaintanceActivity, Double> tableColumnAmount;
	@FXML
	private TextField txtTool;
	@FXML
	private Label lblMachineTool;
	@FXML
	private Label lblCount;
	@FXML
	private Button btnDeleteActivity;
	@FXML
	private ComboBox<Tool> comboBoxToolAdd;
	@FXML
	private ComboBox<Condition> comboBoxCondition;
	@FXML
	private Button btnUpdateCondition;
	@FXML
	private Button btnAdd;
	@FXML
	private DatePicker datePicker;
	@FXML
	private TextField txtAmount;
	@FXML
	private CheckBox checkBoxService;
	@FXML
	private TextArea txtDescription;
	@FXML
	private Button btnAddActivity;

	
	private ObservableList<Tool> listTool;
	private ObservableList<ToolItem> listToolItems;
	private ObservableList<ToolMaintanceActivity> listActivities;
	private ObservableList<Condition> listConditions;
	// Event Listener on ComboBox[#comboBoxTool].onAction
	@FXML
	public void selectToolItems() {
		Tool tool = comboBoxTool.getSelectionModel().getSelectedItem();
		lblMachineTool.setText(tool.getIsMachine()==true ? "Masina" : "Alat");
		lblCount.setText(tool.getCount().toString());
		try {
			Object[] pom = {tool.getToolId(),false};
			listToolItems =(ObservableList<ToolItem>) DAOFactory.getInstance().getToolItemDAO().select(" tool_id=? and is_deleted=?", pom);
			tableToolItems.setItems(listToolItems);
			tableToolItems.getSelectionModel().select(0);
			showActivities();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#btnAddTool].onAction
	@FXML
	public void addTool(ActionEvent event) {
		// TODO Autogenerated
		String name = txtTool.getText();
		boolean masina = false;
		if(radioMachine.isSelected())
			masina=true;
		Tool tool = new Tool(null,name,0,masina);
		try {
			DAOFactory.getInstance().getToolDAO().insert(tool);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listTool.add(tool);
	}
	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteTool(ActionEvent event) {
		ToolItem toolItem = tableToolItems.getSelectionModel().getSelectedItem();
		toolItem.setIsDeleted(true);
		try {
			DAOFactory.getInstance().getToolItemDAO().update(toolItem);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listToolItems.remove(toolItem);
		tableToolItems.refresh();
	}
	// Event Listener on Button[#btnDeleteActivity].onAction
	@FXML
	public void deleteActivity(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnUpdateCondition].onAction
	@FXML
	public void updateCondition(ActionEvent event) {
		ToolItem toolItem = tableToolItems.getSelectionModel().getSelectedItem();
		Condition condition = comboBoxCondition.getSelectionModel().getSelectedItem();
		toolItem.setCondition(condition);
		toolItem.setConditionId(condition.getConditionId());
		try {
			DAOFactory.getInstance().getToolItemDAO().update(toolItem);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableToolItems.refresh();
	}
	// Event Listener on Button[#btnAdd].onAction
	@FXML
	public void addToolItem(ActionEvent event) {
		Tool tool = comboBoxToolAdd.getSelectionModel().getSelectedItem();
		long milis = System.currentTimeMillis();
		
		
		LocalDate ld = LocalDate.now();
		LocalDate localDate = LocalDate.ofYearDay(ld.getYear()+1, 31);
		Instant instant = null;
		Date date = null;
		if(localDate!=null) {
			instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			date = Date.from(instant);
		}
		ToolItem toolItem = new ToolItem(null,date,tool,tool.getToolId(),2,false);
		try {
			DAOFactory.getInstance().getToolItemDAO().insert(toolItem);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listToolItems.add(toolItem);
		tool.setCount(tool.getCount()+1);
		lblCount.setText(tool.getCount().toString());
	}
	// Event Listener on Button[#btnAddActivity].onAction
	@FXML
	public void addActivity(ActionEvent event) {
		// TODO Autogenerated
		ToolItem toolItem = tableToolItems.getSelectionModel().getSelectedItem();
		LocalDate localDate = datePicker.getValue();
		try {
			Double amount = Double.parseDouble(txtAmount.getText());
		}catch(NumberFormatException ex){
			Alert alert = new Alert(AlertType.INFORMATION,"Niste ispravno unijeli iznos!");
			alert.showAndWait();
			return;
		}
		boolean service = checkBoxService.isSelected() ? true : false;
		String description = txtDescription.getText();
		//ToolMaintanceActivity activity = new ToolMaintanceActivity(description,);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ToggleGroup group = new ToggleGroup();
		radioMachine.setToggleGroup(group);
		radioTool.setToggleGroup(group);
		radioMachine.setSelected(true);
		try {
			listTool = (ObservableList<Tool>)DAOFactory.getInstance().getToolDAO().selectAll();
			comboBoxTool.setItems(listTool);
			comboBoxTool.getSelectionModel().select(0);
			comboBoxToolAdd.setItems(listTool);
			comboBoxToolAdd.getSelectionModel().select(0);
			listConditions = (ObservableList<Condition>) DAOFactory.getInstance().getConditionDAO().selectAll();
			comboBoxCondition.setItems(listConditions);
			comboBoxCondition.getSelectionModel().select(0);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableColumnId.setCellValueFactory(new PropertyValueFactory<ToolItem, Integer>("toolItemId"));
		tableColumnCondition.setCellValueFactory(new PropertyValueFactory<ToolItem,String>("condition"));
		tableColumnAmount.setCellValueFactory(new PropertyValueFactory<ToolMaintanceActivity,Double>("amount"));
		tableColumnDate.setCellValueFactory(new PropertyValueFactory<ToolMaintanceActivity,String>("date"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<ToolMaintanceActivity,String>("description"));
		
		tableColumnNextServiceDate.setCellFactory(column->{
		return new TableCell<ToolItem,Date>() {
				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if(item==null) {
						setText(null);
						setStyle("");
					}else {
						setText(new SimpleDateFormat("dd-MM-yyyy").format(item));
						if(item.before(new Date())) {
							setStyle("-fx-background-color: red");
						}
					}
				}
			};
		});
		tableColumnNextServiceDate.setCellValueFactory(new PropertyValueFactory<ToolItem,Date>("nextServiceDate"));

		selectToolItems();
	}
	public void showActivities() throws DAOException {
		Integer id=null;
		if(tableToolItems.getSelectionModel().getSelectedItem()!=null) {
			id = tableToolItems.getSelectionModel().getSelectedItem().getToolItemId();
		}
		Object[] pomActivity = {id};
		if(pomActivity[0]!=null) {
			listActivities = (ObservableList<ToolMaintanceActivity>)DAOFactory.getInstance().getToolMaintanceActivityDAO().select(" tool_item_id=?", pomActivity);
			tableActivities.setItems(listActivities);
			tableActivities.getSelectionModel().select(0);
		}
	}
}