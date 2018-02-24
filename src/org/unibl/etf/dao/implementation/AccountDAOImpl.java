package org.unibl.etf.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.unibl.etf.dao.interfaces.AccountDAO;
import org.unibl.etf.dto.Account;

public class AccountDAOImpl implements AccountDAO{
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "account";
	
	
	static {
		pkColumns.add("account_id");
		stdColumns.add("username");
		stdColumns.add("hash");
		stdColumns.add("first_login");
		stdColumns.add("is_admin");
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
	public AccountDAOImpl() {
		this(null);
	}

	public AccountDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//

	@Override
	public Account getByPrimaryKey(Integer accountID) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, accountID);
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

	@Override
	public List<Account> selectAll() {
		List<Account> ret = new ArrayList<>();
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

	@Override
	public List<Account> select(String whereStatement, Object[] bindVariables) {
		List<Account> ret = new ArrayList<>();
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

	@Override
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

	@Override
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

	@Override
	public int update(Account obj) {
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

	@Override
	public int insert(Account obj) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pos = 1;

		try {
			ps = getConn().prepareStatement(DBUtil.insert(tableName, pkColumns, stdColumns),
					PreparedStatement.RETURN_GENERATED_KEYS);
			pos = bindPrimaryKey(ps, obj, pos);
			bindStdColumns(ps, obj, pos);

			int rowCount = ps.executeUpdate();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				obj.setAccountId(rs.getInt(1));
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

	@Override
	public int delete(Account obj) {
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

	@Override
	public Account getByUsername(String username) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Account ret = null;

		try {
			if (null == username) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "username" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "username" })));
				DBUtil.bind(ps, 1, username);
			}

			rs = ps.executeQuery();

			while (rs.next())
				ret=fromResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	@Override
	public List<Account> getByHash(String hash) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Account> ret = new ArrayList<>();

		try {
			if (null == hash) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "hash" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "hash" })));
				DBUtil.bind(ps, 1, hash);
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

	@Override
	public List<Account> getByFirstLogin(Boolean firstLogin) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Account> ret = new ArrayList<>();

		try {
			if (null == firstLogin) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "first_login" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "first_login" })));
				DBUtil.bind(ps, 1, firstLogin);
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
	
	@Override
	public List<Account> getByIsAdmin(Boolean isAdmin) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Account> ret = new ArrayList<>();

		try {
			if (null == isAdmin) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "is_admin" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "is_admin" })));
				DBUtil.bind(ps, 1, isAdmin);
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

	@Override
	public List<Account> getByDeleted(Boolean deleted) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Account> ret = new ArrayList<>();

		try {
			if (null == deleted) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "deleted" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "deleted" })));
				DBUtil.bind(ps, 1, deleted);
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
	
	//
	// helpers
	//
	protected int bindPrimaryKey(PreparedStatement ps, Account obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getAccountId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Account obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getUsername());
		DBUtil.bind(ps, pos++, obj.getHash());
		DBUtil.bind(ps, pos++, obj.getFirstLogin());
		DBUtil.bind(ps, pos++, obj.getDeleted());

		return pos;
	}

	protected Account fromResultSet(ResultSet rs) throws SQLException {
		Account obj = new Account();

		obj.setAccountId(DBUtil.getInt(rs, "account_id"));
		obj.setUsername(DBUtil.getString(rs, "username"));
		obj.setHash(DBUtil.getString(rs, "hash"));
		obj.setFirstLogin(DBUtil.getBoolean(rs, "first_login"));
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
