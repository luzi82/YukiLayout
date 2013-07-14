package com.luzi82.yukilayout;

public interface YlGraphics {

	public void clear(YlColor c);

	public void push();

	public void pop();

	public void translate(float xf, float yf);

	public void text(String text, float xf, float yf, int align,
			float fontSize, YlColor color);

	public void img(String src, float x0f, float y0f, float x1f, float y1f,
			float u0, float v0, float u1, float v1);

}
