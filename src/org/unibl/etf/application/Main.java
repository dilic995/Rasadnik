package org.unibl.etf.application;

import java.io.FileInputStream;
import java.io.IOException;

import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.ErrorLogger;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			BaseController.changeScene("/org/unibl/etf/gui/login/view/LoginView.fxml", primaryStage);
			primaryStage.getIcons().add(new Image(new FileInputStream("./resources/icon.png")));
			primaryStage.setTitle("Prijava");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorLogger().log(e);
		}
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
