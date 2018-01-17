package org.unibl.etf.tool.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.application.BaseController;
import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dao.interfaces.ToolDAO;
import org.unibl.etf.dto.Condition;
import org.unibl.etf.dto.Tool;
import org.unibl.etf.dto.ToolItem;
import org.unibl.etf.dto.ToolMaintanceActivity;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
	private TableColumn<ToolItem, String> tableColumnNextServiceDate;
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
	public void selectToolItems(ActionEvent event) {
		try {
			listToolItems =(ObservableList<ToolItem>) DAOFactory.getInstance().getToolItemDAO().select(" tool_id="+comboBoxTool.getSelectionModel().getSelectedItem().getToolId(), null);
			tableToolItems.setItems(listToolItems);
			tableToolItems.getSelectionModel().select(0);
			listActivities = (ObservableList<ToolMaintanceActivity>)DAOFactory.getInstance().getToolMaintanceActivityDAO().select(" tool_item_id="+tableToolItems.getSelectionModel().getSelectedItem().getToolItemId(), null);
			tableActivities.setItems(listActivities);
			tableActivities.getSelectionModel().select(0);
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
		Tool tool = new Tool(null,name,0);
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
		//ubaciti flag u bazu da se moze obrisati
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
		toolItem.setCondition(comboBoxCondition.getSelectionModel().getSelectedItem());
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
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnAddActivity].onAction
	@FXML
	public void addActivity(ActionEvent event) {
		// TODO Autogenerated
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ToggleGroup group = new ToggleGroup();
		radioMachine.setToggleGroup(group);
		radioTool.setToggleGroup(group);
		try {
			listTool = (ObservableList<Tool>)DAOFactory.getInstance().getToolDAO().selectAll();
			listConditions = (ObservableList<Condition>)DAOFactory.getInstance().getConditionDAO().selectAll();
			comboBoxCondition.setItems(listConditions);
			comboBoxCondition.getSelectionModel().select(0);
			comboBoxTool.setItems(listTool);
			comboBoxToolAdd.setItems(listTool);
			comboBoxTool.getSelectionModel().select(0);
			comboBoxToolAdd.getSelectionModel().select(0);
			listToolItems =(ObservableList<ToolItem>) DAOFactory.getInstance().getToolItemDAO().select(" tool_id="+comboBoxTool.getSelectionModel().getSelectedItem().getToolId(), null);
			tableToolItems.setItems(listToolItems);
			tableToolItems.getSelectionModel().select(0);
			listActivities = (ObservableList<ToolMaintanceActivity>)DAOFactory.getInstance().getToolMaintanceActivityDAO().select(" tool_item_id="+tableToolItems.getSelectionModel().getSelectedItem().getToolItemId(), null);
			tableActivities.setItems(listActivities);
			tableActivities.getSelectionModel().select(0);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
