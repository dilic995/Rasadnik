package org.unibl.etf.dto;

import java.util.Date;

public class Pricelist {
	private Integer pricelistId;
	private Date dateFrom;
	private Date dateTo;
	private Boolean active;

	//
	// getters / setters
	//
	public Integer getPricelistId() {
		return this.pricelistId;
	}

	public void setPricelistId(Integer pricelistId) {
		this.pricelistId = pricelistId;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Pricelist other = (Pricelist) obj;
		if (pricelistId == null) {
			if (other.pricelistId != null)
				return false;
		} else if (!pricelistId.equals(other.pricelistId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Pricelist").append("(");

		buffer.append("[").append("pricelistId").append("=").append(pricelistId).append("]");
		buffer.append("[").append("dateFrom").append("=").append(dateFrom).append("]");
		buffer.append("[").append("dateTo").append("=").append(dateTo).append("]");
		buffer.append("[").append("active").append("=").append(active).append("]");

		return buffer.append(")").toString();
	}
}
