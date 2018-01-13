package org.unibl.etf.dao.implementation;

import org.unibl.etf.dao.interfaces.CustomerDAO;
import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dto.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "customer";

	static {
		pkColumns.add("customer_id");
		stdColumns.add("first_name");
		stdColumns.add("last_name");
		stdColumns.add("address");
		stdColumns.add("is_supplier");
		allColumns.addAll(pkColumns);
		allColumns.addAll(stdColumns);
	}

	//
	// data
	//
	protected Connection conn = null;

	//
	// construction
	//
	public CustomerDAOImpl() {
		this(null);
	}

	public CustomerDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public Customer getByPrimaryKey(int customerId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, customerId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return fromResultSet(rs);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return null;
	}

	public long selectCount() throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = getConn().prepareStatement("select count(*) from " + tableName);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return 0;
	}

	public long selectCount(String whereStatement, Object[] bindVariables) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (!whereStatement.trim().toUpperCase().startsWith("WHERE")) {
			whereStatement = " WHERE " + whereStatement;
		} else if (whereStatement.startsWith(" ") == false) {
			whereStatement = " " + whereStatement;
		}

		try {
			ps = getConn().prepareStatement("select count(*) from " + tableName + whereStatement);

			for (int i = 0; i < bindVariables.length; i++)
				DBUtil.bind(ps, i + 1, bindVariables[i]);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return 0;
	}

	public List<Customer> selectAll() throws DAOException {
		List<Customer> ret = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns));
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Customer> select(String whereStatement, Object[] bindVariables) throws DAOException {
		List<Customer> ret = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (!whereStatement.trim().toUpperCase().startsWith("WHERE")) {
			whereStatement = " WHERE " + whereStatement;
		} else if (whereStatement.startsWith(" ") == false) {
			whereStatement = " " + whereStatement;
		}

		try {
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns) + whereStatement);

			for (int i = 0; i < bindVariables.length; i++)
				DBUtil.bind(ps, i + 1, bindVariables[i]);

			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException("Error in select(), table = " + tableName, e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public int update(Customer obj) throws DAOException {
		PreparedStatement ps = null;
		int pos = 1;

		try {
			ps = getConn().prepareStatement(DBUtil.update(tableName, stdColumns, pkColumns));
			pos = bindStdColumns(ps, obj, pos);
			bindPrimaryKey(ps, obj, pos);

			int rowCount = ps.executeUpdate();

			if (rowCount != 1) {
				throw new DAOException(
						"Error updating " + obj.getClass() + " in " + tableName + ", affected rows = " + rowCount);
			}

			return rowCount;
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, null, conn);
		}
	}

	public int insert(Customer obj) throws DAOException {
		PreparedStatement ps = null;
		int pos = 1;

		try {
			ps = getConn().prepareStatement(DBUtil.insert(tableName, pkColumns, stdColumns));
			pos = bindPrimaryKey(ps, obj, pos);
			bindStdColumns(ps, obj, pos);

			int rowCount = ps.executeUpdate();

			if (rowCount != 1) {
				throw new DAOException(
						"Error inserting " + obj.getClass() + " in " + tableName + ", affected rows = " + rowCount);
			}

			return rowCount;
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, null, conn);
		}
	}

	public int delete(Customer obj) throws DAOException {
		PreparedStatement ps = null;

		try {
			ps = getConn().prepareStatement(DBUtil.delete(tableName, pkColumns));
			bindPrimaryKey(ps, obj, 1);

			int rowCount = ps.executeUpdate();

			if (rowCount != 1) {
				throw new DAOException(
						"Error deleting " + obj.getClass() + " in " + tableName + ", affected rows = " + rowCount);
			}

			return rowCount;
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, null, conn);
		}
	}

	//
	// finders
	//
	public List<Customer> getByFirstName(String firstName) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Customer> ret = new ArrayList<>();

		try {
			if (null == firstName) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "first_name" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "first_name" })));
				DBUtil.bind(ps, 1, firstName);
			}

			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Customer> getByLastName(String lastName) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Customer> ret = new ArrayList<>();

		try {
			if (null == lastName) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "last_name" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "last_name" })));
				DBUtil.bind(ps, 1, lastName);
			}

			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Customer> getByAddress(String address) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Customer> ret = new ArrayList<>();

		try {
			if (null == address) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "address" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "address" })));
				DBUtil.bind(ps, 1, address);
			}

			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Customer> getByIsSupplier(Boolean isSupplier) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Customer> ret = new ArrayList<>();

		try {
			ps = getConn().prepareStatement(
					DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "is_supplier" })));
			DBUtil.bind(ps, 1, isSupplier);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	//
	// helpers
	//
	protected int bindPrimaryKey(PreparedStatement ps, Customer obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getCustomerId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Customer obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getFirstName());
		DBUtil.bind(ps, pos++, obj.getLastName());
		DBUtil.bind(ps, pos++, obj.getAddress());
		DBUtil.bind(ps, pos++, obj.getIsSupplier());

		return pos;
	}

	protected Customer fromResultSet(ResultSet rs) throws SQLException {
		Customer obj = new Customer();

		obj.setCustomerId(DBUtil.getInt(rs, "customer_id"));
		obj.setFirstName(DBUtil.getString(rs, "first_name"));
		obj.setLastName(DBUtil.getString(rs, "last_name"));
		obj.setAddress(DBUtil.getString(rs, "address"));
		obj.setIsSupplier(DBUtil.getBoolean(rs, "is_supplier"));

		return obj;
	}

	protected Connection getConn() {
		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
