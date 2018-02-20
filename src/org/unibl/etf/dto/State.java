package org.unibl.etf.dto;

import java.util.List;
import java.util.stream.Collectors;

public abstract class State {

	protected String style;

	public State getNewState(MaintenancePlanItem item, Boolean active) {
		//TODO popraviti
		if (!active) {
			return new PartialFinishedState();
		}

		List<Task> list = item.getPlannedTasks().stream().filter(x -> x.getDone() == true).collect(Collectors.toList());
		if (list.size() == item.getPlannedTasks().size()) {
			return new FullyFinishedState();
		} else if (list.size() == 0) {
			return new InitialState();
		} else
			return new PartialActiveState();
	}
	
	public String getStyle() {
		return style;
	}

	public State(String style) {
		super();
		this.style = style;
	}

	public State() {
		super();
	}

}
