package com.luzi82.yukilayout.layout;

import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.YlGraphics;

public class YlTextElement extends YlElement {

	/**
	 * 
	 */
	// public YlStoreRule x = new YlStoreRule(this, "0");
	// public YlStoreRule y = new YlStoreRule(this, "0");
	// public YlStoreRule align = new YlStoreRule(this, "0");
	// public YlStoreRule fontSize = new YlStoreRule(this, "0");
	// public YlStoreRule color = new YlStoreRule(this);
	// public YlStoreRule text = new YlStoreRule(this);

	public YlTextElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, false);
		setAttrDefault("x", 0);
		setAttrDefault("y", 0);
		setAttrDefault("align", 7);
	}

	@Override
	public void paint(YlGraphics graphics) {
		String text = string(attr("text"));
		float x = toFloat(attr("x"));
		float y = toFloat(attr("y"));
		int align = toInt(attr("align"));
		float fontSize = toFloat(attr("fontSize"));
		YlColor color = toColor(attr("color"));
		graphics.text(text, x, y, align, fontSize, color);
	}

}