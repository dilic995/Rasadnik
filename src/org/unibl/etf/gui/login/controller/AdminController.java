package org.unibl.etf.gui.login.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.dao.interfaces.DAOFactory;
import org.unibl.etf.dto.Account;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class AdminController extends BaseController{
	@FXML
	private TableView<Account> tblAccount;
	@FXML
	private TableColumn<Account, String> tblColUsername;
	@FXML
	private Button btnResetPassword;
	@FXML
	private TextField txtUsername;
	@FXML
	private Button btnAddNewAccount;
	@FXML
	private Button btnRemoveAccount;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bind();
		buildTable();
		load();
	}
	
	public void bind() {
		btnResetPassword.disableProperty().bind(tblAccount.getSelectionModel().selectedItemProperty().isNull());
		btnAddNewAccount.disableProperty().bind(txtUsername.textProperty().isEmpty());
		btnRemoveAccount.disableProperty().bind(tblAccount.getSelectionModel().selectedItemProperty().isNull());
	}
	
	public void addNewAccount() {
		Account newAccount = new Account(txtUsername.getText(), null, true, false, false);
		if(DAOFactory.getInstance().getAccountDAO().insert(newAccount) != 0) {
			tblAccount.getItems().add(newAccount);
			tblAccount.refresh();
			DisplayUtil.showMessageDialog("Uspjesno dodan novi korisnik.");
		}
		
		txtUsername.clear();
	}
	
	public void resetPassword() {
		Account account = tblAccount.getSelectionModel().getSelectedItems().get(0);
		account.setHash(null);
		account.setFirstLogin(true);
		DAOFactory.getInstance().getAccountDAO().update(account);
		DisplayUtil.showMessageDialog("Uspjesno resetovana lozinka.");
	}
	
	public void deleteAccount() {
		Account account = tblAccount.getSelectionModel().getSelectedItems().get(0);
		account.setDeleted(true);
		DAOFactory.getInstance().getAccountDAO().update(account);
		tblAccount.getItems().remove(account);
		DisplayUtil.showMessageDialog("Uspjesno obrisan korisnik.");
	}
	
	public void load() {
		Object[] vars = {false};
		populateTable(DAOFactory.getInstance().getAccountDAO().select("WHERE is_admin=?", vars));
	}
	
	private void buildTable() {
		tblColUsername.setCellValueFactory(new PropertyValueFactory<Account, String>("username"));
	}

	private void populateTable(List<Account> accounts) {
		ObservableList<Account> tableItems = FXCollections.observableArrayList();
		for (Account account : accounts) {
			tableItems.add(account);
		}
		tblAccount.setItems(tableItems);
	}

}
