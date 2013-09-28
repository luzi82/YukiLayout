package com.luzi82.yukilayout.unittest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.luzi82.yukilayout.layout.YlLayout;

public class MileStone0Test {

	@Test
	public void clear() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout schema = new YlLayout(new File("res/clear.xml"), null, null);
		// schema.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		schema.paint(graphicsRecorder);

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
		YlLayout schema = new YlLayout(new File("res/emptyscreen.xml"), null, null);
		// schema.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		schema.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void translate() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout schema = new YlLayout(new File("res/translate.xml"), null, null);
		// schema.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		schema.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;
		YlGraphicsRecorder.Record record;
		YlGraphicsRecorder.Translate translate;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(123f, translate.x);
		Assert.assertEquals(456f, translate.y);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void var() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout schema = new YlLayout(new File("res/var.xml"), null, null);
		// schema.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		schema.paint(graphicsRecorder);

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
	public void math() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout schema = new YlLayout(new File("res/math.xml"), null, null);
		// schema.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		schema.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;
		YlGraphicsRecorder.Record record;
		YlGraphicsRecorder.Translate translate;

		while (i < recordAry.length) {
			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
			translate = (YlGraphicsRecorder.Translate) record;
			Assert.assertEquals(translate.y, translate.x, 0.000001);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);
		}

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void id() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout schema = new YlLayout(new File("res/id.xml"), null, null);
		// schema.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		schema.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;
		YlGraphicsRecorder.Record record;
		YlGraphicsRecorder.Translate translate;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(123f, translate.x, 0.000001);
		Assert.assertEquals(456f, translate.y, 0.000001);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		Assert.assertEquals(i, recordAry.length);
	}

	public static class RepeatDummy {
		public int[] itemlist = { 123, 456, 789 };
	}

	@Test
	public void repeat() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout schema = new YlLayout(new File("res/repeat.xml"),
				new RepeatDummy(), null);
		// schema.setRootSize(800, 600);
		// schema.setArg("itemlist", new int[] { 123, 456, 789 });

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		schema.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();

		int i = 0;
		YlGraphicsRecorder.Record record;
		YlGraphicsRecorder.Translate translate;

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(0f, translate.x, 0.0001);
		Assert.assertEquals(123f, translate.y, 0.0001);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(1f, translate.x, 0.0001);
		Assert.assertEquals(456f, translate.y, 0.0001);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
		translate = (YlGraphicsRecorder.Translate) record;
		Assert.assertEquals(2f, translate.x, 0.0001);
		Assert.assertEquals(789f, translate.y, 0.0001);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Pop);

		Assert.assertEquals(i, recordAry.length);
	}

	@Test
	public void img() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/img.xml"), null, null);
		// layout.setRootSize(800, 600);

		YlGraphicsRecorder graphicsRecorder = new YlGraphicsRecorder();
		layout.paint(graphicsRecorder);

		YlGraphicsRecorder.Record[] recordAry = graphicsRecorder.getRecordAry();
		YlGraphicsRecorder.Record record;

		int i = 0;
		YlGraphicsRecorder.Img img;

		record = recordAry[i++];
		Assert.assertTrue(record.getClass().getName(),
				record instanceof YlGraphicsRecorder.Clear);

		record = recordAry[i++];
		Assert.assertTrue(record instanceof YlGraphicsRecorder.Img);
		img = (YlGraphicsRecorder.Img) record;
		Assert.assertEquals(0f, img.x0f);
		Assert.assertEquals(800f, img.x1f, 0.00001);
		Assert.assertEquals(0f, img.y0f);
		Assert.assertEquals(600f, img.y1f, 0.00001);

		Assert.assertEquals(i, recordAry.length);
	}

	public static class UU {
		public U[] itemlist = { new U("a"), new U("b"), new U("c"), new U("d"),
				new U("e"), };
	}

	public static class U {
		public String name;

		public U(String n) {
			this.name = n;
		}
	}

	@Test
	public void milestone0() throws ParserConfigurationException, SAXException,
			IOException {
		YlLayout layout = new YlLayout(new File("res/milestone0.xml"), new UU(), null);
		// layout.setRootSize(800, 600);
		// layout.setArg("itemlist", uu);

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
		Assert.assertEquals(30f, translate.x);
		Assert.assertEquals(30f, translate.y);

		for (int j = 0; j < 5; ++j) {
			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Push);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Translate);
			translate = (YlGraphicsRecorder.Translate) record;
			Assert.assertEquals(0f, translate.x);
			Assert.assertEquals(122f * j, translate.y);

			record = recordAry[i++];
			Assert.assertTrue(record instanceof YlGraphicsRecorder.Text);
			text = (YlGraphicsRecorder.Text) record;
			Assert.assertEquals(0xffffffff, text.color.argb);
			Assert.assertEquals(4, text.align);
			Assert.assertEquals(92f, text.fontSize);

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
