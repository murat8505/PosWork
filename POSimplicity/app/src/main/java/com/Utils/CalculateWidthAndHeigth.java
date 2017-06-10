package com.Utils;

public class CalculateWidthAndHeigth {

	public static int calculatingWidthAndHeight(int screenValue, double newValue) {
		return (int) Math.floor((double) (screenValue * newValue / 100.0));
	}
}
