package org.unibl.etf.gui.util;

import javafx.beans.binding.BooleanBinding;

public abstract class Binder {
	public BooleanBinding bindAll(BooleanBinding...bindings) {
		BooleanBinding result = bindings[0];
		for(int i=1 ; i<bindings.length ; i++) {
			result = bind(result, bindings[i]);
		}
		return result;
	}
	public abstract BooleanBinding bind(BooleanBinding b1, BooleanBinding b2);
}
