package org.unibl.etf.dao.implementation;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.PlantDAO;
import org.unibl.etf.dto.Plant;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlantDAOImpl implements PlantDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "plant";

	static {
		pkColumns.add("plant_id");
		stdColumns.add("scientific_name");
		stdColumns.add("known_as");
		stdColumns.add("description");
		stdColumns.add("image");
		stdColumns.add("owned");
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
	public PlantDAOImpl() {
		this(null);
	}

	public PlantDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public Plant getByPrimaryKey(int plantId) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
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

	public List<Plant> selectAll() throws DAOException {
		List<Plant> ret = new ArrayList<>();
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

	public List<Plant> select(String whereStatement, Object[] bindVariables) throws DAOException {
		List<Plant> ret = new ArrayList<>();
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

	public int update(Plant obj) throws DAOException {
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

	public int insert(Plant obj) throws DAOException {
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

	public int delete(Plant obj) throws DAOException {
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
	public List<Plant> getByScientificName(String scientificName) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Plant> ret = new ArrayList<>();

		try {
			if (null == scientificName) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "scientific_name" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "scientific_name" })));
				DBUtil.bind(ps, 1, scientificName);
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

	public List<Plant> getByKnownAs(String knownAs) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Plant> ret = new ArrayList<>();

		try {
			if (null == knownAs) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "known_as" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "known_as" })));
				DBUtil.bind(ps, 1, knownAs);
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

	public List<Plant> getByDescription(Clob description) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Plant> ret = new ArrayList<>();

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
			throw new DAOException(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Plant> getByImage(Blob image) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Plant> ret = new ArrayList<>();

		try {
			if (null == image) {
				ps = getConn().prepareStatement(
						DBUtil.selectNull(tableName, allColumns, Arrays.asList(new String[] { "image" })));
			} else {
				ps = getConn().prepareStatement(
						DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "image" })));
				DBUtil.bind(ps, 1, image);
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

	public List<Plant> getByOwned(Boolean owned) throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Plant> ret = new ArrayList<>();

		try {
			ps = getConn()
					.prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "owned" })));
			DBUtil.bind(ps, 1, owned);
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
	protected int bindPrimaryKey(PreparedStatement ps, Plant obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getPlantId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Plant obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getScientificName());
		DBUtil.bind(ps, pos++, obj.getKnownAs());
		DBUtil.bind(ps, pos++, obj.getDescription());
		DBUtil.bind(ps, pos++, obj.getImage());
		DBUtil.bind(ps, pos++, obj.getOwned());

		return pos;
	}

	protected Plant fromResultSet(ResultSet rs) throws SQLException {
		Plant obj = new Plant();

		obj.setPlantId(DBUtil.getInt(rs, "plant_id"));
		obj.setScientificName(DBUtil.getString(rs, "scientific_name"));
		obj.setKnownAs(DBUtil.getString(rs, "known_as"));
		obj.setDescription(DBUtil.getClob(rs, "description"));
		obj.setImage(DBUtil.getBlob(rs, "image"));
		obj.setOwned(DBUtil.getBoolean(rs, "owned"));

		return obj;
	}

	protected Connection getConn() {
		return (conn == null) ? DBUtil.getConnection() : conn;
	}
}
