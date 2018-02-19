// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.interfaces;

import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.Task;

public interface TaskDAO {
	// CRUD methods
	public Task getByPrimaryKey(Integer taskId);

	public List<Task> selectAll();

	public List<Task> select(String whereStatement, Object[] bindVariables);

	public Long selectCount();

	public Long selectCount(String whereStatement, Object[] bindVariables);

	public Integer update(Task obj);

	public Integer insert(Task obj);

	public Integer delete(Task obj);

	// Finders
	public List<Task> getByDateFrom(Date dateFrom);

	public List<Task> getByDateTo(Date dateTo);

	public List<Task> getByDone(Boolean done);

	public List<Task> getByIsDeleted(Boolean isDeleted);

	public List<Task> getByRegionId(Integer regionId);
	
	public List<Task> getByPlanId(Integer planId);

	public List<Task> getByPlantMaintanceActivityId(Integer plantMaintanceActivityId);
}
