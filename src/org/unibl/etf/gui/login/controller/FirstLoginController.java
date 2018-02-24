package org.unibl.etf.gui.login.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dao.implementation.JDBCDAOFactory;
import org.unibl.etf.dto.Account;
import org.unibl.etf.gui.util.DisplayUtil;
import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.BCryptHash;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;

public class FirstLoginController extends BaseController {
	@FXML
	private PasswordField txtNovaLozinka;
	@FXML
	private PasswordField txtProvjeraLozinke;
	@FXML
	private Button btnSacuvajLozinku;
	@FXML
	private Label lblStatus;
	private StringProperty status = new SimpleStringProperty();
	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnSacuvajLozinku.disableProperty()
				.bind(txtProvjeraLozinke.textProperty().isEmpty().or(txtNovaLozinka.textProperty().isEmpty()));
		lblStatus.textProperty().bind(status);
	}

	@FXML
	private void save(ActionEvent e) {
		String newPassword = txtNovaLozinka.getText();
		String checkPassword = txtProvjeraLozinke.getText();
		Boolean result = check(newPassword, checkPassword, status);
		if (result) {
			DisplayUtil.close(btnSacuvajLozinku);
		}
	}

	private Boolean check(String password, String repassword, StringProperty property) {
		if (!password.equals(repassword)) {
			status.set("Lozinke se ne podudaraju");
			return false;
		}
		account.setFirstLogin(false);
		account.setHash(BCryptHash.getInstance().hash(password));
		Integer update = JDBCDAOFactory.getInstance().getAccountDAO().update(account);
		if (update > 0) {
			clear();
			return true;
		}
		status.set("Loznika nije promjenjena pokusajte opet");
		return false;
	}

	private void clear() {
		txtNovaLozinka.clear();
		txtProvjeraLozinke.clear();
		status.set(null);
	}
}
