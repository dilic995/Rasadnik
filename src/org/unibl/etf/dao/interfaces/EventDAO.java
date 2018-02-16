package org.unibl.etf.dao.interfaces;

import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.Event;

public interface EventDAO {
	// CRUD methods
	public Event getByPrimaryKey(Integer eventId);

	public List<Event> selectAll();

	public List<Event> select(String whereStatement, Object[] bindVariables);

	public long selectCount();

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(Event obj);

	public int insert(Event obj);

	public int delete(Event obj);

	// Finders
	public List<Event> getByName(String name);

	public List<Event> getByDescription(String description);

	public List<Event> getByDate(Date date);

	public List<Event> getByDone(Boolean done);
	
	public List<Event> getByDeleted(Boolean deleted);
}
