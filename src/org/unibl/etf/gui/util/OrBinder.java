package org.unibl.etf.gui.util;

import javafx.beans.binding.BooleanBinding;

public class OrBinder extends Binder{
	@Override
	public BooleanBinding bind(BooleanBinding b1, BooleanBinding b2) {
		return b1.or(b2);
	}
}
