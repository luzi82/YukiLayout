package com.luzi82.yukilayout.element;

import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlLayout;

public class YlText extends YlEle {

	/**
	 * 
	 */
	public YlStoreRule x = new YlStoreRule(pLayout, this, "0");
	public YlStoreRule y = new YlStoreRule(pLayout, this, "0");
	public YlStoreRule align = new YlStoreRule(pLayout, this, "0");
	public YlStoreRule fontSize = new YlStoreRule(pLayout, this, "0");
	public YlStoreRule color = new YlStoreRule(pLayout, this);
	public YlStoreRule text = new YlStoreRule(pLayout, this);

	public YlText(YlLayout aLayout) {
		super(aLayout);
	}

	@Override
	public void paint(YlGraphics graphics) {
		graphics.text(text.string(), x.floatt(), y.floatt(), align.intt(),
				fontSize.floatt(), color.color());
	}

}