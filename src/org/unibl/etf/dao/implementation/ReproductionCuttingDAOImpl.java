// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dao.interfaces.ReproductionCuttingDAO;
import org.unibl.etf.dto.ReproductionCutting;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class ReproductionCuttingDAOImpl implements ReproductionCuttingDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "reproduction_cutting";

	static {
		pkColumns.add("date");
		pkColumns.add("basis_id");
		stdColumns.add("produces");
		stdColumns.add("take_a_root");
		stdColumns.add("deleted");
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
	public ReproductionCuttingDAOImpl() {
		this(null);
	}

	public ReproductionCuttingDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public ReproductionCutting getByPrimaryKey(Integer idBasis, Date date) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, date);
			DBUtil.bind(ps, pos++, idBasis);
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

	public Long selectCount() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = getConn().prepareStatement("select count(*) from " + tableName + " WHERE deleted=false");
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return 0L;
	}

	public Long selectCount(String whereStatement, Object[] bindVariables) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (!whereStatement.trim().toUpperCase().startsWith("WHERE")) {
			whereStatement = " WHERE " + whereStatement;
		} else if (whereStatement.startsWith(" ") == false) {
			whereStatement = " " + whereStatement;
		}

		try {
			ps = getConn()
					.prepareStatement("select count(*) from " + tableName + whereStatement + " AND deleted=false");

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

		return 0L;
	}

	public List<ReproductionCutting> selectAll() {
		List<ReproductionCutting> ret = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns) + " WHERE deleted=false");
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

	public List<ReproductionCutting> select(String whereStatement, Object[] bindVariables) {
		List<ReproductionCutting> ret = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (!whereStatement.trim().toUpperCase().startsWith("WHERE")) {
			whereStatement = " WHERE " + whereStatement;
		} else if (whereStatement.startsWith(" ") == false) {
			whereStatement = " " + whereStatement;
		}

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns) + whereStatement + " AND deleted=false");

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

	public Integer update(ReproductionCutting obj) {
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

	public Integer insert(ReproductionCutting obj) {
		Integer res = 0;
		res = tryInsert(obj);
		if (res == DBUtil.DUPLICATE_KEYS) {
			ReproductionCutting rep = DAOFactory.getInstance().getReproductionCuttingDAO()
					.getByPrimaryKey(obj.getBasisId(), obj.getDate());
			if (rep != null) {
				rep.setProduces(rep.getProduces() + obj.getProduces());
				rep.setTakeARoot(obj.getTakeARoot() + rep.getTakeARoot());
				res = DAOFactory.getInstance().getReproductionCuttingDAO().update(rep);
			}
		}
		return res;
	}

	private Integer tryInsert(ReproductionCutting obj) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pos = 1;
		int res = 0;
		try {
			ps = getConn().prepareStatement(DBUtil.insert(tableName, pkColumns, stdColumns),
					PreparedStatement.RETURN_GENERATED_KEYS);
			pos = bindPrimaryKey(ps, obj, pos);
			bindStdColumns(ps, obj, pos);

			int rowCount = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				obj.setBasisId(rs.getInt(1));
				obj.setDate(rs.getDate(2));
			}
			if (rowCount != 1) {
				res = 0;
			}

			res = rowCount;
		} catch (SQLException e) {
			if (e instanceof MySQLIntegrityConstraintViolationException) {
				res = DBUtil.DUPLICATE_KEYS;
			}
		} finally {
			DBUtil.close(ps, null, conn);
		}
		return res;
	}

	public Integer delete(ReproductionCutting obj) {
		PreparedStatement ps = null;
		int pos = 1;

		try {
			obj.setDeleted(true);
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

	//
	// finders
	//
	public List<ReproductionCutting> getByBasisId(Integer basisId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReproductionCutting> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "basis_id" })));
			DBUtil.bind(ps, 1, basisId);
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

	public List<ReproductionCutting> getByDate(Date date) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReproductionCutting> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "date" })));
			DBUtil.bind(ps, 1, date);
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

	@Override
	public List<ReproductionCutting> getByProduces(Integer produces) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReproductionCutting> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "produces" })));
			DBUtil.bind(ps, 1, produces);
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

	@Override
	public List<ReproductionCutting> getByTakeARoot(Integer takeARoot) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReproductionCutting> ret = new ArrayList<>();

		try {
			ps = getConn().prepareStatement(
					DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "take_a_root" })));
			DBUtil.bind(ps, 1, takeARoot);
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

	@Override
	public List<ReproductionCutting> getByDeleted(Boolean deleted) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ReproductionCutting> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "deleted" })));
			DBUtil.bind(ps, 1, deleted);
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
	protected int bindPrimaryKey(PreparedStatement ps, ReproductionCutting obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getDate());
		DBUtil.bind(ps, pos++, obj.getBasisId());
		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, ReproductionCutting obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getProduces());
		DBUtil.bind(ps, pos++, obj.getTakeARoot());
		DBUtil.bind(ps, pos++, obj.getDeleted());
		return pos;
	}

	protected ReproductionCutting fromResultSet(ResultSet rs) throws SQLException {
		ReproductionCutting obj = new ReproductionCutting();

		obj.setBasisId((DBUtil.getInt(rs, "basis_id")));
		obj.setDate(DBUtil.getDate(rs, "date"));
		obj.setProduces(DBUtil.getInt(rs, "produces"));
		obj.setTakeARoot(DBUtil.getInt(rs, "take_a_root"));
		obj.setDeleted(DBUtil.getBoolean(rs, "deleted"));
		return obj;
	}

	protected Connection getConn() {
		if (conn == null) {
			conn = DBUtil.getConnection();
		}

		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
