package org.unibl.etf.gui.task.controller;


import java.awt.event.ItemListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.controlsfx.control.CheckListView;
import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Task;
import org.unibl.etf.dto.TaskListItem;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class RegionViewController extends BaseController implements Initializable{
	
	@FXML
	private CheckListView<TaskListItem> clvTasks;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeTaskList();
		populateTaskList(1);
	}
	
	public void saveTasksState() {
		System.out.println(clvTasks.getItems());
	}
	
	public void cancelTasksState() {
		System.out.println(taskList);
	}
	
	public void initializeTaskList() {
		taskList = FXCollections.observableArrayList();
		clvTasks.setItems(taskList);
		clvTasks.getCheckModel().getCheckedItems().addListener(new ListChangeListener<TaskListItem>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends TaskListItem> c) {
				c.next();
				if(c.wasAdded()) {
					System.out.println(c.getAddedSubList().get(0));
					c.getAddedSubList().get(0).setDone(true);
				} else if (c.wasRemoved()) {
					System.out.println(c.getAddedSubList().get(0));
					c.getAddedSubList().get(0).setDone(false);
				}
			}
		});
	}
	
	public void populateTaskList(int regionID) {
		taskList = FXCollections.observableArrayList(pullDataTasks(1));
		clvTasks.setItems(taskList);
		taskList.stream().forEach(x -> {
			if(x.getDone())
				clvTasks.checkModelProperty().get().check(x);
		});
		clvTasks.refresh();
	}
	
	public List<TaskListItem> pullDataTasks(int regionID) {
		List<TaskListItem> retVal = new ArrayList<>();
		try {
			List<Task> tmpList = DAOFactory.getInstance().getTaskDAO().getByRegionId(regionID);
			tmpList = tmpList.stream().filter(x -> !x.getIsDeleted().booleanValue()).collect(Collectors.toList());
			retVal = TaskListItem.convert(tmpList);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	ObservableList<TaskListItem> taskList;

}
