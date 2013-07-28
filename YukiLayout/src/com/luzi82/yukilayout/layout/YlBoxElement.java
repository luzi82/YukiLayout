package com.luzi82.yukilayout.layout;

import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlRect;
import com.luzi82.yukilayout.layout.YlLayout.ShootElement;
import com.luzi82.yukilayout.layout.YlLayout.ShootResult;

public class YlBoxElement extends YlElement {

	public YlBoxElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, true);
		setAttrDefault("x0", 0);
		setAttrDefault("x1", 0);
		setAttrDefault("y0", 0);
		setAttrDefault("y1", 0);
	}

	public float width() {
		float x0 = toFloat(attr("x0"));
		float x1 = toFloat(attr("x1"));
		return x1 - x0;
	}

	public float height() {
		float y0 = toFloat(attr("y0"));
		float y1 = toFloat(attr("y1"));
		return y1 - y0;
	}

	@Override
	public void paint(YlGraphics graphics) {
		float x = toFloat(attr("x0"));
		float y = toFloat(attr("y0"));
		graphics.push();
		graphics.translate(x, y);
		super.paint(graphics);
		graphics.pop();
	}

	public YlRect contentRect() {
		return new YlRect(0, 0, toFloat(attr("x1")) - toFloat(attr("x0")),
				toFloat(attr("y1")) - toFloat(attr("y0")));
	}

	public YlRect rect() {
		return new YlRect(toFloat(attr("x0")), toFloat(attr("y0")),
				toFloat(attr("x1")), toFloat(attr("y1")));
	}

	public void shoot(float x, float y, ShootResult result) {
		YlRect rect = rect();
		if (!rect.inside(x, y))
			return;
		float xx = x - toFloat(attr("x0"));
		float yy = y - toFloat(attr("y0"));
		for (YlElement child : childList) {
			child.shoot(xx, yy, result);
		}
		result.elementList.addLast(new ShootElement(xx, yy, this));
	}

	// public void shoot(float x, float y, ShootResult result) {
	// float x0 = toFloat(attr("x0"));
	// float y0 = toFloat(attr("y0"));
	// float x1 = toFloat(attr("x1"));
	// float y1 = toFloat(attr("y1"));
	//
	// if (x < x0)
	// return;
	// if (x > x1)
	// return;
	// if (y < y0)
	// return;
	// if (y > y1)
	// return;
	// for (YlElement child : childList) {
	// child.shoot(x, y, result);
	// }
	// super.shoot(x - x0, y - y0, result);
	// result.elementList.addLast(new ShootElement(x - x0, y - y0, this));
	// }
}
