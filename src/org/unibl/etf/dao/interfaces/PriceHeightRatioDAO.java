package org.unibl.etf.dao.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.PriceHeightRatio;

public interface PriceHeightRatioDAO {
	// CRUD methods
	public PriceHeightRatio getByPrimaryKey(Date date, BigDecimal heightMin, Integer plantId);

	public List<PriceHeightRatio> selectAll();

	public List<PriceHeightRatio> select(String whereStatement, Object[] bindVariables);

	public long selectCount();

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(PriceHeightRatio obj);

	public int insert(PriceHeightRatio obj);

	public int delete(PriceHeightRatio obj);

	// Finders
	public List<PriceHeightRatio> getByDateFrom(Date dateFrom);

	public List<PriceHeightRatio> getByPlantId(Integer plantId);

	public List<PriceHeightRatio> getByHeightMin(BigDecimal heightMin);

	public List<PriceHeightRatio> getByHeightMax(BigDecimal heightMax);

	public List<PriceHeightRatio> getByPrice(BigDecimal price);

	public List<PriceHeightRatio> getByActive(Boolean active);
	
	public List<PriceHeightRatio> getByDeleted(Boolean deleted);
}
