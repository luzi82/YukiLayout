package com.luzi82.yukilayout;

public class YlRect {

	public YlRect() {
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

}
