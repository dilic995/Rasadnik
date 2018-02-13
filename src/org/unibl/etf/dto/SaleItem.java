// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dto;

import org.unibl.etf.dao.interfaces.DAOFactory;

public class SaleItem {
	private Integer count;
	private Pricelist pricelist;
	private Plant plant;
	private Sale sale;
	private Integer pricelistId;
	private Integer plantId;
	private Integer saleId;

	//
	// getters / setters
	//
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Pricelist getPricelist() {
		if (pricelist == null) {
			pricelist = DAOFactory.getInstance().getPricelistDAO().getByPrimaryKey(pricelistId);
		}
		return pricelist;
	}

	public void setPricelist(Pricelist pricelist) {
		this.pricelist = pricelist;
	}

	public Plant getPlant() {
		if (plant == null) {
			plant = DAOFactory.getInstance().getPlantDAO().getByPrimaryKey(plantId);
		}
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public Sale getSale() {
		if (sale == null) {
			sale = DAOFactory.getInstance().getSaleDAO().getByPrimaryKey(saleId);
		}
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Integer getPricelistId() {
		return pricelistId;
	}

	public void setPricelistId(Integer pricelistId) {
		this.pricelistId = pricelistId;
		pricelist = null;
	}

	public Integer getPlantId() {
		return plantId;
	}

	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
		plant = null;
	}

	public Integer getSaleId() {
		return saleId;
	}

	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
		sale = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((plantId == null) ? 0 : plantId.hashCode());
		result = prime * result + ((pricelistId == null) ? 0 : pricelistId.hashCode());
		result = prime * result + ((saleId == null) ? 0 : saleId.hashCode());
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
		SaleItem other = (SaleItem) obj;
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
		if (saleId == null) {
			if (other.saleId != null)
				return false;
		} else if (!saleId.equals(other.saleId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("SaleItem").append("(");

		buffer.append("[").append("pricelistId").append("=").append(pricelistId).append("]");
		buffer.append("[").append("plantId").append("=").append(plantId).append("]");
		buffer.append("[").append("saleId").append("=").append(saleId).append("]");
		buffer.append("[").append("count").append("=").append(count).append("]");

		return buffer.append(")").toString();
	}

}
