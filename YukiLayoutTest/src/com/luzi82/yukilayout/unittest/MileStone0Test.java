package com.luzi82.yukilayout.unittest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.luzi82.yukilayout.YkLayout;

public class MileStone0Test {

	@Test
	public void clear() throws ParserConfigurationException, SAXException,
			IOException {
		YkLayout layout = new YkLayout(new File("res/clear.xml"));
		layout.setRootSize(800, 600);

		YkGraphicsRecorder graphicsRecorder = new YkGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YkGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();
		YkGraphicsRecorder.Record record;

		int i = 0;
		YkGraphicsRecorder.Clear clear;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YkGraphicsRecorder.Clear);
		clear = (YkGraphicsRecorder.Clear) record;
		Assert.assertEquals(0xff7f7f7f, clear.color);

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void emptyscreen() throws ParserConfigurationException,
			SAXException, IOException {
		YkLayout layout = new YkLayout(new File("res/emptyscreen.xml"));
		layout.setRootSize(800, 600);

		YkGraphicsRecorder graphicsRecorder = new YkGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YkGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void milestone0() throws ParserConfigurationException, SAXException,
			IOException {
		YkLayout layout = new YkLayout(new File("res/milestone0.xml"));
		layout.setRootSize(800, 600);

		YkGraphicsRecorder graphicsRecorder = new YkGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YkGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();
		YkGraphicsRecorder.Record record;

		int i = 0;
		YkGraphicsRecorder.Clear clear;
		YkGraphicsRecorder.ClipStart clipStart;
		YkGraphicsRecorder.ClipEnd clipEnd;
		YkGraphicsRecorder.Text text;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YkGraphicsRecorder.Clear);
		clear = (YkGraphicsRecorder.Clear) record;
		Assert.assertEquals(0xff7f7f7f, clear.color);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YkGraphicsRecorder.ClipStart);
		clipStart = (YkGraphicsRecorder.ClipStart) record;
		Assert.assertEquals(0, clipStart.x);
		Assert.assertEquals(0, clipStart.y);
		Assert.assertEquals(800, clipStart.w);
		Assert.assertEquals(600, clipStart.h);

		for (int j = 0; j < 5; ++j) {
			record = recordAry[i++];
			Assert.assertTrue(record instanceof YkGraphicsRecorder.Text);
			text = (YkGraphicsRecorder.Text) record;
			Assert.assertEquals(0xffffffff, text.color);
		}

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YkGraphicsRecorder.ClipEnd);
		clipEnd = (YkGraphicsRecorder.ClipEnd) record;
		Assert.assertEquals(0, clipEnd.x);
		Assert.assertEquals(0, clipEnd.y);
		Assert.assertEquals(800, clipEnd.w);
		Assert.assertEquals(600, clipEnd.h);

		Assert.assertEquals(i, recordAry.length);
	}

}
