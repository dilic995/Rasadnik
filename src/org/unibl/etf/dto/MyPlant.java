package org.unibl.etf.dto;

import java.util.List;

import javafx.scene.image.Image;

public class MyPlant {
	private Integer id;
	private String latinName;
	private String commonName;
	private String description;
	private Boolean owned;
	private Image image;
	private List<HeightPriceRatio> ratios;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLatinName() {
		return latinName;
	}
	public void setLatinName(String latinName) {
		this.latinName = latinName;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getOwned() {
		return owned;
	}
	public void setOwned(Boolean owned) {
		this.owned = owned;
	}
	public MyPlant(Integer id, String latinName, String commonName, String description, Boolean owned, Image image, List<HeightPriceRatio> ratios) {
		super();
		this.id = id;
		this.latinName = latinName;
		this.commonName = commonName;
		this.description = description;
		this.owned = owned;
		this.image = image;
		this.ratios = ratios;
	}
	public MyPlant() {
		// TODO Auto-generated constructor stub
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public List<HeightPriceRatio> getRatios() {
		return ratios;
	}
	public void setRatios(List<HeightPriceRatio> ratios) {
		this.ratios = ratios;
	}
	@Override
	public String toString() {
		return latinName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		MyPlant other = (MyPlant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
