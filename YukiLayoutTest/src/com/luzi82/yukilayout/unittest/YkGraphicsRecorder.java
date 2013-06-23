package com.luzi82.yukilayout.unittest;

import java.util.LinkedList;

import com.luzi82.yukilayout.YkGraphics;

public class YkGraphicsRecorder implements YkGraphics {

	LinkedList<YkGraphicsRecorder.Record> recordList = new LinkedList<YkGraphicsRecorder.Record>();

	public YkGraphicsRecorder.Record[] getRecordAry() {
		return recordList.toArray(new YkGraphicsRecorder.Record[0]);
	}

	public class Record {

	}

	public class Text extends Record {

		public int color;

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
	public void clear(int color) {
		Clear c = new Clear();
		c.color = color;
		recordList.push(c);
	}

	public class Clear extends Record {

		public int color;

	}

}
