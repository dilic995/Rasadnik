package org.unibl.etf.gui.sales.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Customer;
import org.unibl.etf.dto.Sale;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.ErrorLogger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class ChooseCustomerController extends BaseController {
	@FXML
	private ListView<Customer> lstCustomers;
	@FXML
	private Button btnAddCustomer;
	@FXML
	private Button btnChoose;

	// Event Listener on Button[#btnAddCustomer].onAction
	@FXML
	public void addCustomer(ActionEvent event) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/sales/view/AddCustomerView.fxml");
		AnchorPane root;
		try {
			root = (AnchorPane) loader.load();
			AddCustomerController controller = DisplayUtil.<AddCustomerController>getController(loader);
			customer = new Customer();
			controller.setCustomer(customer);
			DisplayUtil.switchStage(root, 400, 245, true, "Dodavanje kupca", true);
			lstCustomers.getItems().add(customer);
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	// Event Listener on Button[#btnChoose].onAction
	@FXML
	public void choose(ActionEvent event) {
		Customer c = lstCustomers.getSelectionModel().getSelectedItem();
		this.sale.setCustomer(c);
		this.sale.setCustomerId(c.getCustomerId());
		DisplayUtil.close(btnChoose);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Customer> customers = FXCollections.observableArrayList();
		customers.addAll(DAOFactory.getInstance().getCustomerDAO().selectAll());
		lstCustomers.setItems(customers);
		btnChoose.disableProperty().bind(lstCustomers.getSelectionModel().selectedItemProperty().isNull());
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	private Customer customer;
	private Sale sale;
}
