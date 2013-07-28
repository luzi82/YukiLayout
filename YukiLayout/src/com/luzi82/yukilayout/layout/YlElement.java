package com.luzi82.yukilayout.layout;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.YlGraphics;
import com.luzi82.yukilayout.YlRect;
import com.luzi82.yukilayout.layout.YlLayout.ShootElement;
import com.luzi82.yukilayout.layout.YlLayout.ShootResult;

public abstract class YlElement {

	/**
		 * 
		 */
	public final YlLayout pLayout;

	public final boolean iCreateChild;

	public YlElement pParent;

	public YlId2Element mId2Element;

	/**
	 * @param aLayout
	 */
	public YlElement(YlLayout aLayout, YlElement aParent, boolean aCreateChild) {
		pLayout = aLayout;
		pParent = aParent;
		iCreateChild = aCreateChild;
		if (pParent != null) {
			mId2Element = pParent.mId2Element;
		} else {
			mId2Element = new YlId2Element();
		}
	}

	public LinkedList<YlElement> childList = new LinkedList<YlElement>();

	public TreeMap<String, YlStoreRule> var = new TreeMap<String, YlStoreRule>();

	public TreeMap<String, YlVal> attr = new TreeMap<String, YlVal>();
	public TreeMap<String, Object> attrDefault = new TreeMap<String, Object>();
	public TreeMap<String, Object> attrValue = new TreeMap<String, Object>();

	public void processElement(Element aElement) {
		NamedNodeMap attributes = aElement.getAttributes();
		int attrLen = attributes.getLength();
		for (int attrIdx = 0; attrIdx < attrLen; ++attrIdx) {
			Attr attr = (Attr) attributes.item(attrIdx);
			String attrName = attr.getName();
			String attrValue = attr.getValue();
			System.err.println(attrName);
			System.err.println(attrValue);
			System.err.println();
			setRule(attrName, attrValue);
		}
		if (iCreateChild) {
			createChild(aElement);
		}
	}

	public void setRule(String key, String value) {
		if (key.equals("id")) {
			mId2Element.set(value, this);
		}
		// try {
		// Field f = getClass().getField(key);
		// if ((f != null) && (f.getType() == YlStoreRule.class)) {
		// YlStoreRule rule = (YlStoreRule) f.get(this);
		// rule.set(value);
		// }
		// } catch (SecurityException e) {
		// } catch (NoSuchFieldException e) {
		// } catch (IllegalArgumentException e) {
		// } catch (IllegalAccessException e) {
		// }
		attr.put(key, new YlStoreRule(this, value));
		if (key.startsWith(YlLayout.VAR_PREFIX)
				&& key.length() > YlLayout.VAR_PREFIX.length()) {
			String varKey = key.substring(YlLayout.VAR_PREFIX.length());
			var.put(varKey, new YlStoreRule(this, value));
		}
	}

	public void setAttrVal(String key, Object val) {
		attr.put(key, new YlObjectVal(val));
	}

	// public Object cal(String key) throws ParseException {
	// try {
	// Field f = getClass().getField(key);
	// if ((f != null) && (YlVal.class.isAssignableFrom(f.getType()))) {
	// YlVal val = (YlVal) f.get(this);
	// return val.val();
	// }
	// } catch (SecurityException e) {
	// } catch (NoSuchFieldException e) {
	// } catch (IllegalArgumentException e) {
	// } catch (IllegalAccessException e) {
	// }
	// return null;
	// }

	public void paint(YlGraphics graphics) {
		for (YlElement e : childList) {
			e.paint(graphics);
		}
	}

	public Object var(String key) {
		YlStoreRule rule = var.get(key);
		if (rule == null) {
			if (pParent == null) {
				return null;
			}
			return pParent.var(key);
		}
		return rule.val();
	}

	public Object attr(String key) {
		YlVal rule = attr.get(key);
		if (rule != null) {
			// System.err.println("a");
			return rule.val();
		}
		return attrDefault.get(key);
	}

	public boolean attrExist(String key) {
		System.err.println(key);
		for (String a : attr.keySet()) {
			System.err.println(a);
		}
		boolean v = (attr.containsKey(key) || attrDefault.containsKey(key));
		System.err.println(v);
		return v;
	}

	public void createChild(Element aElement) {
		NodeList nodeList = aElement.getChildNodes();
		int nodeLen = nodeList.getLength();
		for (int i = 0; i < nodeLen; ++i) {
			Node n = nodeList.item(i);
			if (!(n instanceof Element)) {
				continue;
			}
			Element e = (Element) n;
			YlElement le = pLayout.createLayoutElement(this, e);
			le.processElement(e);
			childList.addFirst(le);
		}
	};

	public static Float toFloat(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof Float) {
			return (Float) v;
		} else {
			return Float.valueOf(v.toString());
		}
	}

	public static Integer toInt(Object v) {
		if (v == null) {
			return null;
		} else if (v instanceof Integer) {
			return (Integer) v;
		} else {
			return Float.valueOf(v.toString()).intValue();
		}
	}

	public static List<?> toList(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof List) {
			return (List<?>) obj;
		}
		if (obj instanceof Iterable) {
			Iterable<?> objIterable = (Iterable<?>) obj;
			LinkedList<Object> ret = new LinkedList<Object>();
			for (Object o : objIterable) {
				ret.add(o);
			}
			return ret;
		}
		if (obj.getClass().isArray()) {
			LinkedList<Object> ret = new LinkedList<Object>();
			int count = Array.getLength(obj);
			for (int i = 0; i < count; ++i) {
				ret.add(Array.get(obj, i));
			}
			return ret;
		}
		System.err.println(obj.getClass().getSimpleName());
		System.err.println(obj.toString());
		throw new ClassCastException();
	}

	public static YlColor toColor(Object obj) {
		return YlColor.create(obj);
	}

	public static String string(Object v) {
		if (v == null) {
			return null;
		} else {
			return v.toString();
		}
	}

	public void setAttrDefault(String key, Object obj) {
		attrDefault.put(key, obj);
	}

	public YlRect contentRect() {
		YlRect ret = new YlRect();
		for (YlElement child : childList) {
			YlRect rect = child.rect();
			ret = ret.union(rect);
		}
		return ret;
	}

	public YlRect rect() {
		return contentRect();
	}

	public void shoot(float x, float y, ShootResult result) {
		YlRect rect = rect();
		if (!rect.inside(x, y))
			return;
		// float xx = x - rect.x0;
		// float yy = y - rect.y0;
		for (YlElement child : childList) {
			child.shoot(x, y, result);
		}
		result.elementList.addLast(new ShootElement(x, y, this));
	}
}