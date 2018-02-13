package org.unibl.etf.dao.interfaces;

import java.util.List;

import org.unibl.etf.dto.PlantMaintanceActivity;

public interface PlantMaintanceActivityDAO {
	// CRUD methods
	public PlantMaintanceActivity getByPrimaryKey(Integer plantMaintanceActivityId);

	public List<PlantMaintanceActivity> selectAll();

	public List<PlantMaintanceActivity> select(String whereStatement, Object[] bindVariables);

	public long selectCount();

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(PlantMaintanceActivity obj);

	public int insert(PlantMaintanceActivity obj);

	public int delete(PlantMaintanceActivity obj);

	// Finders
	public List<PlantMaintanceActivity> getByActivity(String activity);

}
