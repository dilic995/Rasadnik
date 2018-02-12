package org.unibl.etf.dto;

import java.util.List;

public class Estate {

	public Estate() {
	}

	public Estate(List<Region> regions) {
		super();
		this.regions = regions;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public boolean add(Region region) {
		boolean result = false;
		if (!regions.contains(region)) {
			regions.add(region);
			result = true;
		}
		return result;
	}

	public Region remove(Region region) {
		Region result = null;
		if (regions.contains(region)) {
			result = regions.remove(regions.indexOf(region));
		}
		return result;
	}

	public void addPlants(Region region, int num) {
		int index = regions.indexOf(region);
		if (index > -1) {
			Region target = regions.get(index);
			target.addPlants(num);
		}
	}

	public boolean removePlants(Region region, int num) {
		boolean result = false;
		int index = regions.indexOf(region);
		if (index > -1) {
			Region target = regions.get(index);
			if (target.removePlants(num)) {
				result = true;
			}
		}
		return result;
	}

	public void print() {
		for (Region reg : regions) {
			System.out.println(reg.getBasis().getPlant().getScientificName() + " " + reg.getNumberOfPlants());
		}
	}

	private List<Region> regions;
}
