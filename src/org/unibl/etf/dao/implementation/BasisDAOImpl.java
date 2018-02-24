package org.unibl.etf.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.unibl.etf.dao.interfaces.BasisDAO;
import org.unibl.etf.dto.Basis;

public class BasisDAOImpl implements BasisDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "basis";
	protected static String SELECT_BY_NAME = "select b.basis_id, b.planting_date, b.deleted, b.plant_id "
			+ "from basis b inner join plant p on b.plant_id=p.plant_id where ";
	
	
	static {
		pkColumns.add("basis_id");
		stdColumns.add("planting_date");
		stdColumns.add("deleted");
		stdColumns.add("plant_id");
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
	public BasisDAOImpl() {
		this(null);
	}

	public BasisDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public Basis getByPrimaryKey(Integer basisId) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, basisId);
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

	public List<Basis> selectAll() {
		List<Basis> ret = new ArrayList<>();
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

	public List<Basis> select(String whereStatement, Object[] bindVariables) {
		List<Basis> ret = new ArrayList<>();
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

	public int update(Basis obj) {
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

	public int insert(Basis obj) {
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
				obj.setBasisId(rs.getInt(1));
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

	public int delete(Basis obj) {
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
	public List<Basis> getByPlantingDate(Date plantingDate) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Basis> ret = new ArrayList<>();

		try {
			if (null == plantingDate) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "planting_date" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "planting_date" })));
				DBUtil.bind(ps, 1, plantingDate);
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

	public List<Basis> getByActive(Boolean active) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Basis> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "deleted" })));
			DBUtil.bind(ps, 1, active);
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

	public List<Basis> getByPlantId(Integer plantId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Basis> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "plant_id" })));
			DBUtil.bind(ps, 1, plantId);
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
	protected int bindPrimaryKey(PreparedStatement ps, Basis obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getBasisId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Basis obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getPlantingDate());
		DBUtil.bind(ps, pos++, obj.getDeleted());
		DBUtil.bind(ps, pos++, obj.getPlantId());

		return pos;
	}

	protected Basis fromResultSet(ResultSet rs) throws SQLException {
		Basis obj = new Basis();

		obj.setBasisId(DBUtil.getInt(rs, "basis_id"));
		obj.setPlantingDate(DBUtil.getDate(rs, "planting_date"));
		obj.setDeleted(DBUtil.getBoolean(rs, "deleted"));
		obj.setPlantId(DBUtil.getInteger(rs, "plant_id"));

		return obj;
	}

	protected Connection getConn() {
		if (conn == null) {
			conn = DBUtil.getConnection();
		}

		return (conn == null) ? DBUtil.getConnection() : conn;
	}

	@Override
	public Integer getNum(int basis_id, String type, String tableName) {
		Integer result = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String querry = "select sum(" + type + ") as broj from " + tableName + " where basis_id=?";
		try {
			ps = getConn().prepareStatement(querry);
			DBUtil.bind(ps, 1, basis_id);
			rs = ps.executeQuery();
			if(rs.next()) {
				result = rs.getInt("broj");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(ps, rs, conn);
		}
		return result;
	}

	@Override
	public Basis getAllByPrimaryKey(Integer basisId) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.selectAll(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, basisId);
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
	public List<Basis> getByName(String name, Boolean scientific) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Basis> ret = new ArrayList<>();

		try {
			String sql = SELECT_BY_NAME + (scientific ? "scientific_name" : "known_as") + " like ? and b.deleted=false";
			ps = getConn().prepareStatement(sql);
			DBUtil.bind(ps, 1, "%" + name + "%");
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
}
