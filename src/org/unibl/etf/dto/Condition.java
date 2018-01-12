package org.unibl.etf.dto;

public class Condition {
	private Integer conditionId;
	private String condition;

	//
	// getters / setters
	//
	public Integer getConditionId() {
		return this.conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditionId == null) ? 0 : conditionId.hashCode());
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
		Condition other = (Condition) obj;
		if (conditionId == null) {
			if (other.conditionId != null)
				return false;
		} else if (!conditionId.equals(other.conditionId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Condition").append("(");

		buffer.append("[").append("conditionId").append("=").append(conditionId).append("]");
		buffer.append("[").append("condition").append("=").append(condition).append("]");

		return buffer.append(")").toString();
	}
}
