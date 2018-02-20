package org.unibl.etf.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InitialState extends State {

	public InitialState(String style) {
		super(style);
	}

	public InitialState() {
		super();
		style = "transparent-green";
	}

	
}
