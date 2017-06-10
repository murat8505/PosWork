package com.Utils;

import java.util.Locale;

public class MyStringFormat {

	public static String onFormat(Object value)
	{
		String convertedValue = String.format(Locale.ENGLISH, "%.2f", value);
		return convertedValue;
	}
	
	public static String onTaxRateFormat(Object value)
	{
		String convertedValue = String.format(Locale.ENGLISH, "%.5f", value);
		return convertedValue;
	}

	public static String onStringFormat(String value)
	{
		float conversion      = Float.parseFloat(value);
		String convertedValue = String.format(Locale.ENGLISH, "%.2f", conversion);
		return convertedValue;
	}
	
	public static String onIntFormat(int number){
		String convertedValue = String.format(Locale.ENGLISH, "%02d", number);
		return convertedValue;			
	}

}
