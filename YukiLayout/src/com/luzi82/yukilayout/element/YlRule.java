package com.luzi82.yukilayout.element;

import java.text.ParseException;

import com.luzi82.yukilayout.YlExp;

public abstract class YlRule extends YlVal {

	/**
	 * 
	 */
	public final YlEle pEle;
	protected String[] ruleExp;
	protected boolean ruleExpUpdated;

	public YlRule(YlEle ele) {
		this.pEle = ele;
		ruleExpUpdated = false;
	}

	public Object val() {
		try {
			if (!ruleExpUpdated) {
				String rule = rule();
				if (rule != null)
					ruleExp = YlExp.parse(rule);
				else
					ruleExp = null;
				ruleExpUpdated = true;
			}
			if (ruleExp == null) {
				return null;
			}
			return pEle.pLayout.ruleToVal(pEle, ruleExp);
		} catch (ParseException e) {
			throw new Error(e);
		}
	}

	public abstract String rule();

}