package com.luzi82.yukilayout.layout;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class YlRepeatElement extends YlElement {

	/**
	 * 
	 */
	public YlStoreRule foreach = new YlStoreRule(this);

	private int _index = -1;
	private Object _item = null;

	public YlRepeatElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, false);
	}

	public void processElement(Element aElement) {
		super.processElement(aElement);
		NodeList nodeList = aElement.getChildNodes();
		Element e = null;
		int nodeLen = nodeList.getLength();
		for (int i = 0; i < nodeLen; ++i) {
			Node n = nodeList.item(i);
			if ((n instanceof Element)) {
				e = (Element) n;
				break;
			}
		}
		if (e == null) {
			return;
		}
		if (e.getTagName() != "scope") {
			throw new Error("element not scope");
		}
		List<?> list = toList(attr("foreach"));
		int index = 0;
		for (Object item : list) {
			YlScopeElement scope = (YlScopeElement) pLayout
					.createLayoutElement(this, e);
			scope.setAttrVal("item", item);
			scope.setAttrVal("index", index);
			scope.processElement(e);
			child.addLast(scope);
			++index;
		}
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

	// @Override
	// public void paint(YlGraphics graphics) {
	// List<?> forVal = foreach.list();
	// _index = 0;
	// for (Object v : forVal) {
	// _item = v;
	// super.paint(graphics);
	// ++_index;
	// }
	// _index = -1;
	// _item = null;
	// }

}