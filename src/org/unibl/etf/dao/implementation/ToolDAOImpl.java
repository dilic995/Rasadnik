// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.implementation;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.ToolDAO;
import org.unibl.etf.dto.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ToolDAOImpl implements ToolDAO
{
  //
  // static data
  //
  protected static List<String> pkColumns = new ArrayList<>();
  protected static List<String> stdColumns = new ArrayList<>();
  protected static List<String> allColumns = new ArrayList<>();
  protected static String tableName = "tool";

  static
  {
    pkColumns.add("tool_id");
    stdColumns.add("tool_name");
    stdColumns.add("count");
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
  public ToolDAOImpl()
  {
    this(null);
  }

  public ToolDAOImpl(Connection conn)
  {
    this.conn = conn;
  }

  //
  // CRUD methods
  //
  public Tool getByPrimaryKey(Integer toolId) throws DAOException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try
    {
      int pos = 1;
      ps = getConn().prepareStatement(DBUtil.select(tableName, allColumns, pkColumns));
      DBUtil.bind(ps, pos++, toolId);
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

    return Long.valueOf(0);
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

    return Long.valueOf(0);
  }

  public List<Tool> selectAll() throws DAOException
  {
    List<Tool> ret = new ArrayList<>();
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

  public List<Tool> select(String whereStatement, Object[] bindVariables)
    throws DAOException
  {
    List<Tool> ret = new ArrayList<>();
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

  public Integer update(Tool obj) throws DAOException
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

  public Integer insert(Tool obj) throws DAOException
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

  public Integer delete(Tool obj) throws DAOException
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
  public List<Tool> getByToolName(String toolName) throws DAOException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Tool> ret = new ArrayList<>();

    try
    {
      if (null == toolName)
      {
        ps = getConn()
               .prepareStatement(DBUtil.selectNull(tableName, allColumns,
              Arrays.asList(new String[]{ "tool_name" })));
      }
      else
      {
        ps = getConn()
               .prepareStatement(DBUtil.select(tableName, allColumns,
              Arrays.asList(new String[]{ "tool_name" })));
        DBUtil.bind(ps, 1, toolName);
      }

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

  public List<Tool> getByCount(Integer count) throws DAOException
  {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Tool> ret = new ArrayList<>();

    try
    {
      ps = getConn()
             .prepareStatement(DBUtil.select(tableName, allColumns, Arrays.asList(new String[]{ "count" })));
      DBUtil.bind(ps, 1, count);
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

  //
  // helpers
  //
  protected int bindPrimaryKey(PreparedStatement ps, Tool obj, int pos)
    throws SQLException
  {
    DBUtil.bind(ps, pos++, obj.getToolId());

    return pos;
  }

  protected int bindStdColumns(PreparedStatement ps, Tool obj, int pos)
    throws SQLException
  {
    DBUtil.bind(ps, pos++, obj.getToolName());
    DBUtil.bind(ps, pos++, obj.getCount());

    return pos;
  }

  protected Tool fromResultSet(ResultSet rs) throws SQLException
  {
    Tool obj = new Tool();

    obj.setToolId(DBUtil.getInt(rs, "tool_id"));
    obj.setToolName(DBUtil.getString(rs, "tool_name"));
    obj.setCount(DBUtil.getInt(rs, "count"));

    return obj;
  }

  protected Connection getConn()
  {
    return (conn == null) ? DBUtil.getConnection() : conn;
  }
}
