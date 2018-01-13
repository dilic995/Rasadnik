// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.implementation;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dao.interfaces.ReproductionCuttingDAO;
import org.unibl.etf.dto.ReproductionCutting;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class ReproductionCuttingDAOImpl implements ReproductionCuttingDAO
{
  //
  // static data
  //
  protected static List<String> pkColumns = new ArrayList<>();
  protected static List<String> stdColumns = new ArrayList<>();
  protected static List<String> allColumns = new ArrayList<>();
  protected static String tableName = "reproduction_cutting";

  static
  {
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
  public ReproductionCuttingDAOImpl()
  {
    this(null);
  }

  public ReproductionCuttingDAOImpl(Connection conn)
  {
    this.conn = conn;
  }

  //
  // CRUD methods
  //
  public ReproductionCutting getByPrimaryKey(Integer idBasis,Date date)
    throws DAOException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      int pos = 1;
      ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
      DBUtil.bind(ps, pos++, idBasis);
      DBUtil.bind(ps, pos++, date);
      rs = ps.executeQuery();

      if (rs.next())
      {
        return fromResultSet(rs);
      }
    }
    catch (SQLException e)
    {
      throw new DAOException(e);
    }
    finally
    {
      DBUtil.close(ps, rs,conn);
    }

    return null;
  }

  public Long selectCount() throws DAOException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      ps = getConn().prepareStatement("select count(*) from " + tableName);
      rs = ps.executeQuery();

      if (rs.next())
      {
        return rs.getLong(1);
      }
    }
    catch (SQLException e)
    {
      throw new DAOException(e);
    }
    finally
    {
      DBUtil.close(ps, rs,conn);
    }

    return 0L;
  }

  public Long selectCount(String whereStatement, Object[] bindVariables)
    throws DAOException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;

    if (!whereStatement.trim().toUpperCase().startsWith("WHERE"))
    {
      whereStatement = " WHERE " + whereStatement;
    }
    else if (whereStatement.startsWith(" ") == false)
    {
      whereStatement = " " + whereStatement;
    }

    try
    {
      ps = getConn().prepareStatement("select count(*) from " + tableName + whereStatement);

      for (int i = 0; i < bindVariables.length; i++)
        DBUtil.bind(ps, i + 1, bindVariables[i]);

      rs = ps.executeQuery();

      if (rs.next())
      {
        return rs.getLong(1);
      }
    }
    catch (SQLException e)
    {
      throw new DAOException(e);
    }
    finally
    {
      DBUtil.close(ps, rs,conn);
    }

    return 0L;
  }

  public List<ReproductionCutting> selectAll() throws DAOException
  {
    List<ReproductionCutting> ret = new ArrayList<>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns));
      rs = ps.executeQuery();

      while (rs.next())
        ret.add(fromResultSet(rs));
    }
    catch (SQLException e)
    {
      throw new DAOException(e);
    }
    finally
    {
      DBUtil.close(ps, rs,conn);
    }

    return ret;
  }

  public List<ReproductionCutting> select(String whereStatement, Object[] bindVariables)
    throws DAOException
  {
    List<ReproductionCutting> ret = new ArrayList<>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    if (!whereStatement.trim().toUpperCase().startsWith("WHERE"))
    {
      whereStatement = " WHERE " + whereStatement;
    }
    else if (whereStatement.startsWith(" ") == false)
    {
      whereStatement = " " + whereStatement;
    }

    try
    {
      ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns) + whereStatement);

      for (int i = 0; i < bindVariables.length; i++)
        DBUtil.bind(ps, i + 1, bindVariables[i]);

      rs = ps.executeQuery();

      while (rs.next())
        ret.add(fromResultSet(rs));
    }
    catch (SQLException e)
    {
      throw new DAOException("Error in select(), table = " + tableName, e);
    }
    finally
    {
      DBUtil.close(ps, rs,conn);
    }

    return ret;
  }

  public Integer update(ReproductionCutting obj) throws DAOException
  {
    PreparedStatement ps = null;
    int pos = 1;

    try
    {
      ps = getConn().prepareStatement(DBUtil.update(tableName, stdColumns, pkColumns));
      pos = bindStdColumns(ps, obj, pos);
      bindPrimaryKey(ps, obj, pos);

      int rowCount = ps.executeUpdate();

      if (rowCount != 1)
      {
        throw new DAOException("Error updating " + obj.getClass() + " in " + tableName +
          ", affected rows = " + rowCount);
      }

      return rowCount;
    }
    catch (SQLException e)
    {
      throw new DAOException(e);
    }
    finally
    {
      DBUtil.close(ps, null,conn);
    }
  }

  public Integer insert(ReproductionCutting obj) throws DAOException
  {
    PreparedStatement ps = null;
    int pos = 1;

    try
    {
      ps = getConn().prepareStatement(DBUtil.insert(tableName, pkColumns, stdColumns));
      pos = bindPrimaryKey(ps, obj, pos);
      bindStdColumns(ps, obj, pos);

      int rowCount = ps.executeUpdate();

      if (rowCount != 1)
      {
        throw new DAOException("Error inserting " + obj.getClass() + " in " + tableName +
          ", affected rows = " + rowCount);
      }

      return rowCount;
    }
    catch (SQLException e)
    {
      throw new DAOException(e);
    }
    finally
    {
      DBUtil.close(ps, null,conn);
    }
  }

  public Integer delete(ReproductionCutting obj) throws DAOException
  {
    PreparedStatement ps = null;

    try
    {
      ps = getConn().prepareStatement(DBUtil.delete(tableName, pkColumns));
      bindPrimaryKey(ps, obj, 1);

      int rowCount = ps.executeUpdate();

      if (rowCount != 1)
      {
        throw new DAOException("Error deleting " + obj.getClass() + " in " + tableName +
          ", affected rows = " + rowCount);
      }

      return rowCount;
    }
    catch (SQLException e)
    {
      throw new DAOException(e);
    }
    finally
    {
      DBUtil.close(ps, null,conn);
    }
  }

  //
  // finders
  //
  public List<ReproductionCutting> getByBasisId(Integer basisId) throws DAOException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<ReproductionCutting> ret = new ArrayList<>();

    try
    {
      ps = getConn()
             .prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[]{ "basis_id" })));
      DBUtil.bind(ps, 1, basisId);
      rs = ps.executeQuery();

      while (rs.next())
        ret.add(fromResultSet(rs));
    }
    catch (SQLException e)
    {
      throw new DAOException("SQL Error in finder getByBasisId()", e);
    }
    finally
    {
      DBUtil.close(ps, rs,conn);
    }

    return ret;
  }

  public List<ReproductionCutting> getByDate(Date date) throws DAOException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<ReproductionCutting> ret = new ArrayList<>();

    try
    {
      ps = getConn()
             .prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[]{ "date" })));
      DBUtil.bind(ps, 1, date);
      rs = ps.executeQuery();

      while (rs.next())
        ret.add(fromResultSet(rs));
    }
    catch (SQLException e)
    {
      throw new DAOException("SQL Error in finder getByDate()", e);
    }
    finally
    {
      DBUtil.close(ps, rs,conn);
    }

    return ret;
  }

  //
  // helpers
  //
  protected int bindPrimaryKey(PreparedStatement ps, ReproductionCutting obj, int pos)
    throws SQLException
  {
    DBUtil.bind(ps, pos++, obj.getBasisId());
    DBUtil.bind(ps, pos++, obj.getDate());

    return pos;
  }

  protected int bindStdColumns(PreparedStatement ps, ReproductionCutting obj, int pos)
    throws SQLException
  {
    return pos;
  }

  protected ReproductionCutting fromResultSet(ResultSet rs) throws SQLException
  {
    ReproductionCutting obj = new ReproductionCutting();

    try {
		obj.setBasis(DAOFactory.getInstance().getBasisDAO().getByPrimaryKey(DBUtil.getInt(rs, "basis_id")));
	} catch (DAOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    obj.setBasisId((DBUtil.getInt(rs, "basis_id")));
    obj.setDate(DBUtil.getDate(rs, "date"));

    return obj;
  }

  protected Connection getConn()
  {
    return (conn == null) ? DBUtil.getConnection() : conn;
  }
}
