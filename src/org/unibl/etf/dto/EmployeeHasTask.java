package org.unibl.etf.dto;

import java.math.BigDecimal;
import java.util.Date;

public class EmployeeHasTask {
	private BigDecimal hourlyWage;
	private Integer hours;
	private Boolean paidOff;
	private Date date;
	private Integer taskId;
	private Task task;
	private Integer employeeId;
	private Employee employee;

	//
	// getters / setters
	//

	public BigDecimal getHourlyWage() {
		return this.hourlyWage;
	}

	public void setHourlyWage(BigDecimal hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

	public Integer getHours() {
		return this.hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Boolean getPaidOff() {
		return this.paidOff;
	}

	public void setPaidOff(Boolean paidOff) {
		this.paidOff = paidOff;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
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
		EmployeeHasTask other = (EmployeeHasTask) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("EmployeeHasTask").append("(");

		buffer.append("[").append("hourlyWage").append("=").append(hourlyWage).append("]");
		buffer.append("[").append("hours").append("=").append(hours).append("]");
		buffer.append("[").append("paidOff").append("=").append(paidOff).append("]");
		buffer.append("[").append("date").append("=").append(date).append("]");
		buffer.append("[").append("taskId").append("=").append(taskId).append("]");
		buffer.append("[").append("employeeId").append("=").append(employeeId).append("]");

		return buffer.append(")").toString();
	}
}
