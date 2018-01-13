package org.unibl.etf.dao.implementation;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.PricelistDAO;
import org.unibl.etf.dto.Pricelist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PricelistDAOImpl implements PricelistDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "pricelist";

	static {
		pkColumns.add("pricelist_id");
		stdColumns.add("date_from");
		stdColumns.add("date_to");
		stdColumns.add("active");
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
	public PricelistDAOImpl() {
		this(null);
	}

	public PricelistDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public Pricelist getByPrimaryKey(int pricelistId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, pricelistId);
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

	public List<Pricelist> selectAll() throws DAOException {
		List<Pricelist> ret = new ArrayList<>();
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

	public List<Pricelist> select(String whereStatement, Object[] bindVariables) throws DAOException {
		List<Pricelist> ret = new ArrayList<>();
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

	public int update(Pricelist obj) throws DAOException {
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

	public int insert(Pricelist obj) throws DAOException {
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

	public int delete(Pricelist obj) throws DAOException {
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
	public List<Pricelist> getByDateFrom(Date dateFrom) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Pricelist> ret = new ArrayList<>();

		try {
			if (null == dateFrom) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "date_from" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "date_from" })));
				DBUtil.bind(ps, 1, dateFrom);
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

	public List<Pricelist> getByDateTo(Date dateTo) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Pricelist> ret = new ArrayList<>();

		try {
			if (null == dateTo) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "date_to" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "date_to" })));
				DBUtil.bind(ps, 1, dateTo);
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

	public List<Pricelist> getByActive(Boolean active) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Pricelist> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "active" })));
			DBUtil.bind(ps, 1, active);
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
	protected int bindPrimaryKey(PreparedStatement ps, Pricelist obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getPricelistId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Pricelist obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getDateFrom());
		DBUtil.bind(ps, pos++, obj.getDateTo());
		DBUtil.bind(ps, pos++, obj.getActive());

		return pos;
	}

	protected Pricelist fromResultSet(ResultSet rs) throws SQLException {
		Pricelist obj = new Pricelist();

		obj.setPricelistId(DBUtil.getInt(rs, "pricelist_id"));
		obj.setDateFrom(DBUtil.getDate(rs, "date_from"));
		obj.setDateTo(DBUtil.getDate(rs, "date_to"));
		obj.setActive(DBUtil.getBoolean(rs, "active"));

		return obj;
	}

	protected Connection getConn() {
		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
