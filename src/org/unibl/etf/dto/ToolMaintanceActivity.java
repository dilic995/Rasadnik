// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dto;

import java.math.BigDecimal;

import java.sql.Clob;
import java.util.Date;

public class ToolMaintanceActivity {
	private Clob description;
	private BigDecimal amount;
	private Date date;
	private ToolItem toolItem;
	private Integer toolItemId;
	private Boolean upToDateService;
	

	

	//
	// getters / setters
	//
	public Boolean getUpToDateService() {
		return upToDateService;
	}

	public void setUpToDateService(Boolean upToDateService) {
		this.upToDateService = upToDateService;
	}
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getToolItemId() {
		return this.toolItemId;
	}

	public ToolItem getToolItem() {
		return toolItem;
	}

	public void setToolItem(ToolItem toolItem) {
		this.toolItem = toolItem;
	}

	public void setToolItemId(Integer toolItemId) {
		this.toolItemId = toolItemId;
	}

	public Clob getDescription() {
		return this.description;
	}

	public void setDescription(Clob description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((toolItemId == null) ? 0 : toolItemId.hashCode());
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
		ToolMaintanceActivity other = (ToolMaintanceActivity) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (toolItemId == null) {
			if (other.toolItemId != null)
				return false;
		} else if (!toolItemId.equals(other.toolItemId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("ToolMaintanceActivity")
				.append("(");

		buffer.append("[").append("date").append("=").append(date).append("]");
		buffer.append("[").append("toolItemId").append("=").append(toolItemId).append("]");
		buffer.append("[").append("description").append("=").append(description).append("]");
		buffer.append("[").append("amount").append("=").append(amount).append("]");

		return buffer.append(")").toString();
	}
}
