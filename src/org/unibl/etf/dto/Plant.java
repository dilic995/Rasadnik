package org.unibl.etf.dto;

import java.sql.Blob;

public class Plant {
	private Integer plantId;
	private String scientificName;
	private String knownAs;
	private String description;
	private Blob image;
	private Boolean isEvergreen;
	private Boolean owned;

	//
	// getters / setters
	//
	public Integer getPlantId() {
		return this.plantId;
	}

	public void setPlantId(Integer plantId) {
		this.plantId = plantId;
	}

	public String getScientificName() {
		return this.scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getKnownAs() {
		return this.knownAs;
	}

	public void setKnownAs(String knownAs) {
		this.knownAs = knownAs;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Blob getImage() {
		return this.image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Boolean getOwned() {
		return this.owned;
	}

	public void setOwned(Boolean owned) {
		this.owned = owned;
	}

	public Boolean getIsEvergreen() {
		return isEvergreen;
	}

	public void setIsEvergreen(Boolean isEvergreen) {
		this.isEvergreen = isEvergreen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((plantId == null) ? 0 : plantId.hashCode());
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
		Plant other = (Plant) obj;
		if (plantId == null) {
			if (other.plantId != null)
				return false;
		} else if (!plantId.equals(other.plantId))
			return false;
		return true;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("org.unibl.etf.dto").append(".").append("Plant").append("(");

		buffer.append("[").append("plantId").append("=").append(plantId).append("]");
		buffer.append("[").append("scientificName").append("=").append(scientificName).append("]");
		buffer.append("[").append("knownAs").append("=").append(knownAs).append("]");
		buffer.append("[").append("description").append("=").append(description).append("]");
		buffer.append("[").append("image").append("=").append(image).append("]");
		buffer.append("[").append("isEvergreen").append("=").append(isEvergreen).append("]");
		buffer.append("[").append("owned").append("=").append(owned).append("]");

		return buffer.append(")").toString();
	}
}
