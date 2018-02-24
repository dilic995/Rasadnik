package org.unibl.etf.util;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleManager {

	private static final String MESSAGES = "org/unibl/etf/util/messages";
	private static ResourceBundle bundle = PropertyResourceBundle.getBundle(MESSAGES);

	public ResourceBundleManager() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static String getString(String key) {
		String res = bundle.getString(key);
		if(res == null) {
			return ""; 
		}
		return res;
	}
}
