package org.unibl.etf.gui.task.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.EmployeeHasTask;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EngagementTableItem {
	private EmployeeHasTask task;
	private StringProperty date;
	private IntegerProperty id;
	private StringProperty firstName;
	private StringProperty lastName;
	private IntegerProperty hours;
	private DoubleProperty hourlyWage;
	private DoubleProperty payment;
	private BooleanProperty paidOff;
	
	

	public EngagementTableItem(EmployeeHasTask task) {
		super();
		this.task = task;
		
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
		id = new SimpleIntegerProperty(task.getEmployee().getEmployeeId());
		date = new SimpleStringProperty(df.format(task.getDate()));
		firstName = new SimpleStringProperty(task.getEmployee().getFirstName());
		lastName = new SimpleStringProperty(task.getEmployee().getLastName());
		hours = new SimpleIntegerProperty(task.getHours());
		hourlyWage = new SimpleDoubleProperty(task.getHourlyWage().doubleValue());
		payment = new SimpleDoubleProperty();
		paidOff = new SimpleBooleanProperty(task.getPaidOff());
		
		payment.bind(hours.multiply(hourlyWage));
	}

	public EngagementTableItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public final StringProperty dateProperty() {
		return this.date;
	}

	public EmployeeHasTask getTask() {
		return task;
	}

	public void setTask(EmployeeHasTask task) {
		this.task = task;
	}

	public final String getDate() {
		return this.dateProperty().get();
	}

	public final void setDate(final String date) {
		this.dateProperty().set(date);
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}

	public final int getId() {
		return this.idProperty().get();
	}

	public final void setId(final int id) {
		this.idProperty().set(id);
	}

	public final StringProperty firstNameProperty() {
		return this.firstName;
	}

	public final String getFirstName() {
		return this.firstNameProperty().get();
	}

	public final void setFirstName(final String firstName) {
		this.firstNameProperty().set(firstName);
	}

	public final StringProperty lastNameProperty() {
		return this.lastName;
	}

	public final String getLastName() {
		return this.lastNameProperty().get();
	}

	public final void setLastName(final String lastName) {
		this.lastNameProperty().set(lastName);
	}

	public final IntegerProperty hoursProperty() {
		return this.hours;
	}

	public final int getHours() {
		return this.hoursProperty().get();
	}

	public final void setHours(final int hours) {
		this.task.setHours(hours);
		this.hoursProperty().set(hours);
	}

	public final DoubleProperty hourlyWageProperty() {
		return this.hourlyWage;
	}

	public final double getHourlyWage() {
		return this.hourlyWageProperty().get();
	}

	public final void setHourlyWage(final double hourlyWage) {
		this.task.setHourlyWage(BigDecimal.valueOf(hourlyWage));
		this.hourlyWageProperty().set(hourlyWage);
	}

	public final DoubleProperty paymentProperty() {
		return this.payment;
	}

	public final double getPayment() {
		return this.paymentProperty().get();
	}

	public final void setPayment(final double payment) {
		this.paymentProperty().set(payment);
	}

	public final BooleanProperty paidOffProperty() {
		return this.paidOff;
	}

	public final boolean isPaidOff() {
		return this.paidOffProperty().get();
	}

	public final void setPaidOff(final boolean paidOff) {
		this.task.setPaidOff(paidOff);
		this.paidOffProperty().set(paidOff);
	}
	
	public void update() throws DAOException {
		DAOFactory.getInstance().getEmployeeHasTaskDAO().update(task);
	}
	
	public static List<EngagementTableItem> convert(List<EmployeeHasTask> list) {
		List<EngagementTableItem> retVal = new ArrayList<>();
		for(EmployeeHasTask task: list) {
			retVal.add(new EngagementTableItem(task));
		}
		return retVal;
	}

}
