package com.luzi82.yukilayout.layout;

import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.YlGraphics;

public class YlScreenElement extends YlElement {

	public YlScreenElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, true);
	}

	@Override
	public void paint(YlGraphics graphics) {
		Object backgroundColorObj = attr("backgroundColor");
		YlColor backgroundColor = YlColor.create(backgroundColorObj);
		if (backgroundColor != null) {
			graphics.clear(backgroundColor);
		}
		super.paint(graphics);
	}
}