package org.unibl.etf.dao.implementation;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.unibl.etf.dao.interfaces.PlantDAO;
import org.unibl.etf.dto.Plant;
import org.unibl.etf.util.ErrorLogger;

public class PlantDAOImpl implements PlantDAO {
	//
	// static data
	//
	protected static List<String> pkColumns = new ArrayList<>();
	protected static List<String> stdColumns = new ArrayList<>();
	protected static List<String> allColumns = new ArrayList<>();
	protected static String tableName = "plant";
	protected static String allPlantOnPricelist = "select php.plant_id, p.scientific_name, p.known_as, p.description, p.image, p.is_conifer, p.owned, p.deleted from pricelist_has_plant php inner join plant p on php.plant_id=p.plant_id and php.pricelist_id=?";
	protected static String NUM_IN_REGIONS = "select sum(r.number_of_plants) as total from basis b inner join region r on b.basis_id=r.basis_id where b.plant_id=? and r.number_of_plants>0 and r.deleted=false;";
	
	
	static {
		pkColumns.add("plant_id");
		stdColumns.add("scientific_name");
		stdColumns.add("known_as");
		stdColumns.add("description");
		stdColumns.add("image");
		stdColumns.add("is_conifer");
		stdColumns.add("owned");
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
	public PlantDAOImpl() {
		this(null);
	}

	public PlantDAOImpl(Connection conn) {
		this.conn = conn;
	}

	//
	// CRUD methods
	//
	public Plant getByPrimaryKey(Integer plantId) {
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

	public List<Plant> selectAll() {
		List<Plant> ret = new ArrayList<>();
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

	public List<Plant> select(String whereStatement, Object[] bindVariables) {
		List<Plant> ret = new ArrayList<>();
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

	public int update(Plant obj) {
		PreparedStatement ps = null;
		int pos = 1;

		try {
			ps = getConn().prepareStatement(DBUtil.update(tableName, stdColumns, pkColumns));
			pos = bindStdColumns(ps, obj, pos);
			bindPrimaryKey(ps, obj, pos);

			int rowCount = ps.executeUpdate();

			if (rowCount != 1) {

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

	public int insert(Plant obj){
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
				obj.setPlantId(rs.getInt(1));
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

	public int delete(Plant obj) {
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
	public List<Plant> getByScientificName(String scientificName) {
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
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Plant> getByKnownAs(String knownAs) {
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
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Plant> getByDescription(String description) {
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
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Plant> getByImage(Blob image) {
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
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}

	public List<Plant> getByIsConifer(Boolean isConifer) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Plant> ret = new ArrayList<>();

		try {
			ps = getConn().prepareStatement(
					DBUtil.select(tableName, allColumns, Arrays.asList(new String[] { "is_conifer" })));
			DBUtil.bind(ps, 1, isConifer);
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

	public List<Plant> getByOwned(Boolean owned) {
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
			e.printStackTrace();
			new ErrorLogger().log(e);
		} finally {
			DBUtil.close(ps, rs, conn);
		}

		return ret;
	}
	
	@Override
	public List<Plant> getByDeleted(Boolean deleted) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Plant> ret = new ArrayList<>();

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
	
	@Override
	public List<Plant> getPlantByPricelistId(Integer pricelistId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Plant> ret = new ArrayList<>();

		try {
			ps = getConn().prepareStatement(allPlantOnPricelist);
			ps.setInt(1, pricelistId);
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
	public int getNumInRegions(Plant plant) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int ret = 0;

		try {
			ps = getConn().prepareStatement(NUM_IN_REGIONS);
			ps.setInt(1, plant.getPlantId());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				ret = rs.getInt("total");
			}
			
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
	protected int bindPrimaryKey(PreparedStatement ps, Plant obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getPlantId());

		return pos;
	}

	protected int bindStdColumns(PreparedStatement ps, Plant obj, int pos) throws SQLException {
		DBUtil.bind(ps, pos++, obj.getScientificName());
		DBUtil.bind(ps, pos++, obj.getKnownAs());
		DBUtil.bind(ps, pos++, obj.getDescription());
		DBUtil.bind(ps, pos++, obj.getImage());
		DBUtil.bind(ps, pos++, obj.getIsConifer());
		DBUtil.bind(ps, pos++, obj.getOwned());
		DBUtil.bind(ps, pos++, obj.getDeleted());

		return pos;
	}

	protected Plant fromResultSet(ResultSet rs) throws SQLException {
		Plant obj = new Plant();

		obj.setPlantId(DBUtil.getInt(rs, "plant_id"));
		obj.setScientificName(DBUtil.getString(rs, "scientific_name"));
		obj.setKnownAs(DBUtil.getString(rs, "known_as"));
		obj.setDescription(DBUtil.getString(rs, "description"));
		obj.setImage(DBUtil.getBlob(rs, "image"));
		obj.setIsConifer(DBUtil.getBoolean(rs, "is_conifer"));
		obj.setOwned(DBUtil.getBoolean(rs, "owned"));
		obj.setDeleted(DBUtil.getBoolean(rs, "deleted"));

		return obj;
	}

	protected Connection getConn() {
		if (conn == null) {
			conn = DBUtil.getConnection();
		}

		return (conn == null) ? DBUtil.getConnection() : conn;
	}

	@Override
	public Plant getAllByPrimaryKey(Integer plantId) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			int pos = 1;
			ps = getConn().prepareStatement(DBUtil.selectAll(tableName, allColumns, pkColumns));
			DBUtil.bind(ps, pos++, plantId);
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
}
