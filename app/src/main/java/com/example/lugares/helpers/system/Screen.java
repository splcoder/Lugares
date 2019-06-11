package com.example.lugares.helpers.system;

import android.content.res.Resources;

public class Screen {
	// 1 dp is equal to 1 pixel in a screen of density 160, so:
	// dp = (píxels * 160) / screen_density
	//public static final float DENSITY = (float)App.getAllResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT;
	//public static final float DENSITY = (float)Resources.getResources().getDisplayMetrics().density;
	public static final float DENSITY = (float)Resources.getSystem().getDisplayMetrics().density;
	/**
	 * This method converts dp unit to equivalent pixels, depending on device density.
	 *
	 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
	 * @return A float value to represent px equivalent to dp depending on device density
	 */
	public static float dpToPixels( float dp ){
		return dp * DENSITY;
	}
	/**
	 * This method converts device specific pixels to density independent pixels.
	 *
	 * @param px A value in px (pixels) unit. Which we need to convert into dp
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float pixelsToDp( float px ){
		return px / DENSITY;
	}
}
