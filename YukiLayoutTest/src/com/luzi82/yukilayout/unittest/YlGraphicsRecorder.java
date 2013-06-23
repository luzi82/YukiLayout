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
		recordList.push(c);
	}

	public class Clear extends Record {

		public YlColor color;

	}

}
