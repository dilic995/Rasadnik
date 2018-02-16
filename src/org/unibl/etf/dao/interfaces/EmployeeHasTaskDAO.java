package org.unibl.etf.dao.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.EmployeeHasTask;

public interface EmployeeHasTaskDAO {
	// CRUD methods
	public EmployeeHasTask getByPrimaryKey(Date date, Integer taskId, Integer employeeId);

	public List<EmployeeHasTask> selectAll();

	public List<EmployeeHasTask> select(String whereStatement, Object[] bindVariables);

	public long selectCount();

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(EmployeeHasTask obj);

	public int insert(EmployeeHasTask obj);

	public int delete(EmployeeHasTask obj);

	// Finders
	public List<EmployeeHasTask> getByDate(Date date);

	public List<EmployeeHasTask> getByTaskId(Integer taskId);

	public List<EmployeeHasTask> getByEmployeeId(Integer employeeId);

	public List<EmployeeHasTask> getByHourlyWage(BigDecimal hourlyWage);

	public List<EmployeeHasTask> getByHours(Integer hours);

	public List<EmployeeHasTask> getByPaidOff(Boolean paidOff);
	
	public List<EmployeeHasTask> getByDeleted(Boolean deleted);
}
