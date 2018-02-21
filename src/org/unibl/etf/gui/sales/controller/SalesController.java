package org.unibl.etf.gui.sales.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Sale;
import org.unibl.etf.dto.SaleItem;
import org.unibl.etf.dto.SaleItemTableItem;
import org.unibl.etf.dto.SaleTableItem;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class SalesController extends BaseController {
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
	public void isplati(ActionEvent event) {
		SaleTableItem sale = tblSales.getSelectionModel().getSelectedItem();
		if(sale != null) {
			sale.setPaidOff(!sale.getPaidOff());
			DAOFactory.getInstance().getSaleDAO().update(sale.getSale());
			tblSales.refresh();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildTable();
		populateTable();

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
	}

	private void populateTable() {
		ObservableList<SaleTableItem> items = FXCollections.observableArrayList();
		List<Sale> sales = DAOFactory.getInstance().getSaleDAO().selectAll();
		for (Sale sale : sales) {
			items.add(new SaleTableItem(sale));
		}
		tblSales.setItems(items);
	}
}
