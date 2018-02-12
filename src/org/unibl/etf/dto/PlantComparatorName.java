package org.unibl.etf.dto;

import java.util.Comparator;

public class PlantComparatorName implements Comparator<Plant>{

	@Override
	public int compare(Plant o1, Plant o2) {
		return o1.getScientificName().compareTo(o2.getScientificName());
	}

}
