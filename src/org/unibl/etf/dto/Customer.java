package org.unibl.etf.dto;

public class Customer {
	private Integer customerId;
	private String firstName;
	private String lastName;
	private String address;
	private Boolean isSupplier;

	//
	// getters / setters
	//
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsSupplier() {
		return this.isSupplier;
	}

	public void setIsSupplier(Boolean isSupplier) {
		this.isSupplier = isSupplier;
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
		Customer other = (Customer) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Customer").append("(");

		buffer.append("[").append("customerId").append("=").append(customerId).append("]");
		buffer.append("[").append("firstName").append("=").append(firstName).append("]");
		buffer.append("[").append("lastName").append("=").append(lastName).append("]");
		buffer.append("[").append("address").append("=").append(address).append("]");
		buffer.append("[").append("isSupplier").append("=").append(isSupplier).append("]");

		return buffer.append(")").toString();
	}
}
