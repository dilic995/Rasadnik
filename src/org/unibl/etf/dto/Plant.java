package org.unibl.etf.dto;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Collections;
import java.util.List;

import org.unibl.etf.dao.interfaces.DAOFactory;

public class Plant {

	private Integer plantId;
	private String scientificName;
	private String knownAs;
	private String description;
	private Blob image;
	private Boolean isConifer;
	private Boolean owned;
	private Boolean deleted;
	private List<PriceHeightRatio> ratios;

	//
	// constructors
	//
	// podrazumijevani
	public Plant() {
	}

	// svi parametri
	public Plant(Integer plantId, String scientificName, String knownAs, String description, Blob image,
			Boolean isConifer, Boolean owned, List<PriceHeightRatio> ratios, Boolean deleted) {
		super();
		this.plantId = plantId;
		this.scientificName = scientificName;
		this.knownAs = knownAs;
		this.description = description;
		this.image = image;
		this.isConifer = isConifer;
		this.owned = owned;
		this.ratios = ratios;
		this.deleted = deleted;
	}

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
		if (this.image == null) {

		}
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

	public Boolean getIsConifer() {
		return isConifer;
	}

	public void setIsConifer(Boolean isConifer) {
		this.isConifer = isConifer;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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
		buffer.append("[").append("isConifer").append("=").append(isConifer).append("]");
		buffer.append("[").append("owned").append("=").append(owned).append("]");

		return buffer.append(")").toString();
	}

	public List<PriceHeightRatio> getRatios() {
		if (ratios == null) {
			ratios = DAOFactory.getInstance().getPriceHeightRatioDAO().getByPlantId(plantId);
			Collections.sort(ratios, new PriceHeightRatioComparatorFrom());
		}
		return ratios;
	}

	public void setRatios(List<PriceHeightRatio> ratios) {
		this.ratios = ratios;
	}

	public BigDecimal getPrice(BigDecimal height) {
		BigDecimal result = null;
		for (PriceHeightRatio ratio : getRatios()) {
			if ((height.compareTo(ratio.getHeightMin()) >= 0)
					&& ((ratio.getHeightMax() == null) || (height.compareTo(ratio.getHeightMax()) < 0))) {
				result = ratio.getPrice();
				break;
			}
		}
		return result;
	}
	public BigDecimal getHeightMin(BigDecimal height) {
		BigDecimal result = null;
		for (PriceHeightRatio ratio : getRatios()) {
			if ((height.compareTo(ratio.getHeightMin()) >= 0)
					&& ((ratio.getHeightMax() == null) || (height.compareTo(ratio.getHeightMax()) < 0))) {
				result = ratio.getHeightMin();
				break;
			}
		}
		return result;
	}
}
