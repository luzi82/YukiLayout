package com.luzi82.yukilayout.layout;

public class YlScopeElement extends YlElement {

	public YlScopeElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, true);
		mId2Element = new YlId2Element(pParent.mId2Element);
	}

}
