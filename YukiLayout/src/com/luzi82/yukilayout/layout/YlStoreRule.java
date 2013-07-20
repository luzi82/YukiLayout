package com.luzi82.yukilayout.layout;


public class YlStoreRule extends YlRule {

	/**
	 * 
	 */
	String input;

	public YlStoreRule(YlElement ele) {
		super(ele);
	}

	public YlStoreRule(YlElement ele, String input) {
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