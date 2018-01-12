package org.unibl.etf.dao.interfaces;

import org.unibl.etf.dto.Basis;

import java.util.Date;
import java.util.List;

public interface BasisDAO {
	// CRUD methods
	public Basis getByPrimaryKey(int basisId) throws DAOException;

	public List<Basis> selectAll() throws DAOException;

	public List<Basis> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(Basis obj) throws DAOException;

	public int insert(Basis obj) throws DAOException;

	public int delete(Basis obj) throws DAOException;

	// Finders
	public List<Basis> getByPlantingDate(Date plantingDate) throws DAOException;

	public List<Basis> getByProduced(int produced) throws DAOException;

	public List<Basis> getByTakeARoot(int takeARoot) throws DAOException;

	public List<Basis> getByActive(byte active) throws DAOException;

	public List<Basis> getByPlantId(int plantId) throws DAOException;
}
