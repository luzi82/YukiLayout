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

	public class Text extends Record {

		public YlColor color;

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

}
