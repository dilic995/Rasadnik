package org.unibl.etf.dao.interfaces;

import org.unibl.etf.dto.Customer;

import java.util.List;

public interface CustomerDAO {
	// CRUD methods
	public Customer getByPrimaryKey(int customerId) throws DAOException;

	public List<Customer> selectAll() throws DAOException;

	public List<Customer> select(String whereStatement, Object[] bindVariables) throws DAOException;

	public long selectCount() throws DAOException;

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException;

	public int update(Customer obj) throws DAOException;

	public int insert(Customer obj) throws DAOException;

	public int delete(Customer obj) throws DAOException;

	// Finders
	public List<Customer> getByFirstName(String firstName) throws DAOException;

	public List<Customer> getByLastName(String lastName) throws DAOException;

	public List<Customer> getByAddress(String address) throws DAOException;

	public List<Customer> getByIsSupplier(byte isSupplier) throws DAOException;
}
