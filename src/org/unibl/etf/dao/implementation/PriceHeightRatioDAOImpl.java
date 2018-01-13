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

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dao.interfaces.PriceHeightRatioDAO;
import org.unibl.etf.dto.PriceHeightRatio;

public class PriceHeightRatioDAOImpl implements PriceHeightRatioDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "price_height_ratio";

	static {
		pkColumns.add("date_from");
		pkColumns.add("plant_id");
		stdColumns.add("height_min");
		stdColumns.add("height_max");
		stdColumns.add("price");
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
	public PriceHeightRatioDAOImpl() {
		this(null);
	}

	public PriceHeightRatioDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public PriceHeightRatio getByPrimaryKey(Date date, Integer plantId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, date);
			DBUtil.bind(ps, pos++, plantId);
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

	public List<PriceHeightRatio> selectAll() throws DAOException {
		List<PriceHeightRatio> ret = new ArrayList<>();
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

	public List<PriceHeightRatio> select(String whereStatement, Object[] bindVariables) throws DAOException {
		List<PriceHeightRatio> ret = new ArrayList<>();
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

	public int update(PriceHeightRatio obj) throws DAOException {
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

	public int insert(PriceHeightRatio obj) throws DAOException {
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

	public int delete(PriceHeightRatio obj) throws DAOException {
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
	public List<PriceHeightRatio> getByDateFrom(Date dateFrom) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PriceHeightRatio> ret = new ArrayList<>();

		try {
			ps = getConn().prepareStatement(
					DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "date_from" })));
			DBUtil.bind(ps, 1, dateFrom);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException("SQL Error in finder getByDateFrom()", e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<PriceHeightRatio> getByPlantId(Integer plantId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PriceHeightRatio> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "plant_id" })));
			DBUtil.bind(ps, 1, plantId);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException("SQL Error in finder getByPlantId()", e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<PriceHeightRatio> getByHeightMin(BigDecimal heightMin) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PriceHeightRatio> ret = new ArrayList<>();

		try {
			if (null == heightMin) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "height_min" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "height_min" })));
				DBUtil.bind(ps, 1, heightMin);
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

	public List<PriceHeightRatio> getByHeightMax(BigDecimal heightMax) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PriceHeightRatio> ret = new ArrayList<>();

		try {
			if (null == heightMax) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "height_max" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "height_max" })));
				DBUtil.bind(ps, 1, heightMax);
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

	public List<PriceHeightRatio> getByPrice(BigDecimal price) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PriceHeightRatio> ret = new ArrayList<>();

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
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<PriceHeightRatio> getByActive(Boolean active) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PriceHeightRatio> ret = new ArrayList<>();

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
	protected int bindPrimaryKey(PreparedStatement ps, PriceHeightRatio obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getDateFrom());
		DBUtil.bind(ps, pos++, obj.getPlantId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, PriceHeightRatio obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getHeightMin());
		DBUtil.bind(ps, pos++, obj.getHeightMax());
		DBUtil.bind(ps, pos++, obj.getPrice());
		DBUtil.bind(ps, pos++, obj.getActive());

		return pos;
	}

	protected PriceHeightRatio fromResultSet(ResultSet rs) throws SQLException {
		PriceHeightRatio obj = new PriceHeightRatio();

		try {
			obj.setDateFrom(DBUtil.getDate(rs, "date_from"));
			obj.setPlantId(DBUtil.getInteger(rs, "plant_id"));
			obj.setPlant(DAOFactory.getInstance().getPlantDAO().getByPrimaryKey(DBUtil.getInt(rs, "plant_id")));
			obj.setHeightMin(DBUtil.getBigDecimal(rs, "height_min"));
			obj.setHeightMax(DBUtil.getBigDecimal(rs, "height_max"));
			obj.setPrice(DBUtil.getBigDecimal(rs, "price"));
			obj.setActive(DBUtil.getBoolean(rs, "active"));
		} catch (DAOException e) {
			e.printStackTrace();
		}

		return obj;
	}

	protected Connection getConn() {
		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
