package org.icannt.netherendingores.lib;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MathUtil {
	
	public static int minMax(int inputMin, int inputMax, int value) {
		return min(inputMax, max(inputMin, value));
	}

}
