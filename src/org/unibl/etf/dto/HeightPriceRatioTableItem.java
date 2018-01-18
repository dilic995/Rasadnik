package org.unibl.etf.dto;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HeightPriceRatioTableItem {
	private HeightPriceRatio ratio;
	private DoubleProperty minHeight;
	private StringProperty maxHeight;
	private DoubleProperty price;

	public HeightPriceRatioTableItem() {

	}
	public HeightPriceRatioTableItem(HeightPriceRatio ratio) {
		this.ratio = ratio;
		minHeight = new SimpleDoubleProperty(ratio.getMinHeight());
		maxHeight = new SimpleStringProperty(ratio.getMaxHeight() == null ? "-" : ratio.getMaxHeight()+ "");
		price = new SimpleDoubleProperty(ratio.getPrice());
	}
	
	// properties
	public DoubleProperty minHeightProperty() {
		return minHeight;
	}
	public StringProperty maxHeightProperty() {
		return maxHeight;
	}
	public DoubleProperty priceProperty() {
		return price;
	}
	// getteri
	public Double getMinHeight() {
		return minHeight.get();
	}
	public Double getMaxHeight() {
		return ratio.getMaxHeight();
	}
	public Double getPrice() {
		return price.get();
	}
	// setteri
	public void setMinHeight(Double minHeight) {
		ratio.setMinHeight(minHeight);
		this.minHeight.set(minHeight);
	}
	public void setMaxHeight(String maxHeight) {
		this.maxHeight.set(maxHeight);
		if(!"-".equals(maxHeight)) {
			ratio.setMaxHeight(Double.parseDouble(maxHeight));
		}
	}
	public void setPrice(Double price) {
		ratio.setPrice(price);
		this.price.set(price);
	}
}
