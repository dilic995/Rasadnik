package org.unibl.etf.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MaintenancePlan {

	public MaintenancePlan() {
	}
	
	public MaintenancePlan(Plan plan) {
	}

	public MaintenancePlan(LocalDate dateFrom, LocalDate dateTo, Map<Region, MaintenancePlanItem> plannedTasks, Boolean active) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.plannedTasks = plannedTasks;
		this.active = active;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public Map<Region, MaintenancePlanItem> getPlannedTasks() {
		return plannedTasks;
	}

	public void setPlannedTasks(Map<Region, MaintenancePlanItem> plannedTasks) {
		this.plannedTasks = plannedTasks;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
		if(plannedTasks != null)
			updateState();
	}
	
	public void addTask(Region region, Task task) {
		MaintenancePlanItem item = plannedTasks.get(region);
		if(item == null) {
			item = new MaintenancePlanItem(region, new HashSet<Task>());
		}
		item.addTask(task);
		item.updateState(active);
	}
	public Boolean removeTask(Region region, Task task) {
		Boolean result = false;
		MaintenancePlanItem item = plannedTasks.get(region);
		if(item != null) {
			result = item.removeTask(task);
		}
		item.updateState(active);
		return result;
	}
	
	public void setDone(Region region, Task task, Boolean done) {
		MaintenancePlanItem item = plannedTasks.get(region);
		if(item != null) {
			item.setDone(task, done);
			item.updateState(active);
		}
	}
	
	private void updateState() {
		plannedTasks.values().forEach(x -> x.updateState(active));
	}
	
	@Override
	public String toString() {
		String res = "";
		for(Iterator<MaintenancePlanItem> it = plannedTasks.values().iterator() ; it.hasNext() ;) {
			res += "\n" + it.next();
		}
		return res;
	}
	
	public static List<MaintenancePlan> convert(List<Plan> list) {
		ArrayList<MaintenancePlan> result = new ArrayList<>();
		for(Plan plan : list) {
			result.add(new MaintenancePlan(dateFrom, dateTo, plannedTasks, active))
		}
	}
	
	private Plan plan;
	private Map<Region, MaintenancePlanItem> plannedTasks;
	
}
