package org.unibl.etf.gui.view.base;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseController implements Initializable{

	protected Stage primaryStage;

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static BaseController changeScene(String path, Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(BaseController.class.getResource(path));
		Parent pane = (Parent) loader.load();
		BaseController control = loader.<BaseController>getController();
		control.setPrimaryStage(primaryStage);
		control.getPrimaryStage().setScene(new Scene(pane));
		control.primaryStage.setOnCloseRequest(event->{});
		return control;
	}
}
