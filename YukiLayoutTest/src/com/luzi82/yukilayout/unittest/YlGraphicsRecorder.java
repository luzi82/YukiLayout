package com.luzi82.yukilayout.unittest;

import java.util.LinkedList;

import com.luzi82.yukilayout.YlColor;
import com.luzi82.yukilayout.YlGraphics;

public class YlGraphicsRecorder implements YlGraphics {

	LinkedList<YlGraphicsRecorder.Record> recordList = new LinkedList<YlGraphicsRecorder.Record>();

	public YlGraphicsRecorder.Record[] getRecordAry() {
		return recordList.toArray(new YlGraphicsRecorder.Record[0]);
	}

	public class Record {

	}

	@Override
	public void text(YlColor color, String text) {
		Text t = new Text();
		t.color = color;
		t.text = text;
		recordList.addLast(t);
	}

	public class Text extends Record {

		public YlColor color;
		public String text;

	}

	public class ClipStart extends Record {
		public float x;
		public float y;
		public float w;
		public float h;
	}

	public class ClipEnd extends Record {
		public float x;
		public float y;
		public float w;
		public float h;
	}

	@Override
	public void clear(YlColor color) {
		Clear c = new Clear();
		c.color = color;
		recordList.addLast(c);
	}

	public class Clear extends Record {

		public YlColor color;

	}

	@Override
	public void push() {
		Push p = new Push();
		recordList.addLast(p);
	}

	public class Push extends Record {
	}

	@Override
	public void pop() {
		Pop p = new Pop();
		recordList.addLast(p);
	}

	public class Pop extends Record {
	}

	@Override
	public void translate(float xf, float yf) {
		Translate t = new Translate();
		t.x = xf;
		t.y = yf;
		recordList.addLast(t);
	}

	public class Translate extends Record {
		public float x;
		public float y;
	}

	@Override
	public void img(String src, float x0f, float y0f, float x1f, float y1f,
			float u0, float v0, float u1, float v1) {
		Img i = new Img();
		i.src = src;
		i.x0f = x0f;
		i.y0f = y0f;
		i.x1f = x1f;
		i.y1f = y1f;
		i.u0 = u0;
		i.v0 = v0;
		i.u1 = u1;
		i.v1 = v1;
		recordList.addLast(i);
	}

	public class Img extends Record {
		public String src;
		public float x0f, y0f, x1f, y1f;
		public float u0, v0, u1, v1;
	}

}
