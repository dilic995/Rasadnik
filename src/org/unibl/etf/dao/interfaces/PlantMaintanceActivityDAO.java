package org.unibl.etf.dao.interfaces;

import java.util.List;

import org.unibl.etf.dto.PlantMaintanceActivity;

public interface PlantMaintanceActivityDAO {
	// CRUD methods
	public PlantMaintanceActivity getByPrimaryKey(Integer plantMaintanceActivityId) throws DAOException;

	public List<PlantMaintanceActivity> selectAll() throws DAOException;

	public List<PlantMaintanceActivity> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(PlantMaintanceActivity obj) throws DAOException;

	public int insert(PlantMaintanceActivity obj) throws DAOException;

	public int delete(PlantMaintanceActivity obj) throws DAOException;

	// Finders
	public List<PlantMaintanceActivity> getByActivity(String activity) throws DAOException;

}
