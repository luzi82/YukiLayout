package com.luzi82.yukilayout.layout;

import com.luzi82.yukilayout.YlRect;

public class YlVoidElement extends YlElement {

	public YlVoidElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, false);
	}

	public YlRect contentRect() {
		return new YlRect(0, 0, 0, 0);
	}

	public YlRect rect() {
		return new YlRect(toFloat(attr("x")), toFloat(attr("y")),
				toFloat(attr("x")), toFloat(attr("y")));
	}
}