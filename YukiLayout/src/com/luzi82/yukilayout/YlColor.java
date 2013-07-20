package com.luzi82.yukilayout;

public class YlColor {

	public int argb;

	public int alphaI;
	public int redI;
	public int greenI;
	public int blueI;

	public float alphaF;
	public float redF;
	public float greenF;
	public float blueF;

	public YlColor(Object value) {
		argb = (int) (Long.decode(value.toString()) & 0xffffffff);

		alphaI = (argb >> 24) & 0xff;
		redI = (argb >> 16) & 0xff;
		greenI = (argb >> 8) & 0xff;
		blueI = argb & 0xff;

		alphaF = alphaI / (float) 0xff;
		redF = redI / (float) 0xff;
		greenF = greenI / (float) 0xff;
		blueF = blueI / (float) 0xff;
	}

	public static YlColor create(Object value) {
		if (value == null)
			return null;
		return new YlColor(value);
	}

}
