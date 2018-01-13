// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dto;

public class PricelistHasPlant {

	private Pricelist pricelist;
	private Plant plant;
	private Integer pricelistId;
	private Integer plantId;

	//
	// getters / setters
	//
	public Integer getPricelistId() {
		return pricelistId;
	}

	public void setPricelistId(Integer pricelistId) {
		this.pricelistId = pricelistId;
	}

	public Integer getPlantId() {
		return plantId;
	}

	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}

	public Pricelist getPricelist() {
		return this.pricelist;
	}

	public void setPricelist(Pricelist pricelist) {
		this.pricelist = pricelist;
	}

	public Plant getPlant() {
		return this.plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((plantId == null) ? 0 : plantId.hashCode());
		result = prime * result + ((pricelistId == null) ? 0 : pricelistId.hashCode());
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
		PricelistHasPlant other = (PricelistHasPlant) obj;
		if (plantId == null) {
			if (other.plantId != null)
				return false;
		} else if (!plantId.equals(other.plantId))
			return false;
		if (pricelistId == null) {
			if (other.pricelistId != null)
				return false;
		} else if (!pricelistId.equals(other.pricelistId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("PricelistHasPlant").append("(");

		buffer.append("[").append("pricelistId").append("=").append(pricelistId).append("]");
		buffer.append("[").append("plantId").append("=").append(plantId).append("]");
		buffer.append("[").append("pricelist").append("=").append(pricelist).append("]");
		buffer.append("[").append("plant").append("=").append(plant).append("]");

		return buffer.append(")").toString();
	}
}
