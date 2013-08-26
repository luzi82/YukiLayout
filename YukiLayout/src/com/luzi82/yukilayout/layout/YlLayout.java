package com.luzi82.yukilayout.layout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.luzi82.yukilayout.YlFunc;
import com.luzi82.yukilayout.YlGraphics;

public class YlLayout {

	public YlElement mRoot;

	// public TreeMap<String, YlElement> mId2Ele = new TreeMap<String,
	// YlElement>();

	// public TreeMap<String, Object> arg = new TreeMap<String, Object>();

	public final Object pArg;

	// LinkedList<Ele> mElementList = new LinkedList<YlLayout.Ele>();

	public YlLayout(File file, Object arg) throws ParserConfigurationException,
			SAXException, IOException {
		this(new FileInputStream(file), arg);
	}

	public YlLayout(InputStream inputStream, Object arg)
			throws ParserConfigurationException, SAXException, IOException {
		pArg = arg;

		BufferedInputStream bis = new BufferedInputStream(inputStream);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(bis);

		mRoot = createLayoutElement(null, (Element) doc.getFirstChild());
		mRoot.processElement((Element) doc.getFirstChild());
	}

	public YlElement createLayoutElement(YlElement aParent, Element aElement) {
		String qName = aElement.getTagName();
		YlElement ele = null;
		if (qName.equals("screen")) {
			ele = new YlScreenElement(YlLayout.this, aParent);
		} else if (qName.equals("trans")) {
			ele = new YlTransElement(YlLayout.this, aParent);
		} else if (qName.equals("repeat")) {
			ele = new YlRepeatElement(YlLayout.this, aParent);
		} else if (qName.equals("text")) {
			ele = new YlTextElement(YlLayout.this, aParent);
		} else if (qName.equals("void")) {
			ele = new YlVoidElement(YlLayout.this, aParent);
		} else if (qName.equals("img")) {
			ele = new YlImgElement(YlLayout.this, aParent);
		} else if (qName.equals("scope")) {
			ele = new YlScopeElement(YlLayout.this, aParent);
		} else if (qName.equals("cache")) {
			ele = new YlCacheElement(YlLayout.this, aParent);
		} else if (qName.equals("box")) {
			ele = new YlBoxElement(YlLayout.this, aParent);
		} else if (qName.equals("scroll")) {
			ele = new YlScrollElement(YlLayout.this, aParent);
		} else {
			throw new Error("unknown element: " + qName);
		}
		return ele;
	}

	public void paint(YlGraphics graphics) {
		mRoot.paint(graphics);
	}

	// public class DH extends DefaultHandler {
	//
	// LinkedList<YlElement> stack = new LinkedList<YlElement>();
	//
	// @Override
	// public void startElement(String uri, String localName, String qName,
	// Attributes attributes) throws SAXException {
	// YlElement ele = null;
	// YlElement parent = null;
	// if (stack.size() > 0) {
	// parent = stack.getFirst();
	// }
	// if (qName.equals("screen")) {
	// ele = new YlScreenElement(YlLayout.this);
	// } else if (qName.equals("drag")) {
	// ele = new YlDragElement(YlLayout.this);
	// } else if (qName.equals("trans")) {
	// ele = new YlTransElement(YlLayout.this);
	// } else if (qName.equals("repeat")) {
	// ele = new YlRepeatElement(YlLayout.this);
	// } else if (qName.equals("text")) {
	// ele = new YlTextElement(YlLayout.this);
	// } else if (qName.equals("void")) {
	// ele = new YlVoidElement(YlLayout.this);
	// } else if (qName.equals("img")) {
	// ele = new YlImgElement(YlLayout.this);
	// } else {
	// throw new SAXException("unknown element: " + qName);
	// }
	// ele.processAttributes(attributes);
	// if (parent != null) {
	// parent.child.addFirst(ele);
	// ele.pParent = parent;
	// }
	// stack.push(ele);
	// }
	//
	// @Override
	// public void endElement(String uri, String localName, String qName)
	// throws SAXException {
	// if (stack.size() == 1) {
	// mRoot = stack.getFirst();
	// }
	// stack.pop();
	// }
	//
	// }

	public static final String VAR_PREFIX = "var.";

	public Object ruleToVal(YlElement ele, String[] ruleExp)
			throws ParseException {
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
				System.err.println(b);
				if (a instanceof YlElement) {
					YlElement ae = (YlElement) a;
					boolean good = false;
					if (!good) {
						if (ae.attrExist(b)) {
							System.err.println("ae.attrExist(b)");
							Object obj = ae.attr(b);
							calStack.push(obj);
							good = true;
						}
					}
					// System.err.println("a " + good);
					if (!good) {
						try {
							System.err.println("method " + b);
							Method m = a.getClass().getMethod(b);
							if (m != null) {
								System.err.println("ae "
										+ ae.getClass().getSimpleName());
								System.err.println("method " + b + " ok");
								calStack.push(m.invoke(a));
								good = true;
							}
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
					System.err.println("ae " + ae.getClass().getSimpleName());
					System.err.println("b " + b);
					System.err.println("good " + good);
					if (!good) {
						throw new Error();
					}
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

	public Object var(YlElement ele, Object in) throws ParseException {
		if (in instanceof String) {
			String s = (String) in;

			// check if float
			try {
				return Float.parseFloat(s);
			} catch (NumberFormatException e) {
			}

			// element attr
			if (ele != null) {
				Object v = ele.attr(s);
				if (v != null)
					return v;
			}

			// search parent var- value
			YlElement ele2 = ele;
			while (ele2 != null) {
				YlRule r = ele2.var.get(s);
				if (r != null) {
					return r.val();
				}
				ele2 = ele2.pParent;
			}
			ele2 = ele.mId2Element.get(s);
			if (ele2 != null)
				return ele2;
			if (pArg != null) {
				try {
					Class<?> argClass = pArg.getClass();
					Field f = argClass.getField(s);
					return f.get(pArg);
				} catch (SecurityException e) {
					// e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// e.printStackTrace();
				} catch (IllegalAccessException e) {
					// e.printStackTrace();
				}
			}
			// if (mId2Ele.containsKey(s)) {
			// return mId2Ele.get(s);
			// }
			// if (arg.containsKey(s)) {
			// return arg.get(s);
			// }
			return s;
		} else {
			return in;
		}
	}

	public static class ShootResult {
		public float x, y;
		public LinkedList<ShootElement> elementList = new LinkedList<YlLayout.ShootElement>();
	}

	public static class ShootElement {
		public float x, y;
		public YlElement element;

		public ShootElement(float x, float y, YlElement element) {
			this.x = x;
			this.y = y;
			this.element = element;
		}
	}

	public ShootResult shoot(float x, float y) {
		ShootResult ret = new ShootResult();
		ret.x = x;
		ret.y = y;
		mRoot.shoot(x, y, ret);
		return ret;
	}

	// public static List<?> toList(Object obj) {
	// if (obj instanceof List) {
	// return (List<?>) obj;
	// }
	// if (obj instanceof Iterable) {
	// Iterable<?> objIterable = (Iterable<?>) obj;
	// LinkedList<Object> ret = new LinkedList<Object>();
	// for (Object o : objIterable) {
	// ret.add(o);
	// }
	// return ret;
	// }
	// if (obj.getClass().isArray()) {
	// LinkedList<Object> ret = new LinkedList<Object>();
	// int count = Array.getLength(obj);
	// for (int i = 0; i < count; ++i) {
	// ret.add(Array.get(obj, i));
	// }
	// return ret;
	// }
	// throw new ClassCastException();
	// }

	// public Ele[] getElementArray() {
	// return mElementList.toArray(new Ele[0]);
	// }
}
