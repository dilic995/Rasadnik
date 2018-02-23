package org.unibl.etf.gui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.unibl.etf.util.ConnectionPool;
import org.unibl.etf.util.ResourceBundleManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DisplayUtil {
	private static final String messages = "org/unibl/etf/util/messages";
	public static Map<String, String> IMAGE_EXTENSIONS = new HashMap<String, String>();
	static {
		IMAGE_EXTENSIONS.put("BMP", "*.bmp");
		IMAGE_EXTENSIONS.put("GIF", "*.gif");
		IMAGE_EXTENSIONS.put("JPEG", "*.jpeg");
		IMAGE_EXTENSIONS.put("JPG", "*.jpg");
		IMAGE_EXTENSIONS.put("PNG", "*.png");
	}

	public static FileChooser configureFileChooser(String title, Map<String, String> extensions) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(System.getProperty(ResourceBundleManager.getString("home",messages ))));
		for (String key : extensions.keySet()) {
			String value = extensions.get(key);
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(key, value));
		}
		return fileChooser;
	}

	public static Image convertFromBlob(Blob blob) {
		Image result = null;
		try {
			if (blob != null) {
				result = new Image(blob.getBinaryStream());
			} else {
				result = getDefaultImage();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Blob convertToBlob(File file) {
		Blob result = null;
		try {
			if (file != null) {
				result = ConnectionPool.getInstance().checkOut().createBlob();
				result.setBytes(1, Files.readAllBytes(file.toPath()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static FXMLLoader getLoader(ClassLoader classLoader, String path) {
		return new FXMLLoader(classLoader.getResource(path));
	}

	public static AnchorPane getAnchorPane(FXMLLoader loader) {
		AnchorPane result = null;
		try {
			result = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static <T> T getController(FXMLLoader loader) {
		T result = loader.<T>getController();
		return result;
	}

	public static void switchStage(Pane root, double width, double height, boolean resizable, String title,
			boolean modal) {
		Stage stage = null;
		stage = new Stage();
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.setTitle(title);
		stage.setResizable(resizable);
		if (modal) {
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} else {
			stage.show();
		}
	}

	public static void close(Node node) {
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	public static ButtonType showWarningDialog(String warning) {
		Alert alert = new Alert(AlertType.WARNING, warning, ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		return alert.getResult();
	}

	public static ButtonType showConfirmationDialog(String question) {
		Alert alert = new Alert(AlertType.CONFIRMATION, question, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();
		return alert.getResult();
	}

	public static ButtonType showErrorDialog(String error) {
		Alert alert = new Alert(AlertType.ERROR, error, ButtonType.OK, ButtonType.CANCEL);
		alert.showAndWait();
		return alert.getResult();
	}

	public static void showMessageDialog(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("");
		alert.setContentText(message);
		alert.setTitle(ResourceBundleManager.getString("information",messages));
		ButtonType okButton = new ButtonType(ResourceBundleManager.getString("ok",messages), ButtonData.OK_DONE);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().add(okButton);
		alert.showAndWait();
	}

	public static Image getDefaultImage() throws FileNotFoundException {
		if (defaultImage == null) {
		//	defaultImage = new Image(new FileInputStream("resources/images/add_image.png"));
			defaultImage = new Image(new FileInputStream(ResourceBundleManager.getString("defaultMessage",messages )));
		}
		return defaultImage;
	}

	private static Image defaultImage = null;

}
