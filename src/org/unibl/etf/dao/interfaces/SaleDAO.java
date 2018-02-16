// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.Sale;

public interface SaleDAO {
	// CRUD methods
	public Sale getByPrimaryKey(Integer saleId);

	public List<Sale> selectAll();

	public List<Sale> select(String whereStatement, Object[] bindVariables);

	public Long selectCount();

	public Long selectCount(String whereStatement, Object[] bindVariables);

	public Integer update(Sale obj);

	public Integer insert(Sale obj);

	public Integer delete(Sale obj);

	// Finders
	public List<Sale> getByDate(Date date);

	public List<Sale> getByPrice(BigDecimal amount);

	public List<Sale> getByPaidOff(Boolean paidOff);

	public List<Sale> getByCustomer(Integer customerId);
	
	public List<Sale> getByDeleted(Boolean deleted);
}
