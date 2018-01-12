package org.unibl.etf.dao.interfaces;

import org.unibl.etf.dto.Condition;

import java.util.List;

public interface ConditionDAO {
	// CRUD methods
	public Condition getByPrimaryKey(int conditionId) throws DAOException;

	public List<Condition> selectAll() throws DAOException;

	public List<Condition> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(Condition obj) throws DAOException;

	public int insert(Condition obj) throws DAOException;

	public int delete(Condition obj) throws DAOException;

	// Finders
	public List<Condition> getByCondition(String condition) throws DAOException;
}
