package org.unibl.etf.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.unibl.etf.dao.interfaces.DAOFactory;

public class PriceHeightRatio {

	private BigDecimal heightMin;
	private BigDecimal heightMax;
	private BigDecimal price;
	private Boolean active;
	private Date dateFrom;
	private Integer plantId;
	private Plant plant;

	//
	// constuctors
	//
	// podrazumijevani
	public PriceHeightRatio() {
	}

	// svi parametri
	public PriceHeightRatio(BigDecimal heightMin, BigDecimal heightMax, BigDecimal price, Boolean active, Date dateFrom,
			Integer plantId, Plant plant) {
		super();
		this.heightMin = heightMin;
		this.heightMax = heightMax;
		this.price = price;
		this.active = active;
		this.dateFrom = dateFrom;
		this.plantId = plantId;
		this.plant = plant;
	}

	//
	// getters / setters
	//
	public BigDecimal getHeightMin() {
		return this.heightMin;
	}

	public void setHeightMin(BigDecimal heightMin) {
		this.heightMin = heightMin;
	}

	public BigDecimal getHeightMax() {
		return this.heightMax;
	}

	public void setHeightMax(BigDecimal heightMax) {
		this.heightMax = heightMax;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Integer getPlantId() {
		return this.plantId;
	}

	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
		this.plant = null;
	}

	public Plant getPlant() {
		if (this.plant == null) {
			this.plant = DAOFactory.getInstance().getPlantDAO().getByPrimaryKey(this.plantId);
		}

		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateFrom == null) ? 0 : dateFrom.hashCode());
		result = prime * result + ((plantId == null) ? 0 : plantId.hashCode());
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
		PriceHeightRatio other = (PriceHeightRatio) obj;
		if (dateFrom == null) {
			if (other.dateFrom != null)
				return false;
		} else if (!dateFrom.equals(other.dateFrom))
			return false;
		if (plantId == null) {
			if (other.plantId != null)
				return false;
		} else if (!plantId.equals(other.plantId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("PriceHeightRatio").append("(");

		buffer.append("[").append("heightMin").append("=").append(heightMin).append("]");
		buffer.append("[").append("heightMax").append("=").append(heightMax).append("]");
		buffer.append("[").append("price").append("=").append(price).append("]");
		buffer.append("[").append("active").append("=").append(active).append("]");
		buffer.append("[").append("dateFrom").append("=").append(dateFrom).append("]");
		buffer.append("[").append("plantId").append("=").append(plantId).append("]");

		return buffer.append(")").toString();
	}
}
