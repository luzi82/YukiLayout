package com.luzi82.yukilayout.layout;

import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlRect;

public class YlScrollElement extends YlElement {

	public YlScrollElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, true);
		setAttrDefault("x0", 0);
		setAttrDefault("x1", 0);
		setAttrDefault("y0", 0);
		setAttrDefault("y1", 0);
		setAttrDefault("contentWidth", 0);
		setAttrDefault("contentHeight", 0);
		setAttrDefault("offsetX", 0);
		setAttrDefault("offsetY", 0);
		setAttrDefault("scrollX", false);
		setAttrDefault("scrollY", true);
		setAttrDefault("cursorEvent", true);
	}

	@Override
	public void paint(YlGraphics graphics) {
		float x = toFloat(attr("offsetX"));
		float y = toFloat(attr("offsetY"));
		graphics.push();
		graphics.translate(x, y);
		super.paint(graphics);
		graphics.pop();
	}

	public YlRect contentRect() {
		return new YlRect(0, 0, toFloat(attr("contentWidth")),
				toFloat(attr("contentHeight")));
	}

	public YlRect rect() {
		return new YlRect(toFloat(attr("x0")), toFloat(attr("y0")),
				toFloat(attr("x1")), toFloat(attr("y1")));
	}
}
