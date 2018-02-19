package org.unibl.etf.dao.interfaces;

import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.Plan;

public interface PlanDAO {
	public Plan getByPrimaryKey(Integer planId);

	public List<Plan> selectAll();

	public List<Plan> select(String whereStatement, Object[] bindVariables);

	public Long selectCount();

	public Long selectCount(String whereStatement, Object[] bindVariables);

	public Integer update(Plan obj);

	public Integer insert(Plan obj);

	public Integer delete(Plan obj);

	// Finders
	public List<Plan> getByName(String name);
	
	public List<Plan> getByDateFrom(Date dateFrom);
	public List<Plan> getByDateTo(Date dateTo);
	public List<Plan> getByActive(Boolean active);	
	public List<Plan> getByDeleted(Boolean deleted);
}
