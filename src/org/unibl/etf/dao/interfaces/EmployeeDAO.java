package org.unibl.etf.dao.interfaces;

import java.util.List;

import org.unibl.etf.dto.Employee;

public interface EmployeeDAO {
	// CRUD methods
	public Employee getByPrimaryKey(Integer employeeId);

	public List<Employee> selectAll();

	public List<Employee> select(String whereStatement, Object[] bindVariables);

	public long selectCount();

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(Employee obj);

	public int insert(Employee obj);

	public int delete(Employee obj);

	// Finders
	public List<Employee> getByFirstName(String firstName);

	public List<Employee> getByLastName(String lastName);
	
	public List<Employee> getByIsDeleted(Boolean isDeleted);
}
