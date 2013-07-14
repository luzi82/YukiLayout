package com.luzi82.yukilayout.schema;

import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.YlGraphics;

public class YlScreenSE extends YlSchemaElement {

	public YlScreenSE(YlSchema aLayout) {
		super(aLayout);
	}

	public YlStoreRule backgroundColor = new YlStoreRule(this);

	public YlVal width = new YlVal() {
		@Override
		public Object val() {
			return YlScreenSE.this.pSchema.mRootWidth;
		}
	};

	public YlVal height = new YlVal() {
		@Override
		public Object val() {
			return YlScreenSE.this.pSchema.mRootHeight;
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