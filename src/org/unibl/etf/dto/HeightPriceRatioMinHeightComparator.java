package org.unibl.etf.dto;

import java.util.Comparator;

public class HeightPriceRatioMinHeightComparator implements Comparator<HeightPriceRatio>{

	@Override
	public int compare(HeightPriceRatio o1, HeightPriceRatio o2) {
		return o1.getMinHeight().compareTo(o2.getMinHeight());
	}

}
