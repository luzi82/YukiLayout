package com.luzi82.yukilayout.layout;

import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlRect;

public class YlImgElement extends YlElement {

	// /**
	// *
	// */
	// public YlStoreRule src = new YlStoreRule(this, "");
	// public YlStoreRule x0 = new YlStoreRule(this, "0");
	// public YlStoreRule y0 = new YlStoreRule(this, "0");
	// public YlStoreRule x1 = new YlStoreRule(this, "0");
	// public YlStoreRule y1 = new YlStoreRule(this, "0");
	// public YlStoreRule u0 = new YlStoreRule(this, "0");
	// public YlStoreRule v0 = new YlStoreRule(this, "0");
	// public YlStoreRule u1 = new YlStoreRule(this, "0");
	// public YlStoreRule v1 = new YlStoreRule(this, "0");

	public YlImgElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, false);
		setAttrDefault("x0", 0f);
		setAttrDefault("y0", 0f);
		setAttrDefault("x1", 0f);
		setAttrDefault("y1", 0f);
		setAttrDefault("u0", 0f);
		setAttrDefault("v0", 0f);
		setAttrDefault("u1", 0f);
		setAttrDefault("v1", 0f);
	}

	@Override
	public void paint(YlGraphics graphics) {
		String src = string(attr("src"));
		float x0 = toFloat(attr("x0"));
		float y0 = toFloat(attr("y0"));
		float x1 = toFloat(attr("x1"));
		float y1 = toFloat(attr("y1"));
		float u0 = toFloat(attr("u0"));
		float v0 = toFloat(attr("v0"));
		float u1 = toFloat(attr("u1"));
		float v1 = toFloat(attr("v1"));
		graphics.img(src, x0, y0, x1, y1, u0, v0, u1, v1);
	}

	public YlRect contentRect() {
		return new YlRect(0, 0, toFloat(attr("x1")) - toFloat(attr("x0")),
				toFloat(attr("y1")) - toFloat(attr("y0")));
	}

	public YlRect rect() {
		return new YlRect(toFloat(attr("x0")), toFloat(attr("y0")),
				toFloat(attr("x1")), toFloat(attr("y1")));
	}
}