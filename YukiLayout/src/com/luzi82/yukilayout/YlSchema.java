package com.luzi82.yukilayout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.luzi82.yukilayout.element.YlDrag;
import com.luzi82.yukilayout.element.YlEle;
import com.luzi82.yukilayout.element.YlImg;
import com.luzi82.yukilayout.element.YlRepeat;
import com.luzi82.yukilayout.element.YlRule;
import com.luzi82.yukilayout.element.YlScreen;
import com.luzi82.yukilayout.element.YlText;
import com.luzi82.yukilayout.element.YlTrans;
import com.luzi82.yukilayout.element.YlVoid;


public class YlSchema {

	public YlEle mRoot;
	public int mRootWidth;
	public int mRootHeight;

	public TreeMap<String, YlEle> mId2Ele = new TreeMap<String, YlEle>();

	public TreeMap<String, Object> arg = new TreeMap<String, Object>();

	// LinkedList<Ele> mElementList = new LinkedList<YlLayout.Ele>();

	public YlSchema(File file) throws ParserConfigurationException,
			SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(file, new DH());
	}

	public YlSchema(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(inputStream, new DH());
	}

	public void setRootSize(int width, int height) {
		this.mRootWidth = width;
		this.mRootHeight = height;
	}

	public void paint(YlGraphics graphics) {
		mRoot.paint(graphics);
	}

	public class DH extends DefaultHandler {

		LinkedList<YlEle> stack = new LinkedList<YlEle>();

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			YlEle ele = null;
			YlEle parent = null;
			if (stack.size() > 0) {
				parent = stack.getFirst();
			}
			if (qName.equals("screen")) {
				ele = new YlScreen(YlSchema.this);
			} else if (qName.equals("drag")) {
				ele = new YlDrag(YlSchema.this);
			} else if (qName.equals("trans")) {
				ele = new YlTrans(YlSchema.this);
			} else if (qName.equals("repeat")) {
				ele = new YlRepeat(YlSchema.this);
			} else if (qName.equals("text")) {
				ele = new YlText(YlSchema.this);
			} else if (qName.equals("void")) {
				ele = new YlVoid(YlSchema.this);
			} else if (qName.equals("img")) {
				ele = new YlImg(YlSchema.this);
			} else {
				throw new SAXException("unknown element: " + qName);
			}
			ele.processAttributes(attributes);
			if (parent != null) {
				parent.child.addFirst(ele);
				ele.pParent = parent;
			}
			stack.push(ele);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (stack.size() == 1) {
				mRoot = stack.getFirst();
			}
			stack.pop();
		}

	}

	public static final String VAR_PREFIX = "var.";

	public Object ruleToVal(YlEle ele, String[] ruleExp) throws ParseException {
		// if (rule == null)
		// return null;
		//
		// String[] exp = YlExp.parse(rule);
		int offset = 0;

		LinkedList<Object> calStack = new LinkedList<Object>();

		while (offset < ruleExp.length) {
			String v = ruleExp[offset++];
			if (v.equals("@")) {
				int paramLen = Integer.parseInt((String) calStack.pop());
				Object[] objAry = new Object[paramLen];
				for (int i = paramLen - 1; i >= 0; --i) {
					objAry[i] = var(ele, calStack.pop());
				}
				String funcName = (String) calStack.pop();
				Class<?>[] paramClass = new Class[paramLen];
				Arrays.fill(paramClass, Object.class);
				try {
					Method method = YlFunc.class
							.getMethod(funcName, paramClass);
					Object ret = method.invoke(null, objAry);
					calStack.push(ret);
				} catch (SecurityException e) {
					// throw new ParseException(rule, rule.length());
					throw new Error(e);
				} catch (NoSuchMethodException e) {
					// System.err.println(funcName);
					// throw new ParseException(rule, rule.length());
					throw new Error(e);
				} catch (IllegalArgumentException e) {
					throw new Error(e);
				} catch (IllegalAccessException e) {
					throw new Error(e);
				} catch (InvocationTargetException e) {
					throw new Error(e);
				}
			} else if (v.equals(".")) {
				String b = (String) calStack.pop();
				Object a = var(ele, calStack.pop());
				if (a instanceof YlEle) {
					YlEle ae = (YlEle) a;
					Object obj = ae.cal(b);
					calStack.push(obj);
				} else if (a instanceof String) {
					calStack.push(((String) a) + "." + b);
				} else {
					boolean good = false;
					if (!good) {
						try {
							Method m = a.getClass().getMethod(b);
							if (m != null) {
								calStack.push(m.invoke(a));
								good = true;
							}
						} catch (SecurityException e) {
						} catch (NoSuchMethodException e) {
						} catch (IllegalArgumentException e) {
						} catch (IllegalAccessException e) {
						} catch (InvocationTargetException e) {
						}
					}
					if (!good) {
						try {
							Field f = a.getClass().getField(b);
							if (f != null) {
								calStack.push(f.get(a));
								good = true;
							}
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					if (!good) {
						// throw new ParseException(rule, rule.length());
						throw new Error();
					}
				}
			} else if (v.equals("--")) {
				Object a = var(ele, calStack.pop());
				calStack.push(YlFunc.neg(a));
			} else if (v.equals("+")) {
				Object b = var(ele, calStack.pop());
				Object a = var(ele, calStack.pop());
				calStack.push(YlFunc.add(a, b));
			} else if (v.equals("-")) {
				Object b = var(ele, calStack.pop());
				Object a = var(ele, calStack.pop());
				calStack.push(YlFunc.sub(a, b));
			} else if (v.equals("*")) {
				Object b = var(ele, calStack.pop());
				Object a = var(ele, calStack.pop());
				calStack.push(YlFunc.mul(a, b));
			} else if (v.equals("/")) {
				Object b = var(ele, calStack.pop());
				Object a = var(ele, calStack.pop());
				if ((a instanceof Number) && (b instanceof Number)) {
					calStack.push(YlFunc.div(a, b));
				} else if ((a instanceof String) && (b instanceof String)) {
					calStack.push(((String) a) + "/" + ((String) b));
				} else {
					// throw new ParseException(rule, rule.length());
					throw new Error();
				}
			} else {
				calStack.push(v);
			}
		}

		if (calStack.size() != 1) {
			// throw new ParseException(rule, rule.length());
			throw new Error("calStack.size() != 1");
		}

		Object ret = calStack.getFirst();
		ret = var(ele, ret);

		return ret;
	}

	public Object var(YlEle ele, Object in) throws ParseException {
		if (in instanceof String) {
			String s = (String) in;
			try {
				return Float.parseFloat(s);
			} catch (NumberFormatException e) {
			}
			if (ele != null) {
				Object v = ele.cal(s);
				if (v != null)
					return v;
			}
			while (ele != null) {
				YlRule r = ele.var.get(s);
				if (r != null) {
					return r.val();
				}
				ele = ele.pParent;
			}
			if (mId2Ele.containsKey(s)) {
				return mId2Ele.get(s);
			}
			if (arg.containsKey(s)) {
				return arg.get(s);
			}
			return s;
		} else {
			return in;
		}
	}

	public void setArg(String key, Object value) {
		arg.put(key, value);
	}

	public static List<?> toList(Object obj) {
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
		throw new ClassCastException();
	}

	// public Ele[] getElementArray() {
	// return mElementList.toArray(new Ele[0]);
	// }
}
