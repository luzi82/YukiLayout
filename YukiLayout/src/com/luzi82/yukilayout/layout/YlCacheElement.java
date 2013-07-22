package com.luzi82.yukilayout.layout;

import java.util.LinkedList;

import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.YlGraphics;

public class YlCacheElement extends YlElement implements YlGraphics {

	int cacheVersion = -1;

	public YlCacheElement(YlLayout aLayout, YlElement aParent) {
		super(aLayout, aParent, true);
		setAttrDefault("version", 0);
	}

	LinkedList<GraphicsCall> graphicsCallList = new LinkedList<YlCacheElement.GraphicsCall>();

	public static interface GraphicsCall {
		public void paint(YlGraphics graphics);
	}

	public static class Clear implements GraphicsCall {
		YlColor color;

		public Clear(YlColor color) {
			this.color = color;
		}

		@Override
		public void paint(YlGraphics graphics) {
			graphics.clear(color);
		}
	}

	@Override
	public void clear(YlColor c) {
		graphicsCallList.addLast(new Clear(c));
	}

	public static class Push implements GraphicsCall {

		@Override
		public void paint(YlGraphics graphics) {
			graphics.push();
		}

	}

	@Override
	public void push() {
		graphicsCallList.addLast(new Push());
	}

	public static class Pop implements GraphicsCall {

		@Override
		public void paint(YlGraphics graphics) {
			graphics.pop();
		}

	}

	@Override
	public void pop() {
		graphicsCallList.addLast(new Pop());
	}

	public static class Translate implements GraphicsCall {

		float xf, yf;

		public Translate(float xf, float yf) {
			this.xf = xf;
			this.yf = yf;
		}

		@Override
		public void paint(YlGraphics graphics) {
			graphics.translate(xf, yf);
		}

	}

	@Override
	public void translate(float xf, float yf) {
		graphicsCallList.addLast(new Translate(xf, yf));
	}

	public static class Text implements GraphicsCall {

		String text;
		float xf, yf;
		int align;
		float fontSize;
		YlColor color;

		public Text(String text, float xf, float yf, int align, float fontSize,
				YlColor color) {
			this.text = text;
			this.xf = xf;
			this.yf = yf;
			this.align = align;
			this.fontSize = fontSize;
			this.color = color;
		}

		@Override
		public void paint(YlGraphics graphics) {
			graphics.text(text, xf, yf, align, fontSize, color);
		}

	}

	@Override
	public void text(String text, float xf, float yf, int align,
			float fontSize, YlColor color) {
		graphicsCallList
				.addLast(new Text(text, xf, yf, align, fontSize, color));
	}

	public static class Img implements GraphicsCall {

		String src;
		float x0f;
		float y0f;
		float x1f;
		float y1f;
		float u0;
		float v0;
		float u1;
		float v1;

		public Img(String src, float x0f, float y0f, float x1f, float y1f,
				float u0, float v0, float u1, float v1) {
			this.src = src;
			this.x0f = x0f;
			this.y0f = y0f;
			this.x1f = x1f;
			this.y1f = y1f;
			this.u0 = u0;
			this.v0 = v0;
			this.u1 = u1;
			this.v1 = v1;
		}

		@Override
		public void paint(YlGraphics graphics) {
			graphics.img(src, x0f, y0f, x1f, y1f, u0, v0, u1, v1);
		}
	}

	@Override
	public void img(String src, float x0f, float y0f, float x1f, float y1f,
			float u0, float v0, float u1, float v1) {
		graphicsCallList.addLast(new Img(src, x0f, y0f, x1f, y1f, u0, v0, u1,
				v1));
	}

	@Override
	public void paint(YlGraphics graphics) {
		int ver = toInt(attr("version"));
		if (cacheVersion != ver) {
			graphicsCallList.clear();
			super.paint(this);
		}
		cacheVersion = ver;
		for (GraphicsCall call : graphicsCallList) {
			call.paint(graphics);
		}
	}

}
