// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.unibl.etf.dto.Sale;

public interface SaleDAO {
	// CRUD methods
	public Sale getByPrimaryKey(Integer saleId) throws DAOException;

	public List<Sale> selectAll() throws DAOException;

	public List<Sale> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public Long selectCount() throws DAOException;

	public Long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public Integer update(Sale obj) throws DAOException;

	public Integer insert(Sale obj) throws DAOException;

	public Integer delete(Sale obj) throws DAOException;

	// Finders
	public List<Sale> getByDate(Date date) throws DAOException;

	public List<Sale> getByPrice(BigDecimal amount) throws DAOException;

	public List<Sale> getByPaidOff(Boolean paidOff) throws DAOException;

	public List<Sale> getByCustomer(Integer customerId) throws DAOException;
}
