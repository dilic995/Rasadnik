package org.unibl.etf.dto;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PurchaseTableItem {
	private Purchase purchase;
	private StringProperty date;
	private StringProperty price;
	private StringProperty name;
	private StringProperty address;
	private StringProperty paidOff;
	public PurchaseTableItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PurchaseTableItem(Purchase purchase) {
		super();
		this.purchase = purchase;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
		this.date = new SimpleStringProperty(formatter.format(purchase.getDate()));
		this.price = new SimpleStringProperty(purchase.getPrice().toPlainString());
		this.name = new SimpleStringProperty(purchase.getCustomer().getFirstName() + " " + purchase.getCustomer().getLastName());
		this.address = new SimpleStringProperty(purchase.getCustomer().getAddress());
		this.paidOff = new SimpleStringProperty(purchase.getPaidOff() ? "Da" : "Ne");
	}
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	public StringProperty getDate() {
		return date;
	}
	public void setDate(StringProperty date) {
		this.date = date;
	}
	public StringProperty getPrice() {
		return price;
	}
	public void setPrice(StringProperty price) {
		this.price = price;
	}
	public StringProperty getName() {
		return name;
	}
	public void setName(StringProperty name) {
		this.name = name;
	}
	public StringProperty getAddress() {
		return address;
	}
	public final Boolean getPaidOff() {
		return purchase.getPaidOff();
	}
	public void setAddress(StringProperty address) {
		this.address = address;
	}
	public void setPaidOff(StringProperty paidOff) {
		this.paidOff = paidOff;
	}
	public StringProperty dateProperty() {
		return date;
	}
	public StringProperty priceProperty() {
		return price;
	}
	public StringProperty nameProperty() {
		return name;
	}
	public StringProperty addressProperty() {
		return address;
	}
	public StringProperty paidOffProperty() {
		return paidOff;
	}
	public final void setPaidOff(final Boolean paidOff) {
		if(this.paidOff == null) {
			this.paidOff = new SimpleStringProperty();
		}
		this.paidOff.set(paidOff ? "Da" : "Ne");
		purchase.setPaidOff(paidOff);
	}
}
