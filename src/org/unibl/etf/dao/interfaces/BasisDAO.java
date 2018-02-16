package org.unibl.etf.dao.interfaces;

import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.Basis;

public interface BasisDAO {
	// CRUD methods
	public Basis getByPrimaryKey(Integer basisId);

	public List<Basis> selectAll();

	public List<Basis> select(String whereStatement, Object[] bindVariables);

	public long selectCount();

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(Basis obj);

	public int insert(Basis obj);

	public int delete(Basis obj);

	// Finders
	public List<Basis> getByPlantingDate(Date plantingDate);

	public List<Basis> getByActive(Boolean deleted);

	public List<Basis> getByPlantId(Integer plantId);
	
	public Integer getNum(int basis_id, String type, String tableName);
}
