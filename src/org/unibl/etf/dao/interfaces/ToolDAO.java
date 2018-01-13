// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.interfaces;

import java.util.List;

import org.unibl.etf.dto.Tool;


public interface ToolDAO
{
  // CRUD methods
  public Tool getByPrimaryKey(Integer toolId) throws DAOException;

  public List<Tool> selectAll() throws DAOException;

  public List<Tool> select(String whereStatement, Object[] bindVariables)
    throws DAOException;

  public Long selectCount() throws DAOException;

  public Long selectCount(String whereStatement, Object[] bindVariables)
    throws DAOException;

  public Integer update(Tool obj) throws DAOException;

  public Integer insert(Tool obj) throws DAOException;

  public Integer delete(Tool obj) throws DAOException;

  // Finders
  public List<Tool> getByToolName(String toolName) throws DAOException;

  public List<Tool> getByCount(Integer count) throws DAOException;
}
