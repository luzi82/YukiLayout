package com.luzi82.yukilayout.element;

import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlSchema;

public class YlScreen extends YlEle {

	public YlScreen(YlSchema aLayout) {
		super(aLayout);
	}

	public YlStoreRule backgroundColor = new YlStoreRule(this);

	public YlVal width = new YlVal() {
		@Override
		public Object val() {
			return YlScreen.this.pLayout.mRootWidth;
		}
	};

	public YlVal height = new YlVal() {
		@Override
		public Object val() {
			return YlScreen.this.pLayout.mRootHeight;
		}
	};

	@Override
	public void paint(YlGraphics graphics) {
		YlColor backgroundColor = this.backgroundColor.color();
		if (backgroundColor != null) {
			graphics.clear(backgroundColor);
		}
		super.paint(graphics);
	}
}