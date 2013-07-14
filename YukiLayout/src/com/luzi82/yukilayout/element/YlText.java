package com.luzi82.yukilayout.element;

import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlSchema;

public class YlText extends YlEle {

	/**
	 * 
	 */
	public YlStoreRule x = new YlStoreRule(this, "0");
	public YlStoreRule y = new YlStoreRule(this, "0");
	public YlStoreRule align = new YlStoreRule(this, "0");
	public YlStoreRule fontSize = new YlStoreRule(this, "0");
	public YlStoreRule color = new YlStoreRule(this);
	public YlStoreRule text = new YlStoreRule(this);

	public YlText(YlSchema aLayout) {
		super(aLayout);
	}

	@Override
	public void paint(YlGraphics graphics) {
		graphics.text(text.string(), x.floatt(), y.floatt(), align.intt(),
				fontSize.floatt(), color.color());
	}

}