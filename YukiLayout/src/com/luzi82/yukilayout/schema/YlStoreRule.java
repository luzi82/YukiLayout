package com.luzi82.yukilayout.schema;


public class YlStoreRule extends YlRuleSE {

	/**
	 * 
	 */
	String input;

	public YlStoreRule(YlSchemaElement ele) {
		super(ele);
	}

	public YlStoreRule(YlSchemaElement ele, String input) {
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