package org.unibl.etf.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InitialState extends State {

	public InitialState(String name) {
		super(name);
	}

	public InitialState() {
		super();
		name = "InitialState";
	}

	
}
