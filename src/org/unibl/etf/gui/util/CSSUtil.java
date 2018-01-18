package org.unibl.etf.gui.util;

import javafx.scene.Node;

public class CSSUtil {
	public static void setNewStyleClass(Node node, String styleClass) {
		node.getStyleClass().clear();
		node.getStyleClass().add(styleClass);
	}
}
