package org.unibl.etf.gui.login.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.gui.view.base.BaseController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;

public class FirstLoginController extends BaseController{
	@FXML
	private PasswordField txtNovaLozinka;
	@FXML
	private PasswordField txtProvjeraLozinke;
	@FXML
	private Button btnSacuvajLozinku;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnSacuvajLozinku.disableProperty().bind(txtProvjeraLozinke.textProperty().isEmpty().or(txtNovaLozinka.textProperty().isEmpty()));
	}
	@FXML
	private void check() {
		String newPassword=txtNovaLozinka.getText();
		String checkPassword=txtProvjeraLozinke.getText();
		if(!newPassword.equals(checkPassword)) {
			new Alert(AlertType.ERROR,"Lozinke se ne podudaraju",ButtonType.OK).showAndWait();
		}else {
			clear();
			new Alert(AlertType.CONFIRMATION,"Lozinka uspjesno azurirana",ButtonType.OK).showAndWait();
		}
	}
	private void clear() {
		txtNovaLozinka.clear();
		txtProvjeraLozinke.clear();
	}
}
