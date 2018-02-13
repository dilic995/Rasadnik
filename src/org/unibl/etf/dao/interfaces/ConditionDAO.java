package org.unibl.etf.dao.interfaces;

import java.util.List;

import org.unibl.etf.dto.Condition;

public interface ConditionDAO {
	// CRUD methods
	public Condition getByPrimaryKey(Integer conditionId);

	public List<Condition> selectAll();

	public List<Condition> select(String whereStatement, Object[] bindVariables);

	public long selectCount();

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(Condition obj);

	public int insert(Condition obj);

	public int delete(Condition obj);

	// Finders
	public List<Condition> getByCondition(String condition);
}
