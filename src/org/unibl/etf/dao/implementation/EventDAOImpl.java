package org.unibl.etf.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.unibl.etf.dao.interfaces.EventDAO;
import org.unibl.etf.dto.Event;
import org.unibl.etf.util.ErrorLogger;

public class EventDAOImpl implements EventDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "event";

	static {
		pkColumns.add("event_id");
		stdColumns.add("name");
		stdColumns.add("description");
		stdColumns.add("date");
		stdColumns.add("done");
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
	public EventDAOImpl() {
		this(null);
	}

	public EventDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public Event getByPrimaryKey(Integer eventId) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, eventId);
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

	public long selectCount() {
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

		return 0;
	}

	public long selectCount(String whereStatement, Object[] bindVariables) {
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

		return 0;
	}

	public List<Event> selectAll() {
		List<Event> ret = new ArrayList<>();
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

	public List<Event> select(String whereStatement, Object[] bindVariables) {
		List<Event> ret = new ArrayList<>();
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

	public int update(Event obj) {
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

	public int insert(Event obj) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pos = 1;

		try {
			ps = getConn().prepareStatement(DBUtil.insert(tableName, pkColumns, stdColumns), PreparedStatement.RETURN_GENERATED_KEYS);
			pos = bindPrimaryKey(ps, obj, pos);
			bindStdColumns(ps, obj, pos);

			int rowCount = ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if(rs.next()) {
				obj.setEventId(rs.getInt(1));
			}
			
			if (rowCount != 1) {
				return 0;
			}

			return rowCount;
		} catch (SQLException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}
		
		return 0;
	}

	public int delete(Event obj) {
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
	public List<Event> getByName(String name) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Event> ret = new ArrayList<>();

		try {
			if (null == name) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "name" })));
			} else {
				ps = getConn()
						.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "name" })));
				DBUtil.bind(ps, 1, name);
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

	public List<Event> getByDescription(String description) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Event> ret = new ArrayList<>();

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
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Event> getByDate(Date date) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Event> ret = new ArrayList<>();

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
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Event> getByDone(Boolean done) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Event> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "done" })));
			DBUtil.bind(ps, 1, done);
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
	public List<Event> getByDeleted(Boolean deleted) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Event> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "deleted" })));
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
	protected int bindPrimaryKey(PreparedStatement ps, Event obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getEventId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Event obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getName());
		DBUtil.bind(ps, pos++, obj.getDescription());
		DBUtil.bind(ps, pos++, obj.getDate());
		DBUtil.bind(ps, pos++, obj.getDone());
		DBUtil.bind(ps, pos++, obj.getDeleted());

		return pos;
	}

	protected Event fromResultSet(ResultSet rs) throws SQLException {
		Event obj = new Event();

		obj.setEventId(DBUtil.getInt(rs, "event_id"));
		obj.setName(DBUtil.getString(rs, "name"));
		obj.setDescription(DBUtil.getString(rs, "description"));
		obj.setDate(DBUtil.getDate(rs, "date"));
		obj.setDone(DBUtil.getBoolean(rs, "done"));
		obj.setDeleted(DBUtil.getBoolean(rs, "deleted"));

		return obj;
	}

	protected Connection getConn() {
		if(conn == null) {
			conn = DBUtil.getConnection();
		}
		
		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
