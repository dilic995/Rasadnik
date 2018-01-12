package org.unibl.etf.dto;

public class PlantMaintanceActivity {
	private Integer plantMaintanceActivityId;
	private String activity;
	private Boolean plant;

	//
	// getters / setters
	//
	public Integer getPlantMaintanceActivityId() {
		return this.plantMaintanceActivityId;
	}

	public void setPlantMaintanceActivityId(Integer plantMaintanceActivityId) {
		this.plantMaintanceActivityId = plantMaintanceActivityId;
	}

	public String getActivity() {
		return this.activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Boolean getPlant() {
		return this.plant;
	}

	public void setPlant(Boolean plant) {
		this.plant = plant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((plantMaintanceActivityId == null) ? 0 : plantMaintanceActivityId.hashCode());
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
		PlantMaintanceActivity other = (PlantMaintanceActivity) obj;
		if (plantMaintanceActivityId == null) {
			if (other.plantMaintanceActivityId != null)
				return false;
		} else if (!plantMaintanceActivityId.equals(other.plantMaintanceActivityId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("PlantMaintanceActivity")
				.append("(");

		buffer.append("[").append("plantMaintanceActivityId").append("=").append(plantMaintanceActivityId).append("]");
		buffer.append("[").append("activity").append("=").append(activity).append("]");
		buffer.append("[").append("plant").append("=").append(plant).append("]");

		return buffer.append(")").toString();
	}
}
