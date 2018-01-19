package org.unibl.etf.dto;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeTableItem {
	private Employee employee;
	private IntegerProperty id;
	private StringProperty firstName;
	private StringProperty lastName;

	public EmployeeTableItem() {
		super();
	}

	public EmployeeTableItem(Employee employee) throws NullPointerException {
		if (employee == null) {
			throw new NullPointerException();
		} else {
			this.employee = employee;
			this.id = new SimpleIntegerProperty(employee.getEmployeeId());
			this.firstName = new SimpleStringProperty(employee.getFirstName());
			this.lastName = new SimpleStringProperty(employee.getLastName());
		}
	}
	
	public static List<EmployeeTableItem> convert(List<Employee> list) {
		List<EmployeeTableItem> retVal = new ArrayList<>();
		for(Employee employee: list) {
			retVal.add(new EmployeeTableItem(employee));
		}
		return retVal;
	}
	
	//properties
	public IntegerProperty idProperty() {
		return id;
	}
	public StringProperty firstNameProperty() {
		return firstName;
	}
	public StringProperty lastNameProperty() {
		return lastName;
	}

	//geteri
	public Employee getEmployee() {
		return employee;
	}
	public Integer getId() {
		return id.getValue();
	}
	public String getFirstName() {
		return firstName.getValue();
	}
	public String getLastName() {
		return lastName.getValue();
	}

	//seteri
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public void setId(Integer id) {
		this.id.set(id);
	}
	public void setFirstName(String firstName) {
		this.employee.setFirstName(firstName);
		this.firstName.set(firstName);
	}
	public void setLastName(String lastName) {
		this.employee.setLastName(lastName);
		this.lastName.set(lastName);
	}
	
	public void update() throws DAOException {
		DAOFactory.getInstance().getEmployeeDAO().update(this.employee);
	}

	
}
