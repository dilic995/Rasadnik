// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.interfaces;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.List;

import org.unibl.etf.dto.Transaction;

public interface TransactionDAO {
	// CRUD methods
	public Transaction getByPrimaryKey(Integer transactionId) throws DAOException;

	public List<Transaction> selectAll() throws DAOException;

	public List<Transaction> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public Long selectCount() throws DAOException;

	public Long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public Integer update(Transaction obj) throws DAOException;

	public Integer insert(Transaction obj) throws DAOException;

	public Integer delete(Transaction obj) throws DAOException;

	// Finders
	public List<Transaction> getByAmount(BigDecimal amount) throws DAOException;

	public List<Transaction> getByType(Boolean type) throws DAOException;

	public List<Transaction> getByDescription(Clob description) throws DAOException;
}
