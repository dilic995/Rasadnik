package org.unibl.etf.dto;

import java.math.BigDecimal;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SaleItemTableItem {

	public SaleItemTableItem(SaleItem item) {
		this.item = item;
		this.plant = new SimpleStringProperty(item.getPlant().getScientificName());
		this.height = new SimpleStringProperty(item.getHeightMin().toPlainString());
		this.count = new SimpleIntegerProperty(item.getCount());
		this.price = new SimpleStringProperty(item.getPrice().toPlainString());
	}

	private SaleItem item;
	private StringProperty plant;
	private StringProperty height;
	private IntegerProperty count;
	private StringProperty price;

	public final StringProperty plantProperty() {
		return this.plant;
	}

	public final Plant getPlant() {
		return this.item.getPlant();
	}

	public final StringProperty heightProperty() {
		return this.height;
	}

	public final BigDecimal getHeight() {
		return this.item.getHeightMin();
	}

	public final IntegerProperty countProperty() {
		return this.count;
	}

	public final int getCount() {
		return this.item.getCount();
	}

	public final StringProperty priceProperty() {
		return this.price;
	}

	public final BigDecimal getPrice() {
		return this.item.getPrice();
	}

}
