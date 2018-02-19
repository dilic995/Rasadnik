package org.unibl.etf.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TaskTableItem {
	
	private Task task;
	private StringProperty activity;
	private StringProperty date_from;
	private StringProperty date_to;
	private BooleanProperty done;
	
	
	public TaskTableItem() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TaskTableItem(Task task) {
		super();
		this.task = task;
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		activity = new SimpleStringProperty(task.getPlantMaintanceActivity().getActivity());
		date_from = new SimpleStringProperty(df.format(task.getDateFrom()));
		date_to = new SimpleStringProperty(df.format(task.getDateTo()));
		done = new SimpleBooleanProperty(task.getDone());
	}


	//geteri
	public Task getTask() {
		return task;
	}
	public String getActivity() {
		return task.getPlantMaintanceActivity().getActivity();
	}
	public Date getDate_from() {
		return task.getDateFrom();
	}
	public Date getDate_to() {
		return task.getDateTo();
	}
	public Boolean getDone() {
		return task.getDone();
	}
	public StringProperty getActivityProperty() {
		return activity;
	}
	public StringProperty getDate_fromProperty() {
		return date_from;
	}
	public StringProperty getDate_toProperty() {
		return date_to;
	}
	public BooleanProperty getDoneProperty() {
		return done;
	}


	//seteri
	public void setTask(Task task) {
		this.task = task;
	}
	public void setActivityProperty(StringProperty activity) {
		this.activity = activity;
	}
	public void setDate_fromProperty(StringProperty date_from) {
		this.date_from = date_from;
	}
	public void setDate_toProperty(StringProperty date_to) {
		this.date_to = date_to;
	}
	public void setDoneProperty(BooleanProperty done) {
		this.done = done;
	}
	public void setActivity(String activity) {
		task.getPlantMaintanceActivity().setActivity(activity);
	}
	public void setDate_from(Date date_from) {
		task.setDateFrom(date_from);
	}
	public void setDate_to(Date date_to) {
		task.setDateTo(date_to);
	}
	public void setDone(Boolean done) {
		task.setDone(done);
	}
	
	
	
}
