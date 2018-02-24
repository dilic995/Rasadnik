package org.unibl.etf.gui.login.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.dao.implementation.JDBCDAOFactory;
import org.unibl.etf.dto.Account;
import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.BCryptHash;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {

	private enum Login{
		OK,FIRSTLOGIN,NOK;
	}
	@FXML
	private Button btnPrijaviSe;
	@FXML
	private TextField txtKorisnickoIme;
	@FXML
	private PasswordField txtLozinka;
	@FXML
	private Label lblStatus;
	private StringProperty status=new SimpleStringProperty();
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnPrijaviSe.disableProperty().bind(txtKorisnickoIme.textProperty().isEmpty());
		lblStatus.textProperty().bind(status);
	}
	@FXML
	private void login() {
		Login result=check(txtKorisnickoIme.getText(),txtLozinka.getText());
		if(result==Login.NOK) {
			status.set("Pogresno korisnicko ime ili lozinka");
		}else if(result==Login.FIRSTLOGIN) {
			//prikazati formu za prvu prijavu
		}else {
			//prikazati pocetnu formu
			new Alert(AlertType.CONFIRMATION,"Prijava uspjesna",ButtonType.OK);
		}
	}
	private void clear() {
		txtKorisnickoIme.clear();
		txtLozinka.clear();
		status.set(null);
	}
	private Login check(String username,String password) {
		Account account=JDBCDAOFactory.getInstance().getAccountDAO().getByUsername(username);
		if(password==null && account.getFirstLogin()) {
			return Login.FIRSTLOGIN;
		}
		
		return Login.NOK;
	}

}
