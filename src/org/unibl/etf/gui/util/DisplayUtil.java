package org.unibl.etf.gui.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

public class DisplayUtil {
	public static Map<String, String> IMAGE_EXTENSIONS = new HashMap<String, String>();
	static {
		IMAGE_EXTENSIONS.put("All Images", "*.*");
		IMAGE_EXTENSIONS.put("JPG", "*.jpg");
		IMAGE_EXTENSIONS.put("JPEG", "*.jpeg");
		IMAGE_EXTENSIONS.put("PNG", "*.png");
	}
	public static FileChooser configureFileChooser(String title, Map<String, String> extensions) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		for(String key : extensions.keySet()) {
			String value = extensions.get(key);
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(key, value));
		}
		return fileChooser;
	}
}
