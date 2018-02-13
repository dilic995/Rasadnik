// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dao.interfaces;

import java.util.List;

import org.unibl.etf.dto.Tool;

public interface ToolDAO {
	// CRUD methods
	public Tool getByPrimaryKey(Integer toolId);

	public List<Tool> selectAll();

	public List<Tool> select(String whereStatement, Object[] bindVariables);

	public Long selectCount();

	public Long selectCount(String whereStatement, Object[] bindVariables);

	public Integer update(Tool obj);

	public Integer insert(Tool obj);

	public Integer delete(Tool obj);
	
	public List<Tool> getByIsMachine(Boolean isMachine);

	// Finders
	public List<Tool> getByToolName(String toolName);

	public List<Tool> getByCount(Integer count);
}
