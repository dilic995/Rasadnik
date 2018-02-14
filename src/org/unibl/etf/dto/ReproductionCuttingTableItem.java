package org.unibl.etf.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReproductionCuttingTableItem {

	public ReproductionCuttingTableItem() {
	}

	public ReproductionCuttingTableItem(ReproductionCutting cutting) {
		this.setCutting(cutting);
		date = new SimpleStringProperty(new SimpleDateFormat("dd.MM.yyyy.").format(cutting.getDate()));
		takeARoot = new SimpleIntegerProperty(cutting.getTakeARoot());
		produced = new SimpleIntegerProperty(cutting.getProduces());
	}

	public ReproductionCutting getCutting() {
		return cutting;
	}

	public void setCutting(ReproductionCutting cutting) {
		this.cutting = cutting;
	}

	private ReproductionCutting cutting;
	private StringProperty date;
	private IntegerProperty produced;
	private IntegerProperty takeARoot;

	public final StringProperty dateProperty() {
		return this.date;
	}

	public final Date getDate() {
		return cutting.getDate();
	}

	public final void setDate(final Date date) {
		this.dateProperty().set(new SimpleDateFormat("dd.MM.yyyy.").format(date));
		cutting.setDate(date);
	}

	public final IntegerProperty producedProperty() {
		return this.produced;
	}

	public final int getProduced() {
		return this.producedProperty().get();
	}

	public final void setProduced(final int produced) {
		this.producedProperty().set(produced);
		cutting.setProduces(produced);
	}

	public final IntegerProperty takeARootProperty() {
		return this.takeARoot;
	}

	public final int getTakeARoot() {
		return this.takeARootProperty().get();
	}

	public final void setTakeARoot(final int takeARoot) {
		this.takeARootProperty().set(takeARoot);
		this.cutting.setTakeARoot(takeARoot);
	}

}
