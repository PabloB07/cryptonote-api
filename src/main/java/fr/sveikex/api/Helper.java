package fr.sveikex.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helper {

	public static BigDecimal decimal(long value, int places) 
	{
	    BigDecimal scale = new BigDecimal(Math.pow(10, places));
	    return (new BigDecimal(value).divide(scale)).setScale(places, RoundingMode.HALF_DOWN);
	}
	
}
