package com.luzi82.yukilayout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class YlLayout {

	Ele root;
	int rootWidth;
	int rootHeight;

	public YlLayout(File file) throws ParserConfigurationException,
			SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(file, new DH());
	}

	public YlLayout(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(inputStream, new DH());
	}

	public void setRootSize(int width, int height) {
		this.rootWidth = width;
		this.rootHeight = height;
	}

	public void paint(YlGraphics graphics) {
		// root.paintStart(graphics);
		paint(graphics, root);
	}

	public void paint(YlGraphics graphics, Ele ele) {
		ele.paintStart(graphics);
		for (Ele e : ele.child) {
			paint(graphics, e);
		}
		ele.paintEnd(graphics);
	}

	public class DH extends DefaultHandler {

		LinkedList<Ele> stack = new LinkedList<YlLayout.Ele>();

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			Ele ele = null;
			if (qName.equals("screen")) {
				ele = new Screen();
			} else if (qName.equals("drag")) {
				ele = new Drag();
			} else if (qName.equals("trans")) {
				ele = new Trans();
			} else if (qName.equals("repeat")) {
				ele = new Repeat();
			} else if (qName.equals("text")) {
				ele = new Text();
			} else if (qName.equals("void")) {
				ele = new Void();
			} else {
				throw new SAXException("unknown element: " + qName);
			}
			ele.processAttributes(attributes);
			if (stack.size() > 0)
				stack.getFirst().child.addFirst(ele);
			stack.push(ele);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (stack.size() == 1) {
				root = stack.getFirst();
			}
			stack.pop();
		}

	}

	public abstract class Ele {
		public LinkedList<Ele> child = new LinkedList<YlLayout.Ele>();

		public StoreRule id = new StoreRule();

		public TreeMap<String, StoreRule> var = new TreeMap<String, YlLayout.StoreRule>();

		public void processAttributes(Attributes attributes) {
			int attrLen = attributes.getLength();
			for (int attrIdx = 0; attrIdx < attrLen; ++attrIdx) {
				String attrName = attributes.getQName(attrIdx);
				String attrValue = attributes.getValue(attrIdx);
				setRule(attrName, attrValue);
			}
		}

		public void setRule(String key, String value) {
			try {
				Field f = getClass().getField(key);
				if ((f != null) && (f.getType() == StoreRule.class)) {
					StoreRule rule = (StoreRule) f.get(this);
					rule.set(value);
				}
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
			if (key.startsWith(VAR_PREFIX)
					&& key.length() > VAR_PREFIX.length()) {
				String varKey = key.substring(VAR_PREFIX.length());
				var.put(varKey, new StoreRule(value));
			}
		}

		public Object cal(String key) {
			try {
				Field f = getClass().getField(key);
				if ((f != null) && (f.getType() == StoreRule.class)) {
					StoreRule rule = (StoreRule) f.get(this);
					return rule.val();
				}
			} catch (SecurityException e) {
			} catch (NoSuchFieldException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
			return null;
		}

		public void paintStart(YlGraphics graphics) {
		}

		public void paintEnd(YlGraphics graphics) {
		}

	}

	public class Screen extends Ele {

		public StoreRule backgroundColor = new StoreRule();

		@Override
		public void paintStart(YlGraphics graphics) {
			YlColor backgroundColor = this.backgroundColor.color();
			if (backgroundColor != null) {
				graphics.clear(backgroundColor);
			}
		}
	}

	public class Drag extends Ele {

		public StoreRule x = new StoreRule("0");
		public StoreRule y = new StoreRule("0");

		@Override
		public void paintStart(YlGraphics graphics) {
			Float xf = x.floatt();
			Float yf = y.floatt();
			graphics.push();
			graphics.translate(xf, yf);
		}

		@Override
		public void paintEnd(YlGraphics graphics) {
			graphics.pop();
		}

	}

	public class Repeat extends Ele {
		@Override
		public void paintStart(YlGraphics graphics) {

		}
	}

	public class Text extends Ele {
		@Override
		public void paintStart(YlGraphics graphics) {

		}
	}

	public class Trans extends Ele {
		public StoreRule x = new StoreRule("0");
		public StoreRule y = new StoreRule("0");

		@Override
		public void paintStart(YlGraphics graphics) {
			Float xf = x.floatt();
			Float yf = y.floatt();
			graphics.push();
			graphics.translate(xf, yf);
		}
	}

	public class Void extends Ele {
		@Override
		public void paintStart(YlGraphics graphics) {

		}
	}

	public int valueVer = 0;

	public abstract class Rule {

		int valBufferVer = -1;
		Object valBuffer;

		public Object val() {
			if (valBufferVer != valueVer) {
				String rule = rule();
				valBuffer = ruleToVal(rule);
				valBufferVer = valueVer;
			}
			return valBuffer;
		}

		public abstract void set(String rule);

		public abstract String rule();

		int colorVer = -1;
		YlColor color;

		public YlColor color() {
			if (colorVer != valueVer) {
				Object v = val();
				if (v == null) {
					color = null;
				} else {
					color = new YlColor(v);
				}
				colorVer = valueVer;
			}
			return color;
		}

		int floatVer = -1;
		Float floatt;

		public Float floatt() {
			if (floatVer != valueVer) {
				Object v = val();
				if (v == null) {
					floatt = null;
				} else if (v instanceof Float) {
					floatt = (Float) v;
				} else {
					floatt = Float.valueOf(v.toString());
				}
				floatVer = valueVer;
			}
			return floatt;
		}
	}

	public class StoreRule extends Rule {

		String input;

		public StoreRule() {
			++valueVer;
		}

		public StoreRule(String input) {
			this.input = input;
			++valueVer;
		}

		@Override
		public void set(String value) {
			this.input = value;
			++valueVer;
		}

		@Override
		public String rule() {
			return input;
		}

	}

	public static final String VAR_PREFIX = "var.";

	public Object ruleToVal(String rule) {
		return rule;
	}

}
