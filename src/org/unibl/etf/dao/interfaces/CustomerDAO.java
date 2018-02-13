package org.unibl.etf.dao.interfaces;

import java.util.List;

import org.unibl.etf.dto.Customer;

public interface CustomerDAO {
	// CRUD methods
	public Customer getByPrimaryKey(Integer customerId);

	public List<Customer> selectAll();

	public List<Customer> select(String whereStatement, Object[] bindVariables);

	public long selectCount();

	public long selectCount(String whereStatement, Object[] bindVariables);

	public int update(Customer obj);

	public int insert(Customer obj);

	public int delete(Customer obj);

	// Finders
	public List<Customer> getByFirstName(String firstName);

	public List<Customer> getByLastName(String lastName);

	public List<Customer> getByAddress(String address);

	public List<Customer> getByIsSupplier(Boolean isSupplier);
}
