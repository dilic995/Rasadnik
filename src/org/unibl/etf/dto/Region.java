// Generated by DB Solo 5.2.3 on Jan 11, 2018 at 3:37:10 PM
package org.unibl.etf.dto;

import org.unibl.etf.dao.interfaces.DAOFactory;

public class Region {

	private Integer regionId;
	private Integer numberOfPlants;
	private Basis basis;
	private Integer basisId;
	private Double[] coords;
	private Boolean deleted;

	public Region() {
		// TODO Auto-generated constructor stub
	}

	public Region(Integer regionId, Integer numberOfPlants, Basis basis, Integer basisId, Double[] coords, Boolean deleted) {
		super();
		this.regionId = regionId;
		this.numberOfPlants = numberOfPlants;
		this.basis = basis;
		this.basisId = basisId;
		this.setCoords(coords);
		this.deleted = deleted;
	}

	//
	// getters / setters
	//
	public Integer getBasisId() {
		return basisId;
	}

	public void setBasisId(Integer basisId) {
		this.basisId = (basisId == 0 ? null : basisId);
		basis = null;
	}

	public Integer getRegionId() {
		return this.regionId;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getNumberOfPlants() {
		return this.numberOfPlants;
	}

	public void setNumberOfPlants(Integer numberOfPlants) {
		this.numberOfPlants = numberOfPlants;
	}

	public Basis getBasis() {
		if (basis == null) {
			this.basis = DAOFactory.getInstance().getBasisDAO().getAllByPrimaryKey(basisId);
		}
		return this.basis;
	}

	public void setBasis(Basis basis) {
		this.basis = basis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((regionId == null) ? 0 : regionId.hashCode());
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
		Region other = (Region) obj;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		return true;
	}

	public void addPlants(int num) {
		this.numberOfPlants += num;
	}

	public boolean removePlants(int num) {
		if (num > this.numberOfPlants) {
			return false;
		}
		this.numberOfPlants -= num;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Region").append("(");

		buffer.append("[").append("regionId").append("=").append(regionId).append("]");
		buffer.append("[").append("numberOfPlants").append("=").append(numberOfPlants).append("]");
		buffer.append("[").append("basisId").append("=").append(basis).append("]");

		return buffer.append(")").toString();
	}

	public Double[] getCoords() {
		return coords;
	}

	public void setCoords(Double[] coords) {
		this.coords = new Double[8];
		for(int i=0 ; i <8 ; i++) {
			this.coords[i] = coords[i].doubleValue();
		}
	}
}
