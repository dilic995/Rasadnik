package org.unibl.etf.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.dao.interfaces.DAOException;
import org.unibl.etf.dao.interfaces.DAOFactory;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ActivityTableItem {
	private EmployeeHasTask task;
	private IntegerProperty region;
	private StringProperty date;
	private StringProperty activity;
	private IntegerProperty hours;
	private DoubleProperty hourlyWage;
	private DoubleProperty payment;
	private BooleanProperty paidOff;
	
	public ActivityTableItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActivityTableItem(EmployeeHasTask task) {
		super();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		this.task = task;
		region = new SimpleIntegerProperty(task.getTask().getRegionId());
		date = new SimpleStringProperty(df.format(task.getDate()));
		activity = new SimpleStringProperty(task.getTask().getPlantMaintanceActivity().getActivity());
		hours = new SimpleIntegerProperty(task.getHours());
		hourlyWage = new SimpleDoubleProperty(task.getHourlyWage().doubleValue());
		payment = new SimpleDoubleProperty();
		paidOff = new SimpleBooleanProperty(task.getPaidOff());
		
		payment.bind(hours.multiply(hourlyWage));
	}
	
	
	//properties
	public IntegerProperty regionProperty() {
		return region;
	}
	public StringProperty dateProperty() {
		return date;
	}
	public StringProperty activityProperty() {
		return activity;
	}
	
	
	//geteri
	public EmployeeHasTask getTask() {
		return task;
	}
	public Integer getRegion() {
		return region.getValue();
	}
	public String getDate() {
		return date.getValue();
	}
	public String getActivity() {
		return activity.getValue();
	}
	public Integer getHours() {
		return hours.getValue();
	}
	public Double getHourlyWage() {
		return hourlyWage.getValue();
	}
	public Double getPayment() {
		return payment.getValue();
	}
	public Boolean getPaidOff() {
		return paidOff.getValue();
	}
	
	//seteri
	public void setTask(EmployeeHasTask task) {
		this.task = task;
	}
	public void setRegion(Integer region) {
		this.region.set(region);
	}
	public void setDate(String date) {
		this.date.set(date);
	}
	public void setActivity(String activity) {
		this.activity.set(activity);
	}
	public void setHours(Integer hours) {
		this.task.setHours(hours);
		this.hours.set(hours);
	}
	public void setHourlyWage(Double hourlyWage) {
		this.task.setHourlyWage(BigDecimal.valueOf(hourlyWage));
		this.hourlyWage.set(hourlyWage);
	}
	public void setPayment(Double payment) {
		this.payment.set(payment);
	}
	public void setPaidOff(Boolean paidOff) {
		this.task.setPaidOff(paidOff);
		this.paidOff.set(paidOff);
	}
	
	public void update() throws DAOException {
		DAOFactory.getInstance().getEmployeeHasTaskDAO().update(task);
	}
	
	public static List<ActivityTableItem> convert(List<EmployeeHasTask> list) {
		List<ActivityTableItem> retVal = new ArrayList<>();
		for(EmployeeHasTask task: list) {
			retVal.add(new ActivityTableItem(task));
		}
		return retVal;
	}
	
}	
