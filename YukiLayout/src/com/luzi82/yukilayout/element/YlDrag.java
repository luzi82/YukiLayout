package com.luzi82.yukilayout.element;

import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlLayout;

public class YlDrag extends YlEle {

	/**
	 * 
	 */
	public YlStoreRule x = new YlStoreRule(pLayout, this, "0");
	public YlStoreRule y = new YlStoreRule(pLayout, this, "0");

	public YlDrag(YlLayout aLayout) {
		super(aLayout);
	}

	@Override
	public void paint(YlGraphics graphics) {
		Float xf = x.floatt();
		Float yf = y.floatt();
		graphics.push();
		graphics.translate(xf, yf);
		super.paint(graphics);
		graphics.pop();
	}

}