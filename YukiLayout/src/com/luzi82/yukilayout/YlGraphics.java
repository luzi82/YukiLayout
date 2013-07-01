package com.luzi82.yukilayout;

public interface YlGraphics {

	public void clear(YlColor c);

	public void push();

	public void pop();

	public void translate(float xf, float yf);

}
