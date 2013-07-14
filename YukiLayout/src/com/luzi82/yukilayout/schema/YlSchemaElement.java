package com.luzi82.yukilayout.schema;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.TreeMap;

import org.xml.sax.Attributes;

import com.luzi82.yukilayout.YlGraphics;

public abstract class YlSchemaElement {

		/**
		 * 
		 */
		public final YlSchema pSchema;

		/**
		 * @param aLayout
		 */
		public YlSchemaElement(YlSchema aLayout) {
			pSchema = aLayout;
		}

		public YlSchemaElement pParent;

		public LinkedList<YlSchemaElement> child = new LinkedList<YlSchemaElement>();

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
				pSchema.mId2Ele.put(value, this);
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
			if (key.startsWith(YlSchema.VAR_PREFIX)
					&& key.length() > YlSchema.VAR_PREFIX.length()) {
				String varKey = key.substring(YlSchema.VAR_PREFIX.length());
				var.put(varKey, new YlStoreRule(this, value));
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
			for (YlSchemaElement e : child) {
				e.paint(graphics);
			}
		}
	}