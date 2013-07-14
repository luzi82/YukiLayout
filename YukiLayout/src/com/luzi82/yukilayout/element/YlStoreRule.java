package com.luzi82.yukilayout.element;


public class YlStoreRule extends YlRule {

	/**
	 * 
	 */
	String input;

	public YlStoreRule(YlEle ele) {
		super(ele);
	}

	public YlStoreRule(YlEle ele, String input) {
		super(ele);
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