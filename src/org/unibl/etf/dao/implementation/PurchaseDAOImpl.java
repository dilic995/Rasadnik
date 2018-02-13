// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.implementation;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.unibl.etf.dao.interfaces.PurchaseDAO;
import org.unibl.etf.dto.Purchase;

public class PurchaseDAOImpl implements PurchaseDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "purchase";

	static {
		pkColumns.add("purchase_id");
		stdColumns.add("date");
		stdColumns.add("description");
		stdColumns.add("price");
		stdColumns.add("paid_off");
		stdColumns.add("customer_id");
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
	public PurchaseDAOImpl() {
		this(null);
	}

	public PurchaseDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public Purchase getByPrimaryKey(Integer purchaseId)  {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, purchaseId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return fromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return null;
	}

	public Long selectCount()  {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = getConn().prepareStatement("select count(*) from " + tableName);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return Long.valueOf(0);
	}

	public Long selectCount(String whereStatement, Object[] bindVariables)  {
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
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return Long.valueOf(0);
	}

	public List<Purchase> selectAll()  {
		List<Purchase> ret = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns));
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Purchase> select(String whereStatement, Object[] bindVariables)  {
		List<Purchase> ret = new ArrayList<>();
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
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}
		

		return ret;
	}

	public Integer update(Purchase obj)  {
		PreparedStatement ps = null;
		int pos = 1;

		try {
			ps = getConn().prepareStatement(DBUtil.update(tableName, stdColumns, pkColumns));
			pos = bindStdColumns(ps, obj, pos);
			bindPrimaryKey(ps, obj, pos);

			int rowCount = ps.executeUpdate();

			if (rowCount != 1) {
				return 0;
			}

			return rowCount;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, null, conn);
		}
		return 0;
	}

	public Integer insert(Purchase obj)  {
		PreparedStatement ps = null;
		ResultSet rs=null;
		int pos = 1;

		try {
			ps = getConn().prepareStatement(DBUtil.insert(tableName, pkColumns, stdColumns));
			pos = bindPrimaryKey(ps, obj, pos);
			bindStdColumns(ps, obj, pos);

			int rowCount = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				obj.setPurchaseId(rs.getInt(1));
			}
			if (rowCount != 1) {
				return 0;
			}

			return rowCount;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, null, conn);
		}
		return 0;
	}

	public Integer delete(Purchase obj)  {
		PreparedStatement ps = null;

		try {
			ps = getConn().prepareStatement(DBUtil.delete(tableName, pkColumns));
			bindPrimaryKey(ps, obj, 1);

			int rowCount = ps.executeUpdate();

			if (rowCount != 1) {
				return 0;
			}

			return rowCount;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, null, conn);
		}
		return 0;
	}

	//
	// finders
	//
	public List<Purchase> getByDate(Date date)  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Purchase> ret = new ArrayList<>();

		try {
			if (null == date) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "date" })));
			} else {
				ps = getConn()
						.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "date" })));
				DBUtil.bind(ps, 1, date);
			}

			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Purchase> getByDescription(String description)  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Purchase> ret = new ArrayList<>();

		try {
			if (null == description) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "description" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "description" })));
				DBUtil.bind(ps, 1, description);
			}

			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Purchase> getByPrice(BigDecimal price)  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Purchase> ret = new ArrayList<>();

		try {
			if (null == price) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "price" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "price" })));
				DBUtil.bind(ps, 1, price);
			}

			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Purchase> getByPaidOff(Boolean paidOff)  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Purchase> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "paid_off" })));
			DBUtil.bind(ps, 1, paidOff);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Purchase> getByCustomerId(Integer customerId)  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Purchase> ret = new ArrayList<>();

		try {
			ps = getConn().prepareStatement(
					DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "customer_id" })));
			DBUtil.bind(ps, 1, customerId);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);

		}

		return ret;
	}

	//
	// helpers
	//
	protected int bindPrimaryKey(PreparedStatement ps, Purchase obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getPurchaseId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Purchase obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getDate());
		DBUtil.bind(ps, pos++, obj.getDescription());
		DBUtil.bind(ps, pos++, obj.getPrice());
		DBUtil.bind(ps, pos++, obj.getPaidOff());
		DBUtil.bind(ps, pos++, obj.getCustomerId());

		return pos;
	}

	protected Purchase fromResultSet(ResultSet rs) throws SQLException {
		Purchase obj = new Purchase();

		obj.setPurchaseId(DBUtil.getInt(rs, "purchase_id"));
		obj.setDate(DBUtil.getDate(rs, "date"));
		obj.setDescription(DBUtil.getString(rs, "description"));
		obj.setPrice(DBUtil.getBigDecimal(rs, "price"));
		obj.setPaidOff(DBUtil.getBooleanObject(rs, "paid_off"));
		obj.setCustomerId(DBUtil.getInt(rs, "customer_id"));
		

		return obj;
	}

	protected Connection getConn() {
		if(conn == null) {
			conn = DBUtil.getConnection();
		}
		
		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
