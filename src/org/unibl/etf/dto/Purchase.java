// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;

public class Purchase {
	private Integer purchaseId;
	private Date date;
	private String description;
	private BigDecimal price;
	private Boolean paidOff;
	private Customer customer;
	private Integer customerId;

	//
	// getters / setters
	//
	public Integer getPurchaseId() {
		return this.purchaseId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
		this.customer = null;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Boolean getPaidOff() {
		return this.paidOff;
	}

	public void setPaidOff(Boolean paidOff) {
		this.paidOff = paidOff;
	}

	public Customer getCustomer() {
		if (customer == null) {
			try {
				this.customer = DAOFactory.getInstance().getCustomerDAO().getByPrimaryKey(customerId);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
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
		Purchase other = (Purchase) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Purchase").append("(");

		buffer.append("[").append("purchaseId").append("=").append(purchaseId).append("]");
		buffer.append("[").append("date").append("=").append(date).append("]");
		buffer.append("[").append("description").append("=").append(description).append("]");
		buffer.append("[").append("price").append("=").append(price).append("]");
		buffer.append("[").append("paidOff").append("=").append(paidOff).append("]");
		buffer.append("[").append("customerId").append("=").append(customerId).append("]");

		return buffer.append(")").toString();
	}
}
