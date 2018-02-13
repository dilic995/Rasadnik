package org.unibl.etf.dao.interfaces;

import java.sql.Blob;
import java.util.List;

import org.unibl.etf.dto.Plant;

public interface PlantDAO {
	// CRUD methods
	public Plant getByPrimaryKey(Integer plantId);

	public List<Plant> selectAll();

	public List<Plant> select(String whereStatement, Object[] bindVariables);

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(Plant obj);

	public int insert(Plant obj);

	public int delete(Plant obj);

	// Finders
	public List<Plant> getByScientificName(String scientificName);

	public List<Plant> getByKnownAs(String knownAs);

	public List<Plant> getByDescription(String description);

	public List<Plant> getByImage(Blob image);
	
	public List<Plant> getByIsConifer(Boolean isConifer);

	public List<Plant> getByOwned(Boolean owned);
}
