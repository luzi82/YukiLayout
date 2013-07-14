package com.luzi82.yukilayout.schema;

import com.luzi82.yukilayout.YlGraphics;

public class YlDragSE extends YlSchemaElement {

	/**
	 * 
	 */
	public YlStoreRule x = new YlStoreRule(this, "0");
	public YlStoreRule y = new YlStoreRule(this, "0");

	public YlDragSE(YlSchema aLayout) {
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