package org.unibl.etf.gui.task.controller;


import java.util.Set;

import org.unibl.etf.dto.MaintenancePlan;
import org.unibl.etf.dto.Observable;
import org.unibl.etf.dto.Region;
import org.unibl.etf.dto.Task;

import javafx.scene.control.Label;

public class RegionPane extends Observable{
	
	private Label label;
	private Region region;
	private MaintenancePlan plan;
	
	
	public RegionPane(Label label, Region region, MaintenancePlan plan) {
		super();
		this.label = label;
		this.region = region;
		this.plan = plan;
	}

	public RegionPane(Label label) {
		super();
		this.label = label;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public MaintenancePlan getPlan() {
		return plan;
	}

	public void setPlan(MaintenancePlan plan) {
		this.plan = plan;
	}
	
	public void addTask(Task task) {
		plan.addTask(region, task);
		update();
	}

	public Boolean removeTask(Task task) {
		Boolean retVal = plan.removeTask(region, task);
		update();
		return retVal;
	}

	public void setDone(Task task, Boolean done) {
		plan.setDone(region, task, done);
		update();
	}
	
	public void update() {
		label.setText(plan.getPlannedTasks().get(region).getState().getName());
		observers.forEach(x-> x.update());
	}
	
	public Set<Task> getTasks() {
		return plan.getPlannedTasks().get(region).getPlannedTasks();
	}
}
