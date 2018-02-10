package org.unibl.etf.dto;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class MaintenancePlanItem {

	public MaintenancePlanItem() {
	}

	public MaintenancePlanItem(Region region, Set<Task> plannedTasks) {
		super();
		this.region = region;
		this.plannedTasks = plannedTasks;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Set<Task> getPlannedTasks() {
		return plannedTasks;
	}

	public void setPlannedTasks(Set<Task> plannedTasks) {
		this.plannedTasks = plannedTasks;
	}

	public void addTask(Task task) {
		if(plannedTasks == null) {
			plannedTasks = new HashSet<Task>();
		}
		plannedTasks.add(task);
	}

	public Boolean removeTask(Task task) {
		Boolean result = false;
		if (plannedTasks != null) {
			result = plannedTasks.remove(task);
		}
		return result;
	}

	public void setDone(Task task, Boolean done) {
		if(plannedTasks != null) {
			Optional<Task> res = plannedTasks.stream().filter(x->task.getTaskId()==x.getTaskId()).findFirst();
			if(res != null) {
				res.get().setDone(done);
			}
		}
	}
	@Override
	public String toString() {
		String res = "Region " + region.getRegionId();
		for(Iterator<Task> it = plannedTasks.iterator() ; it.hasNext() ; ) {
			Task t = it.next();
			res += "\n" + t.getPlantMaintanceActivity().getActivity() + " - " + t.getDone();
		}
		return res;
	}
	private Region region;
	private Set<Task> plannedTasks;
}
