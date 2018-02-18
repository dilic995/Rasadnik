package org.unibl.etf.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Pricelist {
	public Pricelist() {
		// TODO Auto-generated constructor stub
	}
	public Pricelist(Integer pricelistId, Date dateFrom, Date dateTo, Boolean active, Boolean deleted) {
		super();
		this.pricelistId = pricelistId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.active = active;
		this.deleted = deleted;
	}

	private Integer pricelistId;
	private Date dateFrom;
	private Date dateTo;
	private Boolean active;
	private Boolean deleted;
	
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		return pricelistId + ": " + format.format(dateFrom) + " - " + (dateTo == null ? "" : format.format(dateTo));
	}
}
