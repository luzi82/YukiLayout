package com.luzi82.yukilayout;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class YkLayout {

	Ele root;
	int rootWidth;
	int rootHeight;

	public YkLayout(File file) throws ParserConfigurationException,
			SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(file, new DH());
	}

	public YkLayout(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(inputStream, new DH());
	}

	public void setRootSize(int width, int height) {
		this.rootWidth = width;
		this.rootHeight = height;
	}

	public void paint(YkGraphics graphics) {
		root.paint(graphics);
	}

	public class DH extends DefaultHandler {

		LinkedList<Ele> stack = new LinkedList<YkLayout.Ele>();

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			Ele ele = null;
			if (qName.equals("screen")) {
				ele = new Screen();
			} else if (qName.equals("box")) {
				ele = new Box();
			} else if (qName.equals("repeat")) {
				ele = new Repeat();
			} else if (qName.equals("text")) {
				ele = new Text();
			} else {
				throw new SAXException("unknown element: " + qName);
			}
			ele.processAttributes(attributes);
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
		LinkedList<Ele> child = new LinkedList<YkLayout.Ele>();
		Dynamic backgroundColor;

		public void processAttributes(Attributes attributes) {
			backgroundColor = new Dynamic(
					attributes.getValue("background-color"));
		}

		public abstract void paint(YkGraphics graphics);
	}

	public class Screen extends Ele {
		@Override
		public void paint(YkGraphics graphics) {
			if (backgroundColor.valid()) {
				graphics.clear(backgroundColor.color());
			}
		}
	}

	public class Box extends Ele {
		@Override
		public void paint(YkGraphics graphics) {

		}
	}

	public class Repeat extends Ele {
		@Override
		public void paint(YkGraphics graphics) {

		}
	}

	public class Text extends Ele {
		@Override
		public void paint(YkGraphics graphics) {

		}
	}

	public class Dynamic {
		int valueVer;
		String value;

		int colorVer;
		YkColor color;

		public Dynamic(String value) {
			this.value = value;
			this.valueVer = 1;
		}

		public boolean valid() {
			return this.value != null;
		}

		public YkColor color() {
			if (colorVer != valueVer) {
				color = new YkColor(value);
				colorVer = valueVer;
			}
			return color;
		}

	}

}
