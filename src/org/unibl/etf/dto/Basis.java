package org.unibl.etf.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.unibl.etf.dao.interfaces.DAOFactory;

public class Basis {
	public Basis(Integer basisId, Date plantingDate, Integer plantId, Plant plant,
			List<ReproductionCutting> cuttings, Boolean deleted) {
		super();
		this.basisId = basisId;
		this.plantingDate = plantingDate;
		this.plantId = plantId;
		this.plant = plant;
		this.cuttings = cuttings;
		this.deleted = deleted;
	}

	private Integer basisId;
	private Date plantingDate;
	private Integer plantId;
	private Plant plant;
	private List<ReproductionCutting> cuttings;
	private Boolean deleted;
	
	
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

	public Integer getPlantId() {
		return this.plantId;
	}

	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
		this.plant = null;
	}

	public Boolean getDeleted() {
		return deleted;
	}


	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}


	public Plant getPlant() {
		if (this.plant == null) {
			this.plant = DAOFactory.getInstance().getPlantDAO().getAllByPrimaryKey(this.plantId);
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

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy.");
		return basisId + " [" + format.format(plantingDate) + "] - " + getPlant().toString();
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
