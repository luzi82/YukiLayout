package com.luzi82.yukilayout.schema;

import java.util.List;

import com.luzi82.yukilayout.YlGraphics;

public class YlRepeatSE extends YlSchemaElement {

	/**
	 * 
	 */
	public YlStoreRule foreach = new YlStoreRule(this);

	private int _index = -1;
	private Object _item = null;

	public YlRepeatSE(YlSchema aLayout) {
		super(aLayout);
	}

	public YlVal index = new YlVal() {
		@Override
		public Object val() {
			return _index;
		}
	};

	public YlVal item = new YlVal() {
		@Override
		public Object val() {
			return _item;
		}
	};

	@Override
	public void paint(YlGraphics graphics) {
		List<?> forVal = foreach.list();
		_index = 0;
		for (Object v : forVal) {
			_item = v;
			super.paint(graphics);
			++_index;
		}
		_index = -1;
		_item = null;
	}

}