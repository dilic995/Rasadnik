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
import org.unibl.etf.dao.interfaces.ReproductionCuttingDAO;
import org.unibl.etf.dto.ReproductionCutting;

public class ReproductionCuttingDAOImpl implements ReproductionCuttingDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "reproduction_cutting";

	static {
		pkColumns.add("basis_id");
		pkColumns.add("date");
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
	public ReproductionCutting getByPrimaryKey(Integer idBasis, Date date)  {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, idBasis);
			DBUtil.bind(ps, pos++, date);
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

		return 0L;
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

		return 0L;
	}

	public List<ReproductionCutting> selectAll()  {
		List<ReproductionCutting> ret = new ArrayList<>();
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

	public List<ReproductionCutting> select(String whereStatement, Object[] bindVariables)  {
		List<ReproductionCutting> ret = new ArrayList<>();
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

	public Integer update(ReproductionCutting obj)  {
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

	public Integer insert(ReproductionCutting obj)  {
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
				obj.setBasisId(rs.getInt(1));
				obj.setDate(rs.getDate(2));
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

	public Integer delete(ReproductionCutting obj)  {
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
	public List<ReproductionCutting> getByBasisId(Integer basisId)  {
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

	public List<ReproductionCutting> getByDate(Date date)  {
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

	//
	// helpers
	//
	protected int bindPrimaryKey(PreparedStatement ps, ReproductionCutting obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getBasisId());
		DBUtil.bind(ps, pos++, obj.getDate());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, ReproductionCutting obj, int pos) throws SQLException {
		return pos;
	}

	protected ReproductionCutting fromResultSet(ResultSet rs) throws SQLException {
		ReproductionCutting obj = new ReproductionCutting();

		obj.setBasisId((DBUtil.getInt(rs, "basis_id")));
		obj.setDate(DBUtil.getDate(rs, "date"));
		return obj;
	}

	protected Connection getConn() {
		if(conn == null) {
			conn = DBUtil.getConnection();
		}
		
		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
