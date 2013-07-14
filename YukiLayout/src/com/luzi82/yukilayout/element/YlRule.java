package com.luzi82.yukilayout.element;

import java.text.ParseException;

import com.luzi82.yukilayout.YlExp;
import com.luzi82.yukilayout.YlLayout;

public abstract class YlRule extends YlVal {

	/**
	 * 
	 */
	private final YlLayout pLayout;
	final YlEle ele;
	protected String[] ruleExp;
	protected boolean ruleExpUpdated;

	public YlRule(YlLayout ylLayout, YlEle ele) {
		pLayout = ylLayout;
		this.ele = ele;
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
			return pLayout.ruleToVal(ele, ruleExp);
		} catch (ParseException e) {
			throw new Error(e);
		}
	}

	public abstract String rule();

}