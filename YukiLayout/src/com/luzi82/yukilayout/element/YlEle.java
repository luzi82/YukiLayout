package com.luzi82.yukilayout.element;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.TreeMap;

import org.xml.sax.Attributes;

import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlLayout;

public abstract class YlEle {

		/**
		 * 
		 */
		public final YlLayout pLayout;

		/**
		 * @param aLayout
		 */
		public YlEle(YlLayout aLayout) {
			pLayout = aLayout;
		}

		public YlEle pParent;

		public LinkedList<YlEle> child = new LinkedList<YlEle>();

		public TreeMap<String, YlStoreRule> var = new TreeMap<String, YlStoreRule>();

//		public Ele() {
//			mElementList.add(this);
//		}

		public void processAttributes(Attributes attributes) {
			int attrLen = attributes.getLength();
			for (int attrIdx = 0; attrIdx < attrLen; ++attrIdx) {
				String attrName = attributes.getQName(attrIdx);
				String attrValue = attributes.getValue(attrIdx);
				setRule(attrName, attrValue);
			}
		}

		public void setRule(String key, String value) {
			if (key.equals("id")) {
				pLayout.mId2Ele.put(value, this);
			}
			try {
				Field f = getClass().getField(key);
				if ((f != null) && (f.getType() == YlStoreRule.class)) {
					YlStoreRule rule = (YlStoreRule) f.get(this);
					rule.set(value);
				}
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
			if (key.startsWith(YlLayout.VAR_PREFIX)
					&& key.length() > YlLayout.VAR_PREFIX.length()) {
				String varKey = key.substring(YlLayout.VAR_PREFIX.length());
				var.put(varKey, new YlStoreRule(pLayout, this, value));
			}
		}

		public Object cal(String key) throws ParseException {
			try {
				Field f = getClass().getField(key);
				if ((f != null) && (YlVal.class.isAssignableFrom(f.getType()))) {
					YlVal val = (YlVal) f.get(this);
					return val.val();
				}
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
			return null;
		}

		public void paint(YlGraphics graphics) {
			for (YlEle e : child) {
				e.paint(graphics);
			}
		}
	}