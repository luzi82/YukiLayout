package com.luzi82.yukilayout.layout;

import java.util.Map;
import java.util.TreeMap;

public class YlId2Element {

	public Map<String, YlElement> mId2Element;

	public YlId2Element pParent;

	public YlId2Element() {
		this(null);
	}

	public YlId2Element(YlId2Element aParent) {
		mId2Element = new TreeMap<String, YlElement>();
		pParent = aParent;
	}

	public void set(String key, YlElement element) {
		mId2Element.put(key, element);
	}

	public YlElement get(String key) {
		YlElement ret = mId2Element.get(key);
		if (ret != null)
			return ret;
		if (pParent != null)
			return pParent.get(key);
		return null;
	}
}
