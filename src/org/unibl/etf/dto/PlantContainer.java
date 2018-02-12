package org.unibl.etf.dto;

import java.util.Collections;
import java.util.List;

public class PlantContainer extends Observable {

	public PlantContainer() {
	}

	public PlantContainer(List<Plant> plants) {
		setPlants(plants);
	}

	public List<Plant> getPlants() {
		return plants;
	}

	public void setPlants(List<Plant> plants) {
		this.plants = plants;
		Collections.sort(this.plants, new PlantComparatorName());
		this.current = plants == null ? -1 : (plants.size() > 0 ? 0 : -1);
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
		update();
	}

	public boolean addPlant(Plant plant) {
		Plant currentPlant = null;
		if(current > -1) {
			currentPlant = plants.get(current);
		}
		boolean result = false;
		if (!plants.contains(plant)) {
			plants.add(plant);
			Collections.sort(plants, new PlantComparatorName());
			if (plants.size() == 1) {
				this.current = 0;
			} else {
				this.current = plants.indexOf(currentPlant);
			}
			result = true;
		}
		update();
		return result;
	}

	public Plant remove(Plant plant) {
		Plant result = null;
		if (plants.contains(plant)) {
			result = plants.remove(plants.indexOf(plant));
			if (plants == null || plants.size() == 0) {
				this.current = -1;
			} else if(current == plants.size()) {
				--current;
			}
		}
		update();
		return result;
	}

	public Plant current() {
		return current == -1 ? null : plants.get(current);
	}

	public Plant next() {
		Plant result = null;
		if (current != -1) {
			current = (current + 1) % plants.size();
			result = plants.get(current);
		}
		update();
		return result;
	}

	public Plant previous() {
		Plant result = null;
		if (current != -1) {
			current = ((current - 1) + plants.size()) % plants.size();
			result = plants.get(current);
		}
		update();
		return result;
	}

	public Plant get(int index) {
		Plant result = null;
		if (index < plants.size()) {
			result = plants.get(index);
		}
		return result;
	}

	private List<Plant> plants;
	private int current;

	@Override
	public void update() {
		for (Observer observer : observers) {
			observer.update();
		}
	}
}
