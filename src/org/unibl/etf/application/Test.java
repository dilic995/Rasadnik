package org.unibl.etf.application;

import java.io.FileInputStream;
import java.io.IOException;

import org.unibl.etf.gui.view.base.BaseController;
import org.unibl.etf.util.ErrorLogger;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Test extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			// BaseController.changeScene("/org/unibl/etf/application/EntryView.fxml",
			// primaryStage);
			// BaseController.changeScene("/org/unibl/etf/gui/tool/view/ToolView.fxml",
			// primaryStage);
			BaseController.changeScene("/org/unibl/etf/gui/task/view/EmployeeView.fxml", primaryStage);
			//primaryStage.getIcons().add(new Image(new FileInputStream("./resources/icon.ico")));
			primaryStage.setResizable(true);
			primaryStage.setTitle("Prijava");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
			new ErrorLogger().log(e);
		}
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
