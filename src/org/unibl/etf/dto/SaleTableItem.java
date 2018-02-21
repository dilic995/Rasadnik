package org.unibl.etf.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SaleTableItem {
	
	public SaleTableItem() {
		// TODO Auto-generated constructor stub
	}

	public SaleTableItem(Sale sale) {
		this.sale = sale;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		this.date = new SimpleStringProperty(formatter.format(sale.getDate()));
		this.price = new SimpleStringProperty(sale.getPrice().toPlainString());
		this.name = new SimpleStringProperty(sale.getCustomer().getFirstName() + " " + sale.getCustomer().getLastName());
		this.address = new SimpleStringProperty(sale.getCustomer().getAddress());
		this.paidOff = new SimpleStringProperty(sale.getPaidOff() ? "Da" : "Ne");
	}
	
	
	
	private Sale sale;
	private StringProperty date;
	private StringProperty price;
	private StringProperty name;
	private StringProperty address;
	private StringProperty paidOff;
	
	public final StringProperty dateProperty() {
		return this.date;
	}
	public final Date getDate() {
		return sale.getDate();
	}
	
	public final void setDate(final Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		if(this.date == null) {
			this.date = new SimpleStringProperty();
		}
		this.date.set(formatter.format(date));
		sale.setDate(date);
	}
	
	public final StringProperty priceProperty() {
		return this.price;
	}
	
	public final BigDecimal getPrice() {
		return sale.getPrice();
	}
	
	public final void setPrice(final BigDecimal price) {
		if(this.price == null) {
			this.price = new SimpleStringProperty();
		}
		this.price.set(price.toPlainString());
		sale.setPrice(price);
	}
	
	public final StringProperty nameProperty() {
		return this.name;
	}
	
	public final String getName() {
		return this.nameProperty().get();
	}
	
	public final void setName(final String name) {
		this.nameProperty().set(name);
	}
	
	public final StringProperty addressProperty() {
		return this.address;
	}
	
	public final String getAddress() {
		return this.addressProperty().get();
	}
	
	public final void setAddress(final String address) {
		this.addressProperty().set(address);
	}
	
	public final StringProperty paidOffProperty() {
		return this.paidOff;
	}
	
	public final Boolean getPaidOff() {
		return sale.getPaidOff();
	}
	
	public final void setPaidOff(final Boolean paidOff) {
		if(this.paidOff == null) {
			this.paidOff = new SimpleStringProperty();
		}
		this.paidOff.set(paidOff ? "Da" : "Ne");
		sale.setPaidOff(paidOff);
	}
	public Sale getSale() {
		return this.sale;
	}
}
