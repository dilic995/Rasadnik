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
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {
	@FXML
	private Button btnPrijaviSe;
	@FXML
	private TextField txtKorisnickoIme;
	@FXML
	private PasswordField txtLozinka;
	@FXML
	private Label lblStatus;
	private StringProperty status = new SimpleStringProperty();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnPrijaviSe.disableProperty().bind(txtKorisnickoIme.textProperty().isEmpty());
		lblStatus.textProperty().bind(status);
	}

	@FXML
	private void login(ActionEvent e) {
		String password = txtLozinka.getText().isEmpty() ? null : txtLozinka.getText();
		check(txtKorisnickoIme.getText(), password, status);
	}

	private void clear() {
		txtKorisnickoIme.clear();
		txtLozinka.clear();
		status.set(null);
	}

	private void check(String username, String password, StringProperty message) {
		Account account = JDBCDAOFactory.getInstance().getAccountDAO().getByUsername(username);
		if (account != null) {
			BCryptHash crypt = BCryptHash.getInstance();
			if (password == null && account.getFirstLogin() && account.getHash()==null) {
				firstLogin(account);
				clear();
				return;
			} else if (password != null)
				if (account.getUsername().equals(username) && crypt.check(password, account.getHash())) {
					clear();
					if (account.getIsAdmin()) {
						adminForm();
						return;
					}
					mainForm();
					return;
				}
		}
		message.set("Pogrešno korisničko ime ili lozinka");
	}

	public void firstLogin(Account account) {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/login/view/FirstLoginView.fxml");
		AnchorPane root = DisplayUtil.getAnchorPane(loader);
		FirstLoginController controller = DisplayUtil.<FirstLoginController>getController(loader);
		controller.setAccount(account);
		primaryStage.hide();
		DisplayUtil.switchStage(root, 400, 225, false, "Promjenite lozinku", true);
		primaryStage.show();
	}
	public void mainForm() {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/application/EntryView.fxml");
		AnchorPane root = DisplayUtil.getAnchorPane(loader);
		primaryStage.hide();
		DisplayUtil.switchStage(root, 650, 600, false, "Glavna forma", true);
		primaryStage.show();
	}
	public void adminForm() {
		FXMLLoader loader = DisplayUtil.getLoader(getClass().getClassLoader(),
				"org/unibl/etf/gui/login/view/AdminView.fxml");
		AnchorPane root = DisplayUtil.getAnchorPane(loader);
		primaryStage.hide();
		DisplayUtil.switchStage(root, 346, 401, false, "Glavna forma", true);
		primaryStage.show();
	}
	



}
