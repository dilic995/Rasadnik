package org.unibl.etf.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlantTableItem {

	public PlantTableItem() {
	}

	public PlantTableItem(Plant plant) {
		this.plant = plant;
		this.scientificName = new SimpleStringProperty(plant.getScientificName());
		this.knownAs = new SimpleStringProperty(plant.getKnownAs());
		this.prices = new SimpleStringProperty(getPricesString());
	}

	public StringProperty scientificNameProperty() {
		return this.scientificName;
	}

	public StringProperty knownAsProperty() {
		return this.knownAs;
	}

	public StringProperty pricesProperty() {
		return this.prices;
	}

	public String getScientificName() {
		return this.scientificNameProperty().get();
	}

	public String getKnownAs() {
		return this.knownAsProperty().get();
	}

	public String getPrices() {
		return this.pricesProperty().get();
	}

	public void setScientificName(String scientificName) {
		this.plant.setScientificName(scientificName);
		this.scientificNameProperty().set(scientificName);
	}

	public final void setKnownAs(String knownAs) {
		this.plant.setKnownAs(knownAs);
		this.knownAsProperty().set(knownAs);
	}

	public final void setPrices(String prices) {
		this.pricesProperty().set(prices);
	}

	private String getPricesString() {
		String res = "";
		for (PriceHeightRatio ratio : plant.getRatios()) {
			res += "Od " + String.format("%04.1f", ratio.getHeightMin()) + " cm do ";
			res += (ratio.getHeightMax() == null ? "       " : String.format("%04.1f", ratio.getHeightMax()))
					+ " cm:  ";
			res += String.format("%04.1f", ratio.getPrice().doubleValue()) + " KM";
			if (!ratio.equals(plant.getRatios().get(plant.getRatios().size() - 1))) {
				res += System.getProperty("line.separator");
			}
		}
		return res;
	}

	private Plant plant;
	private StringProperty scientificName;
	private StringProperty knownAs;
	private StringProperty prices;

}
