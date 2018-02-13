package org.unibl.etf.dao.interfaces;

import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.Pricelist;

public interface PricelistDAO {
	// CRUD methods
	public Pricelist getByPrimaryKey(Integer pricelistId);

	public List<Pricelist> selectAll();

	public List<Pricelist> select(String whereStatement, Object[] bindVariables);

	public long selectCount();

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(Pricelist obj);

	public int insert(Pricelist obj);

	public int delete(Pricelist obj);

	// Finders
	public List<Pricelist> getByDateFrom(Date dateFrom);

	public List<Pricelist> getByDateTo(Date dateTo);

	public List<Pricelist> getByActive(Boolean active);
}
