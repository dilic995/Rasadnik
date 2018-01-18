package org.unibl.etf.dto;

public class HeightPriceRatio {
	private Double minHeight;
	private Double maxHeight;
	private Double price;
	public HeightPriceRatio() {
	}
	public HeightPriceRatio(Double minHeight, Double maxHeight, Double price) {
		super();
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.price = price;
	}
	public Double getMinHeight() {
		return minHeight;
	}
	public void setMinHeight(Double minHeight) {
		this.minHeight = minHeight;
	}
	public Double getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(Double maxHeight) {
		this.maxHeight = maxHeight;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maxHeight == null) ? 0 : maxHeight.hashCode());
		result = prime * result + ((minHeight == null) ? 0 : minHeight.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HeightPriceRatio other = (HeightPriceRatio) obj;
		if (maxHeight == null) {
			if (other.maxHeight != null)
				return false;
		} else if (!maxHeight.equals(other.maxHeight))
			return false;
		if (minHeight == null) {
			if (other.minHeight != null)
				return false;
		} else if (!minHeight.equals(other.minHeight))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
	
}
