package org.unibl.etf.dao.interfaces;

import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.Pricelist;

public interface PricelistDAO {
	// CRUD methods
	public Pricelist getByPrimaryKey(Integer pricelistId) throws DAOException;

	public List<Pricelist> selectAll() throws DAOException;

	public List<Pricelist> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(Pricelist obj) throws DAOException;

	public int insert(Pricelist obj) throws DAOException;

	public int delete(Pricelist obj) throws DAOException;

	// Finders
	public List<Pricelist> getByDateFrom(Date dateFrom) throws DAOException;

	public List<Pricelist> getByDateTo(Date dateTo) throws DAOException;

	public List<Pricelist> getByActive(Boolean active) throws DAOException;
}
