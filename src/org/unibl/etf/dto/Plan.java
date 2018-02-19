package org.unibl.etf.dto;

import java.util.Date;

public class Plan {
	private Integer planId;
	private String name;
	private Date dateFrom;
	private Date dateTo;
	private Boolean active;
	private Boolean deleted;
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((planId == null) ? 0 : planId.hashCode());
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
		Plan other = (Plan) obj;
		if (planId == null) {
			if (other.planId != null)
				return false;
		} else if (!planId.equals(other.planId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Plan").append("(");
		buffer.append("[").append("planId").append("=").append(planId).append("]");
		buffer.append("[").append("dateFrom").append("=").append(dateFrom).append("]");
		buffer.append("[").append("dateTo").append("=").append(dateTo).append("]");
		buffer.append("[").append("active").append("=").append(active).append("]");
		buffer.append("[").append("deleted").append("=").append(deleted).append("]");
		return buffer.append(")").toString();
	}
	
	
	
}
