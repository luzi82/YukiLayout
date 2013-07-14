package com.luzi82.yukilayout.element;

import com.luzi82.yukilayout.YlLayout;


public class YlStoreRule extends YlRule {

	/**
	 * 
	 */
	String input;

	public YlStoreRule(YlLayout ylLayout, YlEle ele) {
		super(ylLayout, ele);
	}

	public YlStoreRule(YlLayout ylLayout, YlEle ele, String input) {
		super(ylLayout, ele);
		this.input = input;
	}

	// @Override
	public void set(String value) {
		this.input = value;
		ruleExp = null;
		ruleExpUpdated = false;
	}

	@Override
	public String rule() {
		return input;
	}

}