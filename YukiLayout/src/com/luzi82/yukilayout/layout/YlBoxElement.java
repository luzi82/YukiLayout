package com.luzi82.yukilayout.layout;

import com.luzi82.yukilayout.YlGraphics;

public class YlBoxElement extends YlElement {

	public YlBoxElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, true);
		setAttrDefault("x0", 0);
		setAttrDefault("x1", 0);
		setAttrDefault("y0", 0);
		setAttrDefault("y1", 0);
	}

	public float width() {
		float x0 = toFloat(attr("x0"));
		float x1 = toFloat(attr("x1"));
		return x1 - x0;
	}

	public float height() {
		float y0 = toFloat(attr("y0"));
		float y1 = toFloat(attr("y1"));
		return y1 - y0;
	}

	@Override
	public void paint(YlGraphics graphics) {
		float x = toFloat(attr("x0"));
		float y = toFloat(attr("y0"));
		graphics.push();
		graphics.translate(x, y);
		super.paint(graphics);
		graphics.pop();
	}
}
