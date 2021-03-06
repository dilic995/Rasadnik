// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.unibl.etf.dao.interfaces.ToolDAO;
import org.unibl.etf.dto.Tool;
import org.unibl.etf.util.ErrorLogger;



public class ToolDAOImpl implements ToolDAO
{
  //
  // static data
  //
  protected static List<String> pkColumns = new ArrayList<>();
  protected static List<String> stdColumns = new ArrayList<>();
  protected static List<String> allColumns = new ArrayList<>();
  protected static String tableName = "tool";

	static {
		pkColumns.add("tool_id");
		stdColumns.add("tool_name");
		stdColumns.add("count");
		stdColumns.add("is_machine");
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
	public ToolDAOImpl() {
		this(null);
	}

	public ToolDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public Tool getByPrimaryKey(Integer toolId)  {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, toolId);
			rs = ps.executeQuery();

			if (rs.next()) {
				return fromResultSet(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return null;
	}

	public Long selectCount()  {
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
			new ErrorLogger().log(e);
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
			ps = getConn().prepareStatement("select count(*) from " + tableName + whereStatement + " AND deleted=false");

			for (int i = 0; i < bindVariables.length; i++)
				DBUtil.bind(ps, i + 1, bindVariables[i]);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return Long.valueOf(0);
	}

	public List<Tool> selectAll()  {
		List<Tool> ret = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns) + " WHERE deleted=false");
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Tool> select(String whereStatement, Object[] bindVariables)  {
		List<Tool> ret = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (!whereStatement.trim().toUpperCase().startsWith("WHERE")) {
			whereStatement = " WHERE " + whereStatement;
		} else if (whereStatement.startsWith(" ") == false) {
			whereStatement = " " + whereStatement;
		}

		try {
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns) + whereStatement + " AND deleted=false");

			for (int i = 0; i < bindVariables.length; i++)
				DBUtil.bind(ps, i + 1, bindVariables[i]);

			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public Integer update(Tool obj)  {
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
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, null, conn);
		}
		return 0;
	}

	public Integer insert(Tool obj)  {
		PreparedStatement ps = null;
		ResultSet rs=null;
		int pos = 1;

		try {
			ps = getConn().prepareStatement(DBUtil.insert(tableName, pkColumns, stdColumns),PreparedStatement.RETURN_GENERATED_KEYS);
			pos = bindPrimaryKey(ps, obj, pos);
			bindStdColumns(ps, obj, pos);

			int rowCount = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				obj.setToolId(rs.getInt(1));
			}
			if (rowCount != 1) {
				return 0;
			}

			return rowCount;
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, null, conn);
		}
		return 0;
	}

	public Integer delete(Tool obj)  {
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
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, null, conn);
		}
		return 0;
	}

	//
	// finders
	//
	public List<Tool> getByToolName(String toolName)  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Tool> ret = new ArrayList<>();

		try {
			if (null == toolName) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "tool_name" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "tool_name" })));
				DBUtil.bind(ps, 1, toolName);
			}

			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Tool> getByCount(Integer count)  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Tool> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "count" })));
			DBUtil.bind(ps, 1, count);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}
	public List<Tool> getByIsMachine(Boolean isMachine)  {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Tool> ret = new ArrayList<>();

		try {
			ps = getConn().prepareStatement(
					DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "is_machine" })));
			DBUtil.bind(ps, 1, isMachine);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}
	
	@Override
	public List<Tool> getByDeleted(Boolean deleted) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Tool> ret = new ArrayList<>();

		try {
			ps = getConn().prepareStatement(
					DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "deleted" })));
			DBUtil.bind(ps, 1, deleted);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	//
	// helpers
	//
	protected int bindPrimaryKey(PreparedStatement ps, Tool obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getToolId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Tool obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getToolName());
		DBUtil.bind(ps, pos++, obj.getCount());
		DBUtil.bind(ps, pos++, obj.getIsMachine());
		DBUtil.bind(ps, pos++, obj.getDeleted());

		return pos;
	}

	protected Tool fromResultSet(ResultSet rs) throws SQLException {
		Tool obj = new Tool();

		obj.setToolId(DBUtil.getInt(rs, "tool_id"));
		obj.setToolName(DBUtil.getString(rs, "tool_name"));
		obj.setCount(DBUtil.getInt(rs, "count"));
		obj.setIsMachine(DBUtil.getBooleanObject(rs, "is_machine"));
		obj.setDeleted(DBUtil.getBooleanObject(rs, "deleted"));
		return obj;
	}

	protected Connection getConn() {
		if(conn == null) {
			conn = DBUtil.getConnection();
		}
		
		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
