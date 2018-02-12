package org.unibl.etf.dao.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.PriceHeightRatio;

public interface PriceHeightRatioDAO {
	// CRUD methods
	public PriceHeightRatio getByPrimaryKey(Date date, Integer plantId) throws DAOException;

	public List<PriceHeightRatio> selectAll() throws DAOException;

	public List<PriceHeightRatio> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(PriceHeightRatio obj) throws DAOException;

	public int insert(PriceHeightRatio obj);

	public int delete(PriceHeightRatio obj) throws DAOException;

	// Finders
	public List<PriceHeightRatio> getByDateFrom(Date dateFrom) throws DAOException;

	public List<PriceHeightRatio> getByPlantId(Integer plantId);

	public List<PriceHeightRatio> getByHeightMin(BigDecimal heightMin) throws DAOException;

	public List<PriceHeightRatio> getByHeightMax(BigDecimal heightMax) throws DAOException;

	public List<PriceHeightRatio> getByPrice(BigDecimal price) throws DAOException;

	public List<PriceHeightRatio> getByActive(Boolean active) throws DAOException;
}
