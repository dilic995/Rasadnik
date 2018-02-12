package org.unibl.etf.dto;

import java.math.BigDecimal;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PriceHeightRatioTableItem {

	public PriceHeightRatioTableItem() {
	}

	public PriceHeightRatioTableItem(PriceHeightRatio ratio) {
		this.ratio = ratio;
		minHeight = set(ratio.getHeightMin());
		maxHeight = set(ratio.getHeightMax());
		price = new SimpleDoubleProperty(ratio.getPrice().doubleValue());
	}

	// Properties
	public final StringProperty minHeightProperty() {
		return minHeight;
	}

	public final StringProperty maxHeightProperty() {
		return maxHeight;
	}

	public final DoubleProperty priceProperty() {
		return price;
	}

	// getters

	public BigDecimal getMinHeight() {
		return ratio.getHeightMin();
	}

	public BigDecimal getMaxHeight() {
		return ratio.getHeightMax();
	}

	public final BigDecimal getPrice() {
		return ratio.getPrice();
	}

	// setters

	public final void setMinHeight(BigDecimal minHeight) {
		ratio.setHeightMin(minHeight);
		this.minHeight = set(minHeight);
	}

	public void setMaxHeight(BigDecimal maxHeight) {
		ratio.setHeightMax(maxHeight);
		this.maxHeight = set(maxHeight);
	}

	public void setPrice(BigDecimal price) {
		ratio.setPrice(price);
		this.priceProperty().set(price.doubleValue());
	}

	private SimpleStringProperty set(BigDecimal value) {
		return new SimpleStringProperty(value == null ? "-" : value.doubleValue() + "");
	}

	private PriceHeightRatio ratio;
	private StringProperty minHeight;
	private StringProperty maxHeight;
	private DoubleProperty price;
}
