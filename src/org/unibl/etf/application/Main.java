package org.unibl.etf.application;

import java.io.IOException;

import org.unibl.etf.gui.view.base.BaseController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage)  {
		try {
			BaseController.changeScene("/org/unibl/etf/tool/view/ToolView.fxml", primaryStage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getCause());
		}
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
