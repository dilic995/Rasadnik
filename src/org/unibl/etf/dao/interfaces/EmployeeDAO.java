package org.unibl.etf.dao.interfaces;

import java.util.List;

import org.unibl.etf.dto.Employee;

public interface EmployeeDAO {
	// CRUD methods
	public Employee getByPrimaryKey(Integer employeeId) throws DAOException;

	public List<Employee> selectAll() throws DAOException;

	public List<Employee> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(Employee obj) throws DAOException;

	public int insert(Employee obj) throws DAOException;

	public int delete(Employee obj) throws DAOException;

	// Finders
	public List<Employee> getByFirstName(String firstName) throws DAOException;

	public List<Employee> getByLastName(String lastName) throws DAOException;
	
	public List<Employee> getByIsDeleted(Boolean isDeleted) throws DAOException;
}
