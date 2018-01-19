// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dto;

import java.util.Date;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;

public class ToolItem {
	private Integer toolItemId;
	private Date nextServiceDate;
	private Tool tool;
	private Condition condition;
	private Integer toolId;
	private Integer conditionId;
	private Boolean isDeleted;

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	//
	// getters / setters
	//
	public Integer getToolItemId() {
		return this.toolItemId;
	}

	public void setToolItemId(Integer toolItemId) {
		this.toolItemId = toolItemId;
		
	}

	public Date getNextServiceDate() {
		return this.nextServiceDate;
	}

	public void setNextServiceDate(Date nextServiceDate) {
		this.nextServiceDate = nextServiceDate;
	}
	public Integer getToolId() {
		return this.toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
		tool=null;
	}

	public Integer getConditionId() {
		return this.conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
		condition=null;
	}

	public Tool getTool() {
		if(tool==null) {
			try {
				tool=DAOFactory.getInstance().getToolDAO().getByPrimaryKey(toolId);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tool;
	}

	public void setTool(Tool tool) {
		this.tool = tool;
	}

	public Condition getCondition() {
		if(condition==null) {
			try {
				condition=DAOFactory.getInstance().getConditionDAO().getByPrimaryKey(conditionId);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((toolItemId == null) ? 0 : toolItemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToolItem other = (ToolItem) obj;
		if (toolItemId == null) {
			if (other.toolItemId != null)
				return false;
		} else if (!toolItemId.equals(other.toolItemId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("ToolItem").append("(");

		buffer.append("[").append("toolItemId").append("=").append(toolItemId).append("]");
		buffer.append("[").append("nextServiceDate").append("=").append(nextServiceDate).append("]");
		buffer.append("[").append("toolId").append("=").append(toolId).append("]");
		buffer.append("[").append("conditionId").append("=").append(conditionId).append("]");
		buffer.append("[").append("isDeleted").append("=").append(isDeleted).append("]");
		return buffer.append(")").toString();
	}
}
