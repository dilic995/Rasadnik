package org.unibl.etf.gui.sales.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Customer;
import org.unibl.etf.dto.Purchase;
import org.unibl.etf.dto.PurchaseTableItem;
import org.unibl.etf.dto.Sale;
import org.unibl.etf.dto.SaleItem;
import org.unibl.etf.dto.SaleItemTableItem;
import org.unibl.etf.dto.SaleTableItem;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.ResourceBundleManager;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SalesController extends BaseController {
	@FXML
	private Spinner<Integer> spinnerMonthSale;
	@FXML
	private Spinner<Integer> spinnerMonthPurchase;
	@FXML
	private Spinner<Integer> spinnerYearSale;
	@FXML
	private Spinner<Integer> spinnerYearPurchase;
	@FXML
	private Label lblError;
	@FXML
	private DatePicker datePicker;
	@FXML
	private TableView<SaleTableItem> tblSales;
	@FXML
	private TableColumn<SaleTableItem, String> colDate;
	@FXML
	private TableColumn<SaleTableItem, String> colPriceSale;
	@FXML
	private TableColumn<SaleTableItem, String> colName;
	@FXML
	private TableColumn<SaleTableItem, String> colAddress;
	@FXML
	private TableColumn<SaleTableItem, String> colPaidOff;
	@FXML
	private TableView<SaleItemTableItem> tblSaleItems;
	@FXML
	private TableColumn<SaleItemTableItem, String> colPlant;
	@FXML
	private TableColumn<SaleItemTableItem, String> colHeight;
	@FXML
	private TableColumn<SaleItemTableItem, Integer> colCount;
	@FXML
	private TableColumn<SaleItemTableItem, String> colPriceItem;
	@FXML
	private Button btnIsplati;
	@FXML
	private TableView<PurchaseTableItem> tblPurchase;
	@FXML
	private TableColumn<PurchaseTableItem, String> colDatePurchase;
	@FXML
	private TableColumn<PurchaseTableItem, String> colPricePurchase;
	@FXML
	private TableColumn<PurchaseTableItem, String> colNamePurchase;
	@FXML
	private TableColumn<PurchaseTableItem, String> colAddressPurchase;
	@FXML
	private TableColumn<PurchaseTableItem, String> colPaidOffPurchase;
	@FXML
	private Button btnPaid;
	@FXML
	private RadioButton rbExisting;
	@FXML
	private RadioButton rbNew;
	@FXML
	private ComboBox<Customer> cbCustomer;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtAddress;
	@FXML
	private Button btnPreviewDescription;
	@FXML
	private TextField txtDate;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextField txtDescription;
	@FXML
	private Button btnAddPurchase;
	@FXML
	private Button btnSearchSales;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		populateTable();
		populateTablePurchase();
		prepareFields();
	}
	
	@FXML
	public void isplati(ActionEvent event) {
		SaleTableItem sale = tblSales.getSelectionModel().getSelectedItem();
		if (sale != null) {
			if(!sale.getPaidOff()) {
				sale.setPaidOff(true);
				DAOFactory.getInstance().getSaleDAO().update(sale.getSale());
				tblSales.refresh();
			}
		}
	}

	// Event Listener on TableView[#tblSales].onMouseClicked
	@FXML
	public void setItems(MouseEvent event) {
		SaleTableItem sale = tblSales.getSelectionModel().getSelectedItem();
		if (sale != null) {
			ObservableList<SaleItemTableItem> items = FXCollections.observableArrayList();
			List<SaleItem> saleItems = sale.getSale().getSaleItems();
			for (SaleItem item : saleItems) {
				items.add(new SaleItemTableItem(item));
			}
			tblSaleItems.setItems(items);
		}
	}


	// Event Listener on Button[#btnPaid].onAction
	@FXML
	public void payOffPurchase(ActionEvent event) {
		PurchaseTableItem purchase = tblPurchase.getSelectionModel().getSelectedItem();
		if (purchase != null) {
			if(!purchase.getPaidOff()) {
				purchase.setPaidOff(!purchase.getPaidOff());
				DAOFactory.getInstance().getPurchaseDAO().update(purchase.getPurchase());
				tblPurchase.refresh();
			}
		}
	}

	// Event Listener on Button[#btnPreviewDescription].onAction
	@FXML
	public void previewDescription(ActionEvent event) {
		PurchaseTableItem purchase = tblPurchase.getSelectionModel().getSelectedItem();
		if (purchase != null) {
			DisplayUtil.showMessageDialog("Napomena: "+purchase.getPurchase().getDescription());
		}
	}

	// Event Listener on Button[#btnAddPurchase].onAction
	@FXML
	public void addPurchase(ActionEvent event) {
		LocalDate localDate = datePicker.getValue();
		Instant instant = null;
		Date date = null;
		if (localDate != null) {
			instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			date = Date.from(instant);
		}
		Customer customer = null;
		if (rbExisting.isSelected()) {
			customer = cbCustomer.getSelectionModel().getSelectedItem();
		} else if (rbNew.isSelected()) {
			customer = new Customer(null, txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), true,
					false);
			DAOFactory.getInstance().getCustomerDAO().insert(customer);
			cbCustomer.getItems().add(customer);
		}
		BigDecimal price = null;
		try {
			price = new BigDecimal(txtPrice.getText());
			Purchase purchase = new Purchase(date, txtDescription.getText(), price, false, customer,
					customer.getCustomerId());
			DAOFactory.getInstance().getPurchaseDAO().insert(purchase);
			tblPurchase.getItems().add(new PurchaseTableItem(purchase));
			tblPurchase.refresh();
			String message = ResourceBundleManager.getString("insertOk");
			clearTextFields();
			DisplayUtil.showMessageDialog(message);
		} catch (NumberFormatException ex) {
			lblError.setText(ResourceBundleManager.getString("numberFormat"));
		}

	}
	
	public void searchSale() {
		Integer month = spinnerMonthSale.getValue();
		Integer year = spinnerYearSale.getValue();
		List<Sale> sales = DAOFactory.getInstance().getSaleDAO().selectAll();
		ObservableList<SaleTableItem> listSales = FXCollections.observableArrayList();
		for(Sale s : sales) {
			Date date = s.getDate();
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Integer monthSale = localDate.getMonthValue();
			Integer yearSale = localDate.getYear();
			if(monthSale.equals(month) && yearSale.equals(year)) 
				listSales.add(new SaleTableItem(s));
		}
		tblSales.setItems(listSales);
	}
	
	public void searchPurchase() {
		Integer month = spinnerMonthPurchase.getValue();
		Integer year = spinnerYearPurchase.getValue();
		List<Purchase> purchases = DAOFactory.getInstance().getPurchaseDAO().selectAll();
		ObservableList<PurchaseTableItem> listSales = FXCollections.observableArrayList();
		for(Purchase s : purchases) {
			Date date = s.getDate();
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Integer monthSale = localDate.getMonthValue();
			Integer yearSale = localDate.getYear();
			if(monthSale.equals(month) && yearSale.equals(year)) {
				listSales.add(new PurchaseTableItem(s));
			}
		}
		tblPurchase.setItems(listSales);
		tblPurchase.refresh();
	}
	
	private void clearTextFields() {
		txtFirstName.setText("");
		txtLastName.setText("");
		txtAddress.setText("");
		txtDescription.setText("");
		txtPrice.setText("");

	}
	
	private void prepareFields() {
		lblError.setText("");
		BooleanBinding bindExisting = datePicker.valueProperty().isNull().or(txtDescription.textProperty().isEmpty()
				.or(txtPrice.textProperty().isEmpty().or(cbCustomer.valueProperty().isNull())));
		BooleanBinding bindNew = datePicker.valueProperty().isNull()
				.or(txtDescription.textProperty().isEmpty()
						.or(txtPrice.textProperty().isEmpty().or(txtFirstName.textProperty().isEmpty()
								.or(txtLastName.textProperty().isEmpty().or(txtAddress.textProperty().isEmpty())))));
		btnAddPurchase.disableProperty()
				.bind((rbNew.selectedProperty().and(bindNew)).or(rbExisting.selectedProperty().and(bindExisting)));
		ToggleGroup group = new ToggleGroup();
		rbExisting.setToggleGroup(group);
		rbNew.setToggleGroup(group);
		cbCustomer.disableProperty().bind(rbExisting.selectedProperty().not());
		txtFirstName.disableProperty().bind(rbNew.selectedProperty().not());
		txtLastName.disableProperty().bind(rbNew.selectedProperty().not());
		txtAddress.disableProperty().bind(rbNew.selectedProperty().not());
		Object[] pom = { true };
		cbCustomer.setItems(FXCollections
				.observableArrayList(DAOFactory.getInstance().getCustomerDAO().select("is_supplier=?", pom)));
		cbCustomer.getSelectionModel().select(0);
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
		SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 1);
		SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2018, 2014);
		SpinnerValueFactory<Integer> valueFactory4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2010, 2018, 2014);
		spinnerMonthPurchase.setValueFactory(valueFactory);
		spinnerMonthSale.setValueFactory(valueFactory2);
		spinnerYearPurchase.setValueFactory(valueFactory4);
		spinnerYearSale.setValueFactory(valueFactory1);
		
		
	}

	private void buildTable() {
		colDate.setCellValueFactory(new PropertyValueFactory<SaleTableItem, String>("date"));
		colPriceSale.setCellValueFactory(new PropertyValueFactory<SaleTableItem, String>("price"));
		colName.setCellValueFactory(new PropertyValueFactory<SaleTableItem, String>("name"));
		colAddress.setCellValueFactory(new PropertyValueFactory<SaleTableItem, String>("address"));
		colPaidOff.setCellValueFactory(new PropertyValueFactory<SaleTableItem, String>("paidOff"));

		colPlant.setCellValueFactory(new PropertyValueFactory<SaleItemTableItem, String>("plant"));
		colHeight.setCellValueFactory(new PropertyValueFactory<SaleItemTableItem, String>("height"));
		colCount.setCellValueFactory(new PropertyValueFactory<SaleItemTableItem, Integer>("count"));
		colPriceItem.setCellValueFactory(new PropertyValueFactory<SaleItemTableItem, String>("price"));

		colDatePurchase.setCellValueFactory(new PropertyValueFactory<PurchaseTableItem, String>("date"));
		colPricePurchase.setCellValueFactory(new PropertyValueFactory<PurchaseTableItem, String>("price"));
		colNamePurchase.setCellValueFactory(new PropertyValueFactory<PurchaseTableItem, String>("name"));
		colAddressPurchase.setCellValueFactory(new PropertyValueFactory<PurchaseTableItem, String>("address"));
		colPaidOffPurchase.setCellValueFactory(new PropertyValueFactory<PurchaseTableItem, String>("paidOff"));
	}

	private void populateTable() {
		ObservableList<SaleTableItem> items = FXCollections.observableArrayList();
		List<Sale> sales = DAOFactory.getInstance().getSaleDAO().selectAll();
		for (Sale sale : sales) {
			items.add(new SaleTableItem(sale));
		}
		tblSales.setItems(items);
	}

	private void populateTablePurchase() {
		ObservableList<PurchaseTableItem> items = FXCollections.observableArrayList();
		List<Purchase> purchases = DAOFactory.getInstance().getPurchaseDAO().selectAll();
		for (Purchase purchase : purchases) {
			items.add(new PurchaseTableItem(purchase));
		}
		tblPurchase.setItems(items);
	}
}
