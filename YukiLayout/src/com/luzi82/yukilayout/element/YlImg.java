package com.luzi82.yukilayout.element;

import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlLayout;

public class YlImg extends YlEle {

	/**
	 * 
	 */
	public YlStoreRule src = new YlStoreRule(this, "");
	public YlStoreRule x0 = new YlStoreRule(this, "0");
	public YlStoreRule y0 = new YlStoreRule(this, "0");
	public YlStoreRule x1 = new YlStoreRule(this, "0");
	public YlStoreRule y1 = new YlStoreRule(this, "0");
	public YlStoreRule u0 = new YlStoreRule(this, "0");
	public YlStoreRule v0 = new YlStoreRule(this, "0");
	public YlStoreRule u1 = new YlStoreRule(this, "0");
	public YlStoreRule v1 = new YlStoreRule(this, "0");

	public YlImg(YlLayout aLayout) {
		super(aLayout);
	}

	@Override
	public void paint(YlGraphics graphics) {
		graphics.img(src.string(), x0.floatt(), y0.floatt(), x1.floatt(),
				y1.floatt(), u0.floatt(), v0.floatt(), u1.floatt(),
				v1.floatt());
	}

}