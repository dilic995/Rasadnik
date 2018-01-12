package org.unibl.etf.dao.interfaces;

import org.unibl.etf.dto.Event;

import java.sql.Clob;

import java.util.Date;
import java.util.List;

public interface EventDAO {
	// CRUD methods
	public Event getByPrimaryKey(int eventId) throws DAOException;

	public List<Event> selectAll() throws DAOException;

	public List<Event> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(Event obj) throws DAOException;

	public int insert(Event obj) throws DAOException;

	public int delete(Event obj) throws DAOException;

	// Finders
	public List<Event> getByName(String name) throws DAOException;

	public List<Event> getByDescription(Clob description) throws DAOException;

	public List<Event> getByDate(Date date) throws DAOException;

	public List<Event> getByDone(byte done) throws DAOException;
}
