package org.unibl.etf.dto;

import java.util.Comparator;

public class PriceHeightRatioComparatorFrom implements Comparator<PriceHeightRatio>{

	@Override
	public int compare(PriceHeightRatio o1, PriceHeightRatio o2) {
		return o1.getHeightMin().compareTo(o2.getHeightMin());
	}

}
