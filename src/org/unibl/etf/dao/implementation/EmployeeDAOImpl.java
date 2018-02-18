package org.unibl.etf.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.unibl.etf.dao.interfaces.EmployeeDAO;
import org.unibl.etf.dto.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "employee";

	static {
		pkColumns.add("employee_id");
		stdColumns.add("first_name");
		stdColumns.add("last_name");
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
	public EmployeeDAOImpl() {
		this(null);
	}

	public EmployeeDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public Employee getByPrimaryKey(Integer employeeId) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, employeeId);
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
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return 0;
	}

	public List<Employee> selectAll() {
		List<Employee> ret = new ArrayList<>();
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

	public List<Employee> select(String whereStatement, Object[] bindVariables) {
		List<Employee> ret = new ArrayList<>();
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
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public int update(Employee obj) {
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

	public int insert(Employee obj) {
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
				obj.setEmployeeId(rs.getInt(1));
			}
			
			if (rowCount != 1) {
				return 0;
			}
			
			return rowCount;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}
		
		return 0;
	}

	public int delete(Employee obj) {
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
	public List<Employee> getByFirstName(String firstName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Employee> ret = new ArrayList<>();

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
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Employee> getByLastName(String lastName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Employee> ret = new ArrayList<>();

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
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}
	
	public List<Employee> getByIsDeleted(Boolean isDeleted) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Employee> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "deleted" })));
			DBUtil.bind(ps, 1, isDeleted);
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
	protected int bindPrimaryKey(PreparedStatement ps, Employee obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getEmployeeId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Employee obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getFirstName());
		DBUtil.bind(ps, pos++, obj.getLastName());
		DBUtil.bind(ps, pos++, obj.getDeleted());

		return pos;
	}

	protected Employee fromResultSet(ResultSet rs) throws SQLException {
		Employee obj = new Employee();

		obj.setEmployeeId(DBUtil.getInt(rs, "employee_id"));
		obj.setFirstName(DBUtil.getString(rs, "first_name"));
		obj.setLastName(DBUtil.getString(rs, "last_name"));
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
