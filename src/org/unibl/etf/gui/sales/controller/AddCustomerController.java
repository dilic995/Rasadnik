package org.unibl.etf.gui.sales.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Customer;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.ResourceBundleManager;

import javafx.event.ActionEvent;

import javafx.scene.control.CheckBox;

public class AddCustomerController extends BaseController {
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtAddress;
	@FXML
	private CheckBox cbSupplier;
	@FXML
	private Button btnSave;

	// Event Listener on Button[#btnSave].onAction
	@FXML
	public void save(ActionEvent event) {
		this.customer.setFirstName(txtFirstName.getText());
		this.customer.setLastName(txtLastName.getText());
		this.customer.setAddress(txtAddress.getText());
		this.customer.setIsSupplier(cbSupplier.isSelected());
		this.customer.setDeleted(false);
		if(DAOFactory.getInstance().getCustomerDAO().insert(this.customer) > 0) {
			DisplayUtil.showMessageDialog(ResourceBundleManager.getString("insertOk"));
		} else {
			DisplayUtil.showMessageDialog(ResourceBundleManager.getString("insertNotOk"));
		}
		DisplayUtil.close(btnSave);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnSave.disableProperty().bind(txtFirstName.textProperty().isEmpty()
				.or(txtLastName.textProperty().isEmpty().or(txtAddress.textProperty().isEmpty())));
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private Customer customer;
}
