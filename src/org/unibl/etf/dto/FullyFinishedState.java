package org.unibl.etf.dto;

import java.util.List;
import java.util.stream.Collectors;

public class FullyFinishedState extends State {

	public FullyFinishedState() {
		super();
		style = "transparent-red";
	}

	public FullyFinishedState(String style) {
		super(style);
		// TODO Auto-generated constructor stub
	}
	
	public State getNewState(MaintenancePlanItem item, Boolean active) {
		if(!active) {
			return new FullyFinishedState();
		}
		
		List<Task> list = item.getPlannedTasks().stream().filter(x -> x.getDone() == true).collect(Collectors.toList());
		if(list.size() == item.getPlannedTasks().size()) {
			return new FullyFinishedState();
		}
		else if (list.size() == 0){
			return new InitialState();
		}
		else 
			return new PartialActiveState();
	}

}
