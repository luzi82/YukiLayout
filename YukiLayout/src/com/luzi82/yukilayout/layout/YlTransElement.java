package com.luzi82.yukilayout.layout;

import com.luzi82.yukilayout.YlGraphics;

public class YlTransElement extends YlElement {

	// /**
	// *
	// */
	// public YlStoreRule x = new YlStoreRule(this, "0");
	// public YlStoreRule y = new YlStoreRule(this, "0");

	public YlTransElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, true);
		setAttrDefault("x", 0);
		setAttrDefault("y", 0);
	}

	@Override
	public void paint(YlGraphics graphics) {
		float x = toFloat(attr("x"));
		float y = toFloat(attr("y"));
		graphics.push();
		graphics.translate(x, y);
		super.paint(graphics);
		graphics.pop();
	}
}