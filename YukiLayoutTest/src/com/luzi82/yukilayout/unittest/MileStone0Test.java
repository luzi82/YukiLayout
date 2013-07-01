package com.luzi82.yukilayout.unittest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.luzi82.yukilayout.YlLayout;

public class MileStone0Test {

	@Test
	public void clear() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/clear.xml"));
		layout.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();
		YlGraphicsRecorder.Record record;

		int i = 0;
		YlGraphicsRecorder.Clear clear;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Clear);
		clear = (YlGraphicsRecorder.Clear) record;
		Assert.assertEquals(0xff7f7f7f, clear.color.argb);

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void emptyscreen() throws ParserConfigurationException,
			SAXException, IOException {
		YlLayout layout = new YlLayout(new File("res/emptyscreen.xml"));
		layout.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void var() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/var.xml"));
		layout.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;
		YlGraphicsRecorder.Record record;
		YlGraphicsRecorder.Translate translate;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(100f, translate.x);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void milestone0() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/milestone0.xml"));
		layout.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();
		YlGraphicsRecorder.Record record;

		int i = 0;
		YlGraphicsRecorder.Clear clear;
		// YlGraphicsRecorder.ClipStart clipStart;
		// YlGraphicsRecorder.ClipEnd clipEnd;
		YlGraphicsRecorder.Translate translate;
		YlGraphicsRecorder.Text text;

		record = recordAry[i++];
		Assert.assertTrue(record.getClass().getName(),
				record instanceof YlGraphicsRecorder.Clear);
		clear = (YlGraphicsRecorder.Clear) record;
		Assert.assertEquals(0xff7f7f7f, clear.color.argb);

		// record = recordAry[i++];
		// Assert.assertTrue(record instanceof YlGraphicsRecorder.ClipStart);
		// clipStart = (YlGraphicsRecorder.ClipStart) record;
		// Assert.assertEquals(0, clipStart.x);
		// Assert.assertEquals(0, clipStart.y);
		// Assert.assertEquals(800, clipStart.w);
		// Assert.assertEquals(600, clipStart.h);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(0f, translate.x);
		Assert.assertEquals(0f, translate.y);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(30, translate.x);
		Assert.assertEquals(30, translate.y);

		for (int j = 0; j < 5; ++j) {
			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Text);
			text = (YlGraphicsRecorder.Text) record;
			Assert.assertEquals(0xffffffff, text.color);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);
		}

		// record = recordAry[i++];
		// Assert.assertTrue(record instanceof YlGraphicsRecorder.ClipEnd);
		// clipEnd = (YlGraphicsRecorder.ClipEnd) record;
		// Assert.assertEquals(0, clipEnd.x);
		// Assert.assertEquals(0, clipEnd.y);
		// Assert.assertEquals(800, clipEnd.w);
		// Assert.assertEquals(600, clipEnd.h);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		Assert.assertEquals(i, recordAry.length);
	}

}
