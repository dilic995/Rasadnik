package org.unibl.etf.dto;

import java.util.Date;
import java.util.List;

import org.unibl.etf.dao.interfaces.DAOFactory;

public class Basis {
	public Basis(Integer basisId, Date plantingDate, Boolean active, Integer plantId, Plant plant,
			List<ReproductionCutting> cuttings) {
		super();
		this.basisId = basisId;
		this.plantingDate = plantingDate;
		this.active = active;
		this.plantId = plantId;
		this.plant = plant;
		this.cuttings = cuttings;
	}

	private Integer basisId;
	private Date plantingDate;
	private Boolean active;
	private Integer plantId;
	private Plant plant;
	private List<ReproductionCutting> cuttings;
	
	
	public Basis() {
	}
	
	
	//
	// getters / setters
	//
	public Integer getBasisId() {
		return this.basisId;
	}

	public void setBasisId(Integer basisId) {
		this.basisId = basisId;
	}

	public Date getPlantingDate() {
		return this.plantingDate;
	}

	public void setPlantingDate(Date plantingDate) {
		this.plantingDate = plantingDate;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getPlantId() {
		return this.plantId;
	}

	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
		this.plant = null;
	}

	public Plant getPlant() {
		if (this.plant == null) {
			this.plant = DAOFactory.getInstance().getPlantDAO().getByPrimaryKey(this.plantId);
		}
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basisId == null) ? 0 : basisId.hashCode());
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
		Basis other = (Basis) obj;
		if (basisId == null) {
			if (other.basisId != null)
				return false;
		} else if (!basisId.equals(other.basisId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Basis").append("(");

		buffer.append("[").append("basisId").append("=").append(basisId).append("]");
		buffer.append("[").append("plantingDate").append("=").append(plantingDate).append("]");
		buffer.append("[").append("active").append("=").append(active).append("]");
		buffer.append("[").append("plantId").append("=").append(plantId).append("]");

		return buffer.append(")").toString();
	}

	public List<ReproductionCutting> getCuttings() {
		if(cuttings == null) {
			cuttings = DAOFactory.getInstance().getReproductionCuttingDAO().selectAll();
		}
		return cuttings;
	}

	public void setCuttings(List<ReproductionCutting> cuttings) {
		this.cuttings = cuttings;
	}
}
