package org.unibl.etf.dto;

public class Employee {
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private Boolean deleted;

	public Employee() {
		super();
	}

	public Employee(Integer employeeId, String firstName, String lastName) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	//
	// getters / setters
	//
	public Integer getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
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
		Employee other = (Employee) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Employee").append("(");

		buffer.append("[").append("employeeId").append("=").append(employeeId).append("]");
		buffer.append("[").append("firstName").append("=").append(firstName).append("]");
		buffer.append("[").append("lastName").append("=").append(lastName).append("]");
		buffer.append("[").append("isDeleted").append("=").append(deleted).append("]");

		return buffer.append(")").toString();
	}
}
