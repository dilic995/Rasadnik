package org.unibl.etf.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskListItem {
	private Task task;
	private Boolean changed;
	
	public TaskListItem(Task task) {
		super();
		this.task = task;
		this.changed = false;
	}
	
	
	//geteri
	public Boolean getDone() {
		return task.getDone();
	}
	public Task getTask() {
		return task;
	}
	public Date getDateFrom() {
		return this.task.getDateFrom();
	}
	public Date getDateTo() {
		return this.task.getDateTo();
	}
	public Boolean getIsDeleted() {
		return this.task.getIsDeleted();
	}
	public Integer getRegionId() {
		return this.task.getRegionId();
	}
	public Region getRegion() {
		return this.task.getRegion();
	}
	public String getActivity() {
		return this.task.getPlantMaintanceActivity().getActivity();
	}
	
	
	//seteri
	public void setTask(Task task) {
		this.task = task;
		this.changed = true;
	}
	public void setDone(Boolean done) {
		this.task.setDone(done);
		this.changed = changed?false:true;
	}
	public void setDateFrom(Date dateFrom) {
		this.task.setDateFrom(dateFrom);
		this.changed = true;
	}
	public void setDateTo(Date dateTo) {
		this.task.setDateTo(dateTo);
		this.changed = true;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.task.setIsDeleted(isDeleted);
		this.changed = true;
	}
	public void setChanged(Boolean changed) {
		this.changed = changed;
	}
	
	@Override
	public String toString() {
		return "TaskListItem [task=" + changed + "]";
	}
	
	public static List<TaskListItem> convert(List<Task> list) {
		List<TaskListItem> retVal = new ArrayList<>();
		list.stream().forEach(x -> retVal.add(new TaskListItem(x)));
		return retVal;
	}
}
