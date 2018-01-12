package org.unibl.etf.dao.interfaces;

import org.unibl.etf.dto.EmployeeHasTask;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

public interface EmployeeHasTaskDAO {
	// CRUD methods
	public EmployeeHasTask getByPrimaryKey(Date date, Integer taskId, Integer employeeId) throws DAOException;

	public List<EmployeeHasTask> selectAll() throws DAOException;

	public List<EmployeeHasTask> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(EmployeeHasTask obj) throws DAOException;

	public int insert(EmployeeHasTask obj) throws DAOException;

	public int delete(EmployeeHasTask obj) throws DAOException;

	// Finders
	public List<EmployeeHasTask> getByDate(Date date) throws DAOException;

	public List<EmployeeHasTask> getByTaskId(int taskId) throws DAOException;

	public List<EmployeeHasTask> getByEmployeeId(int employeeId) throws DAOException;

	public List<EmployeeHasTask> getByHourlyWage(BigDecimal hourlyWage) throws DAOException;

	public List<EmployeeHasTask> getByHours(int hours) throws DAOException;

	public List<EmployeeHasTask> getByPaidOff(byte paidOff) throws DAOException;
}
