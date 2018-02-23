package org.unibl.etf.util;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleManager {

	

	public ResourceBundleManager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static String getString(String key, String path) {
		ResourceBundle bundle = PropertyResourceBundle.getBundle(path);
		return bundle.getString(key);
	}
}
