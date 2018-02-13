package org.unibl.etf.gui.task.controller;


import java.util.Set;

import org.unibl.etf.dto.MaintenancePlanItem;
import org.unibl.etf.dto.Observable;
import org.unibl.etf.dto.Task;

import javafx.scene.control.Label;

public class RegionPane extends Observable{
	
	private Label label;
	private MaintenancePlanItem item;
	
	public RegionPane(Label label, MaintenancePlanItem item) {
		super();
		this.label = label;
		this.item = item;
	}
	
	public RegionPane(Label label) {
		super();
		this.label = label;
	}

	public MaintenancePlanItem getItem() {
		return item;
	}

	public void setItem(MaintenancePlanItem item) {
		this.item = item;
	}
	
	public void addTask(Task task) {
		item.addTask(task);
	}

	public Boolean removeTask(Task task) {
		return item.removeTask(task);
	}

	public void setDone(Task task, Boolean done) {
		item.setDone(task, done);
	}
	
	public void update() {
		label.setText(item.getState().getName());
		observers.forEach(x-> x.update());
	}
	
	public Set<Task> getTasks() {
		return item.getPlannedTasks();
	}
}
