package org.unibl.etf.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.unibl.etf.dao.interfaces.DAOFactory;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BasisTableItem {

	public BasisTableItem() {
	}

	public BasisTableItem(Basis basis) {
		this.basis = basis;
		this.scientificName = new SimpleStringProperty(basis.getPlant().getScientificName());
		this.knownAs = new SimpleStringProperty(basis.getPlant().getKnownAs());
		this.date = new SimpleStringProperty(new SimpleDateFormat("dd.MM.yyyy").format(basis.getPlantingDate()));
		this.current = new SimpleIntegerProperty(
				DAOFactory.getInstance().getBasisDAO().getNum(basis.getBasisId(), "number_of_plants", "region"));
		this.total = new SimpleIntegerProperty(DAOFactory.getInstance().getBasisDAO().getNum(basis.getBasisId(),
				"take_a_root", "reproduction_cutting"));
		this.type = new SimpleStringProperty(basis.getPlant().getIsConifer() ? "Cetinar" : "Liscar");
	}

	private StringProperty scientificName;
	private StringProperty knownAs;
	private StringProperty date;
	private IntegerProperty current;
	private IntegerProperty total;
	private StringProperty type;
	private Basis basis;

	public final StringProperty scientificNameProperty() {
		return this.scientificName;
	}

	public final String getscientificName() {
		return this.basis.getPlant().getScientificName();
	}

	public final void setscientificName(final String scientificName) {
		this.scientificNameProperty().set(scientificName);
		this.basis.getPlant().setScientificName(scientificName);
	}

	public final StringProperty knownAsProperty() {
		return this.knownAs;
	}

	public final String getknownAs() {
		return this.basis.getPlant().getKnownAs();
	}

	public final void setknownAs(final String knownAs) {
		this.knownAsProperty().set(knownAs);
		this.basis.getPlant().setKnownAs(knownAs);
	}

	public final StringProperty dateProperty() {
		return this.date;
	}

	public final Date getdate() {
		return this.basis.getPlantingDate();
	}

	public final void setdate(final Date date) {
		this.dateProperty().set(new SimpleDateFormat("dd.MM.yyyy").format(date));
		this.basis.setPlantingDate(date);
	}

	public final IntegerProperty currentProperty() {
		this.current = new SimpleIntegerProperty(
				DAOFactory.getInstance().getBasisDAO().getNum(basis.getBasisId(), "number_of_plants", "region"));
		return this.current;
	}

	public final int getcurrent() {
		return this.currentProperty().get();
	}

	public final void setcurrent(final int current) {
		this.currentProperty().set(current);
	}

	public final IntegerProperty totalProperty() {
		this.total = new SimpleIntegerProperty(DAOFactory.getInstance().getBasisDAO().getNum(basis.getBasisId(),
				"take_a_root", "reproduction_cutting"));
		return this.total;
	}

	public final int gettotal() {
		return this.totalProperty().get();
	}

	public final void settotal(final int total) {
		this.totalProperty().set(total);
	}

	public final StringProperty typeProperty() {
		return this.type;
	}

	public final String gettype() {
		return this.typeProperty().get();
	}

	public final void settype(final String type) {
		this.typeProperty().set(type);
	}

	public Basis getBasis() {
		return basis;
	}

	public void setBasis(Basis basis) {
		this.basis = basis;
	}
	
}
