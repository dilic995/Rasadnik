// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.interfaces;

import org.unibl.etf.dto.Basis;

import java.util.Date;
import java.util.List;


public interface BasisDAO
{
  // CRUD methods
  public Basis getByPrimaryKey(int basisId) throws DAOException;

  public List selectAll() throws DAOException;

  public List select(String whereStatement, Object[] bindVariables)
    throws DAOException;

  public long selectCount() throws DAOException;

  public long selectCount(String whereStatement, Object[] bindVariables)
    throws DAOException;

  public int update(Basis obj) throws DAOException;

  public int insert(Basis obj) throws DAOException;

  public int delete(Basis obj) throws DAOException;

  // Finders
  public List getByPlantingDate(Date plantingDate) throws DAOException;

  public List getByProduced(int produced) throws DAOException;

  public List getByTakeARoot(int takeARoot) throws DAOException;

  public List getByActive(byte active) throws DAOException;

  public List getByPlantId(int plantId) throws DAOException;
}
