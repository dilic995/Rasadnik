package org.unibl.etf.dto;

import java.util.List;

public class Catalogue {
	
	public Catalogue() {
	}
	

	public Catalogue(List<Plant> plants) {
		super();
		this.plants = plants;
	}



	private List<Plant> plants;
}
