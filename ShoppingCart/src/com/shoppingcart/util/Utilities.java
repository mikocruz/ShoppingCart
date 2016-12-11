package com.shoppingcart.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class Utilities {
	
	public static double roundOff(double price) {
		DecimalFormat priceFormat = new DecimalFormat("#.##");
		priceFormat.setRoundingMode(RoundingMode.CEILING);
		return Double.valueOf(priceFormat.format(price));
	}

}
