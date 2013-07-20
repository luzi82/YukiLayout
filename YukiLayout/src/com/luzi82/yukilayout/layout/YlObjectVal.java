package com.luzi82.yukilayout.layout;

public class YlObjectVal extends YlVal {

	Object mValue;

	public YlObjectVal(Object aValue) {
		mValue = aValue;
	}

	@Override
	public Object val() {
		return mValue;
	}

}
