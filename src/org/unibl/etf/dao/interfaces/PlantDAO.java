package org.unibl.etf.dao.interfaces;

import java.sql.Blob;
import java.util.List;

import org.unibl.etf.dto.Plant;

public interface PlantDAO {
	// CRUD methods
	public Plant getByPrimaryKey(Integer plantId) throws DAOException;

	public List<Plant> selectAll() throws DAOException;

	public List<Plant> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(Plant obj) throws DAOException;

	public int insert(Plant obj) throws DAOException;

	public int delete(Plant obj) throws DAOException;

	// Finders
	public List<Plant> getByScientificName(String scientificName) throws DAOException;

	public List<Plant> getByKnownAs(String knownAs) throws DAOException;

	public List<Plant> getByDescription(String description) throws DAOException;

	public List<Plant> getByImage(Blob image) throws DAOException;
	
	public List<Plant> getByIsConifer(Boolean isConifer) throws DAOException;

	public List<Plant> getByOwned(Boolean owned) throws DAOException;
}
