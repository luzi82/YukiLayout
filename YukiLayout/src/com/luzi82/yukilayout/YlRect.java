package com.luzi82.yukilayout;

public class YlRect {

	public YlRect() {
		this.x0 = Float.POSITIVE_INFINITY;
		this.y0 = Float.POSITIVE_INFINITY;
		this.x1 = Float.NEGATIVE_INFINITY;
		this.y1 = Float.NEGATIVE_INFINITY;
	}

	public YlRect(float x0, float y0, float x1, float y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}

	public float x0;
	public float y0;
	public float x1;
	public float y1;

	public YlRect union(YlRect other) {
		return new YlRect(Math.min(x0, other.x0), Math.min(y0, other.y0),
				Math.max(x1, other.x1), Math.max(y1, other.y1));
	}

	public boolean contains(float x, float y) {
		if (x < x0)
			return false;
		if (x > x1)
			return false;
		if (y < y0)
			return false;
		if (y > y1)
			return false;
		return true;
	}

}
