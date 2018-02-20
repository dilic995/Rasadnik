package org.unibl.etf.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.unibl.etf.dao.interfaces.DAOFactory;

public class Plan {
	private Integer planId;
	private String name;
	private Date dateFrom;
	private Date dateTo;
	private Boolean active;
	private Boolean deleted;
	private Map<Region, MaintenancePlanItem> plannedTasks;

	public Plan() {
		super();
	}

	public Plan(Integer planId, String name, Date dateFrom, Date dateTo, Boolean active, Boolean deleted,
			Map<Region, MaintenancePlanItem> plannedTasks) {
		super();
		this.planId = planId;
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.active = active;
		this.deleted = deleted;
		this.plannedTasks = plannedTasks;
	}

	public Map<Region, MaintenancePlanItem> getTasks() {
		if (plannedTasks == null) {
			List<Task> lista = DAOFactory.getInstance().getTaskDAO().getByPlanId(planId);
			plannedTasks = new HashMap<Region, MaintenancePlanItem>();
			for(Task t : lista) {
				Region r = t.getRegion();
				if(!plannedTasks.containsKey(r)) {
					plannedTasks.put(r, new MaintenancePlanItem(r, new HashSet<Task>()));
				}
				plannedTasks.get(r).addTask(t);
			}
			plannedTasks.values().forEach(x -> x.updateState(active));
		}
		return plannedTasks;
	}

	public void setTasks(Map<Region, MaintenancePlanItem> tasks) {
		this.plannedTasks = tasks;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Boolean getActive() {
		return active;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
		if(plannedTasks != null)
			updateState();
	}
	
	public void updateState() {
		if(plannedTasks == null)
			getTasks();
		plannedTasks.values().forEach(x -> x.updateState(active));
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((planId == null) ? 0 : planId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plan other = (Plan) obj;
		if (planId == null) {
			if (other.planId != null)
				return false;
		} else if (!planId.equals(other.planId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		return name + " - [" + df.format(dateFrom) + " - " + (dateTo == null ? "" : df.format(dateTo)) + "]";
	}

}
