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
import org.unibl.etf.dao.interfaces.EmployeeHasTaskDAO;
import org.unibl.etf.dto.EmployeeHasTask;

public class EmployeeHasTaskDAOImpl implements EmployeeHasTaskDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "employee_has_task";

	static {
		pkColumns.add("date");
		pkColumns.add("task_id");
		pkColumns.add("employee_id");
		stdColumns.add("hourly_wage");
		stdColumns.add("hours");
		stdColumns.add("paid_off");
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
	public EmployeeHasTaskDAOImpl() {
		this(null);
	}

	public EmployeeHasTaskDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public EmployeeHasTask getByPrimaryKey(Date date, Integer taskId, Integer employeeId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, date);
			DBUtil.bind(ps, pos++, taskId);
			DBUtil.bind(ps, pos++, employeeId);
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

	public List<EmployeeHasTask> selectAll() throws DAOException {
		List<EmployeeHasTask> ret = new ArrayList<>();
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

	public List<EmployeeHasTask> select(String whereStatement, Object[] bindVariables) throws DAOException {
		List<EmployeeHasTask> ret = new ArrayList<>();
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

	public int update(EmployeeHasTask obj) throws DAOException {
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

	public int insert(EmployeeHasTask obj) throws DAOException {
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

	public int delete(EmployeeHasTask obj) throws DAOException {
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
	public List<EmployeeHasTask> getByDate(Date date) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EmployeeHasTask> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "date" })));
			DBUtil.bind(ps, 1, date);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException("SQL Error in finder getByDate()", e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<EmployeeHasTask> getByTaskId(Integer taskId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EmployeeHasTask> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "task_id" })));
			DBUtil.bind(ps, 1, taskId);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException("SQL Error in finder getByTaskId()", e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<EmployeeHasTask> getByEmployeeId(Integer employeeId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EmployeeHasTask> ret = new ArrayList<>();

		try {
			ps = getConn().prepareStatement(
					DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "employee_id" })));
			DBUtil.bind(ps, 1, employeeId);
			rs = ps.executeQuery();

			while (rs.next())
				ret.add(fromResultSet(rs));
		} catch (SQLException e) {
			throw new DAOException("SQL Error in finder getByEmployeeId()", e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<EmployeeHasTask> getByHourlyWage(BigDecimal hourlyWage) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EmployeeHasTask> ret = new ArrayList<>();

		try {
			if (null == hourlyWage) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "hourly_wage" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "hourly_wage" })));
				DBUtil.bind(ps, 1, hourlyWage);
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

	public List<EmployeeHasTask> getByHours(Integer hours) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EmployeeHasTask> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "hours" })));
			DBUtil.bind(ps, 1, hours);
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

	public List<EmployeeHasTask> getByPaidOff(Boolean paidOff) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<EmployeeHasTask> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "paid_off" })));
			DBUtil.bind(ps, 1, paidOff);
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
	protected int bindPrimaryKey(PreparedStatement ps, EmployeeHasTask obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getDate());
		DBUtil.bind(ps, pos++, obj.getTaskId());
		DBUtil.bind(ps, pos++, obj.getEmployeeId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, EmployeeHasTask obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getHourlyWage());
		DBUtil.bind(ps, pos++, obj.getHours());
		DBUtil.bind(ps, pos++, obj.getPaidOff());

		return pos;
	}

	protected EmployeeHasTask fromResultSet(ResultSet rs) throws SQLException {
		EmployeeHasTask obj = new EmployeeHasTask();

		obj.setDate(DBUtil.getDate(rs, "date"));
		obj.setTaskId(DBUtil.getInteger(rs, "task_id"));
		obj.setEmployeeId(DBUtil.getInteger(rs, "employee_id"));
		obj.setHourlyWage(DBUtil.getBigDecimal(rs, "hourly_wage"));
		obj.setHours(DBUtil.getInt(rs, "hours"));
		obj.setPaidOff(DBUtil.getBoolean(rs, "paid_off"));

		return obj;
	}

	protected Connection getConn() {
		if(conn == null) {
			conn = DBUtil.getConnection();
		}
		
		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
